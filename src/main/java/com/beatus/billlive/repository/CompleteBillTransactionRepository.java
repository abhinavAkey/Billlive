package com.beatus.billlive.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.CompleteBillTransaction;
import com.beatus.billlive.repository.data.listener.OnGetDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.tasks.Task;
import com.google.firebase.tasks.TaskCompletionSource;
import com.google.firebase.tasks.Tasks;


@Component("completeBillTransactionRepository")
public class CompleteBillTransactionRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(CompleteBillTransactionRepository.class);

	@Autowired
    @Qualifier(value = "databaseReference")
    private DatabaseReference databaseReference;
	
	public String isAdded = "N";
	public String isUpdated = "N";
	public String isDeleted = "N";
	
	private CompleteBillTransaction completeBillTransactionData = null;
	
	List<CompleteBillTransaction> completeBillTransactionsList = new ArrayList<CompleteBillTransaction>();
	
	public String addCompleteBillTransaction(CompleteBillTransaction completeBillTransactionData, String companyId) {
		try {
			DatabaseReference completeBillTransactionsRef = databaseReference.child("completeBillTransactions").child(completeBillTransactionData.getCompanyId());
			Map<String, CompleteBillTransaction> completeBillTransaction = new HashMap<String, CompleteBillTransaction>();
			// Generate a reference to a new location and add some data using push()
			DatabaseReference completeBillTransactionsPostRef = completeBillTransactionsRef.push();
			// Get the unique ID generated by a push()
			String postId = completeBillTransactionsPostRef.getKey();
			completeBillTransactionData.setPostId(postId);
			completeBillTransaction.put(completeBillTransactionData.getBillNumber(), completeBillTransactionData);
			completeBillTransactionsPostRef.setValue(completeBillTransactionData, new DatabaseReference.CompletionListener() {
			    @Override
			    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
			        if (databaseError != null) {
			            System.out.println("Data could not be saved " + databaseError.getMessage() + " " + completeBillTransactionData.getBillNumber());
			            isAdded = "N";
			        } else {
			        	logger.info("CompleteBillTransaction saved successfully, CompleteBillTransaction Details="+completeBillTransactionData.getBillNumber());
			        	isAdded = "Y";
					}
			    }
			});
			return isAdded;
		} catch (Exception e) {
			return isAdded;	
		}
	}

	public String updateCompleteBillTransaction(CompleteBillTransaction completeBillTransactionData) {
		try {
			DatabaseReference completeBillTransactionsRef = databaseReference.child("completeBillTransactions").child(completeBillTransactionData.getCompanyId());
			Map<String, Object> completeBillTransactionUpdates = new HashMap<String, Object>();
			completeBillTransactionUpdates.put(completeBillTransactionData.getBillNumber(), completeBillTransactionData);
			completeBillTransactionsRef.updateChildren(completeBillTransactionUpdates, new DatabaseReference.CompletionListener() {
			    @Override
			    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
			        if (databaseError != null) {
			            System.out.println("Data could not be updated " + databaseError.getMessage() + " " + completeBillTransactionData.getBillNumber());
			            isUpdated = "N";
			        } else {
			        	logger.info("CompleteBillTransaction updated successfully, CompleteBillTransaction Details="+completeBillTransactionData.getBillNumber());
			        	isUpdated = "Y";
					}
			    }
			});
			return isUpdated;
		} catch (Exception e) {
			return isUpdated;	
		}
	}
	
/*	public String removeCompleteBillTransaction(String companyId, String completeBillTransactionId) {
		try {
			DatabaseReference completeBillTransactionsRef = databaseReference.child("completeBillTransactions").child(companyId);
			Map<String, Object> completeBillTransactionUpdates = new HashMap<String, Object>();
			completeBillTransactionUpdates.put(completeBillTransactionId, null);
			completeBillTransactionsRef.updateChildren(completeBillTransactionUpdates, new DatabaseReference.CompletionListener() {
			    @Override
			    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
			        if (databaseError != null) {
			            System.out.println("Data could not be removed " + databaseError.getMessage() + " " + completeBillTransactionId);
			            isDeleted = "N";
			        } else {
			        	logger.info("CompleteBillTransaction removed successfully, CompleteBillTransaction Details="+completeBillTransactionId);
			        	isDeleted = "Y";
					}
			    }
			});
			return isDeleted;
		} catch (Exception e) {
			return isDeleted;	
		}
	}*/
	
	public void getCompleteBillTransactionById(String companyId, String completeBillTransactionId, OnGetDataListener listener) {
		TaskCompletionSource<DataSnapshot> waitSource = new TaskCompletionSource<DataSnapshot>();

		DatabaseReference completeBillTransactionDataRef = databaseReference.child("completeBillTransactions").child(companyId);
		completeBillTransactionData = null;
		completeBillTransactionDataRef.orderByChild("completeBillTransactionId").equalTo(completeBillTransactionId).addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
		    public void onDataChange(DataSnapshot dataSnapshot) {
		    	waitSource.setResult(dataSnapshot);
		    }

		    @Override
		    public void onCancelled(DatabaseError databaseError) {
		    	listener.onFailed(databaseError);
		    }
		});
		waitForTheTaskToCompleteAndReturn(waitSource, listener);

		logger.info("CompleteBillTransaction loaded successfully, CompleteBillTransaction details=" + completeBillTransactionData);
	}
	
	public void getAllCompleteBillTransactions(String companyId, OnGetDataListener listener) {
		TaskCompletionSource<DataSnapshot> waitSource = new TaskCompletionSource<DataSnapshot>();

		DatabaseReference completeBillTransactionDataRef = databaseReference.child("completeBillTransactions").child(companyId);
		completeBillTransactionDataRef.orderByChild("companyId").equalTo(companyId).addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
		    public void onDataChange(DataSnapshot dataSnapshot) {
		    	waitSource.setResult(dataSnapshot);
		    }

		    @Override
		    public void onCancelled(DatabaseError databaseError) {
		    	listener.onFailed(databaseError);
		    }
		});
		waitForTheTaskToCompleteAndReturn(waitSource, listener);

		
	}
	
	private void waitForTheTaskToCompleteAndReturn(TaskCompletionSource<DataSnapshot> waitSource, OnGetDataListener listener) {
		Task<DataSnapshot> waitTask = waitSource.getTask();

		try {
		    Tasks.await(waitTask);
		} catch (ExecutionException | InterruptedException e) {
			waitTask = Tasks.forException(e);
		}

		if(waitTask.isSuccessful()) {
			DataSnapshot result = waitTask.getResult();
			listener.onSuccess(result);
		}
		
	}
}