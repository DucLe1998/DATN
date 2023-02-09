package com.example.demo.dto;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.example.demo.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductListDTO {
	private Page<ProductShow> proPage;
	
	private List<Category> categories;
	
	private Set<String> color;

	public ProductListDTO(Page<ProductShow> proPage, List<Category> categories, Set<String> color) {
		this.proPage = proPage;
		this.categories = categories;
		this.color = color;
	}

	public Page<ProductShow> getProPage() {
		return proPage;
	}

	public void setProPage(Page<ProductShow> proPage) {
		this.proPage = proPage;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Set<String> getColor() {
		return color;
	}

	public void setColor(Set<String> color) {
		this.color = color;
	}
	
	
}
