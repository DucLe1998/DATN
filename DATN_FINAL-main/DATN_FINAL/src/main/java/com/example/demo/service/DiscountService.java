package com.example.demo.service;

import java.util.List;

import com.example.demo.entities.Discount;

public interface DiscountService {
	List<Discount> getAllDiscount();

	Discount getDiscountById(Long id);

	Discount updateDiscountById(Discount newDiscount);

	Discount saveDiscount(Discount newDiscount);

	boolean reductionDiscount(Long id);
}
