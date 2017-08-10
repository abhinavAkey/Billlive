package com.beatus.billlive.domain.model;

import java.util.List;

public class BillData extends BaseData{
	
	private String billNumber;
	private String billFromContactId;
	private String billToContactId;
	private String uid;
	private String postId;
	private String dateOfBill;
	private String dueDate;
	private String referenceMobileNumber;
	private String referenceAadharCardNumber;
	private String isTaxeble;
	private List<BillItemData> billItems;
	private Double totalAmount;
	private Double totalTax;
	private Double totalCGST;
	private Double totalSGST;
	private Double totalIGST;
	private String isRemoved;
	private String year;
	private String month;
	private String day;
	
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public String getBillFromContactId() {
		return billFromContactId;
	}
	public void setBillFromContactId(String billFromContactId) {
		this.billFromContactId = billFromContactId;
	}
	public String getBillToContactId() {
		return billToContactId;
	}
	public void setBillToContactId(String billToContactId) {
		this.billToContactId = billToContactId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getDateOfBill() {
		return dateOfBill;
	}
	public void setDateOfBill(String dateOfBill) {
		this.dateOfBill = dateOfBill;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getIsTaxeble() {
		return isTaxeble;
	}
	public void setIsTaxeble(String isTaxeble) {
		this.isTaxeble = isTaxeble;
	}
	public List<BillItemData> getBillItems() {
		return billItems;
	}
	public void setBillItems(List<BillItemData> billItems) {
		this.billItems = billItems;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
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
	public String getIsRemoved() {
		return isRemoved;
	}
	public void setIsRemoved(String isRemoved) {
		this.isRemoved = isRemoved;
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
}
