
package com.beatus.billlive.service;



import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.beatus.billlive.domain.model.ExcelFile;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.beatus.billlive.domain.model.ColumnTemplate;
import com.beatus.billlive.domain.model.FileTemplate;
import com.beatus.billlive.domain.model.Item;
import com.beatus.billlive.utils.FileUploadTemplateHandler;



public class ExcelService {
	public static final String VARCHAR = "VARCHAR";
	public static final String DECIMAL = "DECIMAL";
	public static final String TIMESTAMP = "TIMESTAMP";
	public static final String BOOLEAN = "BOOLEAN";
	@Autowired
	FileUploadTemplateHandler  fileUploadTemplateHandler;
	public FileUploadTemplateHandler getFileUploadTemplateHandler() {
		return fileUploadTemplateHandler;
	}

	public void setFileUploadTemplateHandler(
			FileUploadTemplateHandler fileUploadTemplateHandler) {
		this.fileUploadTemplateHandler = fileUploadTemplateHandler;
	}
	
	public  List<Object> LoadExcel(MultipartFile file) throws Exception{
    	List<Object> list = null;
    	if (!file.isEmpty()) {
			  try {
	            	Workbook workbook = null;
					if (file.getOriginalFilename().endsWith("xls")) {
						workbook = new HSSFWorkbook(file.getInputStream());
					} else if (file.getOriginalFilename().endsWith("xlsx")) {
						workbook = new XSSFWorkbook(file.getInputStream());
					} 
					ExcelFile excelFile = new ExcelFile(workbook.getSheetAt(0), true, 2000,
							fileUploadTemplateHandler.getUserfileTemplate()
							);
					list = readExcelFile(excelFile.getSheet(), excelFile.getFileTemplate(), 
							true, -1,-1);
				} 
				catch (Exception e) {
	                throw e;
	            }
	    		
	        } else {
	        	 throw new Exception("You failed to upload because the file was empty.");
	        }
		return list;
    }
	public static List<Object> readExcelFile(Sheet sheet,FileTemplate fileTemplate, boolean hasHeaderRow,int startRow, int endRow) {
		List<Object> itemList = null;
		Object item;
		BindException bindException;
		try {
			//Sheet sheet = workbook.getSheetAt(0);
			if(startRow == -1 && endRow == -1){
				startRow=sheet.getFirstRowNum();
				endRow = sheet.getLastRowNum();
			}
			//Iterator<Row> rowIterator = sheet.iterator();

			itemList = new ArrayList<>();
			
			for(int currRow = startRow; currRow <= endRow; currRow++){

				item = new Item();
				Row row = (Row) sheet.getRow(currRow);
				bindException = new BindException(item, ""+ row.getRowNum());
				ColumnTemplate column;
				if (hasHeaderRow && row.getRowNum() < 1) {
					continue; // just skip the row if row number 0  if header row is true
				}
				
				/*
				 * Dynamically calculate positions based on Column Name 
				 * and Bean Name if needed to not use static pos from conf
				 * */
				for (Integer pos : fileTemplate.getColumnTemplatesMap()
							.keySet()) {
						column = fileTemplate.getColumnTemplateByPos(pos);
						switch (column.getType()) {
						case TIMESTAMP:
							try{
							BeanUtils.setProperty(
									item,
									column.getBeanColumnName(),
									getDateCellValue(row.getCell(pos),
											bindException, column));
							}
							catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
								
							}
							break;
						case DECIMAL:
							BeanUtils.setProperty(
									item,
									column.getBeanColumnName(),
									getDecimalCellValue(row.getCell(pos),
											bindException, column));
							break;
						case BOOLEAN:
							BeanUtils.setProperty(
									item,
									column.getBeanColumnName(),
									getBooleanCellValue(row.getCell(pos),
											bindException, column));
							break;

						default:
							BeanUtils.setProperty(
									item,
									column.getBeanColumnName(),
									getStringCellValue(row.getCell(pos),
											bindException, column));
							break;
						}
					}
				
				
				
				itemList.add(item);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		return itemList;
	}
	
	private static Date getDateCellValue(Cell cell ,
			Errors result, ColumnTemplate columnTemplate) {
		Date parsedDate = null;
		if(cell==null){
			return parsedDate;
		}
		try {
		parsedDate = cell.getDateCellValue();
		} catch (IllegalStateException | NumberFormatException e ) {
		}
		return parsedDate;
	}

	@SuppressWarnings("deprecation")
	private static String getStringCellValue(Cell cell, Errors errors,
			ColumnTemplate columnTemplate) {

		String rtrnVal = "";
		if(cell==null){
			return rtrnVal;
		}
		try {

			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				rtrnVal = "" + cell.getNumericCellValue();
			} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				rtrnVal = cell.getCellFormula();
			}else{
				rtrnVal = cell.getStringCellValue();
			}
		} catch (Exception exception) {
			
		}
		return rtrnVal;
	}

	@SuppressWarnings("deprecation")
	private static BigDecimal getDecimalCellValue(Cell cell, Errors errors,
			ColumnTemplate columnTemplate) {
		BigDecimal rtrnVal = new BigDecimal(0);
		if(cell==null){
			return rtrnVal;
		}
		try {
			if (cell != null) {
				if (cell.toString().trim().length() == 0)
					return rtrnVal = new BigDecimal(0);
				else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					rtrnVal = BigDecimal.valueOf(cell.getNumericCellValue());
					return rtrnVal = rtrnVal.setScale(3,
							BigDecimal.ROUND_CEILING).stripTrailingZeros();
				}
			}
		} catch (Exception exception) {
			
		}

		return rtrnVal;
	}
	
	private static Boolean getBooleanCellValue(Cell cell, Errors errors,
			ColumnTemplate columnTemplate) {
		boolean rtrnVal = false;
		if(cell==null){
			return rtrnVal;
		}
		try {
			if (cell != null) {
				if (cell.toString().trim().length() == 0)
					return rtrnVal;
				else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
					rtrnVal = cell.getBooleanCellValue();
					return rtrnVal;
				}
			}
		} catch (IllegalStateException exception) {

		}

		return rtrnVal;
	}
	

    

    	
}
