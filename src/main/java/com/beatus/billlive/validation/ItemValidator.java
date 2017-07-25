package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.ItemData;
import com.beatus.billlive.validation.exception.ItemValidationException;

@Component("itemValidator")
public class ItemValidator {
	
	public void validateItemData(ItemData item) throws ItemValidationException{
		if(item == null || StringUtils.isBlank(String.valueOf(item.getItemId()))){
			throw new ItemValidationException("Item data is null");
		}
	}

}
