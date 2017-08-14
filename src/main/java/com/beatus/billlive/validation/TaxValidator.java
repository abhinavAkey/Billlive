package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.Tax;
import com.beatus.billlive.validation.exception.TaxException;

@Component("taxValidator")
public class TaxValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(TaxValidator.class);

	public boolean validateTax(Tax tax) throws TaxException{
		LOGGER.info(" Validating tac " + tax);

		if(tax == null || StringUtils.isBlank(String.valueOf(tax.getTaxId()))){
			throw new TaxException("Tax data is null");
		}
		if(StringUtils.isBlank(String.valueOf(tax.getTotalTaxPercentage()))){
			throw new TaxException("Tax, the field getTotalTaxPercentage is null for tax with taxId = " + tax.getTaxId());
		}
		if(StringUtils.isBlank(String.valueOf(tax.getTaxPercentageCGST()))){
			throw new TaxException("Tax, the field TaxPercentageCGST is null for tax with taxId = " + tax.getTaxId());
		}
		if(StringUtils.isBlank(String.valueOf(tax.getTaxPercentageSGST()))){
			throw new TaxException("Tax, the field taxPercentageSGST is null for tax with taxId = " + tax.getTaxId());
		}
		if(StringUtils.isBlank(String.valueOf(tax.getTaxPercentageIGST()))){
			throw new TaxException("Tax, the field taxPercentageIGST is null for tax with taxId = " + tax.getTaxId());
		}
		return true;
	}

}
