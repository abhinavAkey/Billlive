package com.beatus.billlive.domain.model;

import java.util.List;

public class ReportData {

	private List<BillData> bills;
	private Double totalAmountIncludingTax;
	private Double totalTax;
	private String taxId;
	private String uid;
	private String month;
	private String year;
	private String day;
	private String companyId;
	
	public List<BillData> getBills() {
		return bills;
	}
	public void setBills(List<BillData> bills) {
		this.bills = bills;
	}
	public Double getTotalAmountIncludingTax() {
		return totalAmountIncludingTax;
	}
	public void setTotalAmountIncludingTax(Double totalAmountIncludingTax) {
		this.totalAmountIncludingTax = totalAmountIncludingTax;
	}
	public Double getTotalTax() {
		return totalTax;
	}
	public void setTotalTax(Double totalTax) {
		this.totalTax = totalTax;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
