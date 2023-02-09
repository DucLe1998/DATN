package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.example.demo.entities.Supplier;
import com.example.demo.excelexporter.SupplierExcelExporter;
import com.example.demo.repository.SupplierRepository;

@Controller
public class SupplierController {
	@Autowired
	SupplierRepository supplierRepository;

	@RequestMapping("admin/supplier/list")
	public String listSupplier(Model model) {
		model.addAttribute("list", supplierRepository.findAll());
		return "admin/importation/supplierList";
	}

	@RequestMapping("admin/supplier/save")
	public String insertSupplier(Supplier supplier, Model model) {
		return "admin/importation/create";
	}

	@PostMapping("/api/supplier/save")
	public String insertSupplier(@Valid @ModelAttribute("supplier") Supplier newSupplier, BindingResult result,
			Model model) {
		try {
			Supplier existingproviderName = supplierRepository.findByProviderName(newSupplier.getProviderName());
			if (existingproviderName != null) {
				model.addAttribute("error1", "Nhà phân phối đã tồn tại!");
				return "admin/importation/create";
			}
			Supplier existingPhonerNumber = supplierRepository.findByPhonerNumber(newSupplier.getPhonerNumber());
			if (existingPhonerNumber != null) {
				model.addAttribute("error2", "Số điện thoại này ở nhà phân phối đã tồn tại!");
				return "admin/importation/create";
			}
			if (result.hasErrors()) {
				return "admin/importation/create";
			}
			newSupplier.setStatus(1);
			supplierRepository.save(newSupplier);
			return "redirect:/admin/supplier/save/success";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/admin/supplier/save/failed";
		}
	}

	@RequestMapping("admin/supplier/save/success")
	public String insertSuccessSupplier(Model model) {
		model.addAttribute("list", supplierRepository.findAll());
		model.addAttribute("insertSuccess", "Thêm nhà phân phối thành công!");
		return "admin/importation/supplierList";
	}

	@RequestMapping("admin/supplier/save/failed")
	public String insertFailedSupplier(Model model) {
		model.addAttribute("list", supplierRepository.findAll());
		model.addAttribute("insertFailed", "Thêm nhà phân phối thất bại!");
		return "admin/importation/supplierList";
	}

	@RequestMapping("/api/supplier/{id}")
	public String editSupplier(@PathVariable("id") Long id, Model model) {
		model.addAttribute("supplier", supplierRepository.getById(id));
		return "admin/importation/update";
	}

	@RequestMapping(value = "/api/supplier/update/{id}", method = RequestMethod.POST)
	public String updateSupplier(@Valid @ModelAttribute("supplier") Supplier newSupplier, @PathVariable("id") Long id,
			Model model, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return "admin/importation/update";
			}
			Supplier _supplierExisting = supplierRepository.getById(id);
			_supplierExisting.setProviderName(newSupplier.getProviderName());
			_supplierExisting.setAddress(newSupplier.getAddress());
			_supplierExisting.setPhonerNumber(newSupplier.getPhonerNumber());
			_supplierExisting.setNote(newSupplier.getNote());
			_supplierExisting.setStatus(newSupplier.getStatus());
			supplierRepository.save(_supplierExisting);
			return "redirect:/admin/supplier/update/success";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/admin/supplier/update/failed";
		}
	}

	@RequestMapping("/api/supplier/delete/{id}")
	public String deleteCategory(@PathVariable("id") Long id, Model model) {
		Supplier _supplierExisting = supplierRepository.getById(id);
		_supplierExisting.setStatus(0);
		supplierRepository.save(_supplierExisting);
		return "redirect:/admin/supplier/update/success";
	}

	@RequestMapping("admin/supplier/update/success")
	public String updateSuccess(Model model) {
		model.addAttribute("list", supplierRepository.findAll());
		model.addAttribute("updateSuccess", "Thay đổi nhà phân phối thành công!");
		return "admin/importation/supplierList";
	}

	@RequestMapping("admin/supplier/update/failed")
	public String updateFailed(Model model) {
		model.addAttribute("list", supplierRepository.findAll());
		model.addAttribute("updateFailed", "Thay đổi nhà phân phối thất bại!");
		return "admin/importation/supplierList";
	}

	/***
	 * Export data to excel file
	 */
	@RequestMapping(value = "/admin/supplier/excelExport", method = RequestMethod.GET)
	public ModelAndView exportToExcel() {
		ModelAndView mav = new ModelAndView();
		mav.setView(new SupplierExcelExporter());
		// read data from DB
		List<Supplier> list = supplierRepository.findAll();
		// send to excelImpl class
		mav.addObject("list", list);
		return mav;
	}
}
