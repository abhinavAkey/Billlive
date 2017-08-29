package com.beatus.billlive.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.beatus.billlive.domain.model.ContactInfo;
import com.beatus.billlive.exception.ContactInfoException;
import com.beatus.billlive.repository.ContactRepository;
import com.beatus.billlive.service.exception.BillliveServiceException;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.validation.ContactValidator;
import com.beatus.billlive.validation.exception.BillValidationException;

@Service
@Component("contactService")
public class ContactService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BillService.class);

	@Resource(name = "contactRepository")
	private ContactRepository contactRepository;

	@Resource(name = "contactValidator")
	private ContactValidator contactValidator;

	public String addContact(HttpServletRequest request, HttpServletResponse response, ContactInfo contact)
			throws BillValidationException, BillliveServiceException, ContactInfoException {
		LOGGER.info("In addContact method of contact Service");
		// Revisit validator
		boolean isValidated = contactValidator.validateContactInfo(contact);
		if (isValidated) {
			String companyId = contact.getCompanyId();
			ContactInfo existingcontact = null;
			if (StringUtils.isNotBlank(contact.getContactId())) {
				existingcontact = contactRepository.getContactByContactId(companyId, contact.getContactId());
				return updateContact(request, response, existingcontact);
			}

			return contactRepository.addContact(contact);
		}
		return "N";
	}

	public String updateContact(HttpServletRequest request, HttpServletResponse response, ContactInfo contact)
			throws BillValidationException, BillliveServiceException, ContactInfoException {
		LOGGER.info("In updateContact method of Contact Service");
		try {
			// Revisit validator
			boolean isValidated = contactValidator.validateContactInfo(contact);
			if (isValidated) {
				String companyId = contact.getCompanyId();
				ContactInfo existingcontact = null;
				if (StringUtils.isNotBlank(contact.getContactId()))
					existingcontact = contactRepository.getContactByContactId(companyId, contact.getContactId());

				return contactRepository.updateContact(existingcontact);
			}
		} catch (BillliveServiceException billException) {
			LOGGER.error("Billlive Service Exception in the updateBillService() {} ", billException.getMessage());
			throw billException;
		}

		return "N";
	}

	public List<ContactInfo> getAllContacts(String companyId) {

		List<ContactInfo> contacts = contactRepository.getAllContacts(companyId);
		List<ContactInfo> contactsNotRemoved = new ArrayList<ContactInfo>();
		for (ContactInfo contactInfo : contacts) {
			if (!Constants.YES.equalsIgnoreCase(contactInfo.getIsRemoved())) {
				contactsNotRemoved.add(contactInfo);
			}
		}
		return contactsNotRemoved;
	}

	public ContactInfo getContactByContactId(String companyId, String contactId) {
		if (StringUtils.isNotBlank(contactId) && StringUtils.isNotBlank(companyId)) {
			ContactInfo contactInfo = contactRepository.getContactByContactId(companyId, contactId);
			if (!Constants.YES.equalsIgnoreCase(contactInfo.getIsRemoved())) {
				return contactInfo;
			} else {
				return null;
			}
		}
		return null;
	}

	public String removeContact(String companyId, String uid, String contactId)
			throws BillliveServiceException, BillValidationException {
		if (StringUtils.isNotBlank(contactId) && StringUtils.isNotBlank(companyId)) {
			ContactInfo contactInfo = contactRepository.getContactByContactId(companyId, contactId);
			contactInfo.setUid(uid);
			contactInfo.setIsRemoved(Constants.YES);
			return contactRepository.updateContact(contactInfo);
		}
		return "N";
	}

}