package com.beatus.billlive.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.beatus.billlive.service.exception.BillliveServiceException;
import com.beatus.billlive.session.management.SessionModel;
import com.beatus.billlive.utils.BillliveMediaType;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.validation.exception.BillValidationException;

@Controller
public class ContactController extends BaseController{
    private static final Logger LOG = LoggerFactory.getLogger(ContactController.class);

	@Resource(name = "contactService")
	private ContactService contactService;
	
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
	public @ResponseBody JSendResponse<List<ContactInfo>> getAllContacts(HttpServletRequest request, HttpServletResponse response) throws BillliveServiceException {
		LOG.info("In getAllContacts method of Contact Controller");
		HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(companyId)){
        	List<ContactInfo> contactList = contactService.getAllContacts(companyId);
			return jsend(contactList);
		}else{
			throw new BillliveServiceException("CompanyID passed cant be null or empty string");
		}
	}
	
	 @RequestMapping(value = "/company/getcontact/{id}", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
		public @ResponseBody JSendResponse<ContactInfo> getContactById(@PathVariable("id") String contactId, HttpServletRequest request, HttpServletResponse response) throws BillliveServiceException, BillliveServiceException {
	    	LOG.info("In getContactById method of Contact Controller");
	    	HttpSession session = request.getSession();
	    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
	    	if(StringUtils.isNotBlank(contactId) && StringUtils.isNotBlank(companyId)){
	    		ContactInfo contactInfo = contactService.getContactByContactId(companyId, contactId);
				return jsend(contactInfo);
			}else{
				throw new BillliveServiceException("billNumber or CompanyId passed can't be null or empty string");
			}
			
		}
	
	//For add and update contact both
	@RequestMapping(value= "/company/addcontact", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
    public @ResponseBody JSendResponse<String> addContact(@RequestBody ContactInfo contactData, HttpServletRequest request, HttpServletResponse response) throws BillValidationException, BillliveServiceException, ContactInfoException{
		LOG.info("In addContact method of Contact Controller");
		if(contactData != null){
	    	SessionModel sessionModel = initSessionModel(request);
	    	String companyId = sessionModel.getCompanyId();
	    	contactData.setCompanyId(companyId);
	    	String isBillUpdated = contactService.addContact(request, response, contactData, companyId);
			return jsend(isBillUpdated);
		}else{
			throw new BillliveServiceException("Bill data passed cant be null or empty string");
		}
    }
	

	
	 @RequestMapping(value = "/company/removecontact/{id}", method = RequestMethod.DELETE, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	    public @ResponseBody JSendResponse<String> removeContact(@PathVariable("id") String contactId, HttpServletRequest request, HttpServletResponse response) throws BillliveServiceException, BillliveServiceException, BillValidationException{	
	    	LOG.info("In removeContact method of Contact Controller");
	    	if(StringUtils.isNotBlank(contactId)){
	    		HttpSession session = request.getSession();
	        	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
	        	String isBillRemoved = contactService.removeContact(companyId, contactId);
	    		return jsend(isBillRemoved);
			}else{
				throw new BillliveServiceException("Bill Number passed cant be null or empty string");
			}
	    }
	
	@RequestMapping(value= "/company/updatecontact", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
    public @ResponseBody JSendResponse<String> editContact(@RequestBody ContactInfo contactData, HttpServletRequest request, HttpServletResponse response) throws BillValidationException, BillliveServiceException, ContactInfoException{
		LOG.info("In editContact method of Contact Controller");
		if(contactData != null){
	    	SessionModel sessionModel = initSessionModel(request);
	    	String companyId = sessionModel.getCompanyId();
	    	contactData.setCompanyId(companyId);
	    	String isBillUpdated = contactService.updateContact(request, response, contactData, companyId);
			return jsend(isBillUpdated);
		}else{
			throw new BillliveServiceException("Bill data passed cant be null or empty string");
		}
    }
	
}