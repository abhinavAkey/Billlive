package com.beatus.billlive.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@RequestMapping("/company")
public class UserController {
	
	private UserService userService;
	private UserValidator userValidator;
	
	@Autowired(required=true)
	@Qualifier(value="userService")
	public void setUserService(UserService ps){
		this.userService = ps;
	}
	
	@RequestMapping(value = "/getallusers", method = RequestMethod.GET)
	public @ResponseBody List<UserData> getAllUsers() {
		List<UserData> userList = userService.getAllUsers();
		return userList;
	}
	
	@RequestMapping(value = "/getuser", method = RequestMethod.GET)
	public @ResponseBody UserData getUserById(String uId) throws UserDataException {
		if(StringUtils.isNotBlank(uId)){
			UserData userData = userService.getUserById(uId);
			return userData;
		}else{
			throw new UserDataException("uId passed cant be null or empty string");
		}
		
	}
	
	//For add and update user both
	@RequestMapping(value= "/signup", method = RequestMethod.POST)
	public @ResponseBody String addUser(@RequestBody UserData userData) throws UserDataException{
		if(userValidator.validateUserData(userData)){
			String isUserCreated = userService.addUser(userData);
			return isUserCreated;
		}else{
			throw new UserDataException("User data passed cant be null or empty string");
		}	
	}
	
	@RequestMapping("/remove/{id}")
    public String removeUser(@PathVariable("id") int id){
		
        this.userService.removeUser(id);
        return "redirect:/users";
    }
 
    @RequestMapping("/edit/{id}")
    public String editUser(@PathVariable("id") int id, Model model){
       
        return "user";
    }
	
}