package com.beatus.billlive.domain.model;

public class CompleteBillTransaction extends BaseData{
	private String billNumber;
	private String postId;
	private Double totalAmount;
	private String date;
	private String reference;
	private Double amountPaid;
	private Double amountDue;
	
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public Double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(Double amountPaid) {
		this.amountPaid = amountPaid;
	}
	public Double getAmountDue() {
		return amountDue;
	}
	public void setAmountDue(Double amountDue) {
		this.amountDue = amountDue;
	}
}
