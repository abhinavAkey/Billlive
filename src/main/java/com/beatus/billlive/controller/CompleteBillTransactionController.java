package com.beatus.billlive.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beatus.billlive.domain.model.CompleteBillTransaction;
import com.beatus.billlive.domain.model.JSendResponse;
import com.beatus.billlive.exception.CompleteBillTransactionException;
import com.beatus.billlive.service.CompleteBillTransactionService;
import com.beatus.billlive.session.management.SessionModel;
import com.beatus.billlive.utils.BillliveMediaType;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.validation.CompleteBillTransactionValidator;

@Controller
public class CompleteBillTransactionController extends BaseController{
	
	@Resource(name = "completeBillTransactionService")
	private CompleteBillTransactionService completeBillTransactionService;
	
	@Resource(name = "completeBillTransactionValidator")
	private CompleteBillTransactionValidator completeBillTransactionValidator;
	
    private JSendResponse<List<CompleteBillTransaction>> jsend(List<CompleteBillTransaction> completeBillTransactionList) {
    	if(completeBillTransactionList == null || completeBillTransactionList.size() == 0){
        	return new JSendResponse<List<CompleteBillTransaction>>(Constants.FAILURE, completeBillTransactionList);
    	}else {
    		return new JSendResponse<List<CompleteBillTransaction>>(Constants.SUCCESS, completeBillTransactionList);
    	}
	}
    
    private JSendResponse<CompleteBillTransaction> jsend(CompleteBillTransaction completeBillTransactionData) {
    	if(completeBillTransactionData == null){
    		return new JSendResponse<CompleteBillTransaction>(Constants.FAILURE, completeBillTransactionData);
    	}else {
    		return new JSendResponse<CompleteBillTransaction>(Constants.SUCCESS, completeBillTransactionData);
    	}
  	}
	
	@RequestMapping(value = "/company/getallcompleteBillTransactions", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<List<CompleteBillTransaction>> getAllCompleteBillTransactions(@RequestBody HttpServletRequest request, HttpServletResponse response) {
		SessionModel sessionModel = initSessionModel(request);
    	String companyId = sessionModel.getCompanyId();
    	List<CompleteBillTransaction> completeBillTransactionList = completeBillTransactionService.getAllCompleteBillTransactions(companyId);
		return jsend(completeBillTransactionList);
	}
	
	@RequestMapping(value = "/company/getcompleteBillTransaction", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<CompleteBillTransaction> getCompleteBillTransactionById(@RequestBody String billNumber, HttpServletRequest request, HttpServletResponse response) throws CompleteBillTransactionException {
		if(StringUtils.isNotBlank(billNumber)){
			SessionModel sessionModel = initSessionModel(request);
	    	String companyId = sessionModel.getCompanyId();
	    	CompleteBillTransaction completeBillTransaction = completeBillTransactionService.getCompleteBillTransactionById(billNumber, companyId);
			return jsend(completeBillTransaction);
		}else{
			throw new CompleteBillTransactionException("completeBillTransactionId passed cant be null or empty string");
		}
	}
	
	@RequestMapping(value= "/company/addcompleteBillTransaction", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<String> addCompleteBillTransaction(@RequestBody CompleteBillTransaction completeBillTransaction, HttpServletRequest request, HttpServletResponse response) throws CompleteBillTransactionException{
		if(completeBillTransactionValidator.validateCompleteBillTransaction(completeBillTransaction)){
			SessionModel sessionModel = initSessionModel(request);
	    	String companyId = sessionModel.getCompanyId();
	    	String isCompleteBillTransactionCreated = completeBillTransactionService.addCompleteBillTransaction(completeBillTransaction, companyId);
			return jsend(isCompleteBillTransactionCreated);
		}else{
			throw new CompleteBillTransactionException("CompleteBillTransaction data passed cant be null or empty");
		}	
	}
	

	@RequestMapping(value= "/company/updatecompleteBillTransaction", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<String> updateCompleteBillTransaction(@RequestBody CompleteBillTransaction completeBillTransaction, HttpServletRequest request, HttpServletResponse response) throws CompleteBillTransactionException{
		if(completeBillTransactionValidator.validateCompleteBillTransaction(completeBillTransaction)){
			SessionModel sessionModel = initSessionModel(request);
	    	String companyId = sessionModel.getCompanyId();
	    	String isCompleteBillTransactionUpdated = completeBillTransactionService.updateCompleteBillTransaction(completeBillTransaction, companyId);
			return jsend(isCompleteBillTransactionUpdated);
		}else{
			throw new CompleteBillTransactionException("CompleteBillTransaction data passed cant be null or empty");
		}	
	}
	
}