package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AddToCart {
    private Long productId;
    private Integer quantity;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public AddToCart(Long productId, Integer quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}
	
	public AddToCart() {
		// TODO Auto-generated constructor stub
	}
}
