package com.example.demo.dto;

import com.example.demo.entities.Discount;

public class ProductDiscountDTO {
	private Discount discount;
	
	private boolean isAble;
	
	private String reason;

	public ProductDiscountDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProductDiscountDTO(Discount discount, boolean isAble, String reason) {
		super();
		this.discount = discount;
		this.isAble = isAble;
		this.reason = reason;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	public boolean isAble() {
		return isAble;
	}

	public void setAble(boolean isAble) {
		this.isAble = isAble;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
}
