package com.example.nhom34.controller;

import com.example.nhom34.entities.Category;
import com.example.nhom34.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {

    CategoryRepository categoryRepository;

    @GetMapping("/{status}")
    public List<Category> getAll(
            @PathVariable("status") Integer status
    ){
        return this.categoryRepository.findCategoriesByStatus(status);
    }
}
