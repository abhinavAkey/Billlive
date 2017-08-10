package com.beatus.billlive.domain.model;

import java.util.Map;

public class Inventory {

	private String inventoryId;
	private String date;
	private String quantityDesc;
	private QuantityType buyQuantityType;
	private Double actualQuantity;
	private Double remainingQuantity;
	private Double unitPrice;
	private Double sellingPrice;
	private QuantityType otherSellQuantityTypeOption;
	private Double otherSellOptionQuantityEquivalent;
	private Double otherSellOptionBuyingPrice;
	private Double otherSellOptionSellingPrice;
	private Map<String, Double> buyPricesPerQuantityType;
	private Map<String, Double> sellPricesPerQuantityType;
	private String purchaseOrderNumber;
	private Double defaultMarginPercentage;
	private Double defaultMarginAmount;
	private Double minimumStockValue;
	private String isTaxeble;
	private String taxid;
	private String isAdded;
	private String isUpdated;
	private String isDeleted;
	
	public String getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
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
	public Double getActualQuantity() {
		return actualQuantity;
	}
	public void setActualQuantity(Double actualQuantity) {
		this.actualQuantity = actualQuantity;
	}
	public Double getRemainingQuantity() {
		return remainingQuantity;
	}
	public void setRemainingQuantity(Double remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public Double getOtherSellOptionQuantityEquivalent() {
		return otherSellOptionQuantityEquivalent;
	}
	public void setOtherSellOptionQuantityEquivalent(Double otherSellOptionQuantityEquivalent) {
		this.otherSellOptionQuantityEquivalent = otherSellOptionQuantityEquivalent;
	}
	public Double getOtherSellOptionBuyingPrice() {
		return otherSellOptionBuyingPrice;
	}
	public void setOtherSellOptionBuyingPrice(Double otherSellOptionBuyingPrice) {
		this.otherSellOptionBuyingPrice = otherSellOptionBuyingPrice;
	}
	public Double getOtherSellOptionSellingPrice() {
		return otherSellOptionSellingPrice;
	}
	public void setOtherSellOptionSellingPrice(Double otherSellOptionSellingPrice) {
		this.otherSellOptionSellingPrice = otherSellOptionSellingPrice;
	}
	public Double getDefaultMarginPercentage() {
		return defaultMarginPercentage;
	}
	public void setDefaultMarginPercentage(Double defaultMarginPercentage) {
		this.defaultMarginPercentage = defaultMarginPercentage;
	}
	public Double getDefaultMarginAmount() {
		return defaultMarginAmount;
	}
	public void setDefaultMarginAmount(Double defaultMarginAmount) {
		this.defaultMarginAmount = defaultMarginAmount;
	}
	public Double getMinimumStockValue() {
		return minimumStockValue;
	}
	public void setMinimumStockValue(Double minimumStockValue) {
		this.minimumStockValue = minimumStockValue;
	}
	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}
	public QuantityType getBuyQuantityType() {
		return buyQuantityType;
	}
	public void setBuyQuantityType(QuantityType buyQuantityType) {
		this.buyQuantityType = buyQuantityType;
	}
	public QuantityType getOtherSellQuantityTypeOption() {
		return otherSellQuantityTypeOption;
	}
	public void setOtherSellQuantityTypeOption(QuantityType otherSellQuantityTypeOption) {
		this.otherSellQuantityTypeOption = otherSellQuantityTypeOption;
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
	public Map<String, Double> getBuyPricesPerQuantityType() {
		return buyPricesPerQuantityType;
	}
	public void setBuyPricesPerQuantityType(Map<String, Double> buyPricesPerQuantityType) {
		this.buyPricesPerQuantityType = buyPricesPerQuantityType;
	}
	public Map<String, Double> getSellPricesPerQuantityType() {
		return sellPricesPerQuantityType;
	}
	public void setSellPricesPerQuantityType(Map<String, Double> sellPricesPerQuantityType) {
		this.sellPricesPerQuantityType = sellPricesPerQuantityType;
	}
	public String getIsTaxeble() {
		return isTaxeble;
	}
	public void setIsTaxeble(String isTaxeble) {
		this.isTaxeble = isTaxeble;
	}
	public String getTaxid() {
		return taxid;
	}
	public void setTaxid(String taxid) {
		this.taxid = taxid;
	}
}
