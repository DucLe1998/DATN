package com.example.demo.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.ProductDetailDTO;
import com.example.demo.dto.ProductDetailShowDTO;
import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import com.example.demo.entities.ProductDetail;
import com.example.demo.entities.User;
import com.example.demo.excelexporter.ProductDetailExcelExporter;
import com.example.demo.repository.BillDetailRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductDetailRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MappingProductDTOService;
import com.example.demo.service.MappingProductDetailDTOService;
import com.example.demo.service.impl.ProductServiceImp;

@Controller
public class ProductDetailController {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductServiceImp productServiceImp;

	@Autowired
	MappingProductDTOService mappingproductdtoRepository;

	@Autowired
	MappingProductDetailDTOService mappingproductdetaildtoRepository;

	@Autowired
	ProductDetailRepository productdetailRepository;

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	BillDetailRepository billDetailRepository;

	@Autowired
	UserRepository userRepository;

	@RequestMapping("admin/product/list")
	public String listProduct(Model model) {
		List<ProductDTO> lstProduct = mappingproductdtoRepository.getAllProductDTO();
		model.addAttribute("listProduct", lstProduct);
		return "admin/product/tables_product";
	}

	@RequestMapping("admin/productdetail/list")
	public String listProductDetail(Model model) {
		List<ProductDetailDTO> lstProductDetail = mappingproductdetaildtoRepository.getAllProductDetailDTO();
		model.addAttribute("listProductDetail", lstProductDetail);
		return "admin/product/tables_productDetail";
	}

	@RequestMapping("/api/productdetail/view/{id}")
	public String viewProductDetailByProduct(@PathVariable("id") Long id, Model model) {
		List<ProductDetailDTO> lstProductDetail = mappingproductdetaildtoRepository.getAllProductDetailByProductDTO(id);
		model.addAttribute("listProductDetail", lstProductDetail);
		return "admin/product/tables_productDetail";
	}

	@RequestMapping("/api/productdetailbyid/view/{id}")
	public String viewProductDetailById(@PathVariable("id") Long id, Model model) {
		ProductDetail productDetail = productdetailRepository.getById(id);
		Product product = productRepository.getById(productDetail.getProduct().getId());
		Category category = categoryRepository.getById(product.getCategory().getId());
		User userCreatedBy = userRepository.getById(productDetail.getCreatedBy());
		User userModifiedBy = userRepository.getById(productDetail.getModifiedBy());
		model.addAttribute("userCreatedBy", userCreatedBy);
		model.addAttribute("userModifiedBy", userModifiedBy);
		model.addAttribute("category", category);
		model.addAttribute("product", product);
		model.addAttribute("productdetail", productDetail);
		return "admin/product/view";
	}

	@RequestMapping("admin/product/save")
	public String insertProduct(Product product, Model model) {
		List<Category> category = categoryRepository.findByStatusEquals(1);
		model.addAttribute("category", category);
		return "admin/product/create";
	}

	@PostMapping("/api/product/save")
	public String insertProduct(@Valid @ModelAttribute("product") Product newProduct, BindingResult result, Model model,
			Principal principal) {
		try {
			Product existingNameProduct = productRepository.findByName(newProduct.getName());
			if (existingNameProduct != null) {
				model.addAttribute("error1", "Sản phẩm này đã tồn tại!");
				return "admin/product/create";
			}
			if (result.hasErrors()) {
				return "admin/product/create";
			}
			Date dates = java.util.Calendar.getInstance().getTime();
			newProduct.setCreateDay(dates);
			newProduct.setModifyDay(dates);
			User userId = userRepository.findByUsernameEquals(principal.getName());
			newProduct.setCreatedBy(userId.getId());
			newProduct.setModifiedBy(userId.getId());
			newProduct.setStatus(1);
			productRepository.save(newProduct);
			return "redirect:/admin/product/save/success";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/admin/product/save/failed";
		}
	}

