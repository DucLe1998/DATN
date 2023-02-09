package com.example.demo.dto;

public class PaymentDTO {
	private String message;
	
	private boolean isPaypal;

	public PaymentDTO(String message, boolean isPaypal) {
		super();
		this.message = message;
		this.isPaypal = isPaypal;
	}

}
