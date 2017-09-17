package com.beatus.billlive.service;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.beatus.billlive.domain.model.ExcelData;
import com.beatus.billlive.domain.model.ExcelReport;
import com.beatus.billlive.repository.ExcelRepository;
import com.beatus.billlive.repository.data.listener.OnGetDataListener;
import com.beatus.billlive.service.exception.BillliveServiceException;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

@Service
@Component("excelService")
public class ExcelService {
	@Resource(name = "excelRepository")
	private ExcelRepository excelRepository;
	
	private ExcelReport excelReport = null;
	
	private static final Logger LOG = LoggerFactory.getLogger(ExcelService.class);

	public static Map<String, ExcelData> items = new HashMap<String, ExcelData>();
	public static Map<String, Integer> columnsids = new HashMap<String, Integer>();

	private List<ExcelReport> excelList = new ArrayList<ExcelReport>();
	
	public static void readXLSFile() throws IOException {
		InputStream ExcelFileToRead = new FileInputStream("C:/Test.xls");
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext()) {
			row = (HSSFRow) rows.next();
			Iterator cells = row.cellIterator();

			while (cells.hasNext()) {
				cell = (HSSFCell) cells.next();

				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
					LOG.debug(cell.getStringCellValue() + " ");
				} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					LOG.debug(cell.getNumericCellValue() + " ");
				} else {
					// U Can Handel Boolean, Formula, Errors
				}
			}
			LOG.debug("Exit readXLSFile");
		}

	}

	public static void writeXLSFile() throws IOException {

		String excelFileName = "C:/Test.xls";// name of excel file

		String sheetName = "Sheet1";// name of sheet

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName);

		// iterating r number of rows
		for (int r = 0; r < 5; r++) {
			HSSFRow row = sheet.createRow(r);

			// iterating c number of columns
			for (int c = 0; c < 5; c++) {
				HSSFCell cell = row.createCell(c);

				cell.setCellValue("Cell " + r + " " + c);
			}
		}

		FileOutputStream fileOut = new FileOutputStream(excelFileName);

		// write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}

	@SuppressWarnings("deprecation")
	public List<ExcelData> readExcelFile(MultipartFile file, String companyId)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
		InputStream excelFileToRead = new ByteArrayInputStream(file.getBytes());
		// XSSFWorkbook wb = new XSSFWorkbook(excelFileToRead);
		List<ExcelData> itemsData = new ArrayList<ExcelData>();
		Workbook wb = WorkbookFactory.create(excelFileToRead);
		if (wb != null) {
			Sheet sheet = wb.getSheetAt(0);
			// XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row;
			XSSFCell cell;

			Iterator<Row> rows = sheet.rowIterator();
			Boolean isColumnNameVisited = true;
			while (isColumnNameVisited) {
				row = (XSSFRow) rows.next();
				Iterator<Cell> cells = row.cellIterator();
				int i = 0;
				while (cells.hasNext()) {
					cell = (XSSFCell) cells.next();

					if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						if (cell.getStringCellValue().equalsIgnoreCase("Item Name")) {
							LOG.debug("inside Item Name");
							columnsids.put("Item Name", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("Kgs/Grams")) {
							LOG.debug("inside Kgs/Grams");
							columnsids.put("Kgs/Grams", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("Bags")) {
							LOG.debug("inside Bags");
							columnsids.put("Bags", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("Rate")) {
							LOG.debug("inside Rate");
							columnsids.put("Rate", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("Qty")) {
							LOG.debug("inside Qty");
							columnsids.put("Qty", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("Gross")) {
							LOG.debug("inside Gross");
							columnsids.put("Gross", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("VAT")) {
							LOG.debug("inside VAT");
							columnsids.put("VAT", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("Net")) {
							LOG.debug("inside Net");
							columnsids.put("Net", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("Date")) {
							LOG.debug("inside Item Name");
							columnsids.put("Item Name", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("Agent")) {
							LOG.debug("inside Kgs/Grams");
							columnsids.put("Kgs/Grams", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("City")) {
							LOG.debug("inside Bags");
							columnsids.put("Bags", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("District")) {
							LOG.debug("inside Rate");
							columnsids.put("Rate", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("State")) {
							LOG.debug("inside Qty");
							columnsids.put("Qty", i);
							isColumnNameVisited = false;
						}

					}
					i++;
				}
				LOG.debug(columnsids.toString());
			}

			while (rows.hasNext()) {
				LOG.debug("inside Second While loop");
				row = (XSSFRow) rows.next();
				LOG.debug("inside Second While loop in cells");
				String itemNameSize = null;
				String itemName = row.getCell((Integer) columnsids.get("Item Name")).getStringCellValue();
				LOG.debug("Item name is" + itemName + "test");
				LOG.debug("length : " + itemName.length());
				if (itemName.length() > 0) {
					LOG.debug("inside If in cells");
					String itemUnitsSize = String.valueOf(row.getCell((Integer) columnsids.get("Kgs/Grams")) == null
							? 0.0 : row.getCell((Integer) columnsids.get("Kgs/Grams")).getNumericCellValue());
					itemNameSize = itemName+" - "+itemUnitsSize;
					LOG.debug("itemNameSize is" + itemNameSize + "test");
					if (items.containsKey(itemNameSize)) {
						LOG.debug("In side map contains");
						ExcelData itemData = (ExcelData) items.get(itemNameSize);
						itemData.setNumberOfBags(row.getCell((Integer) columnsids.get("Bags")) == null ? 0.0
								: row.getCell((Integer) columnsids.get("Bags")).getNumericCellValue()
										+ itemData.getNumberOfBags());
						LOG.debug("Bags : ");
						if (columnsids.get("Qty") != null) {
							itemData.setQuantityOrdered(row.getCell((Integer) columnsids.get("Qty")) == null ? 0.0
									: row.getCell((Integer) columnsids.get("Qty")).getNumericCellValue()
											+ itemData.getQuantityOrdered());
							LOG.debug("Qty : ");
						}
						LOG.debug("Gross amount 1 is: " + String.valueOf(itemData.getGrossAmount()));
						LOG.debug("Gross amount : "
								+ String.valueOf(row.getCell((Integer) columnsids.get("Gross")) == null ? 0.0
										: row.getCell((Integer) columnsids.get("Gross")).getNumericCellValue())
								+ "test");
						itemData.setGrossAmount(row.getCell((Integer) columnsids.get("Gross")) == null ? 0.0
								: row.getCell((Integer) columnsids.get("Gross")).getNumericCellValue()
										+ itemData.getGrossAmount());

						LOG.debug("Gross : " + String.valueOf(itemData.getGrossAmount()));

						itemData.setVatAmount(row.getCell((Integer) columnsids.get("VAT")) == null ? 0.0
								: row.getCell((Integer) columnsids.get("VAT")).getNumericCellValue()
										+ itemData.getVatAmount());
						LOG.debug("VAT   : ");

						itemData.setNetAmount(row.getCell((Integer) columnsids.get("Net")) == null ? 0.0
								: row.getCell((Integer) columnsids.get("Net")).getNumericCellValue()
										+ itemData.getNetAmount());
						LOG.debug("Net : ");
					} else {
						LOG.debug("Inside Else");
						ExcelData itemData = new ExcelData();
						itemData.setItemName(itemName);
						LOG.debug("itemName : " + itemName);
						itemData.setUnits(itemUnitsSize);
						LOG.debug("itemUnitsSize : " + itemUnitsSize);
						itemData.setQuantityOrdered(
								row.getCell((Integer) columnsids.get("Kgs/Grams")).getNumericCellValue());
						LOG.debug("itemUnitsSize : " + itemUnitsSize);
						itemData.setNumberOfBags(row.getCell((Integer) columnsids.get("Bags")) == null ? 0.0
								: row.getCell((Integer) columnsids.get("Bags")).getNumericCellValue());
						LOG.debug("Bags : " + String.valueOf(row.getCell((Integer) columnsids.get("Bags")) == null ? 0.0
								: row.getCell((Integer) columnsids.get("Bags")).getNumericCellValue()));
						itemData.setRateOfEachBag(row.getCell((Integer) columnsids.get("Rate")) == null ? 0.0
								: row.getCell((Integer) columnsids.get("Rate")).getNumericCellValue());
						LOG.debug("Rate : " + String.valueOf(row.getCell((Integer) columnsids.get("Rate")) == null ? 0.0
								: row.getCell((Integer) columnsids.get("Rate")).getNumericCellValue()));
						if (columnsids.get("Qty") != null) {
							LOG.debug("Inside Else Before QTY");
							itemData.setQuantityOrdered(row.getCell((Integer) columnsids.get("Qty")) == null ? 0.0
									: row.getCell((Integer) columnsids.get("Qty")).getNumericCellValue());
							LOG.debug("Qty : " + String.valueOf(row.getCell((Integer) columnsids.get("Qty")) == null
									? 0.0 : row.getCell((Integer) columnsids.get("Qty")).getNumericCellValue()));
						}
						itemData.setGrossAmount(row.getCell((Integer) columnsids.get("Rate")) == null ? 0.0
								: row.getCell((Integer) columnsids.get("Rate")).getNumericCellValue());
						LOG.debug("Gross : " + String.valueOf(row.getCell((Integer) columnsids.get("Gross")) == null ? 0.0
								: row.getCell((Integer) columnsids.get("Gross")).getNumericCellValue()));
						itemData.setVatAmount(row.getCell((Integer) columnsids.get("VAT")) == null ? 0.0
								: row.getCell((Integer) columnsids.get("VAT")).getNumericCellValue());
						LOG.debug("VAT : " + String.valueOf(row.getCell((Integer) columnsids.get("VAT")) == null ? 0.0
								: row.getCell((Integer) columnsids.get("VAT")).getNumericCellValue()));
						itemData.setNetAmount(row.getCell((Integer) columnsids.get("Net")) == null ? 0.0
								: row.getCell((Integer) columnsids.get("Net")).getNumericCellValue());
						LOG.debug("Net : " + String.valueOf(row.getCell((Integer) columnsids.get("Net")) == null ? 0.0
								: row.getCell((Integer) columnsids.get("Net")).getNumericCellValue()));
						items.put(itemNameSize, itemData);
					}
				}
			}
		}
		for (Entry<String, ExcelData> entry : items.entrySet()) {
			ExcelData itemData = entry.getValue();
			String displayValue = "Rate : " + itemData.getRateOfEachBag() + " Qty : " + itemData.getQuantityOrdered()+" Gross : " + itemData.getGrossAmount()+" Tax : " + itemData.getVatAmount()+ " Net : " + itemData.getNetAmount();
			itemData.setLabel(entry.getKey());
			itemData.setDisplayValue(displayValue);
			itemData.setValue(String.valueOf(itemData.getNumberOfBags()));
			itemsData.add(itemData);		
		}
		LOG.debug(items.toString());
		
		excelReport = new ExcelReport();
		excelReport.setReportId(Utils.generateRandomKey(20));
		excelReport.setCompanyId(companyId);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = LocalDate.now();
		excelReport.setDateOfReport(dtf.format(localDate));
		excelReport.setFileName(file.getOriginalFilename());
		excelReport.setFileSize(file.getSize());
		excelReport.setExcelDataList(itemsData);
		excelRepository.addExcelData(excelReport);
		return itemsData;
	}

	@SuppressWarnings("resource")
	public static void writeXLSXFile() throws IOException {

		String excelFileName = "C:/Test2.xlsx";// name of excel file

		String sheetName = "Sheet1";// name of sheet

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName);
		int i = 0;
		int j = 0;
		XSSFRow row = sheet.createRow(i);
		for (String key : columnsids.keySet()) {
			XSSFCell cell = row.createCell(j);
			cell.setCellValue(key);
			j++;
		}

		for (String key : items.keySet()) {
			i++;
			j = 0;
			row = sheet.createRow(i);
			ExcelData item = items.get(key);
			XSSFCell cell1 = row.createCell(j);
			cell1.setCellValue(String.valueOf(item.getItemName()));
			j++;
			XSSFCell cell2 = row.createCell(j);
			cell2.setCellValue(String.valueOf(item.getNumberOfBags()));
			j++;
			XSSFCell cell3 = row.createCell(j);
			cell3.setCellValue(String.valueOf(item.getRateOfEachBag()));
			j++;
			XSSFCell cell4 = row.createCell(j);
			cell4.setCellValue(String.valueOf(item.getUnits()));
			j++;
			XSSFCell cell5 = row.createCell(j);
			cell5.setCellValue(String.valueOf(item.getQuantityOrdered()));
			j++;
			XSSFCell cell6 = row.createCell(j);
			cell6.setCellValue(String.valueOf(item.getVatAmount()));
			j++;
			XSSFCell cell7 = row.createCell(j);
			cell7.setCellValue(String.valueOf(item.getGrossAmount()));
			j++;
			XSSFCell cell8 = row.createCell(j);
			cell8.setCellValue(String.valueOf(item.getNetAmount()));
			j++;

		}

		FileOutputStream fileOut = new FileOutputStream(excelFileName);

		// write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}

	public List<ExcelData> getExcelData(String companyId,String excepReportId) {
		LOG.info("In getExcelData method of Excel Service");
		if (StringUtils.isNotBlank(excepReportId) && StringUtils.isNotBlank(companyId)) {

			excelRepository.getExcelData(companyId, excepReportId, new OnGetDataListener() {

				@Override
		        public void onStart() {
		        }

		        @Override
		        public void onSuccess(DataSnapshot billSnapshot) {
		            ExcelReport excelReport = billSnapshot.getValue(ExcelReport.class);
		            excelList.add(excelReport);
		            LOG.info(" The excel Snapshot Key is " + billSnapshot.getKey());
		        }

		        @Override
		        public void onFailed(DatabaseError databaseError) {
		        	LOG.info("Error retrieving data");
		           throw new BillliveServiceException(databaseError.getMessage());
		        }
		    });
			if (excelReport != null && !Constants.YES.equalsIgnoreCase(excelReport.getIsRemoved())) {
				return excelReport.getExcelDataList();
			} else {
				return null;
			}
		} else {
			LOG.error(
					"Billlive Service Exception in the getExcelData() {},  CompanyId or excepReportId passed cant be null or empty string");
			throw new BillliveServiceException("Company Id or excepReportId passed cant be null or empty string");
		}
	}
	}


