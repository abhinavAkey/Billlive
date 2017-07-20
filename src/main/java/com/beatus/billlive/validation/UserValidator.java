package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;

import com.beatus.billlive.domain.model.UserData;
import com.beatus.billlive.validation.exception.UserDataException;

public class UserValidator {
	
	public boolean validateUserData(UserData userData) throws UserDataException{
		if(userData == null || StringUtils.isBlank(userData.getUid())){
			throw new com.beatus.billlive.validation.exception.UserDataException("UserData data is null");
		}
		if(StringUtils.isBlank(userData.getUid())){
			throw new UserDataException("UId is not available " );
		}
		if(StringUtils.isBlank(userData.getFirstName())){
			throw new UserDataException("User First Name is not available " + userData.getUid());
		}
		if(StringUtils.isBlank(userData.getLastName())){
			throw new UserDataException("User Last Name is not available " + userData.getUid());
		}
		if(StringUtils.isBlank(userData.getCompany())){
			throw new UserDataException("Company is not available " + userData.getUid());
		}
		if(StringUtils.isBlank(userData.getAddress())){
			throw new UserDataException("Address is not available " + userData.getUid());
		}
		if(StringUtils.isBlank(userData.getCountry())){
			throw new UserDataException("Country is not available " + userData.getUid());
		}
		if(StringUtils.isBlank(userData.getState())){
			throw new UserDataException("State is not available " + userData.getUid());
		}
		if(StringUtils.isBlank(userData.getDistrict())){
			throw new UserDataException("Company is not available " + userData.getUid());
		}
		if(StringUtils.isBlank(userData.getCity())){
			throw new UserDataException("Company is not available " + userData.getUid());
		}
		if(StringUtils.isBlank(userData.getEmail())){
			throw new UserDataException("Email is not available " + userData.getUid());
		}
		return true;
		
	}
}
