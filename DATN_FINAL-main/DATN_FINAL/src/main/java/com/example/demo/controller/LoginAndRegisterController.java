package com.example.demo.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.common.EmailUtils;
import com.example.demo.common.GooglePojo;
import com.example.demo.common.GoogleUtils;
import com.example.demo.common.RestFB;
import com.example.demo.entities.Email;
import com.example.demo.entities.User;
import com.example.demo.entities.UserRole;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;

@Controller
public class LoginAndRegisterController {
	@Autowired
	private GoogleUtils googleUtils;

	@Autowired
	private RestFB restFb;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private RoleRepository roleRep;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	HttpSession session;

	@RequestMapping("/login-google")
	public String loginGoogle(HttpServletRequest request) throws ClientProtocolException, IOException {
		String code = request.getParameter("code");

		if (code == null || code.isEmpty()) {
			return "redirect:/login?google=error";
		}

		String accessToken = googleUtils.getToken(code);

		GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);
		UserDetails userDetail = googleUtils.buildUser(googlePojo);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
				userDetail.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "redirect:/";
	}

	@RequestMapping("/login-facebook")
	public String loginFacebook(HttpServletRequest request) throws ClientProtocolException, IOException {
		String code = request.getParameter("code");

		if (code == null || code.isEmpty()) {
			return "redirect:/login?facebook=error";
		}
		String accessToken = restFb.getToken(code);

		com.restfb.types.User user = restFb.getUserInfo(accessToken);
		UserDetails userDetail = restFb.buildUser(user);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
				userDetail.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "redirect:/";
	}

	@RequestMapping("/403")
	public String accessDenied() {
		return "login/403";
	}

	@RequestMapping("/405")
	public String Notallowed() {
		return "login/405";
	}

	@RequestMapping("/customer/index")
	public String customer() {
		return "customer/index";
	}

	@RequestMapping("/security/login")
	public String login(User user, Model model) {
		model.addAttribute("message", "Vui l??ng ????ng nh???p!");
		return "login/loginandregister";
	}
	
	@PostMapping("rest/security/login")
	@ResponseBody
	public ResponseEntity<?> customerLogin(@RequestParam("username") String username,@RequestParam("password") String password) {
		AuthenticationManager authenticationManager = (Authentication authentication) -> authentication;
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		SecurityContextHolder.getContext().setAuthentication(null);
//		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User user = userService.findByUsernameAndStatusEquals(username, 1);
		System.out.println(password);
		String[] roles = user.getUserRoles().stream().map(er -> er.getRole().getRoleName())
				.collect(Collectors.toList()).toArray(new String[0]);

		Map<String, Object> authentication1 = new HashMap<>();
		authentication1.put("user", user);
		byte[] token = (username + ":" + user.getPassword()).getBytes();
		authentication1.put("token", "Basic " + Base64.getEncoder().encodeToString(token));
		session.setAttribute("authentication", authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);
//		AuthenticationManagerBuilder a = new AuthenticationManagerBuilder(null);
		return ResponseEntity.ok(user);
	}

	@PostMapping("/security/register")
	public String register(@Valid @ModelAttribute("user") User u, BindingResult result, Model model) {
		User existingUserName = userRepository.findByUsernameEquals(u.getUsername());
		if (existingUserName != null) {
			model.addAttribute("error1", "Username ???? t???n t???i!");
			return "login/loginandregister";
		}
		User existingEmail = userRepository.findByEmailEquals(u.getEmail());
		if (existingEmail != null) {
			model.addAttribute("error2", "Email ???? t???n t???i!");
			return "login/loginandregister";
		}
		User existingPhoneNumber = userRepository.findByPhoneNumberEquals(u.getPhoneNumber());
		if (existingPhoneNumber != null) {
			model.addAttribute("error3", "S??? ??i???n tho???i ???? t???n t???i!");
			return "login/loginandregister";
		}
		if (result.hasErrors()) {
			return "login/loginandregister";
		}
		u.setTotalSpending(BigDecimal.valueOf(0));
		u.setTbCoin(BigDecimal.valueOf(0));
		Date dates = java.util.Calendar.getInstance().getTime();
		u.setCreateDay(dates);
		u.setStatus(1);
		u.setIsMember(1);
		u.setMemberType("dong");
		u.setTotalSpending(BigDecimal.valueOf(0));
		u.setTbCoin(BigDecimal.valueOf(0));
		userRepository.save(u);
		UserRole ur = new UserRole();
		ur.setUser(u);
		ur.setRole(roleRep.findById(1).get());
		userRoleRepository.save(ur);
		return "redirect:/security/register/success";

	}

	@RequestMapping("/security/register/success")
	public String registerSuccess(User user, Model model) {
		model.addAttribute("message", "Vui l??ng ????ng nh???p!");
		model.addAttribute("message1", "????ng k?? th??nh c??ng!");
		return "login/loginandregister";
	}

	@RequestMapping("/security/login/error")
	public String loginError(User user, Model model) {
		model.addAttribute("message", "Sai th??ng tin ????ng nh???p!");
		return "login/loginandregister";
	}

	@RequestMapping("/security/logoff/success")
	public String logoffSuccess(User user, Model model) {
		model.addAttribute("message", "B???n ???? ????ng xu???t!");
		return "redirect:/";
	}

	@RequestMapping("/security/forgotPassword")
	public String pageforgotPassword(User user, Model model) {
		return "login/forgotPasswod";
	}

	@PostMapping("/forgotPassword")
	public String forgotPassword(@Valid @ModelAttribute("user") User u, BindingResult result, Model model) {
		try {
			User existingUser = userRepository.findByUsernameAndEmailEquals(u.getUsername(), u.getEmail());
			if (existingUser == null) {
				model.addAttribute("error1", "Username ho???c Email kh??ng t???n t???i!");
				return "login/forgotPasswod";
			}
			if (result.hasErrors()) {
				return "login/forgotPasswod";
			}
			Email email = new Email();
			email.setFrom("vietcuong24012001@gmail.com");
			email.setFromPassword("ovvslsxqftaikbte");
			email.setTo(u.getEmail());
			email.setSubject("Forgot Password Function");
			StringBuilder sb = new StringBuilder();
			sb.append("Dear ").append(u.getUsername()).append("<br>");
			sb.append("You are used the forgot password function. <br>");
			sb.append("Your password is <b>").append(existingUser.getPassword()).append("</b><br>");
			sb.append("Regards<br>");
			sb.append("Administrator");
			
			email.setContent(sb.toString());
			EmailUtils.send(email);		
			return "redirect:/security/forgotPassword/succes";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/security/forgotPassword/failed";
		}

	}
	
	@RequestMapping("/security/forgotPassword/succes")
	public String pageforgotPasswordSucces(User user, Model model) {
		model.addAttribute("insertSuccess", "H??y ki???m tra email c???a b???n, m???t kh???u ???? ???????c g???i th??nh c??ng!");
		return "login/forgotPasswod";
	}
	
	@RequestMapping("/security/forgotPassword/failed")
	public String pageforgotPasswordFailed(User user, Model model) {
		model.addAttribute("updateFailed", "G???i email th???t b???i");
		return "login/forgotPasswod";
	}

	@CrossOrigin("*")
	@ResponseBody
	@RequestMapping("/rest/security/authentication")
	public Object getAuthentication(HttpSession session) {
		return session.getAttribute("authentication");
	}
}
