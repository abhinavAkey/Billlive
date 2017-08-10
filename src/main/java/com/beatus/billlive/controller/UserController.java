package com.beatus.billlive.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beatus.billlive.service.UserService;
import com.beatus.billlive.utils.BillliveMediaType;
import com.beatus.billlive.utils.Constants;

@Controller
@RequestMapping("/api")
public class UserController {
	@Resource(name = "userService")
	private UserService userService;
	
/*	//For add and update user both
	@RequestMapping(value= "/company/user/add", method = RequestMethod.POST)
	public @ResponseBody String addUser(@RequestBody UserData userData, HttpServletRequest request, HttpServletResponse response) throws UserDataException{
		
		String isUserCreated = userService.addUser(userData);
		return isUserCreated;
	}
	
	@RequestMapping(value= "/company/user/update", method = RequestMethod.POST)
    public @ResponseBody String editUser(@RequestBody UserData userData, HttpServletRequest request, HttpServletResponse response) throws UserDataException{
    	String isUserUpdated = userService.updateUser(userData);
		return isUserUpdated;
    }
    
    @RequestMapping("/company/user/remove/{id}")
    public @ResponseBody String removeUser(@PathVariable("id") String userId, HttpServletRequest request, HttpServletResponse response) throws UserDataException{	
    	if(StringUtils.isNotBlank(userId)){
    		HttpSession session = request.getSession();
        	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
        	String isUserRemoved = userService.removeUser(companyId, userId);
    		return isUserRemoved;
		}else{
			throw new UserDataException("userId passed cant be null or empty string");
		}
    }
 
    
    @RequestMapping(value = "/company/getuser/{id}", method = RequestMethod.GET)
	public @ResponseBody UserData getUserById(@PathVariable("id") String userId, HttpServletRequest request, HttpServletResponse response) throws UserDataException {
    	HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userId)){
	    	UserData userData = userService.getUserById(companyId, userId);
			return userData;
		}else{
			throw new UserDataException("userId passed cant be null or empty string");
		}
		
	}
    
    @RequestMapping(value = "/company/getallusers", method = RequestMethod.GET)
	public @ResponseBody List<UserData> getAllUsers(HttpServletRequest request, HttpServletResponse response) throws UserDataException {
    	HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(companyId)){
        	List<UserData> userList = userService.getAllUsers(companyId);
			return userList;
		}else{
			throw new UserDataException("userId passed cant be null or empty string");
		}
	}*/
	@RequestMapping(value= "/company/user/isRegistered", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody String isRegistered(HttpServletRequest request, HttpServletResponse response, @RequestParam("uid") String uid){
		String companyId = userService.isRegistered(uid);
		HttpSession session = request.getSession();
		session.setAttribute(Constants.COMPANY_ID, companyId);
		session.setMaxInactiveInterval(Constants.MAX_INTERVAL);
		return companyId;
	}

	
}