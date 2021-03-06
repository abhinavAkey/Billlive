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

import com.beatus.billlive.domain.model.BillDTO;
import com.beatus.billlive.domain.model.JSendResponse;
import com.beatus.billlive.service.BillService;
import com.beatus.billlive.service.exception.BillliveServiceException;
import com.beatus.billlive.utils.BillliveMediaType;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.validation.BillValidator;
import com.beatus.billlive.validation.exception.BillValidationException;

@Controller
public class BillController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(BillController.class);

	@Resource(name = "billService")
	private BillService billService;

	@Resource(name = "billValidator")
	private BillValidator billValidator;

	private JSendResponse<List<BillDTO>> jsend(List<BillDTO> billDTOList) {
		if (billDTOList == null || billDTOList.size() == 0) {
			return new JSendResponse<List<BillDTO>>(Constants.FAILURE, billDTOList);
		} else {
			return new JSendResponse<List<BillDTO>>(Constants.SUCCESS, billDTOList);
		}
	}

	private JSendResponse<BillDTO> jsend(BillDTO billDTO) {
		if (billDTO == null) {
			return new JSendResponse<BillDTO>(Constants.FAILURE, billDTO);
		} else {
			return new JSendResponse<BillDTO>(Constants.SUCCESS, billDTO);
		}
	}

	// For add and update bill both
	@RequestMapping(value = "/company/bill/add", method = RequestMethod.POST, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<String> addBill(@RequestBody BillDTO billDTO, HttpServletRequest request,
			HttpServletResponse response) throws BillValidationException, BillliveServiceException {
		LOG.info("In addBill method of Bill Controller");
		if (billDTO != null) {
			// These comments will be removed once the auth_token is sent from
			// UI
			// SessionModel sessionModel = initSessionModel(request);
			// String companyId = sessionModel.getCompanyId();
			// String uid = sessionModel.getUid();
			String companyId = (String) request.getParameter(Constants.COMPANY_ID);
			String uid = (String) request.getParameter(Constants.UID);
			billDTO.setCompanyId(companyId);
			billDTO.setUid(uid);
			String isBillCreated = billService.addBill(request, response, billDTO);
			return jsend(isBillCreated);
		} else {
			throw new BillliveServiceException("Bill data passed cant be null or empty string");
		}
	}

	@RequestMapping(value = "/company/bill/update", method = RequestMethod.POST, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<String> editBill(@RequestBody BillDTO billDTO, HttpServletRequest request,
			HttpServletResponse response) throws BillValidationException, BillliveServiceException {
		LOG.info("In editBill method of Bill Controller");
		if (billDTO != null) {
			// These comments will be removed once the auth_token is sent from
			// UI
			// SessionModel sessionModel = initSessionModel(request);
			// String companyId = sessionModel.getCompanyId();
			// String uid = sessionModel.getUid();
			String companyId = (String) request.getParameter(Constants.COMPANY_ID);
			String uid = (String) request.getParameter(Constants.UID);
			billDTO.setCompanyId(companyId);
			billDTO.setUid(uid);
			String isBillUpdated = billService.updateBill(request, response, billDTO);
			return jsend(isBillUpdated);
		} else {
			throw new BillliveServiceException("Bill data passed cant be null or empty string");
		}
	}

	@RequestMapping(value = "/company/bill/remove", method = RequestMethod.DELETE, produces = {
			BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<String> removeBill(@RequestParam(Constants.BILL_NUMBER) String billNumber,
			HttpServletRequest request, HttpServletResponse response)
			throws BillliveServiceException, BillliveServiceException, BillValidationException {
		LOG.info("In removeBill method of Bill Controller");
		if (StringUtils.isNotBlank(billNumber)) {
			// These comments will be removed once the auth_token is sent from
			// UI
			// SessionModel sessionModel = initSessionModel(request);
			// String companyId = sessionModel.getCompanyId();
			// String uid = sessionModel.getUid();
			String companyId = (String) request.getParameter(Constants.COMPANY_ID);
			String uid = (String) request.getParameter(Constants.UID);

			String isBillRemoved = billService.removeBill(companyId, uid, billNumber);
			return jsend(isBillRemoved);
		} else {
			throw new BillliveServiceException("Bill Number passed cant be null or empty string");
		}
	}

	@RequestMapping(value = "/company/getbill", method = RequestMethod.GET, produces = {
			BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<BillDTO> getBillById(@RequestParam(Constants.BILL_NUMBER) String billNumber,
			HttpServletRequest request, HttpServletResponse response)
			throws BillliveServiceException, BillliveServiceException {
		LOG.info("In getBillById method of Bill Controller");
		// These comments will be removed once the auth_token is sent from UI
		// SessionModel sessionModel = initSessionModel(request);
		// String companyId = sessionModel.getCompanyId();
		String companyId = (String) request.getParameter(Constants.COMPANY_ID);
		if (StringUtils.isNotBlank(billNumber) && StringUtils.isNotBlank(companyId)) {
			BillDTO billDTO = billService.getBillByBillNumber(companyId, billNumber);
			return jsend(billDTO);
		} else {
			throw new BillliveServiceException("billNumber or CompanyId passed can't be null or empty string");
		}

	}

	@RequestMapping(value = "/company/getallbills", method = RequestMethod.GET, produces = {
			BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<List<BillDTO>> getAllBills(HttpServletRequest request,
			HttpServletResponse response) throws BillliveServiceException {
		LOG.info("In getAllBills method of Bill Controller");
		// These comments will be removed once the auth_token is sent from UI
		// SessionModel sessionModel = initSessionModel(request);
		// String companyId = sessionModel.getCompanyId();
		String companyId = (String) request.getParameter(Constants.COMPANY_ID);
		if (StringUtils.isNotBlank(companyId)) {
			List<BillDTO> billList = billService.getAllBillsBasedOnCompanyId(companyId);
			return jsend(billList);
		} else {
			throw new BillliveServiceException("CompanyID passed cant be null or empty string");
		}
	}

	@RequestMapping(value = "/company/getallbills/year/{year}/month/{month}/day/{day}", method = RequestMethod.GET, produces = {
			BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<List<BillDTO>> getAllBillsInADay(@PathVariable(Constants.YEAR) String year,
			@PathVariable(Constants.MONTH) String month, @PathVariable(Constants.DAY) String day,
			HttpServletRequest request, HttpServletResponse response) throws BillliveServiceException {
		LOG.info("In getAllBillsInAMonth method of Bill Controller");
		// These comments will be removed once the auth_token is sent from UI
		// SessionModel sessionModel = initSessionModel(request);
		// String companyId = sessionModel.getCompanyId();
		String companyId = (String) request.getParameter(Constants.COMPANY_ID);
		if (StringUtils.isNotBlank(companyId)) {
			List<BillDTO> billList = billService.getAllBillsInADay(companyId, year, month, day);
			return jsend(billList);
		} else {
			throw new BillliveServiceException("CompanyID passed cant be null or empty string");
		}
	}

	@RequestMapping(value = "/company/getallbills/year/{year}/month/{month}", method = RequestMethod.GET, produces = {
			BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<List<BillDTO>> getAllBillsInAMonth(@PathVariable(Constants.YEAR) String year,
			@PathVariable(Constants.MONTH) String month, HttpServletRequest request, HttpServletResponse response)
			throws BillliveServiceException {
		LOG.info("In getAllBillsInAMonth method of Bill Controller");
		// These comments will be removed once the auth_token is sent from UI
		// SessionModel sessionModel = initSessionModel(request);
		// String companyId = sessionModel.getCompanyId();
		String companyId = (String) request.getParameter(Constants.COMPANY_ID);
		if (StringUtils.isNotBlank(companyId)) {
			List<BillDTO> billList = billService.getAllBillsInAMonth(companyId, year, month);
			return jsend(billList);
		} else {
			throw new BillliveServiceException("CompanyID passed cant be null or empty string");
		}
	}

	@RequestMapping(value = "/company/getallbills/year/{year}", method = RequestMethod.GET, produces = {
			BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<List<BillDTO>> getAllBillsInAYear(@PathVariable(Constants.YEAR) String year,
			HttpServletRequest request, HttpServletResponse response) throws BillliveServiceException {
		LOG.info("In getAllBillsInAYear method of Bill Controller");
		// These comments will be removed once the auth_token is sent from UI
		// SessionModel sessionModel = initSessionModel(request);
		// String companyId = sessionModel.getCompanyId();
		String companyId = (String) request.getParameter(Constants.COMPANY_ID);
		if (StringUtils.isNotBlank(companyId)) {
			List<BillDTO> billList = billService.getAllBillsInAnYear(companyId, year);
			return jsend(billList);
		} else {
			throw new BillliveServiceException("CompanyId passed cant be null or empty string");
		}
	}

}
