package com.beatus.billlive.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.beatus.billlive.domain.model.Tax;
import com.beatus.billlive.repository.TaxRepository;
import com.beatus.billlive.validation.TaxValidator;
import com.beatus.billlive.validation.exception.TaxException;

@Service
@Component("taxService")
public class TaxService {
	
	@Resource(name = "taxValidator")
	private TaxValidator taxValidator;
	
	@Resource(name = "taxRepository")
	private TaxRepository taxRepository;

	public String addTax(Tax tax) throws TaxException {
		try {
			if(taxValidator.validateTax(tax)){
				if(StringUtils.isNotBlank(tax.getTaxId()) && StringUtils.isNotBlank(tax.getTaxId())){
					Tax existingTax = taxRepository.getTaxById(tax.getTaxId());
					if(existingTax != null){
						return updateTax(tax);
					}
					return "N";
				}
			}
			}catch (TaxException e) {
			throw e;
		}
		return "N";
	}


	public String updateTax(Tax tax) throws TaxException {
		try {
			if(taxValidator.validateTax(tax)){
				if(StringUtils.isNotBlank(tax.getTaxId()) && StringUtils.isNotBlank(tax.getTaxId())){
					Tax existingTax = taxRepository.getTaxById(tax.getTaxId());
					if(existingTax == null){
						return addTax(tax);
					}else {
				return taxRepository.updateTax(tax);
			}
		} }}catch (TaxException e) {
			throw e;
		}
		return "N";
	}
	
	
	public String removeTax(String taxId) {
		return taxRepository.removeTax(taxId);
	}

	public List<Tax> getAllTaxs() {
		return taxRepository.getAllTaxs();
	}

	public Tax getTaxById(String taxId) {
		return taxRepository.getTaxById(taxId);
	}

	
}