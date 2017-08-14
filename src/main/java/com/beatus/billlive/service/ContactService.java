package com.beatus.billlive.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beatus.billlive.domain.model.ContactInfo;
import com.beatus.billlive.repository.ContactRepository;

@Service
@Component("contactService")
public class ContactService {
	
	@Resource(name = "contactRepository")
	private ContactRepository contactRepository;

	@Transactional
	public String addContact(ContactInfo contact) {
		return this.contactRepository.addContact(contact);
	}

	@Transactional
	public void updateContact(ContactInfo contact) {
		this.contactRepository.updateContact(contact);
	}

	@Transactional
	public List<ContactInfo> getAllContacts() {
		return this.contactRepository.getAllContacts();
	}

	@Transactional
	public ContactInfo getContactById(String contactId) {
		return this.contactRepository.getContactById(contactId);
	}

	@Transactional
	public String removeContact(String uid) {
		return this.contactRepository.removeContact(uid);
	}

}