package com.example.demo.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ProductDetailDTO;
import com.example.demo.entities.Category;
import com.example.demo.entities.Importation;
import com.example.demo.entities.ImportationDetail;
import com.example.demo.entities.Product;
import com.example.demo.entities.ProductDetail;
import com.example.demo.entities.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ImportationDetailReponsitory;
import com.example.demo.repository.ImportationRepository;
import com.example.demo.repository.ProductDetailRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MappingProductDetailDTOService;
import com.example.demo.service.impl.ImportationServiceImp;

@Controller
public class importationController {
	@Autowired
	CategoryRepository cateRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ImportationServiceImp importationService;

	@Autowired
	ImportationDetailReponsitory importationDetailReponsitory;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductDetailRepository productDetailRepository;

	@Autowired
	SupplierRepository supplierRepository;

	@Autowired
	ImportationRepository importationRepository;

	@Autowired
	MappingProductDetailDTOService mappingproductdetaildtoRepository;
	

	// List phiếu nhập
	@RequestMapping("/admin/importation/addImportation")
	public String addImportation(Principal principal, Model model) {
		List<Importation> list = importationService.findByNoImportAndImportImportation();
		model.addAttribute("items", list);
		return "admin/importation/addImportation";
	}

	// Đọc file excel
	@PostMapping("/openExcel")
	public String excelRead(@RequestParam("file") MultipartFile excel, Principal principal) {
		try {
			User user = userRepository.findByUsernameEquals(principal.getName());
			List<ImportationDetail> abcs = importationService.readExcel(excel, user);

			for (ImportationDetail abc : abcs) {
				importationDetailReponsitory.save(abc);
//				System.out.println(abc.getTotal());
			}
			return "redirect:/admin/importation/addImportation/succes";
		} catch (IOException e) {
			e.printStackTrace();
			return "redirect:/admin/importation/addImportation/failed";
		}
	}

	@RequestMapping("/admin/importation/addImportation/succes")
	public String addImportationSucces(Principal principal, Model model) {
		List<Importation> list = importationService.findByNoImportAndImportImportation();
		model.addAttribute("items", list);
		model.addAttribute("insertSuccess", "ADD Importation thành công!");
		return "admin/importation/addImportation";
	}

	@RequestMapping("/admin/importation/addImportation/failed")
	public String addImportationFailed(Principal principal, Model model) {
		List<Importation> list = importationService.findByNoImportAndImportImportation();
		model.addAttribute("items", list);
		model.addAttribute("insertFailed", "ADD Importation thất bại!");
		return "admin/importation/addImportation";
	}

