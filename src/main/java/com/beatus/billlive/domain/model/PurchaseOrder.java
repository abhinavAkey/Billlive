package com.beatus.billlive.domain.model;

public class PurchaseOrder {
	
	private String purchaseId;
	private String purchaseFrom;
	private String purchaseTo;
	private String purchaseDate;
	private String itemId;
	private String quantityDesc;
	private QuantityType quantityType;
	private String quantity;
	private String unitPrice;
	private String totalAmount;
	private String taxId;
	
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public String getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}
	public String getPurchaseFrom() {
		return purchaseFrom;
	}
	public void setPurchaseFrom(String purchaseFrom) {
		this.purchaseFrom = purchaseFrom;
	}
	public String getPurchaseTo() {
		return purchaseTo;
	}
	public void setPurchaseTo(String purchaseTo) {
		this.purchaseTo = purchaseTo;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getQuantityDesc() {
		return quantityDesc;
	}
	public void setQuantityDesc(String quantityDesc) {
		this.quantityDesc = quantityDesc;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
}
