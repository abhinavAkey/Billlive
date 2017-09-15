package com.beatus.billlive.domain.model;

public class ItemDTO {
	
	private String itemId;
	private ItemType itemType;
	private String inventoryId;
	private String isTaxeble;
	private Double itemValue;
	private QuantityType quantityType;
	private Double quantity;
	private Double actualUnitPrice;
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
	private Double taxPercentage;
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
	public String getIsTaxeble() {
		return isTaxeble;
	}
	public void setIsTaxeble(String isTaxeble) {
		this.isTaxeble = isTaxeble;
	}
	public ItemType getItemType() {
		return itemType;
	}
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
	public Double getItemValue() {
		return itemValue;
	}
	public void setItemValue(Double itemValue) {
		this.itemValue = itemValue;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Double getActualUnitPrice() {
		return actualUnitPrice;
	}
	public void setActualUnitPrice(Double actualUnitPrice) {
		this.actualUnitPrice = actualUnitPrice;
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
	public Double getAmountAfterTax() {
		return amountAfterTax;
	}
	public void setAmountAfterTax(Double amountAfterTax) {
		this.amountAfterTax = amountAfterTax;
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
	public Double getTaxPercentage() {
		return taxPercentage;
	}
	public void setTaxPercentage(Double taxPercentage) {
		this.taxPercentage = taxPercentage;
	}
	public QuantityType getQuantityType() {
		return quantityType;
	}
	public void setQuantityType(QuantityType quantityType) {
		this.quantityType = quantityType;
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
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
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
}
