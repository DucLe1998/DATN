package com.example.demo.dto;

import com.example.demo.entities.Cart;
import com.example.demo.entities.ProductDetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartItem {
    private Long id;
    private int quantity;
    private ProductDetail product;
    private Double newPrice;
    private Double total;
    
    public CartItem() {
		// TODO Auto-generated constructor stub
	}

    public CartItem(Cart cart) {
        this.setId(cart.getId());
        this.setQuantity(cart.getQuantity());
        this.setProduct(cart.getProduct());
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public ProductDetail getProduct() {
		return product;
	}

	public void setProduct(ProductDetail product) {
		this.product = product;
	}

	public Double getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(Double newPrice) {
		this.newPrice = newPrice;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	
}
