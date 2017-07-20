package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;

import com.beatus.billlive.domain.model.BillDTO;
import com.beatus.billlive.domain.model.ItemDTO;
import com.beatus.billlive.validation.exception.BillValidationException;

public class BillValidator {
	
	public void validateBillData(BillDTO bill) throws BillValidationException{
		if(bill == null || StringUtils.isBlank(bill.getUid())){
			throw new BillValidationException("Bill data is null");
		}
		if(StringUtils.isBlank(bill.getBillFrom())){
			throw new BillValidationException("Bill data the From field is not available " + bill.getUid());
		}
		if(StringUtils.isBlank(bill.getBillTo())){
			throw new BillValidationException("Bill data the To field is not available " + bill.getUid());
		}
		if(StringUtils.isBlank(bill.getDateOfBill())){
			throw new BillValidationException("Bill data the date field is not available " + bill.getUid());
		}
		if(StringUtils.isBlank(bill.getDueDate())){
			throw new BillValidationException("Bill data the due date field is not available " + bill.getUid());
		}
		if(bill.getItems() != null && bill.getItems().size() > 0){
			for(ItemDTO item : bill.getItems()){
				if(StringUtils.isBlank(item.getItemId())){
					throw new BillValidationException("Item Id can't be null");
				}
				if(StringUtils.isBlank(item.getIsTaxeble())){
					throw new BillValidationException("Is taxeble can't be null " + item.getItemId());
				}
				if(StringUtils.isBlank(item.getProductValue())){
					throw new BillValidationException("Product Value can't be null " + item.getItemId());
				}
				if(StringUtils.isBlank(item.getTotalAmount())){
					throw new BillValidationException("Product Value can't be null " + item.getItemId());
				}
				if(StringUtils.isBlank(item.getTaxId())){
					throw new BillValidationException("Tax Id can't be null " + item.getItemId());
				}
			}
		}else {
			throw new BillValidationException("In Bill the items data is not available");
		}
	}
}
