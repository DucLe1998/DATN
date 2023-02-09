package com.example.demo.dto;

import java.io.Serializable;

import com.example.demo.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailModel implements Serializable{
	private String size;
	private String color;
	private int quantity;
	private String thumnail;
	private Product product;
}
