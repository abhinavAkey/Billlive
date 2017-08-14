package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.UserData;
import com.beatus.billlive.validation.exception.UserDataException;

@Component("userValidator")
public class UserValidator {
	
	public boolean validateUserData(UserData userData) throws UserDataException{
		if(userData == null || StringUtils.isBlank(userData.getUid())){
			throw new UserDataException("UserData data is null");
		}
		if(userData == null || StringUtils.isBlank(userData.getEmail())){
			throw new UserDataException("UserData, The field Email is null for User with Uid + " + userData.getUid());
		}
		return true;
		
	}
}
