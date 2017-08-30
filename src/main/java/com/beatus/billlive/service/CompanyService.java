package com.beatus.billlive.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.beatus.billlive.domain.model.CompanyData;
import com.beatus.billlive.exception.CompanyDataException;
import com.beatus.billlive.repository.CompanyRepository;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.utils.Utils;
import com.beatus.billlive.validation.CompanyValidator;

@Service
@Component("companyService")
public class CompanyService {
	
	@Resource(name = "companyValidator")
	private CompanyValidator companyValidator;
	
	@Resource(name = "companyRepository")
	private CompanyRepository companyRepository;

	public String addCompany(HttpServletRequest request, HttpServletResponse response,CompanyData company) throws CompanyDataException {
		if(company == null){
			throw new CompanyDataException("Company Data cant be null");
		}
		try {
			boolean isValidated = companyValidator.validateCompanyData(company);
			if(isValidated){
				String companyId = company.getCompanyId();
				if(StringUtils.isNotBlank(company.getCompanyId())){
					CompanyData existingCompany = companyRepository.getCompanyById(company.getCompanyId());
					if(existingCompany != null){
						return updateCompany(request,response,company);
					}
					return company.getCompanyId();
				}else {
					companyId = Utils.generateRandomKey(20);
					company.setCompanyId(companyId);
					companyRepository.addCompany(company);
					return companyId;
				}
			}
			}catch (CompanyDataException e) {
				throw e;
			}
		return null;
	}


	public String updateCompany(HttpServletRequest request, HttpServletResponse response,CompanyData company) throws CompanyDataException {
		
		try {
			if(companyValidator.validateCompanyData(company)){
				if(StringUtils.isNotBlank(company.getCompanyId())){
					String companyId = company.getCompanyId();
					CompanyData existingCompany = companyRepository.getCompanyById(companyId);
					if(existingCompany == null){
							return addCompany(request, response,company);
					}else {
				return companyRepository.updateCompany(company);
				}
			}
		}}catch (CompanyDataException e) {
			throw e;
		}
		return null;
	}
	
	
	public String removeCompany(String companyId, String uid) {
		if(StringUtils.isNotBlank(companyId)){
			CompanyData company = companyRepository.getCompanyById(companyId);
			company.setAddedOrUpdatedOrRemovedUID(uid);
			company.setIsRemoved(Constants.YES);
			return companyRepository.updateCompany(company);
		}
		return "N";
	}

	public List<CompanyData> getAllCompanies() {
		List<CompanyData> companies = companyRepository.getAllCompanies();
		List<CompanyData> companiesNotRemoved = new ArrayList<CompanyData>();
		for(CompanyData company : companies){
			if(!Constants.YES.equalsIgnoreCase(company.getIsRemoved())){
				companiesNotRemoved.add(company);
			}
		}
		return companiesNotRemoved;
	}

	public CompanyData getCompanyById(String companyId) {
		if(StringUtils.isNotBlank(companyId)){
			CompanyData company = companyRepository.getCompanyById(companyId);
			if(!Constants.YES.equalsIgnoreCase(company.getIsRemoved())){
				return company;
			}
		}
		return null;
	}

	
}