package com.beatus.billlive.domain.model;

import java.util.List;

public class CompleteBillTransaction extends BaseData{
	private String billTransactionId;
	private String billNumber;
	private String postId;
	private String uid;
	private Double totalAmount;
	private List<PaymentTransaction> paymentTransactions; 
	private Double totalAmountPaid;
	private Double totalAmountDue;
	
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
	public String getBillTransactionId() {
		return billTransactionId;
	}
	public void setBillTransactionId(String billTransactionId) {
		this.billTransactionId = billTransactionId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public List<PaymentTransaction> getPaymentTransactions() {
		return paymentTransactions;
	}
	public void setPaymentTransactions(List<PaymentTransaction> paymentTransactions) {
		this.paymentTransactions = paymentTransactions;
	}
	public Double getTotalAmountPaid() {
		return totalAmountPaid;
	}
	public void setTotalAmountPaid(Double totalAmountPaid) {
		this.totalAmountPaid = totalAmountPaid;
	}
	public Double getTotalAmountDue() {
		return totalAmountDue;
	}
	public void setTotalAmountDue(Double totalAmountDue) {
		this.totalAmountDue = totalAmountDue;
	}
}
