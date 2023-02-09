package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entities.Bill;
import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CartService;
import com.example.demo.service.impl.BillServiceImp;

@Controller
public class BillController {
	@Autowired
	BillServiceImp billService;
	
	@Autowired
	UserRepository userRepository;
	 
	@Autowired
	    private CartService cartService;
	 
	@RequestMapping("/home")
	public String home() {
		return "customer/shop";
	}
	
	@RequestMapping("/bill/list")
	public String list(Model model) {
		List<Bill> list = billService.getAllBill();
		model.addAttribute("items", list);
		return "admin/bill/tables";
	}
	
	@RequestMapping("/billadd")
	 public String addBill(Principal principal) {
	    User user = userRepository.findByUsernameEquals(principal.getName());
//		billService.saveBill(user,"Thanh toán khi nhận hàng");
		return "redirect:/bill/list";
	}

	@RequestMapping("/bill/history")
	public String getBuyHistory(Model model, Principal principal) {
		if(principal==null) {
    		return "redirect:/security/login";
    	}
    	User user = userRepository.findByUsernameEquals(principal.getName());
		List<Bill> list = billService.findBillByUserId(user.getId());
		model.addAttribute("items", list);
		return "customer/history";
	}
	@RequestMapping("/bill/cancel/{id}")
	public String cancelBill(Model model, Principal principal,@PathVariable Long id) {
		if(principal==null) {
    		return "redirect:/security/login";
    	}
    	billService.cancelBill(id,3);
		return "redirect:/bill/history";
	}
}
