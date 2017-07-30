package com.beatus.billlive.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.beatus.billlive.domain.model.CompleteBillTransaction;
import com.beatus.billlive.repository.CompleteBillTransactionRepository;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.validation.CompanyBillTransactionValidator;
import com.beatus.billlive.validation.exception.CompleteBillTransactionException;

@Service
@Component("completeBillTransactionService")
public class CompleteBillTransactionService {
	
	@Resource(name = "completeBillTransactionValidator")
	private CompanyBillTransactionValidator completeBillTransactionValidator;
	
	@Resource(name = "completeBillTransactionRepository")
	private CompleteBillTransactionRepository completeBillTransactionRepository;

	public String addCompleteBillTransaction(CompleteBillTransaction completeBillTransaction, String companyId) throws CompleteBillTransactionException {
		try {
			if(completeBillTransactionValidator.validateCompleteBillTransaction(completeBillTransaction)){
				if(StringUtils.isNotBlank(completeBillTransaction.getBillNumber()) && StringUtils.isNotBlank(completeBillTransaction.getBillNumber())){
					CompleteBillTransaction existingCompleteBillTransaction = completeBillTransactionRepository.getCompleteBillTransactionById(completeBillTransaction.getBillNumber(), companyId);
					if(existingCompleteBillTransaction != null){
						return updateCompleteBillTransaction(completeBillTransaction, companyId);
					}
					return "N";
				}
			}
			}catch (CompleteBillTransactionException e) {
			throw e;
		}
		return "N";
	}


	public String updateCompleteBillTransaction(CompleteBillTransaction completeBillTransaction, String companyId) throws CompleteBillTransactionException {
		try {
			if(completeBillTransactionValidator.validateCompleteBillTransaction(completeBillTransaction)){
				if(StringUtils.isNotBlank(completeBillTransaction.getBillNumber()) && StringUtils.isNotBlank(completeBillTransaction.getBillNumber())){
					CompleteBillTransaction existingCompleteBillTransaction = completeBillTransactionRepository.getCompleteBillTransactionById(completeBillTransaction.getBillNumber(), companyId);
					if(existingCompleteBillTransaction == null){
						return addCompleteBillTransaction(completeBillTransaction, companyId);
					}else {
				return completeBillTransactionRepository.updateCompleteBillTransaction(completeBillTransaction);
			}
		} }}catch (CompleteBillTransactionException e) {
			throw e;
		}
		return "N";
	}
	
	
	public List<CompleteBillTransaction> getAllCompleteBillTransactions(String billNumber) {
		return completeBillTransactionRepository.getAllCompleteBillTransactions(billNumber);
	}

	public CompleteBillTransaction getCompleteBillTransactionById(String billNumber, String companyId) {
		
		
		return completeBillTransactionRepository.getCompleteBillTransactionById(billNumber,companyId);
	}

	
}