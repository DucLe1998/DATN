package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

@Service
public class MappingProductDTOService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CategoryRepository categoryRepository;

	public List<ProductDTO> getAllProductDTO() {
		return ((List<Product>) productRepository.findAll()).stream().map(this::convertProductDTO)
				.collect(Collectors.toList());
	}

	public ProductDTO convertProductDTO(Product productData) {
		ProductDTO dto = new ProductDTO();
		dto.setId(productData.getId());
		dto.setName(productData.getName());
		Optional<Category> category = categoryRepository.findById(productData.getCategory().getId());
		dto.setCategory(category.get().getName());
		dto.setMaterial(productData.getMaterial());
		dto.setPrice(productData.getPrice());
		dto.setCreateDay(productData.getCreateDay());
		Optional<User> user = userRepository.findById(productData.getCreatedBy());
		dto.setCreatedBy(user.get().getUsername());
		dto.setModifyDay(productData.getModifyDay());
		Optional<User> user1 = userRepository.findById(productData.getModifiedBy());
		dto.setModifiedBy(user1.get().getUsername());
		dto.setNote(productData.getNote());
		int status = productData.getStatus();
		String statusx = (status == 1) ? "Đang hoạt động" : "Không hoạt động";
		dto.setStatus(statusx);
		return dto;
	}
}
