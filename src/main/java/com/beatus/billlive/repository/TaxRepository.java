package com.beatus.billlive.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.Tax;
import com.beatus.billlive.repository.data.listener.OnGetDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


@Component("taxRepository")
public class TaxRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(TaxRepository.class);

	@Autowired
    @Qualifier(value = "databaseReference")
    private DatabaseReference databaseReference;
	
	public String isAdded = "N";
	public String isUpdated = "N";
	public String isDeleted = "N";
	
	private Tax tax = null;
	
	List<Tax> taxsList = new ArrayList<Tax>();
	
	public String addTax(Tax tax) {
		try {
			DatabaseReference taxsRef = databaseReference.child("taxs").child(tax.getCompanyId());
			Map<String, Tax> taxs = new HashMap<String, Tax>();
			// Generate a reference to a new location and add some data using push()
			DatabaseReference taxsPostRef = taxsRef.push();
			String postId = taxsPostRef.getKey();
			tax.setPostId(postId);
			taxs.put(tax.getTaxId(), tax);
			
			taxsPostRef.setValue(tax, new DatabaseReference.CompletionListener() {
			    @Override
			    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
			        if (databaseError != null) {
			            System.out.println("Data could not be saved " + databaseError.getMessage() + " " + tax.getTaxId());
			            isAdded = "N";
			        } else {
			        	logger.info("Tax saved successfully, Tax Details="+tax.getTaxId());
			        	isAdded = "Y";
					}
			    }
			});
			return tax.getTaxId();
		} catch (Exception e) {
			throw e;
		}
	}

	public String updateTax(Tax tax) {
		try {
			DatabaseReference taxsRef = databaseReference.child("taxs").child(tax.getCompanyId());
			Map<String, Object> taxUpdates = new HashMap<String, Object>();
			taxUpdates.put(tax.getTaxId(), tax);
			taxsRef.updateChildren(taxUpdates, new DatabaseReference.CompletionListener() {
			    @Override
			    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
			        if (databaseError != null) {
			            System.out.println("Data could not be updated " + databaseError.getMessage() + " " + tax.getTaxId());
			            isUpdated = "N";
			        } else {
			        	logger.info("Tax updated successfully, Tax Details = "+tax.getTaxId());
			        	isUpdated = "Y";
					}
			    }
			});
			return isUpdated;
		} catch (Exception e) {
			return isUpdated;	
		}
	}
	
	public String removeTax(String taxId, String companyId) {
		try {
			DatabaseReference taxsRef = databaseReference.child("taxs").child(companyId);
			Map<String, Object> taxUpdates = new HashMap<String, Object>();
			taxUpdates.put(taxId, null);
			taxsRef.updateChildren(taxUpdates, new DatabaseReference.CompletionListener() {
			    @Override
			    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
			        if (databaseError != null) {
			            System.out.println("Data could not be removed " + databaseError.getMessage() + " " + taxId);
			            isDeleted = "N";
			        } else {
			        	logger.info("Tax removed successfully, Tax Details="+taxId);
			        	isDeleted = "Y";
					}
			    }
			});
			return isDeleted;
		} catch (Exception e) {
			return isDeleted;	
		}
	}
	
	public Tax getTaxById(String companyId, String taxId, OnGetDataListener listener) {
		DatabaseReference taxRef = databaseReference.child("taxs").child(companyId);
		tax = null;
		taxRef.orderByChild("taxId").equalTo(taxId).addListenerForSingleValueEvent(new ValueEventListener() {
		    @Override
		    public void onDataChange(DataSnapshot dataSnapshot) {
		    	listener.onSuccess(dataSnapshot);
		    }

		    @Override
		    public void onCancelled(DatabaseError databaseError) {
		    	listener.onFailed(databaseError);
		    }
		});
		logger.info("Tax loaded successfully, Tax details=" + tax);
		return tax;
	}
	
	public List<Tax> getAllTaxs(String companyId, OnGetDataListener listener) {
		DatabaseReference taxRef = databaseReference.child("taxs").child(companyId);
		taxRef.orderByChild("taxId").addListenerForSingleValueEvent(new ValueEventListener() {
		    @Override
		    public void onDataChange(DataSnapshot dataSnapshot) {
		    	listener.onSuccess(dataSnapshot);
		    }

		    @Override
		    public void onCancelled(DatabaseError databaseError) {
		    	listener.onFailed(databaseError);
		    }
		});
		return taxsList;
	}
}