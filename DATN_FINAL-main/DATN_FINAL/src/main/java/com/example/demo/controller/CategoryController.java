package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entities.Category;
import com.example.demo.entities.User;
import com.example.demo.excelexporter.CategoryExcelExporter;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.service.MappingCategoryDTOService;

@Controller
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	MappingCategoryDTOService mappingcategorydtoRepository;

	@Autowired
	UserRepository userRepository;

	@RequestMapping("admin/category/list")
	public String listCategory(Model model) {
		List<CategoryDTO> lstCategory = mappingcategorydtoRepository.getAllCategoryDTO();
		model.addAttribute("listCategory", lstCategory);
		return "admin/category/tables";
	}

	@RequestMapping("/api/category/view/{id}")
	public String viewCategory(@PathVariable("id") Integer id, Model model) {
		Category category = categoryService.getCategoryById(id);
		User userCreatedBy = userRepository.getById(category.getCreatedBy());
		User userModifiedBy = userRepository.getById(category.getModifiedBy());
		model.addAttribute("userCreatedBy", userCreatedBy);
		model.addAttribute("userModifiedBy", userModifiedBy);
		model.addAttribute("category", category);
		return "admin/category/view";
	}

	@RequestMapping("admin/category/save")
	public String insertCategory(Category category, Model model) {
		return "admin/category/create";
	}

	@PostMapping("/api/category/save")
	public String insertCategory(@Valid @ModelAttribute("category") Category newCategory, BindingResult result,
			Model model, Principal principal) {
		try {
			Category existingName = categoryRepository.findByNameEquals(newCategory.getName());
			if (existingName != null) {
				model.addAttribute("error1", "Tên danh mục đã tồn tại!");
				return "admin/category/create";
			}
			if (result.hasErrors()) {
				return "admin/category/create";
			}
			Date dates = java.util.Calendar.getInstance().getTime();
			newCategory.setCreateDay(dates);
			newCategory.setModifyDay(dates);
			User userId = userRepository.findByUsernameEquals(principal.getName());
			newCategory.setCreatedBy(userId.getId());
			newCategory.setModifiedBy(userId.getId());
			newCategory.setStatus(1);
			categoryService.saveCategory(newCategory);
			return "redirect:/admin/category/save/success";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/admin/category/save/failed";
		}
	}

	@RequestMapping("admin/category/save/success")
	public String insertSuccessCategory(Model model) {
		List<CategoryDTO> lstCategory = mappingcategorydtoRepository.getAllCategoryDTO();
		model.addAttribute("listCategory", lstCategory);
		model.addAttribute("insertSuccess", "Thêm tên danh mục thành công!");
		return "admin/category/tables";
	}

	@RequestMapping("admin/category/save/failed")
	public String insertFailedCategory(Model model) {
		List<CategoryDTO> lstCategory = mappingcategorydtoRepository.getAllCategoryDTO();
		model.addAttribute("listCategory", lstCategory);
		model.addAttribute("insertFailed", "Thêm tên danh mục thất bại!");
		return "admin/category/tables";
	}

	@RequestMapping("/api/category/{id}")
	public String editCategory(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("category", categoryService.getCategoryById(id));
		return "admin/category/update";
	}

	@RequestMapping(value = "/api/category/update/{id}", method = RequestMethod.POST)
	public String updateCategory(@Valid @ModelAttribute("category") Category newCategory, @PathVariable("id") Integer id,
			Model model, Principal principal, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return "admin/category/update";
			}
			Category _categoryExisting = categoryService.getCategoryById(id);
			_categoryExisting.setName(newCategory.getName());
			Date dates = java.util.Calendar.getInstance().getTime();
			_categoryExisting.setModifyDay(dates);
			User userId = userRepository.findByUsernameEquals(principal.getName());
			_categoryExisting.setModifiedBy(userId.getId());
			_categoryExisting.setNote(newCategory.getNote());
			_categoryExisting.setStatus(newCategory.getStatus());
			categoryService.updateCategoryById(_categoryExisting);
			return "redirect:/admin/category/update/success";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/admin/category/update/failed";
		}
	}

	@RequestMapping("/api/category/delete/{id}")
	public String deleteCategory(@PathVariable("id") Integer id, Model model) {
		Category _categoryExisting = categoryService.getCategoryById(id);
		_categoryExisting.setStatus(0);
		categoryService.updateCategoryById(_categoryExisting);
		return "redirect:/admin/category/update/success";
	}

	@RequestMapping("admin/category/update/success")
	public String updateSuccess(Model model) {
		List<CategoryDTO> lstCategory = mappingcategorydtoRepository.getAllCategoryDTO();
		model.addAttribute("listCategory", lstCategory);
		model.addAttribute("updateSuccess", "Thay đổi danh mục thành công!");
		return "admin/category/tables";
	}
	
	@RequestMapping("admin/category/update/failed")
	public String updateFailed(Model model) {
		List<CategoryDTO> lstCategory = mappingcategorydtoRepository.getAllCategoryDTO();
		model.addAttribute("listCategory", lstCategory);
		model.addAttribute("updateFailed", "Thay đổi danh mục thất bại!");
		return "admin/category/tables";
	}

	/***
	 * Export data to excel file
	 */
	@RequestMapping(value = "/admin/category/excelExport", method = RequestMethod.GET)
	public ModelAndView exportToExcel() {
		ModelAndView mav = new ModelAndView();
		mav.setView(new CategoryExcelExporter());
		// read data from DB
		List<CategoryDTO> list = mappingcategorydtoRepository.getAllCategoryDTO();
		// send to excelImpl class
		mav.addObject("list", list);
		return mav;
	}
}
