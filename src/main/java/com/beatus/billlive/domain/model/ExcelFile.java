package com.beatus.billlive.domain.model;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Sheet;

import org.springframework.validation.Validator;

public class ExcelFile {

	Sheet sheet;
	
	boolean hasHeader;
	
	int rowsPerThread = 1000;
	
	FileTemplate fileTemplate;
	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public boolean isHasHeader() {
		return hasHeader;
	}

	public void setHasHeader(boolean hasHeader) {
		this.hasHeader = hasHeader;
	}

	public FileTemplate getFileTemplate() {
		return fileTemplate;
	}

	public void setFileTemplate(FileTemplate fileTemplate) {
		this.fileTemplate = fileTemplate;
	}



	public int getNoOfthreadsToProcess(){
		if(sheet.getLastRowNum()%rowsPerThread > 0 )
			return (sheet.getLastRowNum()/rowsPerThread)+1;
		else
			return (sheet.getLastRowNum()/rowsPerThread);
	}
	
	
	
	public ExcelFile(Sheet sheet, boolean hasHeader, int rowsPerThread,
			FileTemplate fileTemplate) {
		super();
		this.sheet = sheet;
		this.hasHeader = hasHeader;
		this.rowsPerThread = rowsPerThread;
		this.fileTemplate = fileTemplate;
		
	}

	
	@Override
	public String toString() {
		return "ExcelFile [hasHeader=" + hasHeader + ", rowsPerThread="
				+ rowsPerThread + ", sheetThreads=]";
	}

	
}


