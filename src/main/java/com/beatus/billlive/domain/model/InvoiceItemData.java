package com.beatus.billlive.domain.model;

public class InvoiceItemData {

	private String itemId;
	private String inventoryId;
	private String isTaxeble;
	private QuantityType quantityType; 
	private Double quantity;
	private Double unitPriceInclusiveOfTaxes;
	private Double unitPriceExclusiveOfTaxes;
	private Double productValue;
	private Double amountBeforeTax;
	private Double taxAmountForItem;
	private Double totalCGST;
	private Double totalSGST;
	private Double totalIGST;
	private Double amountAfterTax;
	private Double discount;
	private Double marginAmount;
	private Double taxOnMargin;
	private String taxId;
	private String isRemoved;
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
	public String getIsTaxeble() {
		return isTaxeble;
	}
	public void setIsTaxeble(String isTaxeble) {
		this.isTaxeble = isTaxeble;
	}
	public QuantityType getQuantityType() {
		return quantityType;
	}
	public void setQuantityType(QuantityType quantityType) {
		this.quantityType = quantityType;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Double getUnitPriceInclusiveOfTaxes() {
		return unitPriceInclusiveOfTaxes;
	}
	public void setUnitPriceInclusiveOfTaxes(Double unitPriceInclusiveOfTaxes) {
		this.unitPriceInclusiveOfTaxes = unitPriceInclusiveOfTaxes;
	}
	public Double getUnitPriceExclusiveOfTaxes() {
		return unitPriceExclusiveOfTaxes;
	}
	public void setUnitPriceExclusiveOfTaxes(Double unitPriceExclusiveOfTaxes) {
		this.unitPriceExclusiveOfTaxes = unitPriceExclusiveOfTaxes;
	}
	public Double getProductValue() {
		return productValue;
	}
	public void setProductValue(Double productValue) {
		this.productValue = productValue;
	}
	public Double getAmountBeforeTax() {
		return amountBeforeTax;
	}
	public void setAmountBeforeTax(Double amountBeforeTax) {
		this.amountBeforeTax = amountBeforeTax;
	}
	public Double getTaxAmountForItem() {
		return taxAmountForItem;
	}
	public void setTaxAmountForItem(Double taxAmountForItem) {
		this.taxAmountForItem = taxAmountForItem;
	}
	public Double getTotalCGST() {
		return totalCGST;
	}
	public void setTotalCGST(Double totalCGST) {
		this.totalCGST = totalCGST;
	}
	public Double getTotalSGST() {
		return totalSGST;
	}
	public void setTotalSGST(Double totalSGST) {
		this.totalSGST = totalSGST;
	}
	public Double getTotalIGST() {
		return totalIGST;
	}
	public void setTotalIGST(Double totalIGST) {
		this.totalIGST = totalIGST;
	}
	public Double getAmountAfterTax() {
		return amountAfterTax;
	}
	public void setAmountAfterTax(Double amountAfterTax) {
		this.amountAfterTax = amountAfterTax;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Double getMarginAmount() {
		return marginAmount;
	}
	public void setMarginAmount(Double marginAmount) {
		this.marginAmount = marginAmount;
	}
	public Double getTaxOnMargin() {
		return taxOnMargin;
	}
	public void setTaxOnMargin(Double taxOnMargin) {
		this.taxOnMargin = taxOnMargin;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public String getIsRemoved() {
		return isRemoved;
	}
	public void setIsRemoved(String isRemoved) {
		this.isRemoved = isRemoved;
	}
}
