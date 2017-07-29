package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.ItemData;
import com.beatus.billlive.validation.exception.ItemDataException;

@Component("itemValidator")
public class ItemValidator {
	
	public boolean validateItemData(ItemData item) throws ItemDataException{
		if(item == null || StringUtils.isBlank(String.valueOf(item.getItemId()))){
			throw new ItemDataException("Item data is null");
		}
		return false;
	}

}
