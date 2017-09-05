package com.beatus.billlive.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.beatus.billlive.domain.model.CompanyUsers;
import com.beatus.billlive.repository.TaxRepository;
import com.beatus.billlive.repository.UserRepository;
import com.beatus.billlive.repository.data.listener.OnGetDataListener;
import com.beatus.billlive.service.exception.BillliveServiceException;
import com.beatus.billlive.validation.UserValidator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

@Service
@Component("userService")
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(TaxRepository.class);

	@Resource(name = "userValidator")
	private UserValidator userValidator;
	
	@Resource(name = "userRepository")
	private UserRepository userRepository;
	
	private String companyId = null;
	
	private CompanyUsers companyUsers;
/*	public String addUser(UserData user) throws UserDataException {
		try {
			if(userValidator.validateUserData(user)){
				if(StringUtils.isNotBlank(user.getUid()) && StringUtils.isNotBlank(user.getCompanyId())){
					UserData existingUser = userRepository.getUserById(user.getCompanyId(), user.getUid());
					if(existingUser != null){
						return updateUser(user);
					}
					return "N";
				}else {
					user.setUid(Utils.generateRandomKey(12));
					
					}
					return userRepository.addUser(user);
			}else {
				return "N";
			}
		} catch (UserDataException e) {
			throw e;
		}
	}


	public String updateUser(UserData user) throws UserDataException {
		try {
			if(userValidator.validateUserData(user)){
				if(StringUtils.isNotBlank(user.getUid()) && StringUtils.isNotBlank(user.getCompanyId())){
					UserData existingUser = userRepository.getUserById(user.getCompanyId(), user.getUid());
					if(existingUser == null){
						return addUser(user);
					}else {
						return updateUser(user);
					}
					
				}
				return userRepository.updateUser(user);
			}else {
				return "N";
			}
		} catch (UserDataException e) {
			throw e;
		}
	}
	
	public String removeUser(String companyId, String userId) {
		return userRepository.removeUser(companyId, userId);
	}

	public List<UserData> getAllUsers(String companyId) {
		return userRepository.getAllUsers(companyId);
	}

	public UserData getUserById(String companyId, String userId) {
		return userRepository.getUserById(companyId, userId);
	}*/

	public String isRegistered(String uid) {
		userRepository.isRegistered(uid, new OnGetDataListener() {
	        @Override
	        public void onStart() {
	        }

	        @Override
	        public void onSuccess(DataSnapshot dataSnapshot) {
	        	companyUsers = dataSnapshot.getValue(CompanyUsers.class);
		        if(companyUsers!=null){
		        	if(companyUsers != null && StringUtils.isNotBlank(companyUsers.getUid()) && StringUtils.isNotBlank(companyUsers.getCompanyId())){
		        		if(companyUsers.getUid() == uid ){
		        			companyId = companyUsers.getCompanyId();
		        		}
		        	}
		        }
		        logger.info(dataSnapshot.getKey() + " was " + companyUsers.getCompanyId());
	        }

	        @Override
	        public void onFailed(DatabaseError databaseError) {
	           logger.info("Error retrieving data");
	           throw new BillliveServiceException(databaseError.getMessage());
	        }
	    });
		return companyId;
	}
}