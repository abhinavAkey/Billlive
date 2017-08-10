package com.beatus.billlive.domain.model;

public class PurchaseOrderItemInventoryDTO {
	
	private String itemId;
	private String inventoryId;
	private String isAdded;
	private String isUpdated;
	private String isDeleted;
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
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
