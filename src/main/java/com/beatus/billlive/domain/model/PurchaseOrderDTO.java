package com.beatus.billlive.domain.model;

import java.util.List;

public class PurchaseOrderDTO {
	
	private String purchaseId;
	private String purchaseFromContactId;
	private String purchaseToContactId;
	private String purchaseDate;
	private List<ItemDTO> itemsInPurchaseOrder;
	private String isAdded;
	private String isUpdated;
	private String isDeleted;
	
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
	public List<ItemDTO> getItemsInPurchaseOrder() {
		return itemsInPurchaseOrder;
	}
	public void setItemsInPurchaseOrder(List<ItemDTO> itemsInPurchaseOrder) {
		this.itemsInPurchaseOrder = itemsInPurchaseOrder;
	}
	public String getIsAdded() {
		return isAdded;
	}
	public void setIsAdded(String isAdded) {
		this.isAdded = isAdded;
	}
	public String getIsUpdated() {
		return isUpdated;
	}
	public void setIsUpdated(String isUpdated) {
		this.isUpdated = isUpdated;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
}
