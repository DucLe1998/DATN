package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.ProductHomeDTO;
import com.example.demo.dto.ProductListDTO;
import com.example.demo.dto.ProductShow;
import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductDetailRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.ProductServiceImp;

@Controller
public class HomeController {

	@Autowired
	private ProductServiceImp productServiceImp;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository cateRepository;
	
	@Autowired
	private ProductDetailRepository productDetailRepo;

	@GetMapping(value = "api/home/index",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ProductHomeDTO home(@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "24") Integer size,
			@RequestParam(name = "user") @Nullable Integer userId) {
		User user = null;
		if (userId != null) {
			user = userRepository.findById(userId).get();
		}
		List<Category> categories = cateRepository.findAll();
		Pageable pageable = PageRequest.of(0, 20);
		Pageable pageable2 = PageRequest.of(page, size);
		Page<ProductShow> allProducts = productServiceImp.getPageProduct(productServiceImp.getAllProduct(), pageable2, user);
		List<ProductShow> topBuy = productServiceImp.getPageProduct(productServiceImp.getProductByBuyQuantity(), pageable, user).getContent();
		List<ProductShow> topRating = productServiceImp.getPageProduct(productServiceImp.getProductByRating(), pageable, user).getContent();
		List<ProductShow> topLike = productServiceImp.getPageProduct(productServiceImp.getProductByLike(), pageable, user).getContent();
		List<ProductShow> discountProduct = productServiceImp.getPageProduct(productServiceImp.getAllProductByDiscount(), pageable, user).getContent();
		List<ProductShow> userLike = new ArrayList<>();
		if (user != null) {
			userLike = productServiceImp.getPageProduct(productServiceImp.getAllProductByUserLike(user), pageable, user).getContent();
		}else {
			userLike = null;
		}

		return new ProductHomeDTO(categories, allProducts, topBuy, topRating, topLike, discountProduct, userLike);
	}

	@RequestMapping({ "/admin", "/admin/home/index" })
	public String admin() {
		return "redirect:/assets/admin/index.html";
	}

	@RequestMapping({ "/products" })
	public String products() {
		return "customer/product";
	}

	@RequestMapping({ "/history" })
	public String history() {
		return "customer/history";
	}

	@RequestMapping({ "/details" })
	public String detail() {
		return "customer/chitiet-sanpham";
	}

	@RequestMapping({ "/contact" })
	public String contact() {
		return "customer/lienhe";
	}

	@RequestMapping({ "/detail-news" })
	public String detailnews() {
		return "customer/detail-news";
	}

	@RequestMapping({ "/tintuc" })
	public String tintuc() {
		return "customer/tintuc";
	}

	@RequestMapping({ "/carts" })
	public String cart() {
		return "customer/cart";
	}

	@RequestMapping({ "/productdetail" })
	public String prodetail() {
		return "customer/productdetail";
	}
	
	@GetMapping({ "/api/home/search" })
	@ResponseBody
	public ProductListDTO search(@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "24") Integer size,
			@RequestParam(name = "user") @Nullable Integer userId,@RequestParam(name = "keyword") String keyword) {
		User user = null;
		if (userId != null) {
			user = userRepository.findById(userId).get();
		}
		List<Product> products =productServiceImp.getAllProduct();
		List<String> strings = new ArrayList<>();
		List<Product> searchProducts = new ArrayList<>();
		for (Product product : products) {
			if (product.getStatus().intValue()==1) {
				strings.add(product.toString());
			}
			
			
		}
		for (String string : strings) {
			if (string.toLowerCase().contains(keyword.toLowerCase())) {
				searchProducts.add(products.get(strings.indexOf(string)));
			}
		}
		List<Category> list = cateRepository.findAll();

		Pageable pageable = PageRequest.of(page, size);
		Page<ProductShow> data = productServiceImp.getPageProduct(searchProducts,pageable, user);
		
		Set<String> colors = new LinkedHashSet<String>(productDetailRepo.findAllColor());
		
		return new ProductListDTO(data, list, colors);
	}
}
