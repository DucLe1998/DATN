package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BillDetailDTO;
import com.example.demo.entities.BillDetail;
import com.example.demo.repository.BillDetailRepository;

@Service
public class MappingBillDetailDTOService {
	@Autowired
	private BillDetailRepository billDetailRepository;

	public List<BillDetailDTO> getAllBillDetailByBillDTO(Long id) {
		return ((List<BillDetail>) billDetailRepository.findByBillId(id)).stream().map(this::convertBillDetailDTO)
				.collect(Collectors.toList());
	}

	public BillDetailDTO convertBillDetailDTO(BillDetail billdetailData) {
		BillDetailDTO dto = new BillDetailDTO();
		dto.setId(billdetailData.getId());
		dto.setBill(billdetailData.getBill().getId());
		dto.setProduct(billdetailData.getProduct().getProduct().getName());
		dto.setQuantity(billdetailData.getQuantity());
		dto.setUnitPrice(billdetailData.getUnitPrice());
		dto.setTotal(billdetailData.getTotal());
		dto.setSize(billdetailData.getProduct().getSize());
		dto.setColor(billdetailData.getProduct().getColor());
		dto.setThumnail(billdetailData.getProduct().getThumnail());
		return dto;
	}
}
