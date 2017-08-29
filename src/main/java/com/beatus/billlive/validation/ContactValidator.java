package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.ContactInfo;
import com.beatus.billlive.exception.ContactInfoException;
import com.beatus.billlive.utils.Constants;

@Component("contactValidator")
public class ContactValidator {
	
	public boolean validateContactInfo(ContactInfo contactData) throws ContactInfoException{
		if(contactData == null){
			throw new com.beatus.billlive.exception.ContactInfoException("ContactInfo data is null");
		}
		if(Constants.CONTACT_TYPE_USER.equalsIgnoreCase(contactData.getTypeOfUser())){
			if(StringUtils.isBlank(contactData.getFirstName()) && StringUtils.isBlank(contactData.getLastName())){
				throw new ContactInfoException("Contact First Name and Last name is not available " + contactData.getContactId());
			}
			if(StringUtils.isBlank(contactData.getPhoneNumber())){
				throw new ContactInfoException("Phone is not available " + contactData.getContactId());
			}
		}else if(Constants.CONTACT_TYPE_COMPANY.equalsIgnoreCase(contactData.getTypeOfUser())){
			if(StringUtils.isBlank(contactData.getFirstName())){
				throw new ContactInfoException("Contact First Name is not available " + contactData.getContactId());
			}
			if(StringUtils.isBlank(contactData.getLastName())){
				throw new ContactInfoException("Contact Last Name is not available " + contactData.getContactId());
			}
			if(StringUtils.isBlank(contactData.getContactCompanyName())){
				throw new ContactInfoException("Company is not available " + contactData.getContactId());
			}
			if(StringUtils.isBlank(contactData.getAddress())){
				throw new ContactInfoException("Address is not available " + contactData.getContactId());
			}
			if(StringUtils.isBlank(contactData.getCountry())){
				throw new ContactInfoException("Country is not available " + contactData.getContactId());
			}
			if(StringUtils.isBlank(contactData.getState())){
				throw new ContactInfoException("State is not available " + contactData.getContactId());
			}
			if(StringUtils.isBlank(contactData.getDistrict())){
				throw new ContactInfoException("Company is not available " + contactData.getContactId());
			}
			if(StringUtils.isBlank(contactData.getCity())){
				throw new ContactInfoException("Company is not available " + contactData.getContactId());
			}
			if(StringUtils.isBlank(contactData.getPhoneNumber())){
				throw new ContactInfoException("Phone is not available " + contactData.getContactId());
			}
		}
		return true;		
	}
}
