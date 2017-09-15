package com.beatus.billlive.domain.model;

import java.util.List;


public class ItemData extends BaseData{
	
	private String itemId;
	private ItemType itemType;
	private String uid;
	private String postId;
	private String hsnCode;
	private String gstItemCode;
	private String itemName;
	private String itemDesc;
	private List<Inventory> inventories;
	private String taxId;
	private String isRemoved;

	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public ItemType getItemType() {
		return itemType;
	}
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
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
	public List<Inventory> getInventories() {
		return inventories;
	}
	public void setInventories(List<Inventory> inventories) {
		this.inventories = inventories;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getIsRemoved() {
		return isRemoved;
	}
	public void setIsRemoved(String isRemoved) {
		this.isRemoved = isRemoved;
	}
}
