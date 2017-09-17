package com.beatus.billlive.domain.model;

import java.util.List;

public class ExcelReport extends BaseData {
	private List<ExcelData> excelDataList;
	private String reportId;
	private String fileName;
	private String dateOfReport;
	private long fileSize;
	private String companyId;
	private String uId;
	private String postId;
	public List<ExcelData> getExcelDataList() {
		return excelDataList;
	}
	public void setExcelDataList(List<ExcelData> excelDataList) {
		this.excelDataList = excelDataList;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDateOfReport() {
		return dateOfReport;
	}
	public void setDateOfReport(String dateOfReport) {
		this.dateOfReport = dateOfReport;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	
	
	
}
