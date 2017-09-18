package com.beatus.billlive.domain.model;

import java.util.Date;

public class ExcelData extends BaseData{
	private Date date;
	private int year;
	private int month;
	private int day;
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
	private String Agent;
	private String City;
	private String District;
	private String State;
	private String warehouse;
	private String postId;

	
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getAgent() {
		return Agent;
	}
	public void setAgent(String agent) {
		Agent = agent;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getDistrict() {
		return District;
	}
	public void setDistrict(String district) {
		District = district;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
}
