package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.Inventory;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.validation.exception.BillliveClientValidationException;

@Component("inventoryValidator")
public class InventoryValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(InventoryValidator.class);

	public boolean validateInventoryData(Inventory inventory) throws BillliveClientValidationException{
		LOGGER.info(" Validating Inventory " + inventory);

		if(inventory == null){
			throw new BillliveClientValidationException("Inventory data","Inventory,Inventory data is null");
		}
		if(Constants.YES.equalsIgnoreCase(inventory.getIsUpdated())){
			if(StringUtils.isBlank(inventory.getInventoryId())){
				throw new BillliveClientValidationException("InventoryId","Inventory, InventoryId is null");
			}
		}
		if(inventory.getBuyQuantityType() == null){
			throw new BillliveClientValidationException("buyQuantityType","Inventory, the buyQuantityType field is not available, for the inventory with id =  " + inventory.getInventoryId());
		}
		if(inventory.getActualQuantity() == null){
			throw new BillliveClientValidationException("actualQuantity","Inventory, the actualQuantity field is not available, for the inventory with id =  " + inventory.getInventoryId());
		}
		if(inventory.getUnitPrice() == null){
			throw new BillliveClientValidationException("unitPrice","Inventory, the unitPrice field is not available, for the inventory with id =  " + inventory.getInventoryId());
		}
		if(inventory.getSellingPrice() == null){
			throw new BillliveClientValidationException("sellingPrice","Inventory, the sellingPrice field is not available, for the inventory with id =  " + inventory.getInventoryId());
		}
		return true;
		
	}

}
