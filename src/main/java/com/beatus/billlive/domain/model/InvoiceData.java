package com.beatus.billlive.domain.model;

import java.util.List;

public class InvoiceData extends BaseData{
	
	private String invoiceNumber;
	private String invoiceFromContactId;
	private String invoiceToContactId;
	private String uid;
	private String postId;
	private String dateOfInvoice;
	private String dueDate;
	private String referenceMobileNumber;
	private String referenceAadharCardNumber;
	private String isTaxeble;
	private List<InvoiceItemData> invoiceItems;
	private Double totalAmount;
	private Double totalTax;
	private Double totalCGST;
	private Double totalSGST;
	private Double totalIGST;
	private String isRemoved;
	private String year;
	private String month;
	private String day;
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getInvoiceFromContactId() {
		return invoiceFromContactId;
	}
	public void setInvoiceFromContactId(String invoiceFromContactId) {
		this.invoiceFromContactId = invoiceFromContactId;
	}
	public String getInvoiceToContactId() {
		return invoiceToContactId;
	}
	public void setInvoiceToContactId(String invoiceToContactId) {
		this.invoiceToContactId = invoiceToContactId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getDateOfInvoice() {
		return dateOfInvoice;
	}
	public void setDateOfInvoice(String dateOfInvoice) {
		this.dateOfInvoice = dateOfInvoice;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getReferenceMobileNumber() {
		return referenceMobileNumber;
	}
	public void setReferenceMobileNumber(String referenceMobileNumber) {
		this.referenceMobileNumber = referenceMobileNumber;
	}
	public String getReferenceAadharCardNumber() {
		return referenceAadharCardNumber;
	}
	public void setReferenceAadharCardNumber(String referenceAadharCardNumber) {
		this.referenceAadharCardNumber = referenceAadharCardNumber;
	}
	public String getIsTaxeble() {
		return isTaxeble;
	}
	public void setIsTaxeble(String isTaxeble) {
		this.isTaxeble = isTaxeble;
	}
	public List<InvoiceItemData> getInvoiceItems() {
		return invoiceItems;
	}
	public void setInvoiceItems(List<InvoiceItemData> invoiceItems) {
		this.invoiceItems = invoiceItems;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Double getTotalTax() {
		return totalTax;
	}
	public void setTotalTax(Double totalTax) {
		this.totalTax = totalTax;
	}
	public Double getTotalCGST() {
		return totalCGST;
	}
	public void setTotalCGST(Double totalCGST) {
		this.totalCGST = totalCGST;
	}
	public Double getTotalSGST() {
		return totalSGST;
	}
	public void setTotalSGST(Double totalSGST) {
		this.totalSGST = totalSGST;
	}
	public Double getTotalIGST() {
		return totalIGST;
	}
	public void setTotalIGST(Double totalIGST) {
		this.totalIGST = totalIGST;
	}
	public String getIsRemoved() {
		return isRemoved;
	}
	public void setIsRemoved(String isRemoved) {
		this.isRemoved = isRemoved;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
}
