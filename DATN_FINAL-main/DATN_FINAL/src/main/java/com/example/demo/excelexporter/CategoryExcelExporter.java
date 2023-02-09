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

import com.example.demo.dto.CategoryDTO;

public class CategoryExcelExporter extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		// define excel file name to be exported
		response.addHeader("Content-Disposition", "attachment;fileName=Category_" + currentDateTime + ".xlsx");

		// read data provided by controller
		@SuppressWarnings("unchecked")
		List<CategoryDTO> list = (List<CategoryDTO>) model.get("list");

		// create one sheet
		Sheet sheet = workbook.createSheet("Category");

		// create row0 as a header
		Row row0 = sheet.createRow(0);
		row0.createCell(0).setCellValue("ID");
		row0.createCell(1).setCellValue("Tên danh mục");
		row0.createCell(2).setCellValue("Ngày tạo");
		row0.createCell(3).setCellValue("Người tạo");
		row0.createCell(4).setCellValue("Ngày sửa");
		row0.createCell(5).setCellValue("Người sửa");
		row0.createCell(6).setCellValue("Chú ý");
		row0.createCell(7).setCellValue("Trạng thái");

		// create row1 onwards from List<T>
		int rowNum = 1;
		for (CategoryDTO category : list) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(category.getId());
			row.createCell(1).setCellValue(category.getName());
			row.createCell(2).setCellValue(category.getCreateDay().toString());
			row.createCell(3).setCellValue(category.getCreatedBy());
			row.createCell(4).setCellValue(category.getModifyDay().toString());
			row.createCell(5).setCellValue(category.getModifiedBy());
			row.createCell(6).setCellValue(category.getNote());
			row.createCell(7).setCellValue(category.getStatus());
		}
	}
}