	@RequestMapping("admin/product/save/success")
	public String insertSuccessProduct(Model model) {
		List<ProductDTO> lstProduct = mappingproductdtoRepository.getAllProductDTO();
		model.addAttribute("listProduct", lstProduct);
		model.addAttribute("insertSuccess", "Thêm sản phẩm thành công!");
		return "admin/product/tables_product";
	}

	@RequestMapping("admin/product/save/failed")
	public String insertFailedProduct(Model model) {
		List<ProductDTO> lstProduct = mappingproductdtoRepository.getAllProductDTO();
		model.addAttribute("listProduct", lstProduct);
		model.addAttribute("insertFailed", "Thêm sản phẩm thất bại!");
		return "admin/product/tables_product";
	}

	@RequestMapping("/api/product/{id}")
	public String editProduct(@PathVariable("id") Long id, Model model) {
		List<Category> category = categoryRepository.findByStatusEquals(1);
		model.addAttribute("category", category);
		model.addAttribute("product", productRepository.getById(id));
		return "admin/product/update";
	}

	@RequestMapping(value = "/api/product/update/{id}", method = RequestMethod.POST)
	public String updateProduct(@Valid @ModelAttribute("product") Product newProduct, @PathVariable("id") Long id,
			Model model, Principal principal, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return "admin/product/update";
			}
			Product _productExisting = productRepository.getById(id);
			_productExisting.setPrice(newProduct.getPrice());
			_productExisting.setMaterial(newProduct.getMaterial());
			_productExisting.setCategory(newProduct.getCategory());
			Date dates = java.util.Calendar.getInstance().getTime();
			_productExisting.setModifyDay(dates);
			User userId = userRepository.findByUsernameEquals(principal.getName());
			_productExisting.setModifiedBy(userId.getId());
			_productExisting.setNote(newProduct.getNote());
			_productExisting.setStatus(newProduct.getStatus());
			productRepository.save(_productExisting);
			return "redirect:/admin/product/update/success";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/admin/product/update/failed";
		}
	}

	@RequestMapping("/api/product/delete/{id}")
	public String deleteProduct(@PathVariable("id") Long id, Model model) {
		Product _productExisting = productRepository.getById(id);
		List<ProductDetail> productDetail = productdetailRepository.findByProduct(_productExisting);
		for (ProductDetail proDetail : productDetail) {
			proDetail.setStatus(0);
			productdetailRepository.save(proDetail);
		}
		_productExisting.setStatus(0);
		productRepository.save(_productExisting);
		return "redirect:/admin/product/update/success";
	}

	@RequestMapping("admin/product/update/success")
	public String updateSuccess(Model model) {
		List<ProductDTO> lstProduct = mappingproductdtoRepository.getAllProductDTO();
		model.addAttribute("listProduct", lstProduct);
		model.addAttribute("updateSuccess", "Thay đổi sản phẩm thành công!");
		return "admin/product/tables_product";
	}

	@RequestMapping("admin/product/update/failed")
	public String updateFailedProduct(Model model) {
		List<ProductDTO> lstProduct = mappingproductdtoRepository.getAllProductDTO();
		model.addAttribute("listProduct", lstProduct);
		model.addAttribute("updateFailed", "Thay đổi sản phẩm thất bại!");
		return "admin/product/tables_product";
	}

	@RequestMapping("/api/productdetailbyid/save/{id}")
	public String insertProductDetailById(@PathVariable("id") Long id, Model model,
			@ModelAttribute("productdetail") ProductDetail ProductDetail) {
		Product product = productRepository.getById(id);
		Category category = categoryRepository.getById(product.getCategory().getId());
		model.addAttribute("category", category);
		model.addAttribute("product", product);
		return "admin/product/create_productdetail";
	}

	@RequestMapping(value = "/api/productdetail/save/{id}", method = RequestMethod.POST)
	public String insertProductDetail(@Valid @ModelAttribute("productdetail") ProductDetail newProductDetail,
			@PathVariable("id") Long id, Model model, Principal principal, BindingResult result,
			@RequestParam("size") String size) {
		try {
			if (result.hasErrors()) {
				return "admin/product/create_productdetail";
			}
			Product product = productRepository.getById(id);
			Date dates = java.util.Calendar.getInstance().getTime();
			newProductDetail.setCreateDay(dates);
			newProductDetail.setModifyDay(dates);
			User userId = userRepository.findByUsernameEquals(principal.getName());
			newProductDetail.setCreatedBy(userId.getId());
			newProductDetail.setModifiedBy(userId.getId());
			newProductDetail.setStatus(1);
			newProductDetail.setProduct(product);
			newProductDetail.setSize(size);
			productdetailRepository.save(newProductDetail);
			return "redirect:/admin/productdetail/save/success";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/admin/productdetail/save/failed";
		}
	}

	@RequestMapping("admin/productdetail/save/success")
	public String insertSuccessProductDetail(Model model) {
		List<ProductDetailDTO> lstProductDetail = mappingproductdetaildtoRepository.getAllProductDetailDTO();
		model.addAttribute("listProductDetail", lstProductDetail);
		model.addAttribute("insertSuccess", "Thêm sản phẩm chi tiết thành công!");
		return "admin/product/tables_productDetail";
	}

	@RequestMapping("admin/productdetail/save/failed")
	public String insertFailedProductDetail(Model model) {
		List<ProductDetailDTO> lstProductDetail = mappingproductdetaildtoRepository.getAllProductDetailDTO();
		model.addAttribute("listProductDetail", lstProductDetail);
		model.addAttribute("insertFailed", "Thêm sản phẩm chi tiết thất bại!");
		return "admin/product/tables_productDetail";
	}

	@RequestMapping("admin/allproductdetail/save")
	public String insertAllProductDetail(@ModelAttribute("productdetail") ProductDetail ProductDetail, Model model) {
		List<Product> product = productRepository.findByStatusEquals(1);
		model.addAttribute("product", product);
		return "admin/product/create_allproductdetail";
	}

	@PostMapping("/api/allproductdetail/save")
	public String insertAllProductDetail(@Valid @ModelAttribute("productdetail") ProductDetail newProductDetail,
			BindingResult result, Model model, Principal principal, @RequestParam("size") String size) {
		System.out.println(size);
		System.out.println(newProductDetail.getProduct());
		System.out.println(newProductDetail.getColor());
		try {
			ProductDetail existingNameProductDetail = productdetailRepository.findByProductDetailNameSizeColorEquals(
					newProductDetail.getProduct(), size, newProductDetail.getColor());
			if (existingNameProductDetail != null) {
				model.addAttribute("error1", "Sản phẩm chi tiết này đã tồn tại!");
				return "admin/product/create_allproductdetail";
			}
			if (result.hasErrors()) {
				return "admin/product/create_allproductdetail";
			}
			Date dates = java.util.Calendar.getInstance().getTime();
			newProductDetail.setCreateDay(dates);
			newProductDetail.setModifyDay(dates);
			User userId = userRepository.findByUsernameEquals(principal.getName());
			newProductDetail.setCreatedBy(userId.getId());
			newProductDetail.setModifiedBy(userId.getId());
			newProductDetail.setStatus(1);
			newProductDetail.setSize(size);
			productdetailRepository.save(newProductDetail);
			return "redirect:/admin/productdetail/save/success";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/admin/productdetail/save/failed";
		}
	}

	@RequestMapping("/api/productdetail/edit/{id}")
	public String editProductDetail(@PathVariable("id") Long id, Model model) {
		ProductDetail productdetail = productdetailRepository.getById(id);
		List<Product> product = productRepository.findByStatusEquals(1);
		model.addAttribute("product", product);
		model.addAttribute("productdetail", productdetail);
		return "admin/product/update_productDetail";
	}

	@RequestMapping(value = "/api/productdetail/update/{id}", method = RequestMethod.POST)
	public String updateProductDetail(@Valid @ModelAttribute("productdetail") ProductDetail newProductDetail,
			@PathVariable("id") Long id, Model model, Principal principal, BindingResult result,
			@RequestParam("size") String size) {
		try {
			if (result.hasErrors()) {
				return "admin/product/update_productDetail";
			}
			ProductDetail _productdetailExisting = productdetailRepository.getById(id);
			_productdetailExisting.setColor(newProductDetail.getColor());
			_productdetailExisting.setQuantity(newProductDetail.getQuantity());
			_productdetailExisting.setSize(size);
			Date dates = java.util.Calendar.getInstance().getTime();
			_productdetailExisting.setModifyDay(dates);
			User userId = userRepository.findByUsernameEquals(principal.getName());
			_productdetailExisting.setModifiedBy(userId.getId());
			_productdetailExisting.setStatus(newProductDetail.getStatus());
			productdetailRepository.save(_productdetailExisting);
			return "redirect:/admin/productdetail/update/success";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/admin/productdetail/update/failed";
		}
	}

	@RequestMapping("/api/productdetail/delete/{id}")
	public String deleteProductDetail(@PathVariable("id") Long id, Model model) {
		ProductDetail _productDeatailExisting = productdetailRepository.getById(id);
		_productDeatailExisting.setStatus(0);
		productdetailRepository.save(_productDeatailExisting);
		return "redirect:/admin/productdetail/update/success";
	}

	@RequestMapping("admin/productdetail/update/success")
	public String updateSuccessProductDetail(Model model) {
		List<ProductDetailDTO> lstProductDetail = mappingproductdetaildtoRepository.getAllProductDetailDTO();
		model.addAttribute("listProductDetail", lstProductDetail);
		model.addAttribute("updateSuccess", "Thay đổi chi tiết sản phẩm thành công!");
		return "admin/product/tables_productDetail";
	}

	@RequestMapping("admin/productdetail/update/failed")
	public String updateFailedProductDetail(Model model) {
		List<ProductDetailDTO> lstProductDetail = mappingproductdetaildtoRepository.getAllProductDetailDTO();
		model.addAttribute("listProductDetail", lstProductDetail);
		model.addAttribute("updateFailed", "Thay đổi chi tiết sản phẩm thất bại!");
		return "admin/product/tables_productDetail";
	}

	/***
	 * Export data to excel file
	 */
	@RequestMapping(value = "/admin/productdetail/excelExport", method = RequestMethod.GET)
	public ModelAndView exportToExcel() {
		ModelAndView mav = new ModelAndView();
		mav.setView(new ProductDetailExcelExporter());
		// read data from DB
		List<ProductDetailDTO> list = mappingproductdetaildtoRepository.getAllProductDetailDTO();
		// send to excelImpl class
		mav.addObject("list", list);
		return mav;
	}
	
	@GetMapping("api/productDetail/index")
	@ResponseBody
	public ProductDetailShowDTO indexDetail(@RequestParam("user") @Nullable Integer userId,
			@RequestParam("id") Long id) {
		User user = null;
		if (userId != null) {
			user = userRepository.findById(userId).get();
			System.out.println(user.getMemberType());
		}
		return productServiceImp.getProductDetail(id, user);
	}

	public String indexDetail(@RequestParam("user") Integer userId,
			@RequestParam("id") Long id) {
		Product product = productRepository.findById(id).get();
//		System.out.println(product);
		model.addAttribute("product", product);

		List<ProductDetail> listPD = productdetailRepository.findByProduct(product);
		model.addAttribute("listProductDetail", listPD);
		if (billDetailRepository.findQuantityByProduct(product) != null) {
			model.addAttribute("buyquantity", billDetailRepository.findQuantityByProduct(product));
		}
		model.addAttribute("buyquantity", 0);
		model.addAttribute("listProduct", productRepository.findByCategory(product.getCategory()));
//		System.out.println(listPD);
		return "customer/productdetail";
	}
	
	@GetMapping("api/productDetail/quantity")
	@ResponseBody
	public Integer getQuantity(@RequestParam("color") String color,
			@RequestParam("size") String size,
			@RequestParam("id") Long id) {
		return productdetailRepository.findByColorAndSizeAndProductId(color, size, id).get(0).getQuantity();
	}

}
