package com.example.nhom34.controller;

import com.example.nhom34.entities.Product;
import com.example.nhom34.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor
public class ProductController {
    ProductRepository productRepository;

    @GetMapping("/{status}")
    public List<Product> findAll(
            @PathVariable("status") Integer status
    ){
        return this.productRepository.findProductsByStatus(status);
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> findByCategory(
            @PathVariable("categoryId") int categoryId
    ){
        return this.productRepository.findProductsByCategoryIdAndStatus(categoryId,1);
    }
}
