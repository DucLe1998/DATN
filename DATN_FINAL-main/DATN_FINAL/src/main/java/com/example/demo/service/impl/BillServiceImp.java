package com.example.demo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CartDto;
import com.example.demo.dto.CartItem;
import com.example.demo.dto.CartShowDTO;
import com.example.demo.entities.Bill;
import com.example.demo.entities.BillDetail;
import com.example.demo.entities.User;
import com.example.demo.repository.BillDetailRepository;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BillService;


@Service
@Transactional
public class BillServiceImp implements BillService {
	@Autowired
	private BillRepository billRepository;

	@Autowired
	private CartServiceImp cartService;

	@Autowired
	private ProductServiceImp productService;

	@Autowired
	private BillDetailRepository billDetailRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<Bill> getAllBill() {
		return billRepository.findAll();
	}

	@Override
	public Bill saveBill(User user, String paymentType,Double decrease) {
		User user2 = userRepository.getById(1);
		CartDto cartDto = cartService.listCartItem(user);
		List<CartItem> cartItemList = cartDto.getCartItems();
		// create the bill and save it
		boolean state = productService.reductionQuantity(user);
		if (state==false) {
			return null;
		}
		Bill bill = new Bill();
		bill.setCustomer(user);
		bill.setStaff(user2);
		if (decrease == null) {
			bill.setTotal(cartDto.getTotalCost());
		}else {
			bill.setTotal(cartDto.getTotalCost() - decrease);
		}
		bill.setCreateDay(new Date());
		bill.setPaymentType(paymentType);
		bill.setNote("");
		bill.setStatus(1);
		billRepository.save(bill);
		System.out.println(bill);
		for (CartItem cartItem : cartItemList) {
			// create billdetail and save each one
			BillDetail billDetail = new BillDetail();
			billDetail.setProduct(cartItem.getProduct());
			billDetail.setUnitPrice(cartItem.getNewPrice());
			billDetail.setQuantity(cartItem.getQuantity());
			billDetail.setBill(bill);
			billDetail.setTotal(cartItem.getQuantity() * billDetail.getUnitPrice());
			billDetailRepository.save(billDetail);
		}
		cartService.deleteUserCartItems(user);
		return bill;
	}

	@Override
	public String removeBillById(Long id) {
		billRepository.deleteById(id);
		return "bill remove" + id;
	}

	@Override
	public Bill getBillById(Long id) {
		return billRepository.findById(id).get();
	}

	@Override
	public List<Bill> findBillByUserId(Integer userId) {
		List<Bill> billList = billRepository.findBillByUserId(userId);
		return billList;
	}

	public List<BillDetail> findBillDetailByBillId(Long id) {
		return billDetailRepository.findByBillId(id);
	}

	@Override
	public boolean cancelBill(Long billId, Integer status) {
		Optional<Bill> foundBill = billRepository.findById(billId);
		if (foundBill.isPresent()) {
			foundBill.get().setDeleteDay(new Date());
			foundBill.get().setStatus(status);
			billRepository.save(foundBill.get());
			return true;
		}
		return false;
	}

}
