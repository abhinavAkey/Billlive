package com.beatus.billlive.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "ItemData")
public class ItemDataTest extends BaseData{
	
	private String itemId;
	private String uid;
	private String postId;
	private String hsnCode;
	private String gstItemCode;
	private String itemName;
	private String itemDesc;
	private String taxId;
	private String isRemoved;
	
	@DynamoDBHashKey
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
//	@DynamoDBTypeConvertedEnum
//	@DynamoDBAttribute(attributeName="ItemType")
//	public ItemType getItemType() {
//		return itemType;
//	}
//	public void setItemType(ItemType itemType) {
//		this.itemType = itemType;
//	}
	@DynamoDBAttribute
	public String getHsnCode() {
		return hsnCode;
	}
	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}
	@DynamoDBAttribute
	public String getGstItemCode() {
		return gstItemCode;
	}
	public void setGstItemCode(String gstItemCode) {
		this.gstItemCode = gstItemCode;
	}
	@DynamoDBAttribute
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@DynamoDBAttribute
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
//	@DynamoDBAttribute
//	public List<Inventory> getInventories() {
//		return inventories;
//	}
//	public void setInventories(List<Inventory> inventories) {
//		this.inventories = inventories;
//	}
	@DynamoDBAttribute
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	@DynamoDBAttribute
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	@DynamoDBAttribute
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	@DynamoDBAttribute
	public String getIsRemoved() {
		return isRemoved;
	}
	public void setIsRemoved(String isRemoved) {
		this.isRemoved = isRemoved;
	}
}
