package com.example.demo.dto;

import java.util.List;

public class CartShowDTO {
	private CartDto cartDetail;
	
	private List<ProductDiscountDTO> discounts;
	
	public CartShowDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public CartShowDTO(CartDto cartDetail, List<ProductDiscountDTO> discounts) {
		super();
		this.cartDetail = cartDetail;
		this.discounts = discounts;
	}

	public CartDto getCartDetail() {
		return cartDetail;
	}

	public void setCartDetail(CartDto cartDetail) {
		this.cartDetail = cartDetail;
	}

	public List<ProductDiscountDTO> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<ProductDiscountDTO> discounts) {
		this.discounts = discounts;
	}
	
	
}
