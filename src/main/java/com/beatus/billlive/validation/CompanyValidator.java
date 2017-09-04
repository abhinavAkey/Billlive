package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.CompanyData;
import com.beatus.billlive.validation.exception.BillliveClientValidationException;
@Component("companyValidator")
public class CompanyValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyValidator.class);

	public boolean validateCompanyData(CompanyData company) throws BillliveClientValidationException{
		LOGGER.info(" Validating CompanyData " + company);

		if(company == null || StringUtils.isBlank(String.valueOf(company.getCompanyId()))){
			throw new BillliveClientValidationException("companyId","CompanyData, the companyId field is not available");

		}
		if(StringUtils.isBlank(String.valueOf(company.getFirstName()))){
			throw new BillliveClientValidationException("firstName","CompanyData, the firstName field is not available for company with companyId = " + company.getCompanyId());

		}
		if(StringUtils.isBlank(String.valueOf(company.getLastName()))){
			throw new BillliveClientValidationException("lastName","CompanyData, the lastName field is not availablefor company with companyId = " + company.getCompanyId());

		}
		if(StringUtils.isBlank(String.valueOf(company.getCompany()))){
			throw new BillliveClientValidationException("company","CompanyData, the company field is not available for company with companyId = " + company.getCompanyId());

		}
		if(StringUtils.isBlank(String.valueOf(company.getWebsite()))){
			throw new BillliveClientValidationException("website","CompanyData, the website field is not available for company with companyId = " + company.getCompanyId());

		}
		if(StringUtils.isBlank(String.valueOf(company.getCountry()))){
			throw new BillliveClientValidationException("Country","CompanyData, the Country field is not available for company with companyId = " + company.getCompanyId());

		}
		if(StringUtils.isBlank(String.valueOf(company.getState()))){
			throw new BillliveClientValidationException("state","CompanyData, the state field is not available for company with companyId = " + company.getCompanyId());

		}
		if(StringUtils.isBlank(String.valueOf(company.getDistrict()))){
			throw new BillliveClientValidationException("district","CompanyData, the district field is not available for company with companyId = " + company.getCompanyId());

		}
		if(StringUtils.isBlank(String.valueOf(company.getCity()))){
			throw new BillliveClientValidationException("city","CompanyData, the city field is not available for company with companyId = " + company.getCompanyId());

		}
		if(StringUtils.isBlank(String.valueOf(company.getPhoneNumber()))){
			throw new BillliveClientValidationException("phoneNumber","CompanyData, the phoneNumber field is not available for company with companyId = " + company.getCompanyId());

		}
		return false;
	}

}
