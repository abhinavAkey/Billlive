package com.beatus.billlive.domain.model;

public class Tax extends BaseData{
	
	private String taxId;
	private String taxDesc;
	private Double totalTaxPercentage; 
	private Double taxPercentageCGST;
	private Double taxPercentageSGST;
	private Double taxPercentageIGST;
	
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public String getTaxDesc() {
		return taxDesc;
	}
	public void setTaxDesc(String taxDesc) {
		this.taxDesc = taxDesc;
	}
	public Double getTotalTaxPercentage() {
		return totalTaxPercentage;
	}
	public void setTotalTaxPercentage(Double totalTaxPercentage) {
		this.totalTaxPercentage = totalTaxPercentage;
	}
	public Double getTaxPercentageCGST() {
		return taxPercentageCGST;
	}
	public void setTaxPercentageCGST(Double taxPercentageCGST) {
		this.taxPercentageCGST = taxPercentageCGST;
	}
	public Double getTaxPercentageSGST() {
		return taxPercentageSGST;
	}
	public void setTaxPercentageSGST(Double taxPercentageSGST) {
		this.taxPercentageSGST = taxPercentageSGST;
	}
	public Double getTaxPercentageIGST() {
		return taxPercentageIGST;
	}
	public void setTaxPercentageIGST(Double taxPercentageIGST) {
		this.taxPercentageIGST = taxPercentageIGST;
	}

}
