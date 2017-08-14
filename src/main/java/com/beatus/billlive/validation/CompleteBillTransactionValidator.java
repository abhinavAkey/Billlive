package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.CompleteBillTransaction;
import com.beatus.billlive.domain.model.PaymentTransaction;
import com.beatus.billlive.validation.exception.CompleteBillTransactionException;

@Component("completeBillTransactionValidator")
public class CompleteBillTransactionValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(CompleteBillTransactionValidator.class);

	public boolean validateCompleteBillTransaction(CompleteBillTransaction completeBillTransaction) throws CompleteBillTransactionException{
		LOGGER.info(" Validating CompleteBillTransaction " + completeBillTransaction);

		if(completeBillTransaction == null || StringUtils.isBlank(String.valueOf(completeBillTransaction.getBillNumber()))){
			throw new CompleteBillTransactionException("CompleteBillTransaction data is null");
		}
		if(StringUtils.isBlank(completeBillTransaction.getBillTransactionId())){
			throw new CompleteBillTransactionException("CompleteBillTransaction, the billTransactionId field is not available, for the bill number " + completeBillTransaction.getBillNumber());
		}
		if(StringUtils.isBlank(completeBillTransaction.getUid())){
			throw new CompleteBillTransactionException("CompleteBillTransaction, the uid field is not available, for the bill number " + completeBillTransaction.getBillNumber());
		}
		if(StringUtils.isBlank(completeBillTransaction.getBillTransactionId())){
			throw new CompleteBillTransactionException("CompleteBillTransaction, the billTransactionId field is not available, for the bill number " + completeBillTransaction.getBillNumber());
		}
		if(completeBillTransaction.getPaymentTransactions() != null && completeBillTransaction.getPaymentTransactions().size() > 0){
			for(PaymentTransaction paymentTransaction : completeBillTransaction.getPaymentTransactions()){
				if(paymentTransaction.getDate() != null){
					throw new CompleteBillTransactionException("CompleteBillTransaction - PaymentTransaction, the date field is not available in PaymentTransaction for the bill number " + completeBillTransaction.getBillNumber());
				}
				if(paymentTransaction.getAmountPaid() != null){
					throw new CompleteBillTransactionException("CompleteBillTransaction - PaymentTransaction, the amountPaid field is not available in PaymentTransaction for the bill number " + completeBillTransaction.getBillNumber());
				}if(paymentTransaction.getAmountDue() != null){
					throw new CompleteBillTransactionException("CompleteBillTransaction - PaymentTransaction, the amountDue field is not available in PaymentTransaction for the bill number " + completeBillTransaction.getBillNumber());
				}
			}
		}
		if(completeBillTransaction.getTotalAmountPaid() != null){
			throw new CompleteBillTransactionException("CompleteBillTransaction, the totalAmountPaid field is not available, for the bill number " + completeBillTransaction.getBillNumber());
		}
		if(completeBillTransaction.getTotalAmountDue() != null){
			throw new CompleteBillTransactionException("CompleteBillTransaction, the totalAmountDue field is not available, for the bill number " + completeBillTransaction.getBillNumber());
		}
		return true;
	}

}
