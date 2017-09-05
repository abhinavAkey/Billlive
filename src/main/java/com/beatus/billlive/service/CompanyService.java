package com.beatus.billlive.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.beatus.billlive.domain.model.CompanyData;
import com.beatus.billlive.exception.CompanyDataException;
import com.beatus.billlive.repository.CompanyRepository;
import com.beatus.billlive.repository.data.listener.OnGetDataListener;
import com.beatus.billlive.service.exception.BillliveServiceException;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.utils.Utils;
import com.beatus.billlive.validation.CompanyValidator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

@Service
@Component("companyService")
public class CompanyService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);
	
	@Resource(name = "companyValidator")
	private CompanyValidator companyValidator;
	
	@Resource(name = "companyRepository")
	private CompanyRepository companyRepository;
	
	private CompanyData companyData = null;
	
	List<CompanyData> companysList = new ArrayList<CompanyData>();

	public String addCompany(HttpServletRequest request, HttpServletResponse response,CompanyData company) throws CompanyDataException {
		if(company == null){
			throw new CompanyDataException("Company Data cant be null");
		}
		try {
			boolean isValidated = companyValidator.validateCompanyData(company);
			if(isValidated){
				String companyId = company.getCompanyId();
				if(StringUtils.isNotBlank(company.getCompanyId())){
					CompanyData existingCompany = getCompanyById(company.getCompanyId());
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
					CompanyData existingCompany = getCompanyById(companyId);
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
			CompanyData company = getCompanyById(companyId);
			company.setAddedOrUpdatedOrRemovedUID(uid);
			company.setIsRemoved(Constants.YES);
			return companyRepository.updateCompany(company);
		}
		return "N";
	}

	public List<CompanyData> getAllCompanies() {
		companyRepository.getAllCompanies(new OnGetDataListener() {

			@Override
	        public void onStart() {
	        }

	        @Override
	        public void onSuccess(DataSnapshot companySnapshot) {
	        	companysList.clear();
		        for (DataSnapshot companyPostSnapshot: companySnapshot.getChildren()) {
		            CompanyData companyData = companyPostSnapshot.getValue(CompanyData.class);
		            companysList.add(companyData);
		        } 
	        	LOGGER.info(" The bill Snapshot Key is " + companySnapshot.getKey());
	        }

	        @Override
	        public void onFailed(DatabaseError databaseError) {
	           LOGGER.info("Error retrieving data");
	           throw new BillliveServiceException(databaseError.getMessage());
	        }
	    });
		List<CompanyData> companiesNotRemoved = new ArrayList<CompanyData>();
		for(CompanyData company : companysList){
			if(company != null && !Constants.YES.equalsIgnoreCase(company.getIsRemoved())){
				companiesNotRemoved.add(company);
			}
		}
		return companiesNotRemoved;
	}

	public CompanyData getCompanyById(String companyId) {
		if(StringUtils.isNotBlank(companyId)){
			companyRepository.getCompanyById(companyId, new OnGetDataListener() {

				@Override
		        public void onStart() {
		        }

		        @Override
		        public void onSuccess(DataSnapshot companySnapshot) {
		        	companyData = companySnapshot.getValue(CompanyData.class);
		        	LOGGER.info(" The bill Snapshot Key is " + companySnapshot.getKey());
		        }

		        @Override
		        public void onFailed(DatabaseError databaseError) {
		           LOGGER.info("Error retrieving data");
		           throw new BillliveServiceException(databaseError.getMessage());
		        }
		    });
			if(companyData != null && !Constants.YES.equalsIgnoreCase(companyData.getIsRemoved())){
				return companyData;
			}
		}
		return null;
	}

	
}