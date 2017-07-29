package com.beatus.billlive.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.beatus.billlive.domain.model.UserData;
import com.beatus.billlive.repository.UserRepository;
import com.beatus.billlive.utils.Utils;
import com.beatus.billlive.validation.UserValidator;
import com.beatus.billlive.validation.exception.UserDataException;

@Service
@Component("userService")
public class UserService {
	
	@Resource(name = "userValidator")
	private UserValidator userValidator;
	
	@Resource(name = "userRepository")
	private UserRepository userRepository;

	public String addUser(UserData user) throws UserDataException {
		try {
			if(userValidator.validateUserData(user)){
				user.setCompanyId(Utils.generateRandomKey(10));
				return this.userRepository.addUser(user);
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
				return this.userRepository.updateUser(user);
			}else {
				return "N";
			}
		} catch (UserDataException e) {
			throw e;
		}
	}
	
	public String removeUser(String uid) {
		return this.userRepository.removeUser(uid);
	}

	public List<UserData> getAllUsers() {
		return this.userRepository.getAllUsers();
	}

	public UserData getUserById(String uId) {
		return this.userRepository.getUserById(uId);
	}
}