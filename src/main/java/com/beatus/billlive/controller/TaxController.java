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

import com.beatus.billlive.domain.model.JSendResponse;
import com.beatus.billlive.domain.model.Tax;
import com.beatus.billlive.exception.TaxException;
import com.beatus.billlive.service.TaxService;
import com.beatus.billlive.utils.BillliveMediaType;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.validation.TaxValidator;

@Controller
public class TaxController extends BaseController{
	
	@Resource(name = "taxService")
	private TaxService taxService;
	
	@Resource(name = "taxValidator")
	private TaxValidator taxValidator;
	
	private JSendResponse<List<Tax>> jsend(List<Tax> taxList) {
    	if(taxList == null || taxList.size() == 0){
        	return new JSendResponse<List<Tax>>(Constants.FAILURE, taxList);
    	}else {
    		return new JSendResponse<List<Tax>>(Constants.SUCCESS, taxList);
    	}
	}
    
    private JSendResponse<Tax> jsend(Tax taxData) {
    	if(taxData == null){
    		return new JSendResponse<Tax>(Constants.FAILURE, taxData);
    	}else {
    		return new JSendResponse<Tax>(Constants.SUCCESS, taxData);
    	}
  	}
	
	//For add and update tax both
	@RequestMapping(value= "/company/tax/add", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<String> addTax(@RequestBody Tax taxData, HttpServletRequest request, HttpServletResponse response) throws TaxException{
		if(taxValidator.validateTax(taxData)){
			HttpSession session = request.getSession();
        	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
			String taxId = taxService.addTax(request, response, taxData, companyId);
			return jsend(taxId);
		}else{
			throw new TaxException("Tax data passed cant be null or empty string");
		}	
	}
	
	@RequestMapping(value= "/company/tax/update", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<String> updateTax(@RequestBody Tax taxData, HttpServletRequest request, HttpServletResponse response) throws TaxException{
		if(taxValidator.validateTax(taxData)){
			HttpSession session = request.getSession();
        	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
			String isTaxUpdated = taxService.updateTax(request, response, taxData, companyId);
			return jsend(isTaxUpdated);
		}else{
			throw new TaxException("Tax data passed cant be null or empty string");
		}	
	}
	
	@RequestMapping(value ="/company/tax/remove/{id}", method = RequestMethod.DELETE, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
    public @ResponseBody JSendResponse<String> removeTax(@PathVariable("id") String taxId,  HttpServletRequest request, HttpServletResponse response) throws TaxException{
		if(StringUtils.isNotBlank(taxId)){
			HttpSession session = request.getSession();
        	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
        	String isTaxRemoved = taxService.removeTax(companyId, taxId);
    		return jsend(isTaxRemoved);
		}else{
			throw new TaxException("TaxId passed cant be null or empty string");
		}
    }
	
	@RequestMapping(value = "/company/getalltaxs", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<List<Tax>> getAllTaxs(HttpServletRequest request, HttpServletResponse response) throws TaxException {
		HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(companyId)){
			List<Tax> taxList = taxService.getAllTaxs(companyId);
			return jsend(taxList);
    	}else {
			throw new TaxException("TaxId passed cant be null or empty string");
    	}
	}
	
	@RequestMapping(value = "/company/gettax/{id}", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<Tax> getTaxById(@PathVariable("id") String taxId, HttpServletRequest request, HttpServletResponse response) throws TaxException {
		HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(taxId) && StringUtils.isNotBlank(companyId)){
			Tax tax = taxService.getTaxById(companyId, taxId);
			return jsend(tax);
    	}else {
			throw new TaxException("TaxId passed cant be null or empty string");
    	}
	}
	
}