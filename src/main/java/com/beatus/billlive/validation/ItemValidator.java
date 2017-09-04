package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.Inventory;
import com.beatus.billlive.domain.model.ItemData;
import com.beatus.billlive.exception.InventoryValidationException;
import com.beatus.billlive.validation.exception.BillliveClientValidationException;


@Component("itemValidator")
public class ItemValidator {
	@Autowired
	private InventoryValidator inventoryValidator;

	public boolean validateItemData(ItemData item) throws BillliveClientValidationException, InventoryValidationException{
		if(item == null){
			throw new BillliveClientValidationException("item","Item, item data is null");
		}
		if(StringUtils.isBlank(String.valueOf(item.getHsnCode()))){
			throw new BillliveClientValidationException("hsnCode","Item, the hsnCode field is not available, for the item with id =  " + item.getItemId());
		}if(StringUtils.isBlank(String.valueOf(item.getGstItemCode()))){
			throw new BillliveClientValidationException("GstItemCode","Item, the GstItemCode field is not available, for the item with id =  " + item.getItemId());
		}
		if(StringUtils.isBlank(String.valueOf(item.getTaxId()))){
			throw new BillliveClientValidationException("taxId","Item, the taxId field is not available, for the item with id =  " + item.getItemId());
		}
		if(item.getInventories() != null && item.getInventories().size() > 0){
			for(Inventory inventory :item.getInventories()){
				if(!inventoryValidator.validateInventoryData(inventory)){
					throw new BillliveClientValidationException("Inventory","Item, the Inventory field is not available, for the item with id =  " + item.getItemId());
				}
			}
		}
		return true;
	}

}
