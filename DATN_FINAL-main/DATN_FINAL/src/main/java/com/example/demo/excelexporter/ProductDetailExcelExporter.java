package com.example.demo.excelexporter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.example.demo.dto.ProductDetailDTO;

public class ProductDetailExcelExporter extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		// define excel file name to be exported
		response.addHeader("Content-Disposition", "attachment;fileName=ProductDetail_" + currentDateTime + ".xlsx");

		// read data provided by controller
		@SuppressWarnings("unchecked")
		List<ProductDetailDTO> list = (List<ProductDetailDTO>) model.get("list");

		// create one sheet
		Sheet sheet = workbook.createSheet("ProductDetail");

		// create row0 as a header
		Row row0 = sheet.createRow(0);
		row0.createCell(0).setCellValue("ID");
		row0.createCell(1).setCellValue("Sản phẩm");
		row0.createCell(2).setCellValue("Danh mục");
		row0.createCell(3).setCellValue("Size");
		row0.createCell(4).setCellValue("Màu");
		row0.createCell(5).setCellValue("Số lượng");
		row0.createCell(6).setCellValue("Người tạo");
		row0.createCell(7).setCellValue("Ngày tạo");
		row0.createCell(8).setCellValue("Người sửa");
		row0.createCell(9).setCellValue("Ngày sửa");
		row0.createCell(10).setCellValue("Ảnh");
		row0.createCell(11).setCellValue("Chú ý");
		row0.createCell(12).setCellValue("Trạng thái");

		// create row1 onwards from List<T>
		int rowNum = 1;
		for (ProductDetailDTO productDetail : list) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(productDetail.getId());
			row.createCell(1).setCellValue(productDetail.getProduct());
			row.createCell(2).setCellValue(productDetail.getCategory());
			row.createCell(3).setCellValue(productDetail.getSize());
			row.createCell(4).setCellValue(productDetail.getColor());
			row.createCell(5).setCellValue(productDetail.getQuantity());
			row.createCell(6).setCellValue(productDetail.getCreatedBy());
			row.createCell(7).setCellValue(productDetail.getCreateDay().toString());
			row.createCell(8).setCellValue(productDetail.getModifiedBy());
			row.createCell(9).setCellValue(productDetail.getModifyDay().toString());
			row.createCell(10).setCellValue(productDetail.getThumnail());
			row.createCell(11).setCellValue(productDetail.getNote());
			row.createCell(12).setCellValue(productDetail.getStatus());
		}
	}
}