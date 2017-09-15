package com.beatus.billlive.domain.model;



public class ExcelData {
	private String itemName;
	private String units;
	private Double NumberOfBags;
	private Double rateOfEachBag;
	private Double quantityOrdered;
	private Double grossAmount;
	private Double vatAmount;
	private Double netAmount;
	private String label;
	private String displayValue;
	private String value;
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public Double getNumberOfBags() {
		return NumberOfBags;
	}
	public void setNumberOfBags(Double numberOfBags) {
		NumberOfBags = numberOfBags;
	}
	public Double getRateOfEachBag() {
		return rateOfEachBag;
	}
	public void setRateOfEachBag(Double rateOfEachBag) {
		this.rateOfEachBag = rateOfEachBag;
	}
	public Double getQuantityOrdered() {
		return quantityOrdered;
	}
	public void setQuantityOrdered(Double quantityOrdered) {
		this.quantityOrdered = quantityOrdered;
	}
	public Double getGrossAmount() {
		return grossAmount;
	}
	public void setGrossAmount(Double grossAmount) {
		this.grossAmount = grossAmount;
	}
	public Double getVatAmount() {
		return vatAmount;
	}
	public void setVatAmount(Double vatAmount) {
		this.vatAmount = vatAmount;
	}
	public Double getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDisplayValue() {
		return displayValue;
	}
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
