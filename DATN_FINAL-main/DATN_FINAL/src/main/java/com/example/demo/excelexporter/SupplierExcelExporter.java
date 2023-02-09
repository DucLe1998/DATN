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

import com.example.demo.entities.Supplier;

public class SupplierExcelExporter extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		// define excel file name to be exported
		response.addHeader("Content-Disposition", "attachment;fileName=Supplier_" + currentDateTime + ".xlsx");

		// read data provided by controller
		@SuppressWarnings("unchecked")
		List<Supplier> list = (List<Supplier>) model.get("list");

		// create one sheet
		Sheet sheet = workbook.createSheet("Supplier");

		// create row0 as a header
		Row row0 = sheet.createRow(0);
		row0.createCell(0).setCellValue("ID");
		row0.createCell(1).setCellValue("Nhà phân phối");
		row0.createCell(2).setCellValue("Địa chỉ");
		row0.createCell(3).setCellValue("Số điện thoại");
		row0.createCell(4).setCellValue("Chú ý");
		row0.createCell(5).setCellValue("Trạng thái");

		// create row1 onwards from List<T>
		int rowNum = 1;
		for (Supplier supplier : list) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(supplier.getId());
			row.createCell(1).setCellValue(supplier.getProviderName());
			row.createCell(2).setCellValue(supplier.getAddress());
			row.createCell(3).setCellValue(supplier.getPhonerNumber());
			row.createCell(4).setCellValue(supplier.getNote());
			row.createCell(5).setCellValue(supplier.getStatus() == 1 ? "Đang hoạt động" : "Không hoạt động");
		}
	}
}