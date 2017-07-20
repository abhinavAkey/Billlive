package com.beatus.billlive.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="inventory")
public class Inventory {
	@Id
	@Column(name="inventory_id")
	private String inventoryId;
	@Column(name="item_id")
	private String itemId;
	@Column(name="date")
	private String date;
	@Column(name="quantity_desc")
	private String quantityDesc;

	private QuantityType buyQuantityType;
	private QuantityType sellQuantityType;
	@Column(name="actual_quantity")
	private String actualQuantity;
	@Column(name="remaining_quantity")
	private String remainingQuantity;
	@Column(name="purchase_id")
	private String purchaseId;
	@Column(name="default_margin_Percentage")
	private String defaultMarginPercentage;
	@Column(name="default_margin_amount")
	private String defaultMarginAmount;
	@Column(name="minimum_stock_value")
	private String minimumStockValue;
	
	public String getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getQuantityDesc() {
		return quantityDesc;
	}
	public void setQuantityDesc(String quantityDesc) {
		this.quantityDesc = quantityDesc;
	}
	public String getActualQuantity() {
		return actualQuantity;
	}
	public void setActualQuantity(String actualQuantity) {
		this.actualQuantity = actualQuantity;
	}
	public String getRemainingQuantity() {
		return remainingQuantity;
	}
	public void setRemainingQuantity(String remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}
	public String getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}
	public String getDefaultMarginPercentage() {
		return defaultMarginPercentage;
	}
	public void setDefaultMarginPercentage(String defaultMarginPercentage) {
		this.defaultMarginPercentage = defaultMarginPercentage;
	}
	public String getDefaultMarginAmount() {
		return defaultMarginAmount;
	}
	public void setDefaultMarginAmount(String defaultMarginAmount) {
		this.defaultMarginAmount = defaultMarginAmount;
	}
	public String getMinimumStockValue() {
		return minimumStockValue;
	}
	public void setMinimumStockValue(String minimumStockValue) {
		this.minimumStockValue = minimumStockValue;
	}
}
