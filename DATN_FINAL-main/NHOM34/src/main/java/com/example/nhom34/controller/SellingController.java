package com.example.nhom34.controller;

import com.example.nhom34.entities.*;
import com.example.nhom34.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/selling")
@AllArgsConstructor
public class SellingController {
    BillRepository billRepository;

    UserRepository userRepository;
    ProductDetailRepository productDetailRepository;

    ProductRepository productRepository;

    CategoryRepository categoryRepository;

    //Bill
    @GetMapping("")
    public List<Bill> getAll(){
        return this.billRepository.findAll();
    }

    @GetMapping("product")
    public List<Product> getAllp(){
        return this.productRepository.findAll();
    }

    //category
    @GetMapping("/category/{status}")
    public List<Category> getAll(
            @PathVariable("status") Integer status
    ){
        return this.categoryRepository.findCategoriesByStatus(status);
    }

    //product
    @GetMapping("/product/{status}")
    public List<Product> findAll(
            @PathVariable("status") Integer status
    ){
        return this.productRepository.findProductsByStatus(status);
    }

    @GetMapping("/product/category/{categoryId}")
    public List<Product> findByCategory(
            @PathVariable("categoryId") int categoryId
    ){
        return this.productRepository.findProductsByCategoryIdAndStatus(categoryId,1);
    }
    //product detail
    @GetMapping("/product-detail/color/{productId}")
    List<String> findColorByProductId(
            @PathVariable("productId") long productId
    ){
        return this.productDetailRepository.findColorByProductId(productId);
    }

    @GetMapping("/product-detail/size/{productId}/{color}")
    List<String> findSizeByProductId(
            @PathVariable("productId") long productId,
            @PathVariable("color") String color
    ){
        return this.productDetailRepository.findSizeByProductId(productId,color);
    }

    @GetMapping("/product-detail/one")
    ProductDetail findByColorAndSize(
            @RequestParam("size") String size,
            @RequestParam("color") String color
    ){
        return this.productDetailRepository.findBySizeAndColor(size, color);
    }
    //user
    @GetMapping("/user/{status}")
    public List<User> findAllUser(
            @PathVariable("status") Integer status
    ){
        return this.userRepository.findUsersByStatus(status);
    }
}
