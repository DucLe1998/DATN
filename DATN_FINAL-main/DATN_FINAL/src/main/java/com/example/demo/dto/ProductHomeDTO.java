package com.example.demo.dto;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.example.demo.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class ProductHomeDTO {
	private List<Category> categories;
	
	private List<ProductShow> allProducts;
	
	private List<ProductShow> topBuy;
	
	private List<ProductShow> topRating;
	
	private List<ProductShow> topLike;
	
	private List<ProductShow> discountProduct;
	
	private List<ProductShow> userLike;

	public ProductHomeDTO(List<Category> categories, List<ProductShow> allProducts, List<ProductShow> topBuy,
			List<ProductShow> topRating, List<ProductShow> topLike, List<ProductShow> discountProduct,
			List<ProductShow> userLike) {
<<<<<<< HEAD
=======
		super();
>>>>>>> 35b31b0f3530a1aca3db6c66a20490c83f77ed7b
		this.categories = categories;
		this.allProducts = allProducts;
		this.topBuy = topBuy;
		this.topRating = topRating;
		this.topLike = topLike;
		this.discountProduct = discountProduct;
		this.userLike = userLike;
	}

	@Override
	public String toString() {
		return "ProductHomeDTO [categories=" + categories + ", allProducts=" + allProducts + ", topBuy=" + topBuy
				+ ", topRating=" + topRating + ", topLike=" + topLike + ", discountProduct=" + discountProduct
				+ ", userLike=" + userLike + "]";
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<ProductShow> getAllProducts() {
		return allProducts;
	}

	public void setAllProducts(List<ProductShow> allProducts) {
		this.allProducts = allProducts;
	}

	public List<ProductShow> getTopBuy() {
		return topBuy;
	}

	public void setTopBuy(List<ProductShow> topBuy) {
		this.topBuy = topBuy;
	}

	public List<ProductShow> getTopRating() {
		return topRating;
	}

	public void setTopRating(List<ProductShow> topRating) {
		this.topRating = topRating;
	}

	public List<ProductShow> getTopLike() {
		return topLike;
	}

	public void setTopLike(List<ProductShow> topLike) {
		this.topLike = topLike;
	}

	public List<ProductShow> getDiscountProduct() {
		return discountProduct;
	}

	public void setDiscountProduct(List<ProductShow> discountProduct) {
		this.discountProduct = discountProduct;
	}

	public List<ProductShow> getUserLike() {
		return userLike;
	}

	public void setUserLike(List<ProductShow> userLike) {
		this.userLike = userLike;
	}
	
	
}
