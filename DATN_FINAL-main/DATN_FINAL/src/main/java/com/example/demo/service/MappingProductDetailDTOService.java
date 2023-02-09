package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductDetailDTO;
import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import com.example.demo.entities.ProductDetail;
import com.example.demo.entities.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductDetailRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

@Service
public class MappingProductDetailDTOService {

	@Autowired
	ProductDetailRepository productdetailRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ProductRepository productRepository;

	public List<ProductDetailDTO> getAllProductDetailDTO() {
		return ((List<ProductDetail>) productdetailRepository.findAll()).stream().map(this::convertProductDetailDTO)
				.collect(Collectors.toList());
	}

	public List<ProductDetailDTO> getAllProductDetailByProductDTO(Long id) {
		return ((List<ProductDetail>) productdetailRepository.findByProductIdEquals(id)).stream()
				.map(this::convertProductDetailDTO).collect(Collectors.toList());
	}

	public ProductDetailDTO convertProductDetailDTO(ProductDetail productdetailData) {
		ProductDetailDTO dto = new ProductDetailDTO();
		dto.setId(productdetailData.getId());
		dto.setSize(productdetailData.getSize());
		dto.setColor(productdetailData.getColor());
		Optional<Product> product = productRepository.findById(productdetailData.getProduct().getId());
		dto.setProduct(product.get().getName());
		if (productdetailData.getSize().equals("XXS")) {
			dto.setPrice(product.get().getPrice());
		} else if (productdetailData.getSize().equals("L")) {
			Double price = product.get().getPrice() + 20000;
			dto.setPrice(price);
		} else if (productdetailData.getSize().equals("XL")) {
			Double price = product.get().getPrice() + 25000;
			dto.setPrice(price);
		} else if (productdetailData.getSize().equals("XXL")) {
			Double price = product.get().getPrice() + 30000;
			dto.setPrice(price);
		} else if (productdetailData.getSize().equals("XXXL")) {
			Double price = product.get().getPrice() + 35000;
			dto.setPrice(price);
		} else if (productdetailData.getSize().equals("M")) {
			Double price = product.get().getPrice() +15000;
			dto.setPrice(price);
		} else if (productdetailData.getSize().equals("S")) {
			Double price = product.get().getPrice() + 10000;
			dto.setPrice(price);
		} else {
			Double price = product.get().getPrice() + 5000;
			dto.setPrice(price);
		}
		Optional<Category> category = categoryRepository.findById(product.get().getCategory().getId());
		dto.setCategory(category.get().getName());
		dto.setNote(product.get().getNote());
		dto.setQuantity(productdetailData.getQuantity());
		dto.setThumnail(productdetailData.getThumnail());
		dto.setCreateDay(productdetailData.getCreateDay());
		Optional<User> user = userRepository.findById(productdetailData.getCreatedBy());
		dto.setCreatedBy(user.get().getUsername());
		dto.setModifyDay(productdetailData.getModifyDay());
		Optional<User> user1 = userRepository.findById(productdetailData.getModifiedBy());
		dto.setModifiedBy(user1.get().getUsername());
		int status = productdetailData.getStatus();
		String statusx = (status == 1) ? "Đang hoạt động" : "Không hoạt động";
		dto.setStatus(statusx);
		return dto;
	}
}
