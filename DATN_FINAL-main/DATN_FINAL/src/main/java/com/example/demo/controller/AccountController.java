package com.example.demo.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.AccountDTO;
import com.example.demo.entities.User;
import com.example.demo.entities.UserRole;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.AccountDTOService;
import com.example.demo.service.UserService;

@Controller
public class AccountController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	AccountDTOService accountService;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private RoleRepository roleRep;

	@RequestMapping("admin/account/list")
	public String listAccount(Model model) {
		List<AccountDTO> lstAccount = accountService.getAllAccountDTO();
		model.addAttribute("listAccount", lstAccount);
		return "admin/account/tables";
	}

	@RequestMapping("/api/account/view/{id}")
	public String viewAccount(@PathVariable("id") Integer id, Model model) {
		Optional<UserRole> userRole = userRoleRepository.findById(id);
		model.addAttribute("role", userRole.get().getRole().getRoleName());
		model.addAttribute("account", userRepository.getById(id));
		return "admin/account/view";
	}

	@RequestMapping("admin/account/save")
	public String insertAccount(User user, Model model) {
		return "admin/account/create";
	}

	@PostMapping("/api/account/save")
	public String insertAccount(@Valid @ModelAttribute("user") User newAccount, BindingResult result, Model model,
			@RequestParam("role") String role) {
		try {
			User existingUserName = userRepository.findByUsernameEquals(newAccount.getUsername());
			if (existingUserName != null) {
				model.addAttribute("error1", "Username đã tồn tại!");
				return "admin/account/create";
			}
			if (result.hasErrors()) {
				return "admin/account/create";
			}
			Date dates = java.util.Calendar.getInstance().getTime();
			newAccount.setCreateDay(dates);
			newAccount.setFirstName("FirstName");
			newAccount.setLastName("LastName");
			newAccount.setEmail("email@gmail.com");
			newAccount.setPhoneNumber("sodienthoai");
			newAccount.setStatus(1);
			newAccount.setGender(1);
			newAccount.setIsMember(1);
			newAccount.setTotalSpending(BigDecimal.valueOf(0));
			newAccount.setTbCoin(BigDecimal.valueOf(0));
			if (role.equals("1")) {
				newAccount.setMemberType("admin");
				userRepository.save(newAccount);
				UserRole ur = new UserRole();
				ur.setUser(newAccount);
				ur.setRole(roleRep.findById(2).get());
				userRoleRepository.save(ur);
			} else {
				newAccount.setMemberType("staff");
				userRepository.save(newAccount);
				UserRole ur = new UserRole();
				ur.setUser(newAccount);
				ur.setRole(roleRep.findById(3).get());
				userRoleRepository.save(ur);
			}
			return "redirect:/admin/account/save/success";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/admin/account/save/failed";
		}
	}

	@RequestMapping("admin/account/save/success")
	public String insertSuccessAccount(Model model) {
		List<AccountDTO> lstAccount = accountService.getAllAccountDTO();
		model.addAttribute("listAccount", lstAccount);
		model.addAttribute("insertSuccess", "Thêm tài khoản thành công!");
		return "admin/account/tables";
	}

	@RequestMapping("admin/account/save/failed")
	public String insertFailedAccount(Model model) {
		List<AccountDTO> lstAccount = accountService.getAllAccountDTO();
		model.addAttribute("listAccount", lstAccount);
		model.addAttribute("insertFailed", "Thêm tài khoản thất bại!");
		return "admin/account/tables";
	}

	@RequestMapping("/api/account/{id}")
	public String editAccount(@PathVariable("id") Integer id, Model model) {
		Optional<UserRole> userRole = userRoleRepository.findById(id);
		model.addAttribute("role", userRole.get().getRole().getId());
		model.addAttribute("account", userRepository.getById(id));
		return "admin/account/update";
	}

	@RequestMapping(value = "/api/account/update/{id}", method = RequestMethod.POST)
	public String updateAccount(@ModelAttribute("account") User newAccount, @PathVariable("id") Integer id, Model model,
			BindingResult result, @RequestParam("role") String role) {
		try {
			if (result.hasErrors()) {
				return "admin/account/update";
			}
			User _accountExisting = userRepository.getById(id);
			_accountExisting.setStatus(newAccount.getStatus());
			Date dates = java.util.Calendar.getInstance().getTime();
			_accountExisting.setCreateDay(dates);
			userRepository.save(_accountExisting);
			if (role.equals("1")) {
				UserRole ur = userRoleRepository.getById(id);
				ur.setRole(roleRep.findById(2).get());
				userRoleRepository.save(ur);
			} else if (role.equals("2")) {
				UserRole ur = userRoleRepository.getById(id);
				ur.setRole(roleRep.findById(3).get());
				userRoleRepository.save(ur);
			} else {
				UserRole ur = userRoleRepository.getById(id);
				ur.setRole(roleRep.findById(1).get());
				userRoleRepository.save(ur);
			}
			return "redirect:/admin/account/update/success";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/admin/account/update/failed";
		}
	}

	@RequestMapping("/api/account/delete/{id}")
	public String deleteAccount(@PathVariable("id") Integer id, Model model) {
		User _accountExisting = userRepository.getById(id);
		_accountExisting.setStatus(0);
		userRepository.save(_accountExisting);
		return "redirect:/admin/account/update/success";
	}

	@RequestMapping("admin/account/update/success")
	public String updateSuccess(Model model) {
		List<AccountDTO> lstAccount = accountService.getAllAccountDTO();
		model.addAttribute("listAccount", lstAccount);
		model.addAttribute("updateSuccess", "Thay đổi tài khoản thành công!");
		return "admin/account/tables";
	}

	@RequestMapping("admin/account/update/failed")
	public String updateFailed(Model model) {
		List<AccountDTO> lstAccount = accountService.getAllAccountDTO();
		model.addAttribute("listAccount", lstAccount);
		model.addAttribute("updateFailed", "Thay đổi tài khoản thất bại!");
		return "admin/account/tables";
	}

	@RequestMapping("/admin/api/account/{id}")
	public String editAccountAdmin(@PathVariable("id") Integer id, Model model, Principal principal) {
		User userId = userRepository.findByUsernameEquals(principal.getName());
		model.addAttribute("accountadmin", userRepository.getById(userId.getId()));
		return "admin/account/sessionupdate";
	}

	@RequestMapping(value = "/admin/api/account/update/{id}", method = RequestMethod.POST)
	public String updateAccountAdmin(@ModelAttribute("accountadmin") User newAccount, @PathVariable("id") Integer id,
			Model model, BindingResult result) {
		User existingUserName = userRepository.findByUsernameEquals(newAccount.getUsername());
		if (existingUserName != null) {
			model.addAttribute("error1", "Username đã tồn tại!");
			return "admin/account/sessionupdate";
		}
		User existingEmail = userRepository.findByEmailEquals(newAccount.getEmail());
		if (existingEmail != null) {
			model.addAttribute("error2", "Email đã tồn tại!");
			return "admin/account/sessionupdate";
		}
		User existingPhoneNumber = userRepository.findByPhoneNumberEquals(newAccount.getPhoneNumber());
		if (existingPhoneNumber != null) {
			model.addAttribute("error3", "Số điện thoại đã tồn tại!");
			return "admin/account/sessionupdate";
		}
		if (result.hasErrors()) {
			return "admin/account/sessionupdate";
		}
		model.addAttribute("provineCity", newAccount.getProvineCity());
		model.addAttribute("district", newAccount.getDistrict());
		model.addAttribute("ward", newAccount.getWard());
		User _accountExisting = userRepository.getById(id);
		_accountExisting.setFirstName(newAccount.getFirstName());
		_accountExisting.setLastName(newAccount.getLastName());
		_accountExisting.setUsername(newAccount.getUsername());
		_accountExisting.setPassword(newAccount.getPassword());
		_accountExisting.setEmail(newAccount.getEmail());
		_accountExisting.setAvatar(newAccount.getAvatar());
		_accountExisting.setGender(newAccount.getGender());
		_accountExisting.setPhoneNumber(newAccount.getPhoneNumber());
		_accountExisting.setProvineCity(newAccount.getProvineCity());
		_accountExisting.setDistrict(newAccount.getDistrict());
		_accountExisting.setWard(newAccount.getWard());
		_accountExisting.setAddress(newAccount.getAddress());
		Date dates = java.util.Calendar.getInstance().getTime();
		_accountExisting.setCreateDay(dates);
		userRepository.save(_accountExisting);
		return "redirect:/admin/accountadmin/update/success";
	}

	@RequestMapping("admin/accountadmin/update/success")
	public String updateAdminSuccess(Model model) {
		return "admin/account/trangchu";
	}

}
