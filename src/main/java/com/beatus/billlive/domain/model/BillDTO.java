package com.beatus.billlive.domain.model;

import java.util.List;

public class BillDTO {

	private String billNumber;
	private String billFromContactId;
	private String billToContactId;
	private String companyId;
	private String uid;
	private List<ItemDTO> items;
	private String dateOfBill;
	private String dueDate;
	private String isTaxeble;
	private String isUpdated;
	private String isDeleted;
	
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public String getBillFromContactId() {
		return billFromContactId;
	}
	public void setBillFromContactId(String billFromContactId) {
		this.billFromContactId = billFromContactId;
	}
	public String getBillToContactId() {
		return billToContactId;
	}
	public void setBillToContactId(String billToContactId) {
		this.billToContactId = billToContactId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getDateOfBill() {
		return dateOfBill;
	}
	public void setDateOfBill(String dateOfBill) {
		this.dateOfBill = dateOfBill;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public List<ItemDTO> getItems() {
		return items;
	}
	public void setItems(List<ItemDTO> items) {
		this.items = items;
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
	public String getIsTaxeble() {
		return isTaxeble;
	}
	public void setIsTaxeble(String isTaxeble) {
		this.isTaxeble = isTaxeble;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
