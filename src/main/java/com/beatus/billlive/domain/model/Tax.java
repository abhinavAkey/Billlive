package com.beatus.billlive.domain.model;

public class Tax {
	
	private String taxId;
	private String taxDesc;
	private String taxPercentageCGST;
	private String taxPercentageSGST;
	private String taxPercentageIGST;
	
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
	public String getTaxPercentageCGST() {
		return taxPercentageCGST;
	}
	public void setTaxPercentageCGST(String taxPercentageCGST) {
		this.taxPercentageCGST = taxPercentageCGST;
	}
	public String getTaxPercentageSGST() {
		return taxPercentageSGST;
	}
	public void setTaxPercentageSGST(String taxPercentageSGST) {
		this.taxPercentageSGST = taxPercentageSGST;
	}
	public String getTaxPercentageIGST() {
		return taxPercentageIGST;
	}
	public void setTaxPercentageIGST(String taxPercentageIGST) {
		this.taxPercentageIGST = taxPercentageIGST;
	}

}
