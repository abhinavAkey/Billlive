package com.beatus.billlive.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.beatus.billlive.domain.model.CompleteBillTransaction;
import com.beatus.billlive.domain.model.PaymentTransaction;
import com.beatus.billlive.exception.CompleteBillTransactionException;
import com.beatus.billlive.repository.CompleteBillTransactionRepository;
import com.beatus.billlive.repository.data.listener.OnGetDataListener;
import com.beatus.billlive.service.exception.BillliveServiceException;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.utils.Utils;
import com.beatus.billlive.validation.CompleteBillTransactionValidator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

@Service
@Component("completeBillTransactionService")
public class CompleteBillTransactionService {
	
	private static final Logger logger = LoggerFactory.getLogger(CompleteBillTransactionRepository.class);

	@Resource(name = "completeBillTransactionValidator")
	private CompleteBillTransactionValidator completeBillTransactionValidator;
	
	@Resource(name = "completeBillTransactionRepository")
	private CompleteBillTransactionRepository completeBillTransactionRepository;
	
	private CompleteBillTransaction completeBillTransactionData = null;
	
	List<CompleteBillTransaction> completeBillTransactionsList = new ArrayList<CompleteBillTransaction>();
	

	public String addCompleteBillTransaction(CompleteBillTransaction completeBillTransaction) throws CompleteBillTransactionException {
		try {
			if(completeBillTransactionValidator.validateCompleteBillTransaction(completeBillTransaction)){
				String companyId = completeBillTransaction.getCompanyId();
				if(StringUtils.isNotBlank(completeBillTransaction.getBillNumber())){
					CompleteBillTransaction existingCompleteBillTransaction = getCompleteBillTransactionById(completeBillTransaction.getBillNumber(), companyId);
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
					CompleteBillTransaction existingCompleteBillTransaction = getCompleteBillTransactionById(completeBillTransaction.getBillNumber(), companyId);
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
		completeBillTransactionRepository.getAllCompleteBillTransactions(companyId, new OnGetDataListener() {
	        @Override
	        public void onStart() {
	        }

	        @Override
	        public void onSuccess(DataSnapshot dataSnapshot) {
	        	completeBillTransactionsList.clear();
		        for (DataSnapshot completeBillTransactionPostSnapshot: dataSnapshot.getChildren()) {
		            CompleteBillTransaction completeBillTransactionData = completeBillTransactionPostSnapshot.getValue(CompleteBillTransaction.class);
		            completeBillTransactionsList.add(completeBillTransactionData);
		        }
	        	logger.info("The data key for the transaction " + dataSnapshot.getKey());
	        }

	        @Override
	        public void onFailed(DatabaseError databaseError) {
	           logger.info("Error retrieving data");
	           throw new BillliveServiceException(databaseError.getMessage());
	        }
	    });
		return completeBillTransactionsList;
	}

	public CompleteBillTransaction getCompleteBillTransactionById(String billNumber, String companyId) {
		completeBillTransactionRepository.getCompleteBillTransactionById(billNumber,companyId, new OnGetDataListener() {
	        @Override
	        public void onStart() {
	        }

	        @Override
	        public void onSuccess(DataSnapshot dataSnapshot) {
	        	completeBillTransactionData = dataSnapshot.getValue(CompleteBillTransaction.class);  
	        	logger.info(dataSnapshot.getKey() + " was " + completeBillTransactionData.getBillTransactionId());
	        }

	        @Override
	        public void onFailed(DatabaseError databaseError) {
	           logger.info("Error retrieving data");
	           throw new BillliveServiceException(databaseError.getMessage());
	        }
	    });
		return completeBillTransactionData;
	}	
}