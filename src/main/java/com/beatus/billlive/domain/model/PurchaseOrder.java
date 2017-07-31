
package com.beatus.billlive.domain.model;

import java.util.List;

public class PurchaseOrder {
	
	private String purchaseId;
	private String purchaseFromContactId;
	private String purchaseToContactId;
	private String purchaseDate;
	private String dateOfPurchaseOrder;
	private String dueDate;
	private String uid;
	private String postId;
	private String totalAmount;
	private List<ItemData> itemsInPurchaseOrder;
	private String isRemoved;
	private String year;	
	private String month;
	private String day;
	
	public String getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}
	public String getPurchaseFromContactId() {
		return purchaseFromContactId;
	}
	public void setPurchaseFromContactId(String purchaseFromContactId) {
		this.purchaseFromContactId = purchaseFromContactId;
	}
	public String getPurchaseToContactId() {
		return purchaseToContactId;
	}
	public void setPurchaseToContactId(String purchaseToContactId) {
		this.purchaseToContactId = purchaseToContactId;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public List<ItemData> getItemsInPurchaseOrder() {
		return itemsInPurchaseOrder;
	}
	public void setItemsInPurchaseOrder(List<ItemData> itemsInPurchaseOrder) {
		this.itemsInPurchaseOrder = itemsInPurchaseOrder;
	}
	public String getDateOfPurchaseOrder() {
		return dateOfPurchaseOrder;
	}
	public void setDateOfPurchaseOrder(String dateOfPurchaseOrder) {
		this.dateOfPurchaseOrder = dateOfPurchaseOrder;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
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
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
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
