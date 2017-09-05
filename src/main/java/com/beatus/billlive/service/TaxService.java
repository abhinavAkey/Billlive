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

import com.beatus.billlive.domain.model.Tax;
import com.beatus.billlive.exception.TaxException;
import com.beatus.billlive.repository.TaxRepository;
import com.beatus.billlive.repository.data.listener.OnGetDataListener;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.utils.Utils;
import com.beatus.billlive.validation.TaxValidator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

@Service
@Component("taxService")
public class TaxService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);
	
	@Resource(name = "taxValidator")
	private TaxValidator taxValidator;
	
	@Resource(name = "taxRepository")
	private TaxRepository taxRepository;
	
	private Tax tax = null;
	
	List<Tax> taxsList = new ArrayList<Tax>();

	public String addTax(HttpServletRequest request, HttpServletResponse response,Tax tax, String companyId) throws TaxException {
		if(tax == null){
			throw new TaxException("Tax Data cant be null");
		}
		try {
			boolean isValidated = taxValidator.validateTax(tax);
			if(isValidated){
				if(StringUtils.isBlank(companyId)){
					companyId = tax.getCompanyId();
				}
				if(StringUtils.isNotBlank(tax.getTaxId())){
					Tax existingTax = getTaxById(companyId,tax.getTaxId());
					if(existingTax != null){
						return updateTax(request,response,tax,companyId);
					}
					return tax.getTaxId();
				}else {
					String taxId = Utils.generateRandomKey(20);
					tax.setTaxId(taxId);
					return taxRepository.addTax(tax);
				}
			}
			}catch (TaxException e) {
			throw e;
		}
		return null;
	}


	public String updateTax(HttpServletRequest request, HttpServletResponse response,Tax tax, String companyId) throws TaxException {
		try {
			if(taxValidator.validateTax(tax)){
				if(StringUtils.isNotBlank(tax.getTaxId()) && StringUtils.isNotBlank(tax.getTaxId())){
					Tax existingTax = getTaxById(companyId, tax.getTaxId());
					if(existingTax == null){
						return addTax(request,response,tax,companyId);
					}else {
				return taxRepository.updateTax(tax);
			}
		} }}catch (TaxException e) {
			throw e;
		}
		return null;
	}
	
	
	public String removeTax(String companyId, String uid, String taxId) {
		if(StringUtils.isNotBlank(taxId) && StringUtils.isNotBlank(companyId)){
			Tax tax = getTaxById(companyId, taxId);
			tax.setUid(uid);
			tax.setIsRemoved(Constants.YES);
			return taxRepository.updateTax(tax);
		}
		return "N";
	}

	public List<Tax> getAllTaxs(String companyId) {
		taxRepository.getAllTaxs(companyId, new OnGetDataListener() {
	        @Override
	        public void onStart() {
	            //DO SOME THING WHEN START GET DATA HERE
	        }

	        @Override
	        public void onSuccess(DataSnapshot taxSnapshot) {
	        	taxsList.clear();
		        for (DataSnapshot taxPostSnapshot: taxSnapshot.getChildren()) {
		            Tax tax = taxPostSnapshot.getValue(Tax.class);
		            taxsList.add(tax);
		        }
	        	LOGGER.info("The key for the transaction is " + taxSnapshot.getKey());
	        }

	        @Override
	        public void onFailed(DatabaseError databaseError) {
	           //DO SOME THING WHEN GET DATA FAILED HERE
	        }
	    });
		List<Tax> taxsNotRemoved = new ArrayList<Tax>();
		for(Tax tax : taxsList){
			if(tax != null && !Constants.YES.equalsIgnoreCase(tax.getIsRemoved())){
				taxsNotRemoved.add(tax);
			}
		}
		return taxsNotRemoved;
	}

	public Tax getTaxById(String companyId, String taxId) {
		if(StringUtils.isNotBlank(taxId) && StringUtils.isNotBlank(companyId)){
			taxRepository.getTaxById(companyId, taxId, new OnGetDataListener() {
		        @Override
		        public void onStart() {
		            //DO SOME THING WHEN START GET DATA HERE
		        }

		        @Override
		        public void onSuccess(DataSnapshot dataSnapshot) {
		        	tax = dataSnapshot.getValue(Tax.class);  
		        	LOGGER.info(dataSnapshot.getKey() + " was " + tax.getTaxId());
		        }

		        @Override
		        public void onFailed(DatabaseError databaseError) {
		           //DO SOME THING WHEN GET DATA FAILED HERE
		        }
		    });
			if(tax != null && !Constants.YES.equalsIgnoreCase(tax.getIsRemoved())){
				return tax;
			}
		}
		return tax;
	}

	
}