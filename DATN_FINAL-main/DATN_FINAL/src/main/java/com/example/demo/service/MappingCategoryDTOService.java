package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entities.Category;
import com.example.demo.entities.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.UserRepository;

@Service
public class MappingCategoryDTOService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	CategoryService categoryService;

	@Autowired
	private UserRepository userRepository;

	public List<CategoryDTO> getAllCategoryDTO() {
		return ((List<Category>) categoryRepository.findAll()).stream().map(this::convertDataIntoDTO)
				.collect(Collectors.toList());
	}

	private CategoryDTO convertDataIntoDTO(Category categoryData) {
		CategoryDTO dto = new CategoryDTO();
		dto.setId(categoryData.getId());
		dto.setName(categoryData.getName());
		dto.setCreateDay(categoryData.getCreateDay());
		Optional<User> user = userRepository.findById(categoryData.getCreatedBy());
		dto.setCreatedBy(user.get().getUsername());
		dto.setModifyDay(categoryData.getModifyDay());
		Optional<User> user1 = userRepository.findById(categoryData.getModifiedBy());
		dto.setModifiedBy(user1.get().getUsername());
		dto.setNote(categoryData.getNote());
		int status = categoryData.getStatus();
		String statusx = (status == 1) ? "Đang hoạt động" : "Không hoạt động";
		dto.setStatus(statusx);
		return dto;
	}
}
