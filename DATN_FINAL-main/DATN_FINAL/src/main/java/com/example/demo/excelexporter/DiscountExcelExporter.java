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

import com.example.demo.entities.Discount;

public class DiscountExcelExporter extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
		
		
		// define excel file name to be exported
		response.addHeader("Content-Disposition", "attachment;fileName=Discount_" + currentDateTime + ".xlsx");

		// read data provided by controller
		@SuppressWarnings("unchecked")
		List<Discount> list = (List<Discount>) model.get("list");

		// create one sheet
		Sheet sheet = workbook.createSheet("Discount");

		// create row0 as a header
		Row row0 = sheet.createRow(0);
		row0.createCell(0).setCellValue("ID");
		row0.createCell(1).setCellValue("Tên mã giảm giá");
		row0.createCell(2).setCellValue("Phần trăm giảm giá (%)");
		row0.createCell(3).setCellValue("Ngày bắt đầu");
		row0.createCell(4).setCellValue("Ngày kết thúc");
		row0.createCell(5).setCellValue("Tình trạng");
		

		// create row1 onwards from List<T>
		int rowNum = 1;
		for (Discount discount : list) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(discount.getId());
			row.createCell(1).setCellValue(discount.getDiscountName());
			row.createCell(2).setCellValue(discount.getDecreasePercent());
			row.createCell(3).setCellValue(discount.getStartDay().toString());
			row.createCell(4).setCellValue(discount.getEndDay().toString());
			row.createCell(5).setCellValue(discount.getCondition());
		}
	}
}