package com.beatus.billlive.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beatus.billlive.domain.model.CompanyData;
import com.beatus.billlive.domain.model.JSendResponse;
import com.beatus.billlive.exception.CompanyDataException;
import com.beatus.billlive.service.CompanyService;
import com.beatus.billlive.session.management.SessionModel;
import com.beatus.billlive.utils.BillliveMediaType;
import com.beatus.billlive.utils.Constants;

@Controller
public class CompanyController extends BaseController  {
	

	@Resource(name = "companyService")
	private CompanyService companyService;
    
	private JSendResponse<List<CompanyData>> jsend(List<CompanyData> companyDataList) {
    	if(companyDataList == null || companyDataList.size() == 0){
        	return new JSendResponse<List<CompanyData>>(Constants.FAILURE, companyDataList);
    	}else {
    		return new JSendResponse<List<CompanyData>>(Constants.SUCCESS, companyDataList);
    	}
	}
    
    private JSendResponse<CompanyData> jsend(CompanyData companyDataData) {
    	if(companyDataData == null){
    		return new JSendResponse<CompanyData>(Constants.FAILURE, companyDataData);
    	}else {
    		return new JSendResponse<CompanyData>(Constants.SUCCESS, companyDataData);
    	}
  	}
	
	//For add and update company both
	@RequestMapping(value= "/company/add", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<String> addCompany(@RequestBody CompanyData companyData, HttpServletRequest request, HttpServletResponse response) throws CompanyDataException{
		SessionModel sessionModel = initSessionModel(request);
		String companyId =  sessionModel.getCompanyId();
		companyId = companyService.addCompany(request, response, companyData, companyId);
		sessionModel.setCompanyId(companyId);
		return jsend(companyId);
	}
	
	@RequestMapping(value= "/company/update", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
    public @ResponseBody JSendResponse<String> editCompany(@RequestBody CompanyData companyData, HttpServletRequest request, HttpServletResponse response) throws CompanyDataException{
    	
		SessionModel sessionModel = initSessionModel(request);
		String companyId = sessionModel.getCompanyId();
		companyId = companyService.updateCompany(request, response, companyData, companyId);
      	sessionModel.setCompanyId(companyId);
		return jsend(companyId);
    }
    
    @RequestMapping(value ="/company/remove/{id}", method = RequestMethod.DELETE, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
    public @ResponseBody JSendResponse<String> removeCompany(@PathVariable("id") String companyId, HttpServletRequest request, HttpServletResponse response) throws CompanyDataException{	
    	if(StringUtils.isNotBlank(companyId)){
        	String isCompanyRemoved = companyService.removeCompany(companyId);
    		return jsend(isCompanyRemoved);
		}else{
			throw new CompanyDataException("companyId passed cant be null or empty string");
		}
    }
 
    
    @RequestMapping(value = "/company/getcompany/{id}", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<CompanyData> getCompanyById(@PathVariable("id") String companyId, HttpServletRequest request, HttpServletResponse response) throws CompanyDataException {
    	
    	if(StringUtils.isNotBlank(companyId) && StringUtils.isNotBlank(companyId)){
	    	CompanyData companyData = companyService.getCompanyById(companyId);
			return jsend(companyData);
		}else{
			throw new CompanyDataException("companyId passed cant be null or empty string");
		}
		
	}
    
    @RequestMapping(value = "/company/getallcompanys", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<List<CompanyData>> getAllCompanys(HttpServletRequest request, HttpServletResponse response) throws CompanyDataException {
    	List<CompanyData> companyList = companyService.getAllCompanies();
			return jsend(companyList);
		
	}

}
