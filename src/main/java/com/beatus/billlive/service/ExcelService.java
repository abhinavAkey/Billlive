package com.beatus.billlive.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

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
import com.beatus.billlive.domain.model.ExcelPieChart;
import com.beatus.billlive.domain.model.ExcelReport;
import com.beatus.billlive.repository.ExcelRepository;
import com.beatus.billlive.repository.data.listener.OnGetDataListener;
import com.beatus.billlive.service.exception.BillliveServiceException;
import com.beatus.billlive.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

@Service
@Component("excelService")
public class ExcelService {
	@Resource(name = "excelRepository")
	private ExcelRepository excelRepository;

	private ExcelReport excelReport = null;
	private ExcelData excelData = null;

	private static final Logger LOG = LoggerFactory.getLogger(ExcelService.class);

	public static Map<String, ExcelData> items = new HashMap<String, ExcelData>();
	public static Map<String, Integer> columnsids = new HashMap<String, Integer>();

	private List<ExcelReport> excelList = new ArrayList<ExcelReport>();
	private List<ExcelData> excelDataList = new ArrayList<ExcelData>();


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
			throws IOException, EncryptedDocumentException, InvalidFormatException, ParseException {
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
							LOG.debug("inside Date");
							columnsids.put("Date", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("Agent")) {
							LOG.debug("inside Agent");
							columnsids.put("Agent", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("City")) {
							LOG.debug("inside City");
							columnsids.put("City", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("District")) {
							LOG.debug("inside District");
							columnsids.put("District", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("State")) {
							LOG.debug("inside State");
							columnsids.put("State", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("warehouse")) {
							LOG.debug("inside warehouse");
							columnsids.put("warehouse", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("Entered by")) {
							System.out.println("inside Entered by");
							columnsids.put("Entered by", i);
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
					itemNameSize = itemName + " - " + itemUnitsSize;
					LOG.debug("itemNameSize is" + itemNameSize + "test");
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
						LOG.debug("Qty : " + String.valueOf(row.getCell((Integer) columnsids.get("Qty")) == null ? 0.0
								: row.getCell((Integer) columnsids.get("Qty")).getNumericCellValue()));
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
					StringTokenizer st = new StringTokenizer(
							String.valueOf(row.getCell((Integer) columnsids.get("Entered by")) == null ? ""
									: row.getCell((Integer) columnsids.get("Entered by")).getStringCellValue()),
							" ");
					if (st.hasMoreTokens()) {
						// System.out.println(st.nextToken());
						Date date = new SimpleDateFormat("dd-mm-yyyy").parse(st.nextToken());
						System.out.println("Date : " + date.toString());
						itemData.setDate(date);
						itemData.setMonth(date.getMonth());
						itemData.setYear(date.getYear()+ 1900);
						itemData.setDay(date.getDay());
					}

					/*
					 * SimpleDateFormat formatter = new
					 * SimpleDateFormat("dd-MM-yyyy");
					 * System.out.println("Date : " +
					 * String.valueOf(row.getCell((Integer)
					 * columnsids.get("Date")) == null ? "" :
					 * row.getCell((Integer)
					 * columnsids.get("Date")).getNumericCellValue()));
					 * SimpleDateFormat format = new
					 * SimpleDateFormat("dd-MM-yy"); Date d =
					 * format.parse("01-01-2017"); long milliseconds =
					 * d.getTime(); System.out.println("Date 2017 : "
					 * +String.valueOf(milliseconds)); Double itemDouble =
					 * (Double) (row.getCell((Integer) columnsids.get("Date"))
					 * == null ? "" : row.getCell((Integer)
					 * columnsids.get("Date")).getNumericCellValue()); long
					 * itemLong = (long) (itemDouble * 100000) + milliseconds;
					 * 
					 * Date date = new Date(itemLong); String itemDateStr = new
					 * SimpleDateFormat("dd-MM-yyyy").format(date);
					 * System.out.println("Date : " +itemDateStr); Date date =
					 * formatter.parse((row.getCell((Integer)
					 * columnsids.get("Date")) == null ? "" :
					 * row.getCell((Integer)
					 * columnsids.get("Date")).getNumericCellValue()));
					 */
					/*
					 * itemData.setDate(date);
					 * itemData.setMonth(date.getMonth());
					 * itemData.setYear(date.getYear());
					 * itemData.setDay(date.getDay());
					 */
					/*
					 * itemData.setCity(row.getCell((Integer)
					 * columnsids.get("City")) == null ? "Other" :
					 * row.getCell((Integer)
					 * columnsids.get("City")).getStringCellValue());
					 * System.out.println("City : " +
					 * String.valueOf(row.getCell((Integer)
					 * columnsids.get("City")) == null ? 0.0 :
					 * row.getCell((Integer)
					 * columnsids.get("City")).getStringCellValue()));
					 * 
					 * itemData.setDistrict(row.getCell((Integer)
					 * columnsids.get("District")) == null ? "Other" :
					 * row.getCell((Integer)
					 * columnsids.get("District")).getStringCellValue());
					 * System.out.println("District : " +
					 * String.valueOf(row.getCell((Integer)
					 * columnsids.get("District")) == null ? 0.0 :
					 * row.getCell((Integer)
					 * columnsids.get("District")).getStringCellValue()));
					 * 
					 * itemData.setState(row.getCell((Integer)
					 * columnsids.get("State")) == null ? "Other" :
					 * row.getCell((Integer)
					 * columnsids.get("State")).getStringCellValue());
					 * System.out.println("State : " +
					 * String.valueOf(row.getCell((Integer)
					 * columnsids.get("State")) == null ? 0.0 :
					 * row.getCell((Integer)
					 * columnsids.get("State")).getStringCellValue()));
					 */
					itemData.setCity(row.getCell((Integer) columnsids.get("City")) == null || "None"
							.equalsIgnoreCase(row.getCell((Integer) columnsids.get("City")).getStringCellValue())
									? "Other" : row.getCell((Integer) columnsids.get("City")).getStringCellValue());
					System.out.println("City : " + String
							.valueOf(row.getCell((Integer) columnsids.get("City")) == null || "None".equalsIgnoreCase(
									row.getCell((Integer) columnsids.get("City")).getStringCellValue()) ? "Other"
											: row.getCell((Integer) columnsids.get("City")).getStringCellValue()));

					itemData.setDistrict(row.getCell((Integer) columnsids.get("District")) == null || "None"
							.equalsIgnoreCase(row.getCell((Integer) columnsids.get("District")).getStringCellValue())
									? "Other" : row.getCell((Integer) columnsids.get("District")).getStringCellValue());
					System.out.println("District : " + String
							.valueOf(row.getCell((Integer) columnsids.get("City")) == null || "None".equalsIgnoreCase(
									row.getCell((Integer) columnsids.get("City")).getStringCellValue()) ? "Other"
											: row.getCell((Integer) columnsids.get("District")).getStringCellValue()));

					itemData.setState(row.getCell((Integer) columnsids.get("State")) == null || "None"
							.equalsIgnoreCase(row.getCell((Integer) columnsids.get("District")).getStringCellValue())
									? "Other" : row.getCell((Integer) columnsids.get("State")).getStringCellValue());
					System.out.println("State : " + String
							.valueOf(row.getCell((Integer) columnsids.get("State")) == null || "None".equalsIgnoreCase(
									row.getCell((Integer) columnsids.get("City")).getStringCellValue()) ? "Other"
											: row.getCell((Integer) columnsids.get("State")).getStringCellValue()));
					excelRepository.addExcelData(itemData);
				}
			}
		}

		for (Entry<String, ExcelData> entry : items.entrySet()) {
			ExcelData itemData = entry.getValue();
			String displayValue = "Rate : " + itemData.getRateOfEachBag() + " Qty : " + itemData.getQuantityOrdered()
					+ " Gross : " + itemData.getGrossAmount() + " Tax : " + itemData.getVatAmount() + " Net : "
					+ itemData.getNetAmount();
			itemData.setLabel(entry.getKey());
			itemData.setDisplayValue(displayValue);
			itemData.setValue(String.valueOf(itemData.getNumberOfBags()));
			itemsData.add(itemData);
		}
		LOG.debug(items.toString());

		/*excelReport = new ExcelReport();
		excelReport.setReportId(Utils.generateRandomKey(20));
		excelReport.setCompanyId(companyId);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = LocalDate.now();
		excelReport.setDateOfReport(dtf.format(localDate));
		excelReport.setFileName(file.getOriginalFilename());
		excelReport.setFileSize(file.getSize());
		excelReport.setExcelDataList(itemsData);
		excelRepository.addExcelDataReport(excelReport);*/
		return itemsData;
	}
	
	public List<ExcelData> readExcelFile1(File file, String companyId)
			throws IOException, EncryptedDocumentException, InvalidFormatException, ParseException {
		InputStream excelFileToRead = new FileInputStream(file);
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
							LOG.debug("inside Date");
							columnsids.put("Date", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("Agent")) {
							LOG.debug("inside Agent");
							columnsids.put("Agent", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("City")) {
							LOG.debug("inside City");
							columnsids.put("City", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("District")) {
							LOG.debug("inside District");
							columnsids.put("District", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("State")) {
							LOG.debug("inside State");
							columnsids.put("State", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("warehouse")) {
							LOG.debug("inside warehouse");
							columnsids.put("warehouse", i);
							isColumnNameVisited = false;
						}
						if (cell.getStringCellValue().equalsIgnoreCase("Entered by")) {
							System.out.println("inside Entered by");
							columnsids.put("Entered by", i);
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
					itemNameSize = itemName + " - " + itemUnitsSize;
					LOG.debug("itemNameSize is" + itemNameSize + "test");
					LOG.debug("Inside Else");
					ExcelData itemData = new ExcelData();
					itemData.setCompanyId(companyId);
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
						LOG.debug("Qty : " + String.valueOf(row.getCell((Integer) columnsids.get("Qty")) == null ? 0.0
								: row.getCell((Integer) columnsids.get("Qty")).getNumericCellValue()));
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
					StringTokenizer st = new StringTokenizer(
							String.valueOf(row.getCell((Integer) columnsids.get("Entered by")) == null ? ""
									: row.getCell((Integer) columnsids.get("Entered by")).getStringCellValue()),
							" ");
					if (st.hasMoreTokens()) {
						// System.out.println(st.nextToken());
						Date date = new SimpleDateFormat("dd-MM-yyyy").parse(st.nextToken());
						System.out.println("Date : " + date.toString());
						itemData.setDate(date);
						itemData.setMonth(date.getMonth());
						itemData.setYear(date.getYear() + 1900);
						itemData.setDay(date.getDay());
					}

					/*
					 * SimpleDateFormat formatter = new
					 * SimpleDateFormat("dd-MM-yyyy");
					 * System.out.println("Date : " +
					 * String.valueOf(row.getCell((Integer)
					 * columnsids.get("Date")) == null ? "" :
					 * row.getCell((Integer)
					 * columnsids.get("Date")).getNumericCellValue()));
					 * SimpleDateFormat format = new
					 * SimpleDateFormat("dd-MM-yy"); Date d =
					 * format.parse("01-01-2017"); long milliseconds =
					 * d.getTime(); System.out.println("Date 2017 : "
					 * +String.valueOf(milliseconds)); Double itemDouble =
					 * (Double) (row.getCell((Integer) columnsids.get("Date"))
					 * == null ? "" : row.getCell((Integer)
					 * columnsids.get("Date")).getNumericCellValue()); long
					 * itemLong = (long) (itemDouble * 100000) + milliseconds;
					 * 
					 * Date date = new Date(itemLong); String itemDateStr = new
					 * SimpleDateFormat("dd-MM-yyyy").format(date);
					 * System.out.println("Date : " +itemDateStr); Date date =
					 * formatter.parse((row.getCell((Integer)
					 * columnsids.get("Date")) == null ? "" :
					 * row.getCell((Integer)
					 * columnsids.get("Date")).getNumericCellValue()));
					 */
					/*
					 * itemData.setDate(date);
					 * itemData.setMonth(date.getMonth());
					 * itemData.setYear(date.getYear());
					 * itemData.setDay(date.getDay());
					 */
					/*
					 * itemData.setCity(row.getCell((Integer)
					 * columnsids.get("City")) == null ? "Other" :
					 * row.getCell((Integer)
					 * columnsids.get("City")).getStringCellValue());
					 * System.out.println("City : " +
					 * String.valueOf(row.getCell((Integer)
					 * columnsids.get("City")) == null ? 0.0 :
					 * row.getCell((Integer)
					 * columnsids.get("City")).getStringCellValue()));
					 * 
					 * itemData.setDistrict(row.getCell((Integer)
					 * columnsids.get("District")) == null ? "Other" :
					 * row.getCell((Integer)
					 * columnsids.get("District")).getStringCellValue());
					 * System.out.println("District : " +
					 * String.valueOf(row.getCell((Integer)
					 * columnsids.get("District")) == null ? 0.0 :
					 * row.getCell((Integer)
					 * columnsids.get("District")).getStringCellValue()));
					 * 
					 * itemData.setState(row.getCell((Integer)
					 * columnsids.get("State")) == null ? "Other" :
					 * row.getCell((Integer)
					 * columnsids.get("State")).getStringCellValue());
					 * System.out.println("State : " +
					 * String.valueOf(row.getCell((Integer)
					 * columnsids.get("State")) == null ? 0.0 :
					 * row.getCell((Integer)
					 * columnsids.get("State")).getStringCellValue()));
					 */
					itemData.setCity(row.getCell((Integer) columnsids.get("City")) == null || "None"
							.equalsIgnoreCase(row.getCell((Integer) columnsids.get("City")).getStringCellValue())
									? "Other" : row.getCell((Integer) columnsids.get("City")).getStringCellValue());
					System.out.println("City : " + String
							.valueOf(row.getCell((Integer) columnsids.get("City")) == null || "None".equalsIgnoreCase(
									row.getCell((Integer) columnsids.get("City")).getStringCellValue()) ? "Other"
											: row.getCell((Integer) columnsids.get("City")).getStringCellValue()));

					itemData.setDistrict(row.getCell((Integer) columnsids.get("District")) == null || "None"
							.equalsIgnoreCase(row.getCell((Integer) columnsids.get("District")).getStringCellValue())
									? "Other" : row.getCell((Integer) columnsids.get("District")).getStringCellValue());
					System.out.println("District : " + String
							.valueOf(row.getCell((Integer) columnsids.get("City")) == null || "None".equalsIgnoreCase(
									row.getCell((Integer) columnsids.get("City")).getStringCellValue()) ? "Other"
											: row.getCell((Integer) columnsids.get("District")).getStringCellValue()));

					itemData.setState(row.getCell((Integer) columnsids.get("State")) == null || "None"
							.equalsIgnoreCase(row.getCell((Integer) columnsids.get("District")).getStringCellValue())
									? "Other" : row.getCell((Integer) columnsids.get("State")).getStringCellValue());
					System.out.println("State : " + String
							.valueOf(row.getCell((Integer) columnsids.get("State")) == null || "None".equalsIgnoreCase(
									row.getCell((Integer) columnsids.get("City")).getStringCellValue()) ? "Other"
											: row.getCell((Integer) columnsids.get("State")).getStringCellValue()));
					
					excelRepository.addExcelData(itemData);
				}
			}
		}

		/*for (Entry<String, ExcelData> entry : items.entrySet()) {
			ExcelData itemData = entry.getValue();
			String displayValue = "Rate : " + itemData.getRateOfEachBag() + " Qty : " + itemData.getQuantityOrdered()
					+ " Gross : " + itemData.getGrossAmount() + " Tax : " + itemData.getVatAmount() + " Net : "
					+ itemData.getNetAmount();
			itemData.setLabel(entry.getKey());
			itemData.setDisplayValue(displayValue);
			itemData.setValue(String.valueOf(itemData.getNumberOfBags()));
			itemsData.add(itemData);
		}*/
		//LOG.debug(items.toString());

		/*excelReport = new ExcelReport();
		excelReport.setReportId(Utils.generateRandomKey(20));
		excelReport.setCompanyId(companyId);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = LocalDate.now();
		excelReport.setDateOfReport(dtf.format(localDate));
		//excelReport.setFileName(file.getOriginalFilename());
		//excelReport.setFileSize(file.getSize());
		excelReport.setExcelDataList(itemsData);
		excelRepository.addExcelDataReport(excelReport);*/
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

	public List<ExcelData> getExcelData(String companyId, String excepReportId) {
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
	
	public List<ExcelPieChart> getExcelReport(String companyId, String itemname, String state,String district,String year) {
		LOG.info("In getExcelData method of Excel Service");
		if (StringUtils.isNotBlank(state) && StringUtils.isNotBlank(companyId) && StringUtils.isNotBlank(district) && StringUtils.isNotBlank(year)) {

			excelRepository.getExcelReport(companyId,itemname,state,district,year, new OnGetDataListener() {

				@Override
				public void onStart() {
				}

				@Override
				public void onSuccess(DataSnapshot billSnapshot) {
					excelDataList.clear();
			        for (DataSnapshot billPostSnapshot: billSnapshot.getChildren()) {
			            ExcelData excelData = billPostSnapshot.getValue(ExcelData.class);
			            excelDataList.add(excelData);
			        } 
				}

				@Override
				public void onFailed(DatabaseError databaseError) {
					LOG.info("Error retrieving data");
					throw new BillliveServiceException(databaseError.getMessage());
				}
			});
			LOG.info(excelDataList.toString());
			if (excelDataList != null) {
				double janGrossAmount = 0;
				double febGrossAmount = 0;
				double marGrossAmount = 0;
				double aprGrossAmount = 0;
				double mayGrossAmount = 0;
				double junGrossAmount = 0;
				double julGrossAmount = 0;
				double augGrossAmount = 0;
				double sepGrossAmount = 0;
				double octGrossAmount = 0;
				double novGrossAmount = 0;
				double decGrossAmount = 0;
				for(int i=0 ; i< excelDataList.size(); i++){
					ExcelData excel = excelDataList.get(i);
					if(!(excel.getState().equals(state) && excel.getDistrict().equals(district) && excel.getYear() == Integer.parseInt(year))){
						excelDataList.remove(i);
					}
					else{
						if(excel.getMonth() == 0){
							LOG.info("In January method of Excel Service");
							janGrossAmount = janGrossAmount + excel.getGrossAmount();
							
						}
						if(excel.getMonth() == 1){
							febGrossAmount = febGrossAmount + excel.getGrossAmount();
							
						}
						if(excel.getMonth() == 2){
							marGrossAmount = marGrossAmount + excel.getGrossAmount();
							
						}
						if(excel.getMonth() == 3){
							aprGrossAmount = aprGrossAmount + excel.getGrossAmount();
							
						}
						if(excel.getMonth() == 4){
							mayGrossAmount = mayGrossAmount + excel.getGrossAmount();
							
						}
						if(excel.getMonth() == 5){
							junGrossAmount = junGrossAmount + excel.getGrossAmount();
							
						}
						if(excel.getMonth() == 6){
							julGrossAmount = julGrossAmount + excel.getGrossAmount();
							
						}
						if(excel.getMonth() == 7){
							augGrossAmount = augGrossAmount + excel.getGrossAmount();
							
						}
						if(excel.getMonth() == 8){
							sepGrossAmount = sepGrossAmount + excel.getGrossAmount();
							
						}
						if(excel.getMonth() == 9){
							octGrossAmount = octGrossAmount + excel.getGrossAmount();
							
						}
						if(excel.getMonth() == 10){
							novGrossAmount = novGrossAmount + excel.getGrossAmount();
							
						}
						if(excel.getMonth() == 11){
							decGrossAmount = decGrossAmount + excel.getGrossAmount();	
						}
							
						}
					}
			ExcelPieChart jan = new ExcelPieChart();
			jan.setValue(String.valueOf(janGrossAmount));
			jan.setDisplayValue(itemname  + ", " + String.valueOf(janGrossAmount)); 
			jan.setLabel("January");
			
			ExcelPieChart feb = new ExcelPieChart();
			feb.setValue(String.valueOf(febGrossAmount));
			feb.setDisplayValue(itemname  + ", " + String.valueOf(febGrossAmount)); 
			feb.setLabel("February");
			
			ExcelPieChart mar = new ExcelPieChart();
			mar.setValue(String.valueOf(marGrossAmount));
			mar.setDisplayValue(itemname  + ", " + String.valueOf(marGrossAmount)); 
			mar.setLabel("March");
			
			
			ExcelPieChart apr = new ExcelPieChart();
			apr.setValue(String.valueOf(aprGrossAmount));
			apr.setDisplayValue(itemname  + ", " + String.valueOf(aprGrossAmount)); 
			apr.setLabel("April");
			
			ExcelPieChart may = new ExcelPieChart();
			may.setValue(String.valueOf(mayGrossAmount));
			may.setDisplayValue(itemname  + ", " + String.valueOf(mayGrossAmount)); 
			may.setLabel("May");
			
			ExcelPieChart jun = new ExcelPieChart();
			jun.setValue(String.valueOf(junGrossAmount));
			jun.setDisplayValue(itemname  + ", " + String.valueOf(junGrossAmount)); 
			jun.setLabel("June");
			
			ExcelPieChart jul = new ExcelPieChart();
			jul.setValue(String.valueOf(julGrossAmount));
			jul.setDisplayValue(itemname  + ", " + String.valueOf(julGrossAmount)); 
			jul.setLabel("July");
			
			ExcelPieChart aug = new ExcelPieChart();
			aug.setValue(String.valueOf(augGrossAmount));
			aug.setDisplayValue(itemname  + ", " + String.valueOf(augGrossAmount)); 
			aug.setLabel("August");
			
			ExcelPieChart sep = new ExcelPieChart();
			sep.setValue(String.valueOf(sepGrossAmount));
			sep.setDisplayValue(itemname  + ", " + String.valueOf(sepGrossAmount)); 
			sep.setLabel("September");
			
			ExcelPieChart oct = new ExcelPieChart();
			oct.setValue(String.valueOf(octGrossAmount));
			oct.setDisplayValue(itemname  + ", " + String.valueOf(octGrossAmount)); 
			oct.setLabel("October");
			
			ExcelPieChart nov = new ExcelPieChart();
			nov.setValue(String.valueOf(novGrossAmount));
			nov.setDisplayValue(itemname  + ", " + String.valueOf(novGrossAmount)); 
			nov.setLabel("November");
			
			ExcelPieChart dec = new ExcelPieChart();
			dec.setValue(String.valueOf(decGrossAmount));
			dec.setDisplayValue(itemname  + ", " + String.valueOf(decGrossAmount)); 
			dec.setLabel("December");
			
			
			List<ExcelPieChart> excelPieChartList = new ArrayList<ExcelPieChart>();
			excelPieChartList.add(jan);
			excelPieChartList.add(feb);
			excelPieChartList.add(mar);
			excelPieChartList.add(apr);
			excelPieChartList.add(may);
			excelPieChartList.add(jun);
			excelPieChartList.add(jul);
			excelPieChartList.add(aug);
			excelPieChartList.add(sep);
			
			excelPieChartList.add(oct);
			excelPieChartList.add(nov);
			excelPieChartList.add(dec);
				
				//Map<String,ExcelData> excels = new HashMap<String,ExcelData>();
				
				
				

				
				
				return excelPieChartList;
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
