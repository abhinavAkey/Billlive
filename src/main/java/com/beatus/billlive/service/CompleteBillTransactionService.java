package com.beatus.billlive.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.beatus.billlive.domain.model.CompleteBillTransaction;
import com.beatus.billlive.domain.model.PaymentTransaction;
import com.beatus.billlive.exception.CompleteBillTransactionException;
import com.beatus.billlive.repository.CompleteBillTransactionRepository;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.utils.Utils;
import com.beatus.billlive.validation.CompleteBillTransactionValidator;

@Service
@Component("completeBillTransactionService")
public class CompleteBillTransactionService {
	
	@Resource(name = "completeBillTransactionValidator")
	private CompleteBillTransactionValidator completeBillTransactionValidator;
	
	@Resource(name = "completeBillTransactionRepository")
	private CompleteBillTransactionRepository completeBillTransactionRepository;

	public String addCompleteBillTransaction(CompleteBillTransaction completeBillTransaction) throws CompleteBillTransactionException {
		try {
			if(completeBillTransactionValidator.validateCompleteBillTransaction(completeBillTransaction)){
				String companyId = completeBillTransaction.getCompanyId();
				if(StringUtils.isNotBlank(completeBillTransaction.getBillNumber())){
					CompleteBillTransaction existingCompleteBillTransaction = completeBillTransactionRepository.getCompleteBillTransactionById(completeBillTransaction.getBillNumber(), companyId);
					if(existingCompleteBillTransaction != null){
						return updateCompleteBillTransaction(completeBillTransaction);
					}else {
						completeBillTransaction.setBillTransactionId(Utils.generateRandomKey(20));
						return completeBillTransactionRepository.addCompleteBillTransaction(completeBillTransaction, companyId);
					}
				}
			}
			}catch (CompleteBillTransactionException e) {
			throw e;
		}
		return "N";
	}


	public String updateCompleteBillTransaction(CompleteBillTransaction completeBillTransaction) throws CompleteBillTransactionException {
		try {
			if(completeBillTransactionValidator.validateCompleteBillTransaction(completeBillTransaction)){
				String companyId = completeBillTransaction.getCompanyId();
				if(StringUtils.isNotBlank(completeBillTransaction.getBillNumber())){
					CompleteBillTransaction existingCompleteBillTransaction = completeBillTransactionRepository.getCompleteBillTransactionById(completeBillTransaction.getBillNumber(), companyId);
					if(existingCompleteBillTransaction == null){
						return addCompleteBillTransaction(completeBillTransaction);
					}else {
						List<PaymentTransaction> paymentTransactions = existingCompleteBillTransaction.getPaymentTransactions();
						paymentTransactions.add(completeBillTransaction.getPaymentTransactions().get(0));
						Double amountPaid = Constants.DEFAULT_DOUBLE_VALUE;
						for(PaymentTransaction payment : paymentTransactions){
							amountPaid += payment.getAmountPaid();				
						}
						completeBillTransaction.setTotalAmountPaid(amountPaid);
						completeBillTransaction.setTotalAmountDue(completeBillTransaction.getTotalAmount() - amountPaid);
						return completeBillTransactionRepository.updateCompleteBillTransaction(completeBillTransaction);
					}
				} 
			}
			}catch (CompleteBillTransactionException e) {
				throw e;
			}
		return "N";
	}
	
	
	public List<CompleteBillTransaction> getAllCompleteBillTransactions(String companyId) {
		return completeBillTransactionRepository.getAllCompleteBillTransactions(companyId);
	}

	public CompleteBillTransaction getCompleteBillTransactionById(String billNumber, String companyId) {
		return completeBillTransactionRepository.getCompleteBillTransactionById(billNumber,companyId);
	}	
}