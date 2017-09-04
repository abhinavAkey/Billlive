package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.ContactInfo;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.validation.exception.BillliveClientValidationException;

@Component("contactValidator")
public class ContactValidator {
	
	public boolean validateContactInfo(ContactInfo contactData) throws BillliveClientValidationException{
		if(contactData == null){
			throw new BillliveClientValidationException("","ContactInfo data is null");
		}
		if(Constants.CONTACT_TYPE_USER.equalsIgnoreCase(contactData.getTypeOfUser())){
			if(StringUtils.isBlank(contactData.getFirstName()) && StringUtils.isBlank(contactData.getLastName())){
				throw new BillliveClientValidationException("First Name or Last Name","ContactInfo,  First Name and Last name is not available " + contactData.getContactId());
			}
			if(StringUtils.isBlank(contactData.getPhoneNumber())){
				throw new BillliveClientValidationException("Phone","ContactInfo,  Phone is not available " + contactData.getContactId());
			}
		}else if(Constants.CONTACT_TYPE_COMPANY.equalsIgnoreCase(contactData.getTypeOfUser())){
			if(StringUtils.isBlank(contactData.getFirstName())){
				throw new BillliveClientValidationException("First Name","ContactInfo,  First Name is not available " + contactData.getContactId());
			}
			if(StringUtils.isBlank(contactData.getLastName())){
				throw new BillliveClientValidationException("Last Name","ContactInfo,   Last Name is not available " + contactData.getContactId());
			}
			if(StringUtils.isBlank(contactData.getContactCompanyName())){
				throw new BillliveClientValidationException("Company","ContactInfo,  Company is not available " + contactData.getContactId());
			}
			if(StringUtils.isBlank(contactData.getAddress())){
				throw new BillliveClientValidationException("Address","ContactInfo,  Address is not available " + contactData.getContactId());
			}
			if(StringUtils.isBlank(contactData.getCountry())){
				throw new BillliveClientValidationException("Country","ContactInfo,  Country is not available " + contactData.getContactId());
			}
			if(StringUtils.isBlank(contactData.getState())){
				throw new BillliveClientValidationException("State","ContactInfo,  State is not available " + contactData.getContactId());
			}
			if(StringUtils.isBlank(contactData.getDistrict())){
				throw new BillliveClientValidationException("District","ContactInfo,  District is not available " + contactData.getContactId());
			}
			if(StringUtils.isBlank(contactData.getCity())){
				throw new BillliveClientValidationException("City","ContactInfo,  City is not available " + contactData.getContactId());
			}
			if(StringUtils.isBlank(contactData.getPhoneNumber())){
				throw new BillliveClientValidationException("Phone","ContactInfo,  Phone is not available " + contactData.getContactId());
			}
		}
		return true;		
	}
}
