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

import com.beatus.billlive.domain.model.JSendResponse;
import com.beatus.billlive.domain.model.PurchaseOrderDTO;
import com.beatus.billlive.exception.InventoryValidationException;
import com.beatus.billlive.exception.ItemDataException;
import com.beatus.billlive.exception.PurchaseOrderDataException;
import com.beatus.billlive.service.PurchaseOrderService;
import com.beatus.billlive.service.exception.BillliveServiceException;
import com.beatus.billlive.utils.BillliveMediaType;
import com.beatus.billlive.utils.Constants;

@Controller
public class PurchaseOrderController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(PurchaseOrderController.class);

	@Resource(name = "purchaseOrderService")
	private PurchaseOrderService purchaseOrderService;

	private JSendResponse<List<PurchaseOrderDTO>> jsend(List<PurchaseOrderDTO> purchaseOrderDTOList) {
		if (purchaseOrderDTOList == null || purchaseOrderDTOList.size() == 0) {
			return new JSendResponse<List<PurchaseOrderDTO>>(Constants.FAILURE, purchaseOrderDTOList);
		} else {
			return new JSendResponse<List<PurchaseOrderDTO>>(Constants.SUCCESS, purchaseOrderDTOList);
		}
	}

	private JSendResponse<PurchaseOrderDTO> jsend(PurchaseOrderDTO purchaseOrderDTOData) {
		if (purchaseOrderDTOData == null) {
			return new JSendResponse<PurchaseOrderDTO>(Constants.FAILURE, purchaseOrderDTOData);
		} else {
			return new JSendResponse<PurchaseOrderDTO>(Constants.SUCCESS, purchaseOrderDTOData);
		}
	}

	// For add and update purchaseOrder both
	@RequestMapping(value = "/company/purchaseOrder/add", method = RequestMethod.POST, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<String> addPurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO,
			HttpServletRequest request, HttpServletResponse response)
			throws PurchaseOrderDataException, ItemDataException, InventoryValidationException {
		LOG.info("In addPurchaseOrder method of Purchase Order Controller");
		// These comments will be removed once the auth_token is sent from
		// UI
		// SessionModel sessionModel = initSessionModel(request);
		// String companyId = sessionModel.getCompanyId();
		// String uid = sessionModel.getUid();
		if (purchaseOrderDTO != null) {
			String companyId = (String) request.getParameter(Constants.COMPANY_ID);
			String uid = (String) request.getParameter(Constants.UID);
			purchaseOrderDTO.setCompanyId(companyId);
			purchaseOrderDTO.setUid(uid);
			String purchaseOrderId = purchaseOrderService.addPurchaseOrder(request, response, purchaseOrderDTO,
					companyId);
			return jsend(purchaseOrderId);
		} else {
			LOG.error(
					"Billlive Service Exception in the addPurchaseOrder() {} of PurchaseOrderController,  Purchase Order data passed cant be null or empty string");
			throw new BillliveServiceException("Company Data passed cant be null or empty string");
		}
	}

	@RequestMapping(value = "/company/purchaseOrder/update", method = RequestMethod.POST, consumes = {
			BillliveMediaType.APPLICATION_JSON }, produces = { BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<String> editPurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO,
			HttpServletRequest request, HttpServletResponse response)
			throws PurchaseOrderDataException, ItemDataException, InventoryValidationException {
		LOG.info("In editPurchaseOrder method of PurchaseOrderController");
		if (purchaseOrderDTO != null) {
			// These comments will be removed once the auth_token is sent from
			// UI
			// SessionModel sessionModel = initSessionModel(request);
			// String companyId = sessionModel.getCompanyId();
			// String uid = sessionModel.getUid();
			String companyId = (String) request.getParameter(Constants.COMPANY_ID);
			String uid = (String) request.getParameter(Constants.UID);
			purchaseOrderDTO.setCompanyId(companyId);
			purchaseOrderDTO.setUid(uid);
			String purchaseOrderId = purchaseOrderService.updatePurchaseOrder(request, response, purchaseOrderDTO,
					companyId);
			return jsend(purchaseOrderId);
		} else {
			LOG.error(
					"Billlive Service Exception in the editPurchaseOrder() {} of PurchaseOrderController,  Purchase Order data passed cant be null or empty string");
			throw new BillliveServiceException("Purchase Order data  passed cant be null or empty string");
		}
	}

	@RequestMapping(value = "/company/purchaseOrder/remove", method = RequestMethod.DELETE, produces = {
			BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<String> removePurchaseOrder(
			@RequestParam(Constants.PURCHASE_ORDER_NUMBER) String purchaseOrderNumber, HttpServletRequest request,
			HttpServletResponse response)
			throws PurchaseOrderDataException, ItemDataException, InventoryValidationException {
		LOG.info("In removePurchaseOrder method of PurchaseOrderController");
		if (StringUtils.isNotBlank(purchaseOrderNumber)) {
			// These comments will be removed once the auth_token is sent from
			// UI
			// SessionModel sessionModel = initSessionModel(request);
			// String companyId = sessionModel.getCompanyId();
			// String uid = sessionModel.getUid();
			String companyId = (String) request.getParameter(Constants.COMPANY_ID);
			String uid = (String) request.getParameter(Constants.UID);
			String isPurchaseOrderRemoved = purchaseOrderService.removePurchaseOrder(companyId, uid,
					purchaseOrderNumber);
			return jsend(isPurchaseOrderRemoved);
		} else {
			LOG.error(
					"Billlive Service Exception in the removePurchaseOrder() {} of PurchaseOrderController,  Purchase Order number passed cant be null or empty string");
			throw new BillliveServiceException("Purchase Order number  passed cant be null or empty string");
		}
	}

	@RequestMapping(value = "/company/getpurchaseOrder/{id}", method = RequestMethod.GET, produces = {
			BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<PurchaseOrderDTO> getPurchaseOrderById(
			@RequestParam(Constants.PURCHASE_ORDER_NUMBER) String purchaseOrderNumber, HttpServletRequest request,
			HttpServletResponse response) throws PurchaseOrderDataException {
		LOG.info("In getPurchaseOrderById method of PurchaseOrderController");
		// These comments will be removed once the auth_token is sent from UI
		// SessionModel sessionModel = initSessionModel(request);
		// String companyId = sessionModel.getCompanyId();
		String companyId = (String) request.getParameter(Constants.COMPANY_ID);
		if (StringUtils.isNotBlank(purchaseOrderNumber) && StringUtils.isNotBlank(companyId)) {
			PurchaseOrderDTO purchaseOrderDTO = purchaseOrderService.getPurchaseOrderByPurchaseOrderNumber(companyId,
					purchaseOrderNumber);
			return jsend(purchaseOrderDTO);
		} else {
			LOG.error(
					"Billlive Service Exception in the getPurchaseOrderById() {} of PurchaseOrderController,  Purchase Order number or company ID passed cant be null or empty string");
			throw new BillliveServiceException(
					"Purchase Order number or CompanyID  passed cant be null or empty string");
		}

	}

	@RequestMapping(value = "/company/getallpurchaseOrders", method = RequestMethod.GET, produces = {
			BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<List<PurchaseOrderDTO>> getAllPurchaseOrders(HttpServletRequest request,
			HttpServletResponse response) throws PurchaseOrderDataException {
		LOG.info("In getAllPurchaseOrders method of PurchaseOrderController");
		// These comments will be removed once the auth_token is sent from UI
		// SessionModel sessionModel = initSessionModel(request);
		// String companyId = sessionModel.getCompanyId();
		String companyId = (String) request.getParameter(Constants.COMPANY_ID);
		if (StringUtils.isNotBlank(companyId)) {
			List<PurchaseOrderDTO> purchaseOrderList = purchaseOrderService
					.getAllPurchaseOrdersBasedOnCompanyId(companyId);
			return jsend(purchaseOrderList);
		} else {
			LOG.error(
					"Billlive Service Exception in the getAllPurchaseOrders() {} of PurchaseOrderController,  companyId passed cant be null or empty string");
			throw new BillliveServiceException("companyId  passed cant be null or empty string");
		}
	}

	@RequestMapping(value = "/company/getallpurchaseOrders/year/{year}/month/{month}/day/{day}", method = RequestMethod.GET, produces = {
			BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<List<PurchaseOrderDTO>> getAllPurchaseOrdersInADay(
			@PathVariable(Constants.YEAR) String year, @PathVariable(Constants.MONTH) String month,
			@PathVariable(Constants.DAY) String day, HttpServletRequest request, HttpServletResponse response)
			throws PurchaseOrderDataException {
		// These comments will be removed once the auth_token is sent from UI
		// SessionModel sessionModel = initSessionModel(request);
		// String companyId = sessionModel.getCompanyId();
		String companyId = (String) request.getParameter(Constants.COMPANY_ID);
		if (StringUtils.isNotBlank(companyId)) {
			List<PurchaseOrderDTO> purchaseOrderList = purchaseOrderService.getAllPurchaseOrdersInADay(companyId, year,
					month, day);
			return jsend(purchaseOrderList);
		} else {
			LOG.error(
					"Billlive Service Exception in the getAllPurchaseOrdersInADay() {} of PurchaseOrderController,  CompanyID  or month or year passed cant be null or empty string");
			throw new BillliveServiceException("CompanyID  or month or year passed cant be null or empty string");
		}
	}

	@RequestMapping(value = "/company/getallpurchaseOrders/year/{year}/month/{month}", method = RequestMethod.GET, produces = {
			BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<List<PurchaseOrderDTO>> getAllPurchaseOrdersInAMonth(
			@PathVariable(Constants.YEAR) String year, @PathVariable(Constants.MONTH) String month,
			HttpServletRequest request, HttpServletResponse response) throws PurchaseOrderDataException {
		LOG.info("In getAllPurchaseOrdersInAMonth method of PurchaseOrderController");
		// These comments will be removed once the auth_token is sent from UI
		// SessionModel sessionModel = initSessionModel(request);
		// String companyId = sessionModel.getCompanyId();
		String companyId = (String) request.getParameter(Constants.COMPANY_ID);
		LOG.info("In getAllPurchaseOrdersInAMonth method of PurchaseOrderController");
		if (StringUtils.isNotBlank(companyId)) {
			List<PurchaseOrderDTO> purchaseOrderList = purchaseOrderService.getAllPurchaseOrdersInAMonth(companyId,
					year, month);
			return jsend(purchaseOrderList);
		} else {
			LOG.error(
					"Billlive Service Exception in the getPurchaseOrderById() {} of PurchaseOrderController,  Purchase Order number passed cant be null or empty string");
			throw new BillliveServiceException("Purchase Order number  passed cant be null or empty string");
		}
	}

	@RequestMapping(value = "/company/getallpurchaseOrders/year/{year}", method = RequestMethod.GET, produces = {
			BillliveMediaType.APPLICATION_JSON })
	public @ResponseBody JSendResponse<List<PurchaseOrderDTO>> getAllPurchaseOrdersInAYear(
			@PathVariable("year") String year, HttpServletRequest request, HttpServletResponse response)
			throws PurchaseOrderDataException {
		LOG.info("In getAllPurchaseOrdersInAYear method of PurchaseOrderController");
		// These comments will be removed once the auth_token is sent from UI
		// SessionModel sessionModel = initSessionModel(request);
		// String companyId = sessionModel.getCompanyId();
		String companyId = (String) request.getParameter(Constants.COMPANY_ID);
		if (StringUtils.isNotBlank(companyId)) {
			List<PurchaseOrderDTO> purchaseOrderList = purchaseOrderService.getAllPurchaseOrdersInAnYear(companyId,
					year);
			return jsend(purchaseOrderList);
		} else {
			LOG.error(
					"Billlive Service Exception in the getPurchaseOrderById() {} of PurchaseOrderController,  Purchase Order number passed cant be null or empty string");
			throw new BillliveServiceException("Purchase Order munber  passed cant be null or empty string");
		}
	}

}
