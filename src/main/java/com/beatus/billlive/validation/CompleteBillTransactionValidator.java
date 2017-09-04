package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.CompleteBillTransaction;
import com.beatus.billlive.domain.model.PaymentTransaction;
import com.beatus.billlive.validation.exception.BillliveClientValidationException;

@Component("completeBillTransactionValidator")
public class CompleteBillTransactionValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(CompleteBillTransactionValidator.class);

	public boolean validateCompleteBillTransaction(CompleteBillTransaction completeBillTransaction) throws BillliveClientValidationException{
		LOGGER.info(" Validating CompleteBillTransaction " + completeBillTransaction);

		if(completeBillTransaction == null || StringUtils.isBlank(String.valueOf(completeBillTransaction.getBillNumber()))){
			throw new BillliveClientValidationException("CompleteBillTransaction","CompleteBillTransaction data is null");
		}
		if(StringUtils.isBlank(completeBillTransaction.getBillTransactionId())){
			throw new BillliveClientValidationException("billTransactionId","CompleteBillTransaction, the billTransactionId field is not available, for the bill number " + completeBillTransaction.getBillNumber());
		}
		if(StringUtils.isBlank(completeBillTransaction.getUid())){
			throw new BillliveClientValidationException("uid","CompleteBillTransaction, the uid field is not available, for the bill number " + completeBillTransaction.getBillNumber());
		}
		if(StringUtils.isBlank(completeBillTransaction.getBillTransactionId())){
			throw new BillliveClientValidationException("billTransactionId","CompleteBillTransaction, the billTransactionId field is not available, for the bill number " + completeBillTransaction.getBillNumber());
		}
		if(completeBillTransaction.getPaymentTransactions() != null && completeBillTransaction.getPaymentTransactions().size() > 0){
			for(PaymentTransaction paymentTransaction : completeBillTransaction.getPaymentTransactions()){
				if(paymentTransaction.getDate() != null){
					throw new BillliveClientValidationException("PaymentTransaction - date","CompleteBillTransaction - PaymentTransaction, the date field is not available in PaymentTransaction for the bill number " + completeBillTransaction.getBillNumber());
				}
				if(paymentTransaction.getAmountPaid() != null){
					throw new BillliveClientValidationException("PaymentTransaction - amountPaid","CompleteBillTransaction - PaymentTransaction, the amountPaid field is not available in PaymentTransaction for the bill number " + completeBillTransaction.getBillNumber());
				}if(paymentTransaction.getAmountDue() != null){
					throw new BillliveClientValidationException("PaymentTransaction - amountDue","CompleteBillTransaction - PaymentTransaction, the amountDue field is not available in PaymentTransaction for the bill number " + completeBillTransaction.getBillNumber());
				}
			}
		}
		if(completeBillTransaction.getTotalAmountPaid() != null){
			throw new BillliveClientValidationException("totalAmountPaid","CompleteBillTransaction, the totalAmountPaid field is not available, for the bill number " + completeBillTransaction.getBillNumber());
		}
		if(completeBillTransaction.getTotalAmountDue() != null){
			throw new BillliveClientValidationException("totalAmountDue","CompleteBillTransaction, the totalAmountDue field is not available, for the bill number " + completeBillTransaction.getBillNumber());
		}
		return true;
	}

}
