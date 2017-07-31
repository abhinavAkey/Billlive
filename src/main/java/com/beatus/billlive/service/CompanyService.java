package com.beatus.billlive.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.beatus.billlive.domain.model.CompanyData;
import com.beatus.billlive.repository.CompanyRepository;
import com.beatus.billlive.utils.Utils;
import com.beatus.billlive.validation.CompanyValidator;
import com.beatus.billlive.validation.exception.CompanyDataException;

@Service
@Component("companyService")
public class CompanyService {
	
	@Resource(name = "companyValidator")
	private CompanyValidator companyValidator;
	
	@Resource(name = "companyRepository")
	private CompanyRepository companyRepository;

	public String addCompany(CompanyData company) throws CompanyDataException {
		try {
			if(companyValidator.validateCompanyData(company)){
				if(StringUtils.isNotBlank(company.getCompanyId())){
					CompanyData existingCompany = companyRepository.getCompanyById(company.getCompanyId());
					if(existingCompany != null){
						return updateCompany(company);
					}
				}else {
					String companyId = Utils.generateRandomKey(20);
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


	public String updateCompany(CompanyData company) throws CompanyDataException {
		try {
			if(companyValidator.validateCompanyData(company)){
				if(company.getCompanyId() == null){
					return addCompany(company);
				}else {
					String companyId = companyRepository.updateCompany(company);
					return companyId;
				}
			}
		}catch (CompanyDataException e) {
			throw e;
		}
		return null;
	}
	
	
	public String removeCompany(String companyId) {
		return companyRepository.removeCompany(companyId);
	}

	public List<CompanyData> getAllCompanys(String companyId) {
		return companyRepository.getAllCompanys(companyId);
	}

	public CompanyData getCompanyById(String companyId) {
		return companyRepository.getCompanyById(companyId);
	}

	
}