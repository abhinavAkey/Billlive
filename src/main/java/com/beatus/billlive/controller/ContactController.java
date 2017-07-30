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

import com.beatus.billlive.domain.model.ContactInfo;
import com.beatus.billlive.service.ContactService;
import com.beatus.billlive.validation.ContactValidator;
import com.beatus.billlive.validation.exception.ContactInfoException;

@Controller
@RequestMapping("/api")
public class ContactController {
	
	@Resource(name = "contactService")
	private ContactService contactService;
	
	@Resource(name = "contactValidator")
	private ContactValidator contactValidator;
	
	@RequestMapping(value = "/company/getallcontacts", method = RequestMethod.GET)
	public @ResponseBody List<ContactInfo> getAllContacts() {
		List<ContactInfo> contactList = contactService.getAllContacts();
		return contactList;
	}
	
	@RequestMapping(value = "/company/getcontact", method = RequestMethod.GET)
	public @ResponseBody ContactInfo getContactById(String contactId) throws ContactInfoException {
		if(StringUtils.isNotBlank(contactId)){
			ContactInfo contactData = contactService.getContactById(contactId);
			return contactData;
		}else{
			throw new ContactInfoException("contactId passed cant be null or empty string");
		}
	}
	
	//For add and update contact both
	@RequestMapping(value= "/company/addcontact", method = RequestMethod.POST)
	public @ResponseBody String addContact(@RequestBody ContactInfo contactData) throws ContactInfoException{
		if(contactValidator.validateContactInfo(contactData)){
			String isContactCreated = contactService.addContact(contactData);
			return isContactCreated;
		}else{
			throw new ContactInfoException("Contact data passed cant be null or empty string");
		}	
	}
	
	@RequestMapping("/company/removecontact/{id}")
    public String removeContact(@PathVariable("id") String uid){
		
        this.contactService.removeContact(uid);
        return "redirect:/contacts";
    }
 
    @RequestMapping("/company/editcontact/{id}")
    public String editContact(@PathVariable("id") int id, Model model){
       
        return "contact";
    }
	
}