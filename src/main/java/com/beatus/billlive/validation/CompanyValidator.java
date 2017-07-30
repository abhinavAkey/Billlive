package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.CompanyData;
import com.beatus.billlive.validation.exception.CompanyDataException;

@Component("companyValidator")
public class CompanyValidator {
	
	public boolean validateCompanyData(CompanyData company) throws CompanyDataException{
		if(company == null || StringUtils.isBlank(String.valueOf(company.getCompanyId()))){
			throw new CompanyDataException("Company data is null");
		}
		return false;
	}

}
