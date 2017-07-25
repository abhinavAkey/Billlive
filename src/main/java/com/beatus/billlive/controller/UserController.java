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
	
	@Resource(name = "userValidator")
	private UserValidator userValidator;
	
	@RequestMapping(value = "/company/getallusers", method = RequestMethod.GET)
	public @ResponseBody List<UserData> getAllUsers() {
		List<UserData> userList = userService.getAllUsers();
		return userList;
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
	
	//For add and update user both
	@RequestMapping(value= "/company/signup", method = RequestMethod.POST)
	public @ResponseBody String addUser(@RequestBody UserData userData) throws UserDataException{
		if(userValidator.validateUserData(userData)){
			String isUserCreated = userService.addUser(userData);
			return isUserCreated;
		}else{
			throw new UserDataException("User data passed cant be null or empty string");
		}	
	}
	
	@RequestMapping("/company/remove/{id}")
    public String removeUser(@PathVariable("id") String uid){
		
        this.userService.removeUser(uid);
        return "redirect:/users";
    }
 
    @RequestMapping("/companyO/edit/{id}")
    public String editUser(@PathVariable("id") int id, Model model){
       
        return "user";
    }
	
}