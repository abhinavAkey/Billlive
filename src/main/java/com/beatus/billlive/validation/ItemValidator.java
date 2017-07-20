package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;

import com.beatus.billlive.domain.model.Item;
import com.beatus.billlive.validation.exception.ItemValidationException;

public class ItemValidator {
	
	public void validateItemData(Item item) throws ItemValidationException{
		if(item == null || StringUtils.isBlank(String.valueOf(item.getItemId()))){
			throw new ItemValidationException("Item data is null");
		}
	}

}
