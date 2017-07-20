package com.beatus.billlive.domain.model;

public class ItemDTO {
	
	private String itemId;
	private String isTaxeble;
	private String productValue;
	private String totalAmount;
	private QuantityType quantityType;
	private String quantity;
	private String taxAmountForItem;
	private String marginAmount;
	private String marginPercentage;
	private String taxOnMargin;
	private String taxId;
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getIsTaxeble() {
		return isTaxeble;
	}
	public void setIsTaxeble(String isTaxeble) {
		this.isTaxeble = isTaxeble;
	}
	public String getProductValue() {
		return productValue;
	}
	public void setProductValue(String productValue) {
		this.productValue = productValue;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getTaxAmountForItem() {
		return taxAmountForItem;
	}
	public void setTaxAmountForItem(String taxAmountForItem) {
		this.taxAmountForItem = taxAmountForItem;
	}
	public String getMarginAmount() {
		return marginAmount;
	}
	public void setMarginAmount(String marginAmount) {
		this.marginAmount = marginAmount;
	}
	public String getMarginPercentage() {
		return marginPercentage;
	}
	public void setMarginPercentage(String marginPercentage) {
		this.marginPercentage = marginPercentage;
	}
	public String getTaxOnMargin() {
		return taxOnMargin;
	}
	public void setTaxOnMargin(String taxOnMargin) {
		this.taxOnMargin = taxOnMargin;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public QuantityType getQuantityType() {
		return quantityType;
	}
	public void setQuantityType(QuantityType quantityType) {
		this.quantityType = quantityType;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
}
