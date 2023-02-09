package com.example.demo.dto;

import com.example.demo.entities.Cart;

public class UpdateCartDTO {
	private CartItem item;
	
	private boolean success;
	
	private String message;

	public UpdateCartDTO(CartItem item, boolean success, String message) {
		super();
		this.item = item;
		this.success = success;
		this.message = message;
	}

	public CartItem getItem() {
		return item;
	}

	public void setItem(CartItem item) {
		this.item = item;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
