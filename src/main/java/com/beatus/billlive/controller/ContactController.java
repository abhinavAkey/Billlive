package com.beatus.billlive.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beatus.billlive.domain.model.ContactInfo;
import com.beatus.billlive.domain.model.JSendResponse;
import com.beatus.billlive.exception.ContactInfoException;
import com.beatus.billlive.service.ContactService;
import com.beatus.billlive.utils.BillliveMediaType;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.validation.ContactValidator;

@Controller
public class ContactController extends BaseController{
	
	@Resource(name = "contactService")
	private ContactService contactService;
	
	@Resource(name = "contactValidator")
	private ContactValidator contactValidator;
    
    private JSendResponse<List<ContactInfo>> jsend(List<ContactInfo> contactInfoList) {
    	if(contactInfoList == null || contactInfoList.size() == 0){
        	return new JSendResponse<List<ContactInfo>>(Constants.FAILURE, contactInfoList);
    	}else {
    		return new JSendResponse<List<ContactInfo>>(Constants.SUCCESS, contactInfoList);
    	}
	}
    
    private JSendResponse<ContactInfo> jsend(ContactInfo contactInfoData) {
    	if(contactInfoData == null){
    		return new JSendResponse<ContactInfo>(Constants.FAILURE, contactInfoData);
    	}else {
    		return new JSendResponse<ContactInfo>(Constants.SUCCESS, contactInfoData);
    	}
  	}
	
	@RequestMapping(value = "/company/getallcontacts", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<List<ContactInfo>> getAllContacts() {
		List<ContactInfo> contactList = contactService.getAllContacts();
		return jsend(contactList);
	}
	
	@RequestMapping(value = "/company/getcontact", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<ContactInfo> getContactById(String contactId) throws ContactInfoException {
		if(StringUtils.isNotBlank(contactId)){
			ContactInfo contactData = contactService.getContactById(contactId);
			return jsend(contactData);
		}else{
			throw new ContactInfoException("contactId passed cant be null or empty string");
		}
	}
	
	//For add and update contact both
	@RequestMapping(value= "/company/addcontact", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<String> addContact(@RequestBody ContactInfo contactData) throws ContactInfoException{
		if(contactValidator.validateContactInfo(contactData)){
			String isContactCreated = contactService.addContact(contactData);
			return jsend(isContactCreated);
		}else{
			throw new ContactInfoException("Contact data passed cant be null or empty string");
		}	
	}
	
	@RequestMapping(value = "/company/removecontact/{id}", method = RequestMethod.DELETE, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
    public JSendResponse<String> removeContact(@PathVariable("id") String uid){
		
        String isContactRemoved = contactService.removeContact(uid);
        return jsend(isContactRemoved);
    }
	
}