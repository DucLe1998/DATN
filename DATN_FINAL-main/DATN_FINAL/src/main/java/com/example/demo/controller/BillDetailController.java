package com.example.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entities.BillDetail;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.BillServiceImp;

@Controller
@RequestMapping("/api/billdetails")
public class BillDetailController {
	@Autowired
    BillServiceImp billDetailService;
	
    @Autowired
    UserRepository userRepository;

    //view bill Details
    @GetMapping("/billdetail/{billId}")
    @ResponseBody
    public List<BillDetail> billDetail(@PathVariable("billId") Long billId) {
        List<BillDetail> billDetail = billDetailService.findBillDetailByBillId(billId);
        return billDetail;
    }
}
