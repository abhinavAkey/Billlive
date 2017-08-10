package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.Tax;
import com.beatus.billlive.validation.exception.TaxException;

@Component("taxValidator")
public class TaxValidator {
	
	public boolean validateTax(Tax company) throws TaxException{
		if(company == null || StringUtils.isBlank(String.valueOf(company.getTaxId()))){
			throw new TaxException("Tax data is null");
		}
		return false;
	}

}
