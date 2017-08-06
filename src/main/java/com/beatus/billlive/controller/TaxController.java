package com.beatus.billlive.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beatus.billlive.domain.model.Tax;
import com.beatus.billlive.service.TaxService;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.validation.TaxValidator;
import com.beatus.billlive.validation.exception.TaxException;

@Controller
@RequestMapping("/api")
public class TaxController {
	
	@Resource(name = "taxService")
	private TaxService taxService;
	
	@Resource(name = "taxValidator")
	private TaxValidator taxValidator;
	
	//For add and update tax both
	@RequestMapping(value= "/company/tax/add", method = RequestMethod.POST)
	public @ResponseBody String addTax(@RequestBody Tax taxData, HttpServletRequest request, HttpServletResponse response) throws TaxException{
		if(taxValidator.validateTax(taxData)){
			HttpSession session = request.getSession();
        	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
			String taxId = taxService.addTax(request, response, taxData, companyId);
			return taxId;
		}else{
			throw new TaxException("Tax data passed cant be null or empty string");
		}	
	}
	
	@RequestMapping(value= "/company/tax/update", method = RequestMethod.POST)
	public @ResponseBody String updateTax(@RequestBody Tax taxData, HttpServletRequest request, HttpServletResponse response) throws TaxException{
		if(taxValidator.validateTax(taxData)){
			HttpSession session = request.getSession();
        	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
			String isTaxUpdated = taxService.updateTax(request, response, taxData, companyId);
			return isTaxUpdated;
		}else{
			throw new TaxException("Tax data passed cant be null or empty string");
		}	
	}
	
	@RequestMapping("/company/tax/remove/{id}")
    public @ResponseBody String removeTax(@PathVariable("id") String taxId,  HttpServletRequest request, HttpServletResponse response) throws TaxException{
		if(StringUtils.isNotBlank(taxId)){
			HttpSession session = request.getSession();
        	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
        	String isTaxRemoved = taxService.removeTax(companyId, taxId);
    		return isTaxRemoved;
		}else{
			throw new TaxException("TaxId passed cant be null or empty string");
		}
    }
	
	@RequestMapping(value = "/company/getalltaxs", method = RequestMethod.GET)
	public @ResponseBody List<Tax> getAllTaxs(HttpServletRequest request, HttpServletResponse response) throws TaxException {
		HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(companyId)){
			List<Tax> taxList = taxService.getAllTaxs(companyId);
			return taxList;
    	}else {
			throw new TaxException("TaxId passed cant be null or empty string");
    	}
	}
	
	@RequestMapping(value = "/company/gettax/{id}", method = RequestMethod.GET)
	public @ResponseBody Tax getTaxById(@PathVariable("id") String taxId, HttpServletRequest request, HttpServletResponse response) throws TaxException {
		HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(taxId) && StringUtils.isNotBlank(companyId)){
			Tax tax = taxService.getTaxById(companyId, taxId);
			return tax;
    	}else {
			throw new TaxException("TaxId passed cant be null or empty string");
    	}
	}
	
}