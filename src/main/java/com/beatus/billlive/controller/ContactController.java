package com.beatus.billlive.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beatus.billlive.domain.model.ContactInfo;
import com.beatus.billlive.domain.model.JSendResponse;
import com.beatus.billlive.exception.CompleteBillTransactionException;
import com.beatus.billlive.exception.ContactInfoException;
import com.beatus.billlive.service.ContactService;
import com.beatus.billlive.service.exception.BillliveServiceException;
import com.beatus.billlive.utils.BillliveMediaType;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.validation.exception.BillValidationException;

@Controller
public class ContactController extends BaseController {

	@Resource(name = "contactService")
	private ContactService contactService;
	private static final Logger LOG = LoggerFactory.getLogger(CompleteBillTransactionController.class);

	private JSendResponse<List<ContactInfo>> jsend(List<ContactInfo> contactInfoList) {
		if (contactInfoList == null || contactInfoList.size() == 0) {
			return new JSendResponse<List<ContactInfo>>(Constants.FAILURE, contactInfoList);
		} else {
			return new JSendResponse<List<ContactInfo>>(Constants.SUCCESS, contactInfoList);
		}
	}

	private JSendResponse<ContactInfo> jsend(ContactInfo contactInfoData) {
		if (contactInfoData == null) {
			return new JSendResponse<ContactInfo>(Constants.FAILURE, contactInfoData);
		} else {
			return new JSendResponse<ContactInfo>(Constants.SUCCESS, contactInfoData);
		}
	}

	@RequestMapping(value = "/company/getallcontacts", method = RequestMethod.GET, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<List<ContactInfo>> getAllContacts(HttpServletRequest request,
			HttpServletResponse response) {
		// These comments will be removed once the auth_token is sent from UI
		// SessionModel sessionModel = initSessionModel(request);
		// String companyId = sessionModel.getCompanyId();
		String companyId = (String) request.getParameter(Constants.COMPANY_ID);
		LOG.info("In getAllContacts method of ContactController");
		List<ContactInfo> contactList = contactService.getAllContacts(companyId);
		return jsend(contactList);
	}

	@RequestMapping(value = "/company/getcontact", method = RequestMethod.GET, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<ContactInfo> getContactById(@RequestParam(Constants.CONTACT_ID) String contactId,
			HttpServletRequest request, HttpServletResponse response) throws ContactInfoException {
		LOG.info("In getContactById method of ContactController");
		if (StringUtils.isNotBlank(contactId)) {
			// These comments will be removed once the auth_token is sent from
			// UI
			// SessionModel sessionModel = initSessionModel(request);
			// String companyId = sessionModel.getCompanyId();
			String companyId = (String) request.getParameter(Constants.COMPANY_ID);
			ContactInfo contactData = contactService.getContactByContactId(companyId, contactId);
			return jsend(contactData);
		} else {
			LOG.error("contactId is null in getContactById method of ContactController");
			throw new BillliveServiceException("contactId passed cant be null or empty string");
		}
	}

	@RequestMapping(value = "/company/addcontact", method = RequestMethod.POST, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<String> addContact(@RequestBody ContactInfo contactData,
			HttpServletRequest request, HttpServletResponse response)
			throws ContactInfoException, BillliveServiceException, BillValidationException {
		if (contactData != null) {
			// These comments will be removed once the auth_token is sent from
			// UI
			// SessionModel sessionModel = initSessionModel(request);
			// String companyId = sessionModel.getCompanyId();
			// String uid = sessionModel.getUid();
			String companyId = (String) request.getParameter(Constants.COMPANY_ID);
			String uid = (String) request.getParameter(Constants.UID);
			LOG.info("In addContact method of ContactController");
			contactData.setCompanyId(companyId);
			contactData.setUid(uid);
			String isContactCreated = contactService.addContact(request, response, contactData);
			return jsend(isContactCreated);
		} else {
			LOG.error("contactData is null in addContact method of ContactController");
			throw new BillliveServiceException("contactData data passed cant be null or empty");
		}
	}

	@RequestMapping(value = "/company/updatecontact", method = RequestMethod.POST, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<String> updateContact(@RequestBody ContactInfo contactData,
			HttpServletRequest request, HttpServletResponse response)
			throws ContactInfoException, BillliveServiceException, BillValidationException {
		LOG.info("In updateContact method of ContactController");
		if (contactData != null) {
			// These comments will be removed once the auth_token is sent from
			// UI
			// SessionModel sessionModel = initSessionModel(request);
			// String companyId = sessionModel.getCompanyId();
			// String uid = sessionModel.getUid();
			String companyId = (String) request.getParameter(Constants.COMPANY_ID);
			String uid = (String) request.getParameter(Constants.UID);
			contactData.setCompanyId(companyId);
			contactData.setUid(uid);
			String isContactCreated = contactService.updateContact(request, response, contactData);
			return jsend(isContactCreated);
		} else {
			LOG.error("contactData is null in updateContact method of ContactController");
			throw new BillliveServiceException("contactData data passed cant be null or empty");
		}
	}

	@RequestMapping(value = "/company/removecontact", method = RequestMethod.DELETE, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public JSendResponse<String> removeContact(@RequestParam(Constants.CONTACT_ID) String contactId,
			HttpServletRequest request, HttpServletResponse response)
			throws BillliveServiceException, BillValidationException {
		if (StringUtils.isNotBlank(contactId)) {
			// These comments will be removed once the auth_token is sent from
			// UI
			// SessionModel sessionModel = initSessionModel(request);
			// String companyId = sessionModel.getCompanyId();
			// String uid = sessionModel.getUid();
			String companyId = (String) request.getParameter(Constants.COMPANY_ID);
			String uid = (String) request.getParameter(Constants.UID);
			String isContactRemoved = contactService.removeContact(companyId, uid, contactId);
			return jsend(isContactRemoved);
		} else {
			LOG.error("contactId is null in removeContact method of ContactController");
			throw new BillliveServiceException("contactId data passed cant be null or empty");
		}
	}
}