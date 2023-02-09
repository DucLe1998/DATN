package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BillDTO;
import com.example.demo.entities.Bill;
import com.example.demo.entities.User;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.UserRepository;
@Service
public class MappingBillDTOService {
	@Autowired
	private BillRepository billRepository;

	@Autowired
	private UserRepository userRepository;

	public List<BillDTO> getAllBillDTO() {
		return ((List<Bill>) billRepository.findAll()).stream().map(this::convertDataIntoDTO)
				.collect(Collectors.toList());
	}

	public List<BillDTO> getBillDTOByStatus(Integer status) {
		return ((List<Bill>) billRepository.findByStatus(status)).stream().map(this::convertDataIntoDTO)
				.collect(Collectors.toList());
	}

	private BillDTO convertDataIntoDTO(Bill billData) {
		BillDTO dto = new BillDTO();
		dto.setId(billData.getId());
		Optional<User> user = userRepository.findById(billData.getStaff().getId());
		dto.setStaff(user.get().getUsername());
		Optional<User> user1 = userRepository.findById(billData.getCustomer().getId());
		dto.setCustomer(user1.get().getUsername());
		dto.setTotal(billData.getTotal());
		dto.setCreateDay(billData.getCreateDay());
		dto.setNote(billData.getNote());
		dto.setPaymentType(billData.getPaymentType());
		if (billData.getStatus() == 0) {
			dto.setStatus("Chưa duyệt");
		} else if (billData.getStatus() == 1) {
			dto.setStatus("Đã duyệt");
		} else if (billData.getStatus() == 2) {
			dto.setStatus("Thành công");
		} else {
			dto.setStatus("Trả hàng");
		}
		return dto;
	}
}
