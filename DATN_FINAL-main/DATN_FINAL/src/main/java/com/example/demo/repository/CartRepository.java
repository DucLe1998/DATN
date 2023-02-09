package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Cart;
import com.example.demo.entities.ProductDetail;
import com.example.demo.entities.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	List<Cart> findAllByUser(User user);

    List<Cart> deleteByUser(User user);
    
    Cart findByProductAndUser(ProductDetail product, User user);
}
