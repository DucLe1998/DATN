package com.example.nhom34.repository;

import com.example.nhom34.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findProductsByStatus(Integer status);

    List<Product> findProductsByCategoryIdAndStatus(int category_id, Integer status);
}
