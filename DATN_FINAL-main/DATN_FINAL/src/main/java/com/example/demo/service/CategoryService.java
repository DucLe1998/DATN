package com.example.demo.service;

import java.util.List;

import com.example.demo.entities.Category;

public interface CategoryService {
	List<Category> getAllCategory();

	Category getCategoryById(Integer id);

	Category updateCategoryById(Category newCategory);

	Category saveCategory(Category newCategory);
}
