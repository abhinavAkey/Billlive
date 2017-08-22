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

import com.beatus.billlive.domain.model.ContactInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


@Component("contactRepository")
public class ContactRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(ContactRepository.class);

	@Autowired
    @Qualifier(value = "databaseReference")
    private DatabaseReference databaseReference;
	
	public String isAdded = "N";
	public String isUpdated = "N";
	public String isDeleted = "N";
	
	private ContactInfo contactInfo = null;
	
	List<ContactInfo> contactsList = new ArrayList<ContactInfo>();
	
	public String addContact(ContactInfo contactInfo) {
		try {
			DatabaseReference contactsRef = databaseReference.child("contacts");
			Map<String, ContactInfo> contact = new HashMap<String, ContactInfo>();
			// Generate a reference to a new location and add some data using push()
			DatabaseReference contactsPostRef = contactsRef.push();
			// Get the unique ID generated by a push()
			String postId = contactsPostRef.getKey();
			contactInfo.setPostId(postId);
			contact.put(contactInfo.getContactId(), contactInfo);
			contactsPostRef.setValue(contactInfo, new DatabaseReference.CompletionListener() {
			    @Override
			    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
			        if (databaseError != null) {
			            System.out.println("Data could not be saved " + databaseError.getMessage() + " " + contactInfo.getContactId());
			            isAdded = "N";
			        } else {
			        	logger.info("Contact saved successfully, Contact Details="+contactInfo.getContactId());
			        	isAdded = "Y";
					}
			    }
			});
			return isAdded;
		} catch (Exception e) {
			return isAdded;	
		}
	}

	public String updateContact(ContactInfo contactInfo) {
		try {
			DatabaseReference contactsRef = databaseReference.child("contacts");
			Map<String, Object> contactUpdates = new HashMap<String, Object>();
			contactUpdates.put(contactInfo.getContactId(), contactInfo);
			contactsRef.updateChildren(contactUpdates, new DatabaseReference.CompletionListener() {
			    @Override
			    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
			        if (databaseError != null) {
			            System.out.println("Data could not be updated " + databaseError.getMessage() + " " + contactInfo.getContactId());
			            isUpdated = "N";
			        } else {
			        	logger.info("Contact updated successfully, Contact Details="+contactInfo.getContactId());
			        	isUpdated = "Y";
					}
			    }
			});
			return isUpdated;
		} catch (Exception e) {
			return isUpdated;	
		}
	}
	
	public String removeContact(String contactId) {
		try {
			DatabaseReference contactsRef = databaseReference.child("contacts");
			Map<String, Object> contactUpdates = new HashMap<String, Object>();
			contactUpdates.put(contactId, null);
			contactsRef.updateChildren(contactUpdates, new DatabaseReference.CompletionListener() {
			    @Override
			    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
			        if (databaseError != null) {
			            System.out.println("Data could not be removed " + databaseError.getMessage() + " " + contactId);
			            isDeleted = "N";
			        } else {
			        	logger.info("Contact removed successfully, Contact Details="+contactId);
			        	isDeleted = "Y";
					}
			    }
			});
			return isDeleted;
		} catch (Exception e) {
			return isDeleted;	
		}
	}
	
	public ContactInfo getContactByContactId(String companyId, String contactId) {
		DatabaseReference contactInfoRef = databaseReference.child("contacts").child(companyId);
		contactInfo = null;
		contactInfoRef.orderByChild("contactId").equalTo(contactId).addChildEventListener(new ChildEventListener() {
		    @Override
		    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
		        contactInfo = dataSnapshot.getValue(ContactInfo.class);
		        System.out.println(dataSnapshot.getKey() + " was " + contactInfo.getContactId());
		    }

			@Override
			public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
				
			}

			@Override
			public void onChildRemoved(DataSnapshot snapshot) {
				
			}

			@Override
			public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
				
			}

			@Override
			public void onCancelled(DatabaseError error) {
				
			}
		});
		logger.info("Contact loaded successfully, Contact details=" + contactInfo);
		return contactInfo;
	}
	
	public List<ContactInfo> getAllContacts(String companyId) {
		DatabaseReference contactInfoRef = databaseReference.child("contacts").child(companyId);
		contactInfoRef.addValueEventListener(new ValueEventListener() {
		    public void onDataChange(DataSnapshot contactSnapshot) {
		    	contactsList.clear();
		        for (DataSnapshot contactPostSnapshot: contactSnapshot.getChildren()) {
		            ContactInfo contactInfo = contactPostSnapshot.getValue(ContactInfo.class);
		            contactsList.add(contactInfo);
		        }
		    }
		    
			@Override
			public void onCancelled(DatabaseError error) {
				
			}
		});
		return contactsList;
	}


}