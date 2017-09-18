package com.beatus.billlive.domain.model;

import java.util.Date;

public class ExcelRecord {
	private Date date;
	private int year;
	private String month;
	private int day;
	private String item_Name;
	private String Kgs_Grams;
	private Double Bags;
	private Double Rate;
	private Double Qty;
	private Double Gross;
	private Double VAT;
	private Double Net;
	private String Agent;
	private String City;
	private String District;
	private String State;
	private String warehouse;
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
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getItem_Name() {
		return item_Name;
	}
	public void setItem_Name(String item_Name) {
		this.item_Name = item_Name;
	}
	public String getKgs_Grams() {
		return Kgs_Grams;
	}
	public void setKgs_Grams(String kgs_Grams) {
		Kgs_Grams = kgs_Grams;
	}
	public Double getBags() {
		return Bags;
	}
	public void setBags(Double bags) {
		Bags = bags;
	}
	public Double getRate() {
		return Rate;
	}
	public void setRate(Double rate) {
		Rate = rate;
	}
	public Double getQty() {
		return Qty;
	}
	public void setQty(Double qty) {
		Qty = qty;
	}
	public Double getGross() {
		return Gross;
	}
	public void setGross(Double gross) {
		Gross = gross;
	}
	public Double getVAT() {
		return VAT;
	}
	public void setVAT(Double vAT) {
		VAT = vAT;
	}
	public Double getNet() {
		return Net;
	}
	public void setNet(Double net) {
		Net = net;
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
