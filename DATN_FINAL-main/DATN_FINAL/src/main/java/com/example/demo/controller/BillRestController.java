package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Bill;
import com.example.demo.service.impl.BillServiceImp;

@RestController
@RequestMapping(path = "/api")
public class BillRestController {
	@Autowired
	private BillServiceImp billServiceImp;

//	@Autowired
//	private UserRepository userRepository;

//    @PostMapping("/order")
//    public boolean placeOrder(){
//    	List<UserRole> list = new ArrayList<UserRole>();
//    	UserRole u = new UserRole();
//    	list.add(u);
//        User user = userRepository.save(new User(1, "Vinh", "Nguyen", "vinh123", "vinhnn1997@gmail.com", "123456", "1.jpg", true, "0345401309", 1,list));
//        // place the order
//        billServiceImp.saveBill(user);
//        return true;
//    }

	@GetMapping("/bill/historyAdmin")
	public List<Bill> historyOrdeAdmin() {
		return billServiceImp.getAllBill();
	}

	@GetMapping("/order/{id}")
	public Bill getOrderById(@PathVariable("id") Long id) {
		return billServiceImp.getBillById(id);
	}

	@GetMapping("")
	List<Bill> getAllApartments() {
		return billServiceImp.getAllBill();
	}

	@GetMapping("/{id}")
	Bill findById(@PathVariable Long id) {
		return billServiceImp.getBillById(id);
	}

}
