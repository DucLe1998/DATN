package com.example.demo.config;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.service.UserService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	BCryptPasswordEncoder pe;

	@Autowired
	UserService userService;

	@Autowired
	HttpSession session;

	// Cung cấp nguồn dữ liệu đăng nhập
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> {
			try {
				com.example.demo.entities.User user = userService.findByUsernameAndStatusEquals(username, 1);
				String password = pe.encode(user.getPassword());
				System.out.println(password);
				String[] roles = user.getUserRoles().stream().map(er -> er.getRole().getRoleName())
						.collect(Collectors.toList()).toArray(new String[0]);

				Map<String, Object> authentication = new HashMap<>();
				authentication.put("user", user);
				byte[] token = (username + ":" + user.getPassword()).getBytes();
				authentication.put("token", "Basic " + Base64.getEncoder().encodeToString(token));
				session.setAttribute("authentication", authentication);

				return User.withUsername(username).password(password).roles(roles).build();
			} catch (NoSuchElementException e) {
				throw new UsernameNotFoundException(username + " not found!");
			}
		});
	}

	// Phân quyền sử dụng
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/customer/**").authenticated().antMatchers("/admin/**")
				.hasAnyRole("Directors", "Staffs").antMatchers("/...").hasRole("Directors").anyRequest().permitAll();

		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		http.formLogin().loginPage("/security/login").loginProcessingUrl("/security/login")
				.defaultSuccessUrl("/", false).failureUrl("/security/login/error").usernameParameter("username")
				.passwordParameter("password");
		http.rememberMe().tokenValiditySeconds(86400);

		http.exceptionHandling().accessDeniedPage("/403");

		http.logout().logoutUrl("/security/logoff").logoutSuccessUrl("/security/logoff/success");

	}

	// Cơ chế mã hóa mật khẩu
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Cho phép truy xuất REST API từ domain khác
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}
}
