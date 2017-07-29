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
	private String isTaxeble;
	private List<BillItemData> billItems;
	private String totalAmount;
	private String amountPaid;
	private String amountDue;
	
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
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(String amountPaid) {
		this.amountPaid = amountPaid;
	}
	public String getAmountDue() {
		return amountDue;
	}
	public void setAmountDue(String amountDue) {
		this.amountDue = amountDue;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
}
