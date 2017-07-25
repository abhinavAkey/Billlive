package com.beatus.billlive.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bill")
public class BillData {
	@Id
	@Column(name="bill_number")
	private String billNumber;
	@Column(name="bill_from")
	private String billFrom;
	@Column(name="bill_to")
	private String billTo;
	@Column(name="u_id")
	private String uid;
	private String postId;
	@Column(name="item_id")
	private String itemId;
	@Column(name="date_of_bill")
	private String dateOfBill;
	@Column(name="due_date")
	private String dueDate;
	@Column(name="inventory_id")
	private String inventoryId;
	@Column(name="is_taxeble")
	private String isTaxeble;
	@Column(name="unit_price")
	private String unitPriceInclusiveOrexclusiveOfTaxes;
	@Column(name="product_value")
	private String productValue;
	@Column(name="total_amount")
	private String totalAmount;
	@Column(name="tax_amount")
	private String taxAmountForItem;
	@Column(name="margin_amount")
	private String marginAmount;
	@Column(name="margin_percentage")
	private String marginPercentage;
	@Column(name="tax_on_margin")
	private String taxOnMargin;
	@Column(name="tax_id")
	private String taxId;
	private String amountPaid;
	private String amountDue;
	
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public String getBillFrom() {
		return billFrom;
	}
	public void setBillFrom(String billFrom) {
		this.billFrom = billFrom;
	}
	public String getBillTo() {
		return billTo;
	}
	public void setBillTo(String billTo) {
		this.billTo = billTo;
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
	public String getUnitPriceInclusiveOrexclusiveOfTaxes() {
		return unitPriceInclusiveOrexclusiveOfTaxes;
	}
	public void setUnitPriceInclusiveOrexclusiveOfTaxes(String unitPriceInclusiveOrexclusiveOfTaxes) {
		this.unitPriceInclusiveOrexclusiveOfTaxes = unitPriceInclusiveOrexclusiveOfTaxes;
	}
	public String getProductValue() {
		return productValue;
	}
	public void setProductValue(String productValue) {
		this.productValue = productValue;
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
	public String getTaxOnMargin() {
		return taxOnMargin;
	}
	public void setTaxOnMargin(String taxOnMargin) {
		this.taxOnMargin = taxOnMargin;
	}
	public String getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(String amountPaid) {
		this.amountPaid = amountPaid;
	}
	public String getAmountDue() {
		return amountDue;
	}
	public void setAmountDue(String amountDue) {
		this.amountDue = amountDue;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
}
