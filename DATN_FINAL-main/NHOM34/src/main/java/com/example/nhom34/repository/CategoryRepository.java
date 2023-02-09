package com.example.nhom34.repository;

import com.example.nhom34.entities.Bill;
import com.example.nhom34.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findCategoriesByStatus(Integer status);
}
