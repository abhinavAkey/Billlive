package com.beatus.billlive.domain.model;

public class Invoice {

	private String invoiceNumber;
	private String invoiceFrom;
	private String invoiceTo;
	private String uid;
	private String itemId;
	private String dateOfBill;
	private String dueDate;
	private String inventoryId;
	private String isTaxeble;
	private String unitPrice;
	private String totalAmount;
	private String taxId;
	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getInvoiceFrom() {
		return invoiceFrom;
	}
	public void setInvoiceFrom(String invoiceFrom) {
		this.invoiceFrom = invoiceFrom;
	}
	public String getInvoiceTo() {
		return invoiceTo;
	}
	public void setInvoiceTo(String invoiceTo) {
		this.invoiceTo = invoiceTo;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
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
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
}
