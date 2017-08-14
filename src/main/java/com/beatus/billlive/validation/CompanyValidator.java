package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.CompanyData;
import com.beatus.billlive.exception.CompanyDataException;

@Component("companyValidator")
public class CompanyValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyValidator.class);

	public boolean validateCompanyData(CompanyData company) throws CompanyDataException{
		LOGGER.info(" Validating CompanyData " + company);

		if(company == null || StringUtils.isBlank(String.valueOf(company.getCompanyId()))){
			throw new CompanyDataException("CompanyData, the companyId field is not available");

		}
		if(StringUtils.isBlank(String.valueOf(company.getFirstName()))){
			throw new CompanyDataException("CompanyData, the firstName field is not available for company with companyId = " + company.getCompanyId());

		}
		if(StringUtils.isBlank(String.valueOf(company.getLastName()))){
			throw new CompanyDataException("CompanyData, the lastName field is not availablefor company with companyId = " + company.getCompanyId());

		}
		if(StringUtils.isBlank(String.valueOf(company.getCompany()))){
			throw new CompanyDataException("CompanyData, the company field is not available for company with companyId = " + company.getCompanyId());

		}
		if(StringUtils.isBlank(String.valueOf(company.getWebsite()))){
			throw new CompanyDataException("CompanyData, the website field is not available for company with companyId = " + company.getCompanyId());

		}
		if(StringUtils.isBlank(String.valueOf(company.getCountry()))){
			throw new CompanyDataException("CompanyData, the website field is not available for company with companyId = " + company.getCompanyId());

		}
		if(StringUtils.isBlank(String.valueOf(company.getState()))){
			throw new CompanyDataException("CompanyData, the state field is not available for company with companyId = " + company.getCompanyId());

		}
		if(StringUtils.isBlank(String.valueOf(company.getDistrict()))){
			throw new CompanyDataException("CompanyData, the district field is not available for company with companyId = " + company.getCompanyId());

		}
		if(StringUtils.isBlank(String.valueOf(company.getCity()))){
			throw new CompanyDataException("CompanyData, the city field is not available for company with companyId = " + company.getCompanyId());

		}
		if(StringUtils.isBlank(String.valueOf(company.getPhoneNumber()))){
			throw new CompanyDataException("CompanyData, the phoneNumber field is not available for company with companyId = " + company.getCompanyId());

		}
		return false;
	}

}
