package com.beatus.billlive.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.beatus.billlive.domain.model.Tax;
import com.beatus.billlive.exception.TaxException;
import com.beatus.billlive.repository.TaxRepository;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.utils.Utils;
import com.beatus.billlive.validation.TaxValidator;

@Service
@Component("taxService")
public class TaxService {
	
	@Resource(name = "taxValidator")
	private TaxValidator taxValidator;
	
	@Resource(name = "taxRepository")
	private TaxRepository taxRepository;

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
					Tax existingTax = taxRepository.getTaxById(companyId,tax.getTaxId());
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
					Tax existingTax = taxRepository.getTaxById(companyId, tax.getTaxId());
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
	
	
	public String removeTax(String companyId,String taxId) {
		if(StringUtils.isNotBlank(taxId) && StringUtils.isNotBlank(companyId)){
			Tax tax = taxRepository.getTaxById(companyId, taxId);
			tax.setIsRemoved(Constants.YES);
			return taxRepository.updateTax(tax);
		}
		return "N";
	}

	public List<Tax> getAllTaxs(String companyId) {
		List<Tax> taxs = taxRepository.getAllTaxs(companyId);
		List<Tax> taxsNotRemoved = new ArrayList<Tax>();
		for(Tax tax : taxs){
			if(!Constants.YES.equalsIgnoreCase(tax.getIsRemoved())){
				taxsNotRemoved.add(tax);
			}
		}
		return taxsNotRemoved;
	}

	public Tax getTaxById(String companyId, String taxId) {
		if(StringUtils.isNotBlank(taxId) && StringUtils.isNotBlank(companyId)){
			Tax tax = taxRepository.getTaxById(companyId, taxId);
			if(!Constants.YES.equalsIgnoreCase(tax.getIsRemoved())){
				return tax;
			}
		}
		return null;
	}

	
}