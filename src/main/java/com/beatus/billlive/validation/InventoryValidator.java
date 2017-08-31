package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.Inventory;
import com.beatus.billlive.exception.InventoryValidationException;
import com.beatus.billlive.utils.Constants;

@Component("inventoryValidator")
public class InventoryValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(InventoryValidator.class);

	public boolean validateInventoryData(Inventory inventory) throws InventoryValidationException{
		LOGGER.info(" Validating Inventory " + inventory);

		if(inventory == null){
			throw new InventoryValidationException("Inventory data is null");
		}
		if(Constants.YES.equalsIgnoreCase(inventory.getIsUpdated())){
			if(StringUtils.isBlank(inventory.getInventoryId())){
				throw new InventoryValidationException("InventoryId is null");
			}
		}
		if(inventory.getBuyQuantityType() == null){
			throw new InventoryValidationException("Inventory, the buyQuantityType field is not available, for the inventory with id =  " + inventory.getInventoryId());
		}
		if(inventory.getActualQuantity() == null){
			throw new InventoryValidationException("Inventory, the actualQuantity field is not available, for the inventory with id =  " + inventory.getInventoryId());
		}
		if(inventory.getUnitPrice() == null){
			throw new InventoryValidationException("Inventory, the unitPrice field is not available, for the inventory with id =  " + inventory.getInventoryId());
		}
		if(inventory.getSellingPrice() == null){
			throw new InventoryValidationException("Inventory, the sellingPrice field is not available, for the inventory with id =  " + inventory.getInventoryId());
		}
		return true;
		
	}

}
