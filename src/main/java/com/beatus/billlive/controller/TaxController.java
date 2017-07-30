package com.beatus.billlive.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	@RequestMapping(value = "/company/getalltaxs", method = RequestMethod.GET)
	public @ResponseBody List<Tax> getAllTaxs(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(companyId)){
			List<Tax> taxList = taxService.getAllTaxs(companyId);
			return taxList;
    	}else {
    		return null;
    	}
	}
	
	@RequestMapping(value = "/company/gettax", method = RequestMethod.GET)
	public @ResponseBody Tax getTaxById(String taxId, HttpServletRequest request, HttpServletResponse response) throws TaxException {
		if(StringUtils.isNotBlank(taxId)){
			Tax taxData = taxService.getTaxById(taxId);
			return taxData;
		}else{
			throw new TaxException("taxId passed cant be null or empty string");
		}
	}
	
	//For add and update tax both
	@RequestMapping(value= "/company/addtax", method = RequestMethod.POST)
	public @ResponseBody String addTax(@RequestBody Tax taxData, HttpServletRequest request, HttpServletResponse response) throws TaxException{
		if(taxValidator.validateTax(taxData)){
			HttpSession session = request.getSession();
        	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
			String isTaxCreated = taxService.addTax(taxData, companyId);
			return isTaxCreated;
		}else{
			throw new TaxException("Tax data passed cant be null or empty string");
		}	
	}
	
	@RequestMapping("/company/removetax/{id}")
    public String removeTax(@PathVariable("id") String uid){
		
        this.taxService.removeTax(uid);
        return "redirect:/taxs";
    }
 
    @RequestMapping("/company/edittax/{id}")
    public String editTax(@PathVariable("id") int id, Model model){
       
        return "tax";
    }
	
}