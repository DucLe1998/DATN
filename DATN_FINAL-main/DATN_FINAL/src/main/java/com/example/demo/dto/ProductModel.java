package com.example.demo.dto;

import java.io.Serializable;

import com.example.demo.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel implements Serializable{
	private Integer id;
	private String name;
	private float price;
	private String note;
	private Category category;
}
