package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entities.User;
import com.example.demo.excelexporter.CustomerExcelExporter;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.UserService;

@Controller
public class CustomerController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private RoleRepository roleRep;

	@RequestMapping("admin/customer/list")
	public String listCustomer(Model model) {
		List<User> lstCustomer = userRepository.getCustomers();
		model.addAttribute("listCustomer", lstCustomer);
		return "admin/customer/tables";
	}

	@RequestMapping("/api/customer/view/{id}")
	public String viewCustomer(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("user", userService.getCustomerById(id));
		return "admin/customer/view";
	}

	@RequestMapping("/api/customer/{id}")
	public String editCustomer(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("user", userService.getCustomerById(id));
		return "admin/customer/update";
	}

	@RequestMapping(value = "/api/customer/update/{id}", method = RequestMethod.POST)
	public String updateCustomer(@ModelAttribute("user") User newUser, @PathVariable("id") Integer id, Model model,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return "admin/customer/update";
			}
			model.addAttribute("provineCity", newUser.getProvineCity());
			model.addAttribute("district", newUser.getDistrict());
			model.addAttribute("ward", newUser.getWard());
			User _userExisting = userService.getCustomerById(id);
			_userExisting.setFirstName(newUser.getFirstName());
			_userExisting.setLastName(newUser.getLastName());
			_userExisting.setEmail(newUser.getEmail());
			_userExisting.setAvatar(newUser.getAvatar());
			_userExisting.setGender(newUser.getGender());
			_userExisting.setPhoneNumber(newUser.getPhoneNumber());
			_userExisting.setProvineCity(newUser.getProvineCity());
			_userExisting.setDistrict(newUser.getDistrict());
			_userExisting.setWard(newUser.getWard());
			_userExisting.setAddress(newUser.getAddress());
			_userExisting.setTotalSpending(newUser.getTotalSpending());
			_userExisting.setTbCoin(newUser.getTbCoin());
			Date dates = java.util.Calendar.getInstance().getTime();
			_userExisting.setCreateDay(dates);
			_userExisting.setTbCoin(newUser.getTbCoin());
			int total = newUser.getTotalSpending().intValue();
			if (total <= 1000000) {
				_userExisting.setMemberType("dong");
			} else if (total <= 3000000) {
				_userExisting.setMemberType("bac");
			} else if (total <= 6000000) {
				_userExisting.setMemberType("vang");
			} else {
				_userExisting.setMemberType("kimcuong");
			}
			_userExisting.setStatus(newUser.getStatus());
			userService.updateCustomerById(_userExisting);
			return "redirect:/admin/customer/update/success";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/admin/customer/update/failed";
		}
	}

	@RequestMapping("/api/customer/delete/{id}")
	public String deleteCustomer(@PathVariable("id") Integer id, Model model) {
		User _userExisting = userService.getCustomerById(id);
		_userExisting.setStatus(0);
		userService.updateCustomerById(_userExisting);
		return "redirect:/admin/customer/update/success";
	}

	@RequestMapping("admin/customer/update/success")
	public String updateSuccess(Model model) {
		List<User> lstCustomer = userRepository.getCustomers();
		model.addAttribute("listCustomer", lstCustomer);
		model.addAttribute("updateSuccess", "Thay đổi khách hàng thành công!");
		return "admin/customer/tables";
	}

	@RequestMapping("admin/customer/update/failed")
	public String updateFailed(Model model) {
		List<User> lstCustomer = userRepository.getCustomers();
		model.addAttribute("listCustomer", lstCustomer);
		model.addAttribute("updateFailed", "Thay đổi khách hàng thất bại!");
		return "admin/customer/tables";
	}

	/***
	 * Export data to excel file
	 */
	@RequestMapping(value = "/admin/customer/excelExport", method = RequestMethod.GET)
	public ModelAndView exportToExcel() {
		ModelAndView mav = new ModelAndView();
		mav.setView(new CustomerExcelExporter());
		// read data from DB
		List<User> list = userRepository.getCustomers();
		// send to excelImpl class
		mav.addObject("list", list);
		return mav;
	}

}
