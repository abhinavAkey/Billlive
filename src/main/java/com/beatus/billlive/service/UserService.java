package com.beatus.billlive.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beatus.billlive.domain.model.UserData;
import com.beatus.billlive.repository.UserRepository;

@Service
@Component("userService")
public class UserService {
	
	@Resource(name = "userRepository")
	private UserRepository userRepository;

	@Transactional
	public String addUser(UserData user) {
		return this.userRepository.addUser(user);
	}


	@Transactional
	public void updateUser(UserData user) {
		this.userRepository.updateUser(user);
	}

	@Transactional
	public List<UserData> getAllUsers() {
		return this.userRepository.getAllUsers();
	}

	@Transactional
	public UserData getUserById(String uId) {
		return this.userRepository.getUserById(uId);
	}

	@Transactional
	public void removeUser(String uid) {
		this.userRepository.removeUser(uid);
	}

}