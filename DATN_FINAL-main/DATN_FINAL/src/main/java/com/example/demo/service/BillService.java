package com.example.demo.service;

import java.util.List;

import com.example.demo.entities.Bill;
import com.example.demo.entities.User;


public interface BillService {
	List<Bill> getAllBill();

    Bill saveBill(User user, String paymentType, Double decrease);

    String removeBillById(Long id);

    Bill getBillById(Long id);

    List<Bill> findBillByUserId(Integer userId);
    
    boolean cancelBill(Long billId, Integer status);
}
