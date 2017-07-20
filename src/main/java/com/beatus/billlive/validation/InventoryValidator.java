package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;

import com.beatus.billlive.domain.model.Inventory;
import com.beatus.billlive.validation.exception.InventoryValidationException;

public class InventoryValidator {
	
	public void validateInventoryData(Inventory inventory) throws InventoryValidationException{
		if(inventory == null || StringUtils.isBlank(inventory.getInventoryId())){
			throw new InventoryValidationException("Inventory data is null");
		}
	}

}
