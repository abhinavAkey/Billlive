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
import com.beatus.billlive.service.CompleteBillTransactionService;
import com.beatus.billlive.session.management.SessionModel;
import com.beatus.billlive.utils.BillliveMediaType;
import com.beatus.billlive.validation.CompleteBillTransactionValidator;
import com.beatus.billlive.validation.exception.CompleteBillTransactionException;

@Controller
public class CompleteBillTransactionController extends BaseController{
	
	@Resource(name = "completeBillTransactionService")
	private CompleteBillTransactionService completeBillTransactionService;
	
	@Resource(name = "completeBillTransactionValidator")
	private CompleteBillTransactionValidator completeBillTransactionValidator;
	
	@RequestMapping(value = "/company/getallcompleteBillTransactions", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody List<CompleteBillTransaction> getAllCompleteBillTransactions(@RequestBody HttpServletRequest request, HttpServletResponse response) {
		SessionModel sessionModel = initSessionModel(request);
    	String companyId = sessionModel.getCompanyId();
    	List<CompleteBillTransaction> completeBillTransactionList = completeBillTransactionService.getAllCompleteBillTransactions(companyId);
		return completeBillTransactionList;
	}
	
	@RequestMapping(value = "/company/getcompleteBillTransaction", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody CompleteBillTransaction getCompleteBillTransactionById(@RequestBody String billNumber, HttpServletRequest request, HttpServletResponse response) throws CompleteBillTransactionException {
		if(StringUtils.isNotBlank(billNumber)){
			SessionModel sessionModel = initSessionModel(request);
	    	String companyId = sessionModel.getCompanyId();
	    	CompleteBillTransaction completeBillTransaction = completeBillTransactionService.getCompleteBillTransactionById(billNumber, companyId);
			return completeBillTransaction;
		}else{
			throw new CompleteBillTransactionException("completeBillTransactionId passed cant be null or empty string");
		}
	}
	
	@RequestMapping(value= "/company/addcompleteBillTransaction", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody String addCompleteBillTransaction(@RequestBody CompleteBillTransaction completeBillTransaction, HttpServletRequest request, HttpServletResponse response) throws CompleteBillTransactionException{
		if(completeBillTransactionValidator.validateCompleteBillTransaction(completeBillTransaction)){
			SessionModel sessionModel = initSessionModel(request);
	    	String companyId = sessionModel.getCompanyId();
	    	String isCompleteBillTransactionCreated = completeBillTransactionService.addCompleteBillTransaction(completeBillTransaction, companyId);
			return isCompleteBillTransactionCreated;
		}else{
			throw new CompleteBillTransactionException("CompleteBillTransaction data passed cant be null or empty");
		}	
	}
	

	@RequestMapping(value= "/company/updatecompleteBillTransaction", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody String updateCompleteBillTransaction(@RequestBody CompleteBillTransaction completeBillTransaction, HttpServletRequest request, HttpServletResponse response) throws CompleteBillTransactionException{
		if(completeBillTransactionValidator.validateCompleteBillTransaction(completeBillTransaction)){
			SessionModel sessionModel = initSessionModel(request);
	    	String companyId = sessionModel.getCompanyId();
	    	String isCompleteBillTransactionUpdated = completeBillTransactionService.updateCompleteBillTransaction(completeBillTransaction, companyId);
			return isCompleteBillTransactionUpdated;
		}else{
			throw new CompleteBillTransactionException("CompleteBillTransaction data passed cant be null or empty");
		}	
	}
	
}