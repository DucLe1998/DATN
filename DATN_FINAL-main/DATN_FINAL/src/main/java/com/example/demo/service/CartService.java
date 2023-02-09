package com.example.demo.service;

import com.example.demo.dto.AddToCart;
import com.example.demo.dto.CartDto;
import com.example.demo.dto.CartShowDTO;
import com.example.demo.entities.Cart;
import com.example.demo.entities.ProductDetail;
import com.example.demo.entities.User;

public interface CartService {
	 Cart addToCart(AddToCart addToCart, ProductDetail product, User user);

	 CartShowDTO listCartShow(User user);
	 
	 CartDto listCartItem(User user);

	 Cart updateCart(AddToCart addToCart, Long id);


	 void removeCartItem(Long id, Long userId);

	 void deleteUserCartItems(User user);
}
