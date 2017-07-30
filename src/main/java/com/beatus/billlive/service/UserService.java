package com.beatus.billlive.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.beatus.billlive.repository.UserRepository;
import com.beatus.billlive.validation.UserValidator;

@Service
@Component("userService")
public class UserService {
	
	@Resource(name = "userValidator")
	private UserValidator userValidator;
	
	@Resource(name = "userRepository")
	private UserRepository userRepository;
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
		// TODO Auto-generated method stub
		return userRepository.isRegistered(uid);
	}
}