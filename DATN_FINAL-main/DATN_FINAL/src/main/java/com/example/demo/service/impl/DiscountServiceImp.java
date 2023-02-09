package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CartDto;
import com.example.demo.dto.CartItem;
import com.example.demo.entities.Discount;
import com.example.demo.entities.ProductDetail;
import com.example.demo.entities.User;
import com.example.demo.repository.DiscountRepository;
import com.example.demo.service.DiscountService;

import java.util.List;

@Service
public class DiscountServiceImp implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Override
	public synchronized boolean reductionDiscount(Long id) {
		Discount discount = discountRepository.getById(id);
		if (discount == null) {
			return false;
		}
		if (discount.getQuantity() <= 0) {
			return false;
		}
		discount.setQuantity(discount.getQuantity() - 1);
		return true;
	}

    @Override
    public List<Discount> getAllDiscount() {
        return  discountRepository.findAll();
    }

    @Override
    public Discount getDiscountById(Long id) {
        return discountRepository.findById(id).get();
    }

    @Override
    public Discount updateDiscountById(Discount newDiscount) {

			return discountRepository.save(newDiscount);
    }

    @Override
    public Discount saveDiscount(Discount newDiscount) {
        return discountRepository.save(newDiscount);
    }
}
