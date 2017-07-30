package com.beatus.billlive.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beatus.billlive.service.UserService;

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
	@RequestMapping(value= "/company/user/isRegistered", method = RequestMethod.POST)
	public @ResponseBody String isReistered(String Uid){
		return userService.isRegistered(Uid);
		
	}

	
}