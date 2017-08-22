package com.beatus.billlive.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller
public class CompleteBillTransactionController extends BaseController{
    private static final Logger LOG = LoggerFactory.getLogger(CompleteBillTransactionController.class);
	
	@Resource(name = "completeBillTransactionService")
	private CompleteBillTransactionService completeBillTransactionService;
	
	
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
		LOG.info("In getAllCompleteBillTransactions method of CompleteBillTransactionController");
		SessionModel sessionModel = initSessionModel(request);
    	String companyId = sessionModel.getCompanyId();
    	List<CompleteBillTransaction> completeBillTransactionList = completeBillTransactionService.getAllCompleteBillTransactions(companyId);
		return jsend(completeBillTransactionList);
	}
	
	@RequestMapping(value = "/company/getcompleteBillTransaction", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<CompleteBillTransaction> getCompleteBillTransactionById(@RequestBody String billNumber, HttpServletRequest request, HttpServletResponse response) throws CompleteBillTransactionException {
		LOG.info("In getCompleteBillTransactionById method of CompleteBillTransactionController");
		if(StringUtils.isNotBlank(billNumber)){
			SessionModel sessionModel = initSessionModel(request);
	    	String companyId = sessionModel.getCompanyId();
	    	CompleteBillTransaction completeBillTransaction = completeBillTransactionService.getCompleteBillTransactionById(billNumber, companyId);
			return jsend(completeBillTransaction);
		}else{
			LOG.error("Bill Number is null in getAllCompleteBillTransactions method of CompleteBillTransactionController");
			throw new CompleteBillTransactionException("completeBillTransactionId passed cant be null or empty string");
		}
	}
	
	@RequestMapping(value= "/company/addcompleteBillTransaction", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<String> addCompleteBillTransaction(@RequestBody CompleteBillTransaction completeBillTransaction, HttpServletRequest request, HttpServletResponse response) throws CompleteBillTransactionException{
		LOG.info("In addCompleteBillTransaction method of CompleteBillTransactionController");
		if(completeBillTransaction != null){
			SessionModel sessionModel = initSessionModel(request);
	    	String companyId = sessionModel.getCompanyId();
	    	String isCompleteBillTransactionCreated = completeBillTransactionService.addCompleteBillTransaction(completeBillTransaction, companyId);
			return jsend(isCompleteBillTransactionCreated);
		}else{
			LOG.error("completeBillTransaction is null in addCompleteBillTransaction method of CompleteBillTransactionController");
			throw new CompleteBillTransactionException("CompleteBillTransaction data passed cant be null or empty");
		}	
	}
	

	@RequestMapping(value= "/company/updatecompleteBillTransaction", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<String> updateCompleteBillTransaction(@RequestBody CompleteBillTransaction completeBillTransaction, HttpServletRequest request, HttpServletResponse response) throws CompleteBillTransactionException{
		LOG.info("In updateCompleteBillTransaction method of CompleteBillTransactionController");
		if(completeBillTransaction != null){
			SessionModel sessionModel = initSessionModel(request);
	    	String companyId = sessionModel.getCompanyId();
	    	String isCompleteBillTransactionUpdated = completeBillTransactionService.updateCompleteBillTransaction(completeBillTransaction, companyId);
			return jsend(isCompleteBillTransactionUpdated);
		}else{
			LOG.error("completeBillTransaction is null in updateCompleteBillTransaction method of CompleteBillTransactionController");
			throw new CompleteBillTransactionException("CompleteBillTransaction data passed cant be null or empty");
		}	
	}
	
}