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

import com.example.demo.entities.User;

public class CustomerExcelExporter extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		// define excel file name to be exported
		response.addHeader("Content-Disposition", "attachment;fileName=Customer_" + currentDateTime + ".xlsx");

		// read data provided by controller
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>) model.get("list");

		// create one sheet
		Sheet sheet = workbook.createSheet("Customer");

		// create row0 as a header
		Row row0 = sheet.createRow(0);
		row0.createCell(0).setCellValue("ID");
		row0.createCell(1).setCellValue("Họ và tên");
		row0.createCell(2).setCellValue("Username");
		row0.createCell(4).setCellValue("Email");
		row0.createCell(5).setCellValue("Avatar");
		row0.createCell(6).setCellValue("Giới tính");
		row0.createCell(7).setCellValue("Số điện thoại");
		row0.createCell(8).setCellValue("Địa chỉ");
		row0.createCell(9).setCellValue("Tổng chi tiêu");
		row0.createCell(10).setCellValue("tb_coin");
		row0.createCell(11).setCellValue("Ngày tạo");
		row0.createCell(12).setCellValue("Thành viên");
		row0.createCell(13).setCellValue("Loại thành viên");
		row0.createCell(14).setCellValue("Trạng thái");

		// create row1 onwards from List<T>
		int rowNum = 1;
		for (User customer : list) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(customer.getId());
			row.createCell(1).setCellValue(customer.getFirstName() + customer.getLastName());
			row.createCell(2).setCellValue(customer.getUsername());
			row.createCell(4).setCellValue(customer.getEmail());
			row.createCell(5).setCellValue(customer.getAvatar());
			row.createCell(6).setCellValue(customer.getGender() == 1 ? "Nam" : "Nữ");
			row.createCell(7).setCellValue(customer.getPhoneNumber());
			row.createCell(8).setCellValue(
					customer.getAddress() + customer.getWard() + customer.getDistrict() + customer.getProvineCity());
			row.createCell(9).setCellValue(customer.getTotalSpending().doubleValue());
			row.createCell(10).setCellValue(customer.getTbCoin().doubleValue());
			row.createCell(11).setCellValue(customer.getCreateDay().toString());
			row.createCell(12).setCellValue(customer.getIsMember() == 1 ? "Thành viên" : "Không thành viên");
			if (customer.getMemberType().equals("dong")) {
				row.createCell(13).setCellValue("Đồng");
			} else if (customer.getMemberType().equals("bac")) {
				row.createCell(13).setCellValue("Bạc");
			} else if (customer.getMemberType().equals("vang")) {
				row.createCell(13).setCellValue("Vàng");
			} else {
				row.createCell(13).setCellValue("Kim cương");
			}
			row.createCell(14).setCellValue(customer.getStatus() == 1 ? "Đang hoạt động" : "Không hoạt động");
		}
	}
}
