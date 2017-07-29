package com.beatus.billlive.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beatus.billlive.domain.model.UserData;
import com.beatus.billlive.service.UserService;
import com.beatus.billlive.validation.UserValidator;
import com.beatus.billlive.validation.exception.UserDataException;

@Controller
@RequestMapping("/api")
public class UserController {
	
	@Resource(name = "userService")
	private UserService userService;
	
	//For add and update user both
	@RequestMapping(value= "/company/user/signup", method = RequestMethod.POST)
	public @ResponseBody String addUser(@RequestBody UserData userData) throws UserDataException{
		String isUserCreated = userService.addUser(userData);
		return isUserCreated;
	}
	
	@RequestMapping(value= "/company/user/update", method = RequestMethod.POST)
    public @ResponseBody String editUser(@RequestBody UserData userData) throws UserDataException{
    	String isUserUpdated = userService.updateUser(userData);
		return isUserUpdated;
    }
    
    @RequestMapping("/company/user/remove/{id}")
    public @ResponseBody String removeUser(@PathVariable("id") String uid){		
    	String isUserRemoved = userService.removeUser(uid);
		return isUserRemoved;
    }
 
    
    @RequestMapping(value = "/company/getuser", method = RequestMethod.GET)
	public @ResponseBody UserData getUserById(String uId) throws UserDataException {
		if(StringUtils.isNotBlank(uId)){
			UserData userData = userService.getUserById(uId);
			return userData;
		}else{
			throw new UserDataException("uId passed cant be null or empty string");
		}
		
	}
    
    @RequestMapping(value = "/company/getallusers", method = RequestMethod.GET)
	public @ResponseBody List<UserData> getAllUsers() {
		List<UserData> userList = userService.getAllUsers();
		return userList;
	}
	
}