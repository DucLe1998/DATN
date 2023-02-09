package com.example.nhom34.controller;

import com.example.nhom34.entities.Product;
import com.example.nhom34.entities.ProductDetail;
import com.example.nhom34.repository.ProductDetailRepository;
import com.example.nhom34.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/productDetail")
@AllArgsConstructor
public class ProductDetailController {

    ProductDetailRepository productDetailRepository;

    @GetMapping("color/{productId}")
    List<String> findColorByProductId(
            @PathVariable("productId") long productId
    ){
        return this.productDetailRepository.findColorByProductId(productId);
    }

    @GetMapping("size/{productId}/{color}")
    List<String> findSizeByProductId(
            @PathVariable("productId") long productId,
            @PathVariable("color") String color
            ){
        return this.productDetailRepository.findSizeByProductId(productId,color);
    }

    @GetMapping("one")
    ProductDetail findByColorAndSize(
            @RequestParam("size") String size,
            @RequestParam("color") String color
            ){
        return this.productDetailRepository.findBySizeAndColor(size, color);
    }
}
