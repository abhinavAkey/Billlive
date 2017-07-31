
package com.beatus.billlive.domain.model;

import java.util.List;

public class PurchaseOrder {
	
	private String purchaseId;
	private String purchaseFromContactId;
	private String purchaseToContactId;
	private String purchaseDate;
	private String Uid;
	private String postId;
	private String totalAmount;
	private String year;	
	private String month;
	private String day;
	private List<ItemData> itemsInPurchaseOrder;
	
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
}
