package com.example.demo.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Importation;
import com.example.demo.entities.ImportationDetail;
import com.example.demo.entities.Supplier;
import com.example.demo.entities.User;
import com.example.demo.repository.ImportationDetailReponsitory;
import com.example.demo.repository.ImportationRepository;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.service.ImportationService;

@Service
@Transactional
public class ImportationServiceImp implements ImportationService {
	String name;
	String address;
	String phone;
	String note;
	@Autowired
	private ImportationRepository importationRep;

	@Autowired
	private SupplierRepository supplierRepository;
	@Autowired
	private ImportationDetailReponsitory detailReponsitory;

	// Get cell value
	private static Object getCellValue(Cell cell) {
//		CellType cellType = cell.getCellType();
		CellType cellType = cell.getCellTypeEnum();
		Object cellValue = null;
		switch (cellType) {
		case BOOLEAN:
			cellValue = cell.getBooleanCellValue();
			break;
		case FORMULA:
			Workbook workbook = cell.getSheet().getWorkbook();
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			cellValue = evaluator.evaluate(cell).getNumberValue();
			break;
		case NUMERIC:
			Object o = cell.getNumericCellValue();
			cellValue = o.toString();
			break;
		case STRING:
			cellValue = cell.getStringCellValue();
			break;
		case _NONE:
		case BLANK:
		case ERROR:
			break;
		default:
			break;
		}

		return cellValue;
	}

	public List<ImportationDetail> readExcel(MultipartFile file, User user) throws IOException {
		List<ImportationDetail> listImportationDetail = new ArrayList<>();
		Importation importation = new Importation();
		// Get file
		XSSFWorkbook workBook = new XSSFWorkbook(file.getInputStream());
		XSSFSheet sheet = workBook.getSheetAt(0);

		// Get all rows
		Iterator<Row> iterator = sheet.iterator();
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			if (nextRow.getRowNum() == 0) {
				// Ignore header
				continue;
			}

			if (nextRow.getRowNum() == 1) {
				Iterator<Cell> cellIterator = nextRow.cellIterator();

				// Read cells and set value for book object
				ImportationDetail book = new ImportationDetail();
				while (cellIterator.hasNext()) {
					// Read cell
					Cell cell = cellIterator.next();
					Object cellValue = getCellValue(cell);
					if (cellValue == null || cellValue.toString().isEmpty()) {
						continue;
					}

					// Set value for book object
					int columnIndex = cell.getColumnIndex();
					switch (columnIndex) {
					case 0:
						name = (String) getCellValue(cell);
						break;
					case 1:
						address = (String) getCellValue(cell);

						break;
					case 2:
						phone = (String) getCellValue(cell);
					case 3:
						note = (String) getCellValue(cell);
					default:
						break;

					}
				}
				Supplier suppli = supplierRepository.findByProviderName(name);
				if (suppli != null) {
					importation.setSupplier(suppli);
					importation.setStaff(user);
					importation.setCreateDay(new Date());
					importation.setTotal(0);
					importation.setStatus(1); // 1:chua nhap hang
					importationRep.save(importation);
					System.out.println(name);
				} else {
					Supplier suppli1 = new Supplier();
					System.out.println(name);
					suppli1.setProviderName(name);
					suppli1.setAddress(address);
					suppli1.setPhonerNumber(phone);
					suppli1.setNote(note);
					suppli1.setStatus(1);
					supplierRepository.save(suppli1);
					importation.setSupplier(suppli1);
					importation.setStaff(user);
					importation.setCreateDay(new Date());
					importation.setTotal(0);
					importation.setStatus(1); // 1:chua nhap hang
					importationRep.save(importation);
				}
			}
			if (nextRow.getRowNum() >= 4) {
				// Get all cells
				Iterator<Cell> cellIterator = nextRow.cellIterator();

				// Read cells and set value for book object
				ImportationDetail importationDetail = new ImportationDetail();
				importationDetail.setImportation(importation);
				while (cellIterator.hasNext()) {
					// Read cell
					Cell cell = cellIterator.next();
					Object cellValue = getCellValue(cell);
					if (cellValue == null || cellValue.toString().isEmpty()) {
						continue;
					}

					// Set value for book object
					int columnIndex = cell.getColumnIndex();
					switch (columnIndex) {
					case 0:
						importationDetail.setProductName((String) getCellValue(cell));
						break;
					case 1:
						importationDetail.setUnitPrice(Double.parseDouble((String) getCellValue(cell)));
						break;
					case 2:
						importationDetail.setMaterial((String) getCellValue(cell));
						break;
					case 3:
						importationDetail.setProductImage((String) getCellValue(cell));
						break;
					case 4:
						importationDetail.setSize((String) getCellValue(cell));
						break;
					case 5:
						importationDetail.setColor((String) getCellValue(cell));
						break;
					case 6:
						importationDetail.setQuantity((int) (Double.parseDouble((String) getCellValue(cell))));
						break;
					case 7:
						importationDetail.setProductDetailImage((String) getCellValue(cell));
						break;
					case 8:
						importationDetail.setTotal((Double) getCellValue(cell));
					default:
						break;
					}

				}
				listImportationDetail.add(importationDetail);
			}

		}
		Double total = 0.0;
		for (ImportationDetail idl : listImportationDetail) {
			total += idl.getUnitPrice() * idl.getQuantity();
		}
		importation.setTotal(total);
		importationRep.save(importation);
		return listImportationDetail;
	}

//	@Override
	public List<Importation> findByNoImportAndImportImportation() {
		return importationRep.findByNoImportAndImportImportation();
	}

	public List<ImportationDetail> findByImportationId(Importation id) {
		return detailReponsitory.findByImportationId(id);
	}

	public Importation findById(Long id) {
		return importationRep.findById(id).get();
	}

	public Importation update(Importation impor) throws Exception {
		Long id = impor.getId();
		if (importationRep.findById(id).isPresent()) {
			return importationRep.save(impor);
		} else {
			throw new Exception();
		}
	}

	@Override
	public List<Importation> getAllImportation() {
		// TODO Auto-generated method stub
		return null;
	}
}
