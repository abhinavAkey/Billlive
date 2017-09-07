
package com.beatus.billlive.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beatus.billlive.domain.model.InvoiceDTO;
import com.beatus.billlive.domain.model.JSendResponse;
import com.beatus.billlive.service.InvoiceService;
import com.beatus.billlive.service.exception.BillliveServiceException;
import com.beatus.billlive.utils.BillliveMediaType;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.validation.exception.BillliveClientValidationException;


@Controller
public class InvoiceController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(InvoiceController.class);

	@Resource(name = "invoiceService")
	private InvoiceService invoiceService;


	private JSendResponse<List<InvoiceDTO>> jsend(List<InvoiceDTO> invoiceDTOList) {
		if (invoiceDTOList == null || invoiceDTOList.size() == 0) {
			return new JSendResponse<List<InvoiceDTO>>(Constants.FAILURE, invoiceDTOList);
		} else {
			return new JSendResponse<List<InvoiceDTO>>(Constants.SUCCESS, invoiceDTOList);
		}
	}

	private JSendResponse<InvoiceDTO> jsend(InvoiceDTO invoiceDTO) {
		if (invoiceDTO == null) {
			return new JSendResponse<InvoiceDTO>(Constants.FAILURE, invoiceDTO);
		} else {
			return new JSendResponse<InvoiceDTO>(Constants.SUCCESS, invoiceDTO);
		}
	}

	// For add and update invoice both
	@RequestMapping(value = "/company/invoice/add", method = RequestMethod.POST, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<String> addInvoice(@RequestBody InvoiceDTO invoiceDTO, HttpServletRequest request,
			HttpServletResponse response) throws BillliveClientValidationException, BillliveServiceException {
		LOG.info("In addInvoice method of Invoice Controller");
		if (invoiceDTO != null) {
			// These comments will be removed once the auth_token is sent from
			// UI
			// SessionModel sessionModel = initSessionModel(request);
			// String companyId = sessionModel.getCompanyId();
			// String uid = sessionModel.getUid();
			String companyId = (String) request.getParameter(Constants.COMPANY_ID);
			String uid = (String) request.getParameter(Constants.UID);
			invoiceDTO.setCompanyId(companyId);
			invoiceDTO.setUid(uid);
			String isInvoiceCreated = invoiceService.addInvoice(request, response, invoiceDTO);
			return jsend(isInvoiceCreated);
		} else {
			throw new BillliveServiceException("Invoice data passed cant be null or empty string");
		}
	}

	@RequestMapping(value = "/company/invoice/update", method = RequestMethod.POST, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<String> editInvoice(@RequestBody InvoiceDTO invoiceDTO, HttpServletRequest request,
			HttpServletResponse response) throws BillliveClientValidationException, BillliveServiceException {
		LOG.info("In editInvoice method of Invoice Controller");
		if (invoiceDTO != null) {
			// These comments will be removed once the auth_token is sent from
			// UI
			// SessionModel sessionModel = initSessionModel(request);
			// String companyId = sessionModel.getCompanyId();
			// String uid = sessionModel.getUid();
			String companyId = (String) request.getParameter(Constants.COMPANY_ID);
			String uid = (String) request.getParameter(Constants.UID);
			invoiceDTO.setCompanyId(companyId);
			invoiceDTO.setUid(uid);
			String isInvoiceUpdated = invoiceService.updateInvoice(request, response, invoiceDTO);
			return jsend(isInvoiceUpdated);
		} else {
			throw new BillliveServiceException("Invoice data passed cant be null or empty string");
		}
	}

	@RequestMapping(value = "/company/invoice/remove", method = RequestMethod.DELETE, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<String> removeInvoice(@RequestParam(Constants.BILL_NUMBER) String invoiceNumber,
			HttpServletRequest request, HttpServletResponse response)
			throws BillliveServiceException, BillliveServiceException, BillliveClientValidationException {
		LOG.info("In removeInvoice method of Invoice Controller");
		if (StringUtils.isNotBlank(invoiceNumber)) {
			// These comments will be removed once the auth_token is sent from
			// UI
			// SessionModel sessionModel = initSessionModel(request);
			// String companyId = sessionModel.getCompanyId();
			// String uid = sessionModel.getUid();
			String companyId = (String) request.getParameter(Constants.COMPANY_ID);
			String uid = (String) request.getParameter(Constants.UID);

			String isInvoiceRemoved = invoiceService.removeInvoice(companyId, uid, invoiceNumber);
			return jsend(isInvoiceRemoved);
		} else {
			throw new BillliveServiceException("Invoice Number passed cant be null or empty string");
		}
	}

	@RequestMapping(value = "/company/getinvoice", method = RequestMethod.GET, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<InvoiceDTO> getInvoiceById(@RequestParam(Constants.BILL_NUMBER) String invoiceNumber,
			HttpServletRequest request, HttpServletResponse response)
			throws BillliveServiceException, BillliveServiceException {
		LOG.info("In getInvoiceById method of Invoice Controller");
		// These comments will be removed once the auth_token is sent from UI
		// SessionModel sessionModel = initSessionModel(request);
		// String companyId = sessionModel.getCompanyId();
		String companyId = (String) request.getParameter(Constants.COMPANY_ID);
		if (StringUtils.isNotBlank(invoiceNumber) && StringUtils.isNotBlank(companyId)) {
			InvoiceDTO invoiceDTO = invoiceService.getInvoiceByInvoiceNumber(companyId, invoiceNumber);
			return jsend(invoiceDTO);
		} else {
			throw new BillliveServiceException("invoiceNumber or CompanyId passed can't be null or empty string");
		}

	}

	@RequestMapping(value = "/company/getallinvoices", method = RequestMethod.GET, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<List<InvoiceDTO>> getAllInvoices(HttpServletRequest request,
			HttpServletResponse response) throws BillliveServiceException {
		LOG.info("In getAllInvoices method of Invoice Controller");
		// These comments will be removed once the auth_token is sent from UI
		// SessionModel sessionModel = initSessionModel(request);
		// String companyId = sessionModel.getCompanyId();
		String companyId = (String) request.getParameter(Constants.COMPANY_ID);
		if (StringUtils.isNotBlank(companyId)) {
			List<InvoiceDTO> invoiceList = invoiceService.getAllInvoicesBasedOnCompanyId(companyId);
			return jsend(invoiceList);
		} else {
			throw new BillliveServiceException("CompanyID passed cant be null or empty string");
		}
	}
	
	@RequestMapping(value = "/company/getallinvoices/year/{year}/month/{month}/day/{day}", method = RequestMethod.GET, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<List<InvoiceDTO>> getAllInvoicesInADay(@PathVariable(Constants.YEAR) String year,
			@PathVariable(Constants.MONTH) String month, @PathVariable(Constants.DAY) String day, HttpServletRequest request, HttpServletResponse response)
			throws BillliveServiceException {
		LOG.info("In getAllInvoicesInAMonth method of Invoice Controller");
		// These comments will be removed once the auth_token is sent from UI
		// SessionModel sessionModel = initSessionModel(request);
		// String companyId = sessionModel.getCompanyId();
		String companyId = (String) request.getParameter(Constants.COMPANY_ID);
		if (StringUtils.isNotBlank(companyId)) {
			List<InvoiceDTO> invoiceList = invoiceService.getAllInvoicesInADay(companyId, year, month, day);
			return jsend(invoiceList);
		} else {
			throw new BillliveServiceException("CompanyID passed cant be null or empty string");
		}
	}

	@RequestMapping(value = "/company/getallinvoices/year/{year}/month/{month}", method = RequestMethod.GET, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<List<InvoiceDTO>> getAllInvoicesInAMonth(@PathVariable(Constants.YEAR) String year,
			@PathVariable(Constants.MONTH) String month, HttpServletRequest request, HttpServletResponse response)
			throws BillliveServiceException {
		LOG.info("In getAllInvoicesInAMonth method of Invoice Controller");
		// These comments will be removed once the auth_token is sent from UI
		// SessionModel sessionModel = initSessionModel(request);
		// String companyId = sessionModel.getCompanyId();
		String companyId = (String) request.getParameter(Constants.COMPANY_ID);
		if (StringUtils.isNotBlank(companyId)) {
			List<InvoiceDTO> invoiceList = invoiceService.getAllInvoicesInAMonth(companyId, year, month);
			return jsend(invoiceList);
		} else {
			throw new BillliveServiceException("CompanyID passed cant be null or empty string");
		}
	}

	@RequestMapping(value = "/company/getallinvoices/year/{year}", method = RequestMethod.GET, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<List<InvoiceDTO>> getAllInvoicesInAYear(@PathVariable(Constants.YEAR) String year,
			HttpServletRequest request, HttpServletResponse response) throws BillliveServiceException {
		LOG.info("In getAllInvoicesInAYear method of Invoice Controller");
		// These comments will be removed once the auth_token is sent from UI
		// SessionModel sessionModel = initSessionModel(request);
		// String companyId = sessionModel.getCompanyId();
		String companyId = (String) request.getParameter(Constants.COMPANY_ID);
		if (StringUtils.isNotBlank(companyId)) {
			List<InvoiceDTO> invoiceList = invoiceService.getAllInvoicesInAnYear(companyId, year);
			return jsend(invoiceList);
		} else {
			throw new BillliveServiceException("CompanyId passed cant be null or empty string");
		}
	}
	
}
