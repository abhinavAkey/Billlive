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

import com.beatus.billlive.domain.model.CompanyData;
import com.beatus.billlive.domain.model.JSendResponse;
import com.beatus.billlive.exception.CompanyDataException;
import com.beatus.billlive.service.CompanyService;
import com.beatus.billlive.service.exception.BillliveServiceException;
import com.beatus.billlive.utils.BillliveMediaType;
import com.beatus.billlive.utils.Constants;

@Controller
public class CompanyController extends BaseController {

	@Resource(name = "companyService")
	private CompanyService companyService;

	private static final Logger LOG = LoggerFactory.getLogger(CompanyController.class);

	private JSendResponse<List<CompanyData>> jsend(List<CompanyData> companyDataList) {
		if (companyDataList == null || companyDataList.size() == 0) {
			return new JSendResponse<List<CompanyData>>(Constants.FAILURE, companyDataList);
		} else {
			return new JSendResponse<List<CompanyData>>(Constants.SUCCESS, companyDataList);
		}
	}

	private JSendResponse<CompanyData> jsend(CompanyData companyDataData) {
		if (companyDataData == null) {
			return new JSendResponse<CompanyData>(Constants.FAILURE, companyDataData);
		} else {
			return new JSendResponse<CompanyData>(Constants.SUCCESS, companyDataData);
		}
	}

	// For add and update company both
	@RequestMapping(value = "/company/add", method = RequestMethod.POST, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<String> addCompany(@RequestBody CompanyData companyData,
			HttpServletRequest request, HttpServletResponse response) throws CompanyDataException {
		LOG.info("In addCompany method of Company Controller");
		if (companyData != null) {
			// These comments will be removed once the auth_token is sent from
			// UI
			// SessionModel sessionModel = initSessionModel(request);
			// String companyId = sessionModel.getCompanyId();
			// String uid = sessionModel.getUid();
			String companyId = (String) request.getParameter(Constants.COMPANY_ID);
			String uid = (String) request.getParameter(Constants.UID);
			if (companyId == null) {
				companyData.setAddedOrUpdatedOrRemovedUID(uid);
				companyId = companyService.addCompany(request, response, companyData);
			}
			// sessionModel.setCompanyId(companyId);
			request.setAttribute(Constants.COMPANY_ID, companyId);
			return jsend(companyId);
		} else {
			LOG.error(
					"Billlive Service Exception in the addCompany() {} of CompanyController,  Bill data passed cant be null or empty string");
			throw new BillliveServiceException("Company Data passed cant be null or empty string");
		}
	}

	@RequestMapping(value = "/company/update", method = RequestMethod.POST, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<String> editCompany(@RequestBody CompanyData companyData,
			HttpServletRequest request, HttpServletResponse response) throws CompanyDataException {
		LOG.info("In updateCompany method of Company Controller");
		if (companyData != null) {
			// These comments will be removed once the auth_token is sent from
			// UI
			// SessionModel sessionModel = initSessionModel(request);
			// String companyId = sessionModel.getCompanyId();
			// String uid = sessionModel.getUid();
			String companyId = (String) request.getParameter(Constants.COMPANY_ID);
			String uid = (String) request.getParameter(Constants.UID);
			if (StringUtils.isNotBlank(companyId) && companyId.equalsIgnoreCase(companyData.getCompanyId())) {
				companyData.setAddedOrUpdatedOrRemovedUID(uid);
				companyId = companyService.updateCompany(request, response, companyData);
			}
			return jsend(companyId);
		} else {
			LOG.error(
					"Billlive Service Exception in the editCompany() {} of CompanyController,  Company data passed cant be null or empty string");
			throw new BillliveServiceException("Company passed cant be null or empty string");
		}
	}

	@RequestMapping(value = "/company/remove", method = RequestMethod.DELETE, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<String> removeCompany(@RequestParam(Constants.COMPANY_ID) String companyId,
			HttpServletRequest request, HttpServletResponse response) throws CompanyDataException {
		LOG.info("In removeCompany method of Company Controller");
		// These comments will be removed once the auth_token is sent from UI
		// SessionModel sessionModel = initSessionModel(request);
		// String companyId = sessionModel.getCompanyId();
		// String uid = sessionModel.getUid();
		String companyIdFromSession = (String) request.getParameter(Constants.COMPANY_ID);
		if (companyId.equalsIgnoreCase(companyIdFromSession)) {
			String uid = (String) request.getParameter(Constants.UID);
			if (StringUtils.isNotBlank(companyId)) {
				String isCompanyRemoved = companyService.removeCompany(companyId, uid);
				return jsend(isCompanyRemoved);
			} else {
				LOG.error(
						"Billlive Service Exception in the removeCompany() {} of CompanyController,  Company Id passed cant be null or empty string");
				throw new BillliveServiceException("Company Id passed cant be null or empty string");
			}
		}
		return jsend(Constants.NO);
	}

	@RequestMapping(value = "/company/getcompany/{id}", method = RequestMethod.GET, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<CompanyData> getCompanyById(HttpServletRequest request,
			HttpServletResponse response) throws CompanyDataException {
		LOG.info("In getCompanyById method of Company Controller");
		// SessionModel sessionModel = initSessionModel(request);
		// String companyId = sessionModel.getCompanyId();
		String companyId = (String) request.getParameter(Constants.COMPANY_ID);
		if (StringUtils.isNotBlank(companyId)) {
			CompanyData companyData = companyService.getCompanyById(companyId);
			return jsend(companyData);
		} else {
			LOG.error(
					"Billlive Service Exception in the addCompany() {} of CompanyController,  CompanyId data passed cant be null or empty string");
			throw new BillliveServiceException("CompanyId passed cant be null or empty string");
		}

	}

	@RequestMapping(value = "/company/getallcompanys", method = RequestMethod.GET, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<List<CompanyData>> getAllCompanys(HttpServletRequest request,
			HttpServletResponse response) throws CompanyDataException {
		LOG.info("In getAllCompanys method of Company Controller");
		List<CompanyData> companyList = companyService.getAllCompanies();
		return jsend(companyList);

	}

}
