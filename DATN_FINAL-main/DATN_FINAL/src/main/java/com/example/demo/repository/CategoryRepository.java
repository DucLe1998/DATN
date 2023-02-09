package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	List<Category> findCategoriesByStatus(Integer status);
	public Category findByNameEquals(String name);

	Category findByName(String name);

	public List<Category> findByStatusEquals(Integer status);
}
