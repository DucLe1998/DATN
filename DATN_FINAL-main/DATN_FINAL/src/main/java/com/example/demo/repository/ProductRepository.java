package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Category;
import com.example.demo.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findProductsByStatus(Integer status);

	List<Product> findProductsByCategoryIdAndStatus(int category_id, Integer status);
	@Query("SELECT p FROM Product p WHERE p.name = :name")
	Product findByName(@Param("name") String name);

	public List<Product> findByStatusEquals(Integer status);
	
	List<Product> findByCategory(Category category);
	
	List<Product> findByCategoryIdAndStatus(Integer id,Integer status);
	
	List<Product> findByMaterial(String material);
	
	List<Product> findAllByOrderByPriceDesc();
	
	List<Product> findAllByOrderByPrice();
	
	List<Product> findAllByOrderByNameDesc();
	
	List<Product> findAllByOrderByName();
	
	List<Product> findByPriceBetween(Double start, Double end);
	
	List<Product> findAllByOrderByCreateDayDesc();
	
	List<Product> findAllByOrderByCreateDay();
	
	Page<Product> findByCategoryId(Long id, Pageable pageable);
}