	// Nhập hàng
	@GetMapping("/importImportationDetail/{id}")
	public String importImportationDetail(Model model, @PathVariable(value = "id") Importation id, Principal principal)
			throws Exception {
		User user = userRepository.findByUsernameEquals(principal.getName());

		List<ImportationDetail> imDetailList = importationService.findByImportationId(id);
		if (cateRepository.findByName(id.getSupplier().getProviderName()) == null) {
			Category cate = new Category();
			cate.setStatus(1);
			cate.setCreateDay(new Date());
			cate.setCreatedBy(user.getId());
			cate.setModifyDay(new Date());
			cate.setModifiedBy(user.getId());
			cate.setName(id.getSupplier().getProviderName());
			cate.setNote("1");
			cateRepository.save(cate);

			for (ImportationDetail im : imDetailList) {
				Product newProduct = new Product();
				if (productRepository.findByName(im.getProductName()) == null) {
					newProduct.setCategory(cate);
					newProduct.setName(im.getProductName());
					newProduct.setNote(im.getProductImage());
					newProduct.setPrice(im.getUnitPrice());
					newProduct.setCreateDay(new Date());
					newProduct.setCreatedBy(user.getId());
					newProduct.setModifyDay(new Date());
					newProduct.setModifiedBy(user.getId());
					newProduct.setStatus(1);
					newProduct.setMaterial(im.getMaterial());
					productRepository.save(newProduct);
				}
				if (productRepository.findByName(im.getProductName()) != null) {
					newProduct = productRepository.findByName(im.getProductName());
				}
				if (productDetailRepository.findBySizeAndColorAndProduct(im.getSize(), im.getColor(),
						newProduct) == null) {
					ProductDetail proDetail = new ProductDetail();
					proDetail.setColor(im.getColor());
					proDetail.setSize(im.getSize());
					proDetail.setCreateDay(new Date());
					proDetail.setCreatedBy(user.getId());
					proDetail.setModifyDay(new Date());
					proDetail.setModifiedBy(user.getId());
					proDetail.setProduct(newProduct);
					proDetail.setQuantity(im.getQuantity());
					proDetail.setThumnail(im.getProductDetailImage());
					proDetail.setStatus(1);
					productDetailRepository.save(proDetail);
				} else {
					ProductDetail proDetail = productDetailRepository.findBySizeAndColorAndProduct(im.getSize(),
							im.getColor(), newProduct);
					proDetail.setModifyDay(new Date());
					proDetail.setModifiedBy(user.getId());
					proDetail.setQuantity(im.getQuantity() + proDetail.getQuantity());
					proDetail.setThumnail(im.getProductDetailImage());
					productDetailRepository.save(proDetail);
				}

			}

		} else {

			for (ImportationDetail im : imDetailList) {
				Product newProduct = new Product();
				if (productRepository.findByName(im.getProductName()) == null) {
					newProduct.setCategory(cateRepository.findByName(id.getSupplier().getProviderName()));
					newProduct.setName(im.getProductName());
					newProduct.setNote(im.getProductImage());
					newProduct.setPrice(im.getUnitPrice());
					newProduct.setCreateDay(new Date());
					newProduct.setCreatedBy(user.getId());
					newProduct.setModifyDay(new Date());
					newProduct.setModifiedBy(user.getId());
					newProduct.setStatus(1);
					newProduct.setMaterial(im.getMaterial());
					productRepository.save(newProduct);
				}
				if (productRepository.findByName(im.getProductName()) != null) {
					newProduct = productRepository.findByName(im.getProductName());
				}
				if (productDetailRepository.findBySizeAndColorAndProduct(im.getSize(), im.getColor(),
						newProduct) == null) {
					ProductDetail proDetail = new ProductDetail();
					proDetail.setColor(im.getColor());
					proDetail.setSize(im.getSize());
					proDetail.setCreateDay(new Date());
					proDetail.setCreatedBy(user.getId());
					proDetail.setModifyDay(new Date());
					proDetail.setModifiedBy(user.getId());
					proDetail.setProduct(newProduct);
					proDetail.setQuantity(im.getQuantity());
					proDetail.setThumnail(im.getProductDetailImage());
					proDetail.setStatus(1);
					productDetailRepository.save(proDetail);
				} else {
					ProductDetail proDetail = productDetailRepository.findBySizeAndColorAndProduct(im.getSize(),
							im.getColor(), newProduct);
					proDetail.setModifyDay(new Date());
					proDetail.setModifiedBy(user.getId());
					proDetail.setQuantity(im.getQuantity() + proDetail.getQuantity());
					proDetail.setThumnail(im.getProductDetailImage());
					productDetailRepository.save(proDetail);
				}
			}
		}
		id.setStatus(2);
		importationService.update(id);
		return "redirect:/admin/importation/addImportation/save/success";
	}

	@RequestMapping("admin/importation/addImportation/save/success")
	public String importExcelSuccessProductDetail(Model model) {
		List<ProductDetailDTO> lstProductDetail = mappingproductdetaildtoRepository.getAllProductDetailDTO();
		model.addAttribute("listProductDetail", lstProductDetail);
		model.addAttribute("insertSuccess", "Thêm sản phẩm chi tiết từ excel thành công!");
		return "admin/product/tables_productDetail";
	}

	// xem chi tiết phiếu nhập
	@GetMapping("/checkImportation/{id}")
	public String checkImportation(Model model, @PathVariable(value = "id") Importation id) {
		model.addAttribute("list", importationService.findByImportationId(id));
		model.addAttribute("idImportation", id);
		return "admin/importation/checkImportation";
	}

	// xóa phiếu nhập
	@GetMapping("/deleteImportation/{id}")
	public String deleteImportation(Model model, @PathVariable(value = "id") Long id) throws Exception {
		Importation impor = importationService.findById(id);
		impor.setStatus(0);
		importationService.update(impor);
		return "redirect:/admin/importation/addImportation";
	}

}
