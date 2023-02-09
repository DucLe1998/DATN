package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;

@Service
public class CategoryServiceImp implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}

	@Override
	public Category getCategoryById(Integer id) {
		return categoryRepository.findById(id).get();
	}

	@Override
	public Category updateCategoryById(Category newCategory) {

		return categoryRepository.save(newCategory);
	}

	@Override
	public Category saveCategory(Category newCategory) {
		return categoryRepository.save(newCategory);
	}
}
