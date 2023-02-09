package com.example.demo.service;

import java.util.List;

import com.example.demo.entities.BillDetail;

public interface BillDetailService {
	 List<BillDetail> getAllBillDetail();

	    BillDetail updateBillDetail(BillDetail billDetail);

	    BillDetail saveBillDetail(BillDetail billDetail);

	    BillDetail getBillDetailById(Long id);

	    List<BillDetail> findBillDetailByBillId(Long id);

	    long totalMoney(Long billId);

}
