package com.beatus.billlive.utils;

import java.security.SecureRandom;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import com.beatus.billlive.domain.model.Inventory;
import com.beatus.billlive.domain.model.ItemDTO;
import com.beatus.billlive.domain.model.ItemData;

public class Utils {
	
	public static String generateRandomKey(final int numberOfCharacters) {
    	String	randomNumber = RandomStringUtils.random(numberOfCharacters, 0, 0, false, true, null, new SecureRandom());
		return randomNumber;
	}
	
	public static Double calculateTaxAmount(Double amount, Double percentage) {
		double taxAmount = (amount * percentage)/100;
		return taxAmount;
	}
	
	public static Double calculateMarginAmount(ItemData itemDataFromDatabase, ItemDTO billItemToBeSaved) {
		List<Inventory> inventories = itemDataFromDatabase.getInventories();
		Double marginAmount = Constants.DEFAULT_DOUBLE_VALUE;
		if(inventories != null){
			for(int i=0; i<inventories.size(); i++){
				if(inventories.get(i).getInventoryId().equalsIgnoreCase(billItemToBeSaved.getInventoryId())){
					Double unitPrice = inventories.get(i).getBuyPricesPerQuantityType().get(billItemToBeSaved.getQuantityType().toString());
					Double actualBuyPriceForQuantity = unitPrice*billItemToBeSaved.getQuantity();
					Double actualProductValue = billItemToBeSaved.getProductValue() * billItemToBeSaved.getQuantity();
					marginAmount = actualProductValue - actualBuyPriceForQuantity;
				}
			}
		}
		return marginAmount;
	}

	public static Double calculateTaxOnMargin(Double marginAmount, Double taxPercentage) {
		double taxAmount = (marginAmount * taxPercentage)/100;
		return taxAmount;
	}

}
