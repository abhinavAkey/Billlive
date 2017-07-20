package com.beatus.billlive.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="item")
public class Item {
	@Id
	@Column(name="item_id")
	private String itemId;
	@Column(name="hsn_code")
	private String hsnCode;
	@Column(name="gst_item_code")
	private String gstItemCode;
	@Column(name="item_name")
	private String itemName;
	@Column(name="item_desc")
	private String itemDesc;
	@Column(name="inventory_id")
	private String inventoryId;
	@Column(name="unit_price")
	private String unitPrice;
	@Column(name="tax_id")
	private String taxId;
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getHsnCode() {
		return hsnCode;
	}
	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}
	public String getGstItemCode() {
		return gstItemCode;
	}
	public void setGstItemCode(String gstItemCode) {
		this.gstItemCode = gstItemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	
	public String getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	
}
