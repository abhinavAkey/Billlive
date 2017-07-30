package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.CompleteBillTransaction;
import com.beatus.billlive.validation.exception.CompleteBillTransactionException;

@Component("companyValidator")
public class CompanyBillTransactionValidator {
	
	public boolean validateCompleteBillTransaction(CompleteBillTransaction company) throws CompleteBillTransactionException{
		if(company == null || StringUtils.isBlank(String.valueOf(company.getBillNumber()))){
			throw new CompleteBillTransactionException("CompleteBillTransaction data is null");
		}
		return false;
	}

}
