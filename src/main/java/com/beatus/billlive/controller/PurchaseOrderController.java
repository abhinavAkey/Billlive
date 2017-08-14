package com.beatus.billlive.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beatus.billlive.domain.model.JSendResponse;
import com.beatus.billlive.domain.model.PurchaseOrderDTO;
import com.beatus.billlive.exception.InventoryValidationException;
import com.beatus.billlive.exception.ItemDataException;
import com.beatus.billlive.exception.PurchaseOrderDataException;
import com.beatus.billlive.service.PurchaseOrderService;
import com.beatus.billlive.session.management.SessionModel;
import com.beatus.billlive.utils.BillliveMediaType;
import com.beatus.billlive.utils.Constants;

@Controller
public class PurchaseOrderController extends BaseController{
	

	@Resource(name = "purchaseOrderService")
	private PurchaseOrderService purchaseOrderService;
	
	private JSendResponse<List<PurchaseOrderDTO>> jsend(List<PurchaseOrderDTO> purchaseOrderDTOList) {
    	if(purchaseOrderDTOList == null || purchaseOrderDTOList.size() == 0){
        	return new JSendResponse<List<PurchaseOrderDTO>>(Constants.FAILURE, purchaseOrderDTOList);
    	}else {
    		return new JSendResponse<List<PurchaseOrderDTO>>(Constants.SUCCESS, purchaseOrderDTOList);
    	}
	}
    
    private JSendResponse<PurchaseOrderDTO> jsend(PurchaseOrderDTO purchaseOrderDTOData) {
    	if(purchaseOrderDTOData == null){
    		return new JSendResponse<PurchaseOrderDTO>(Constants.FAILURE, purchaseOrderDTOData);
    	}else {
    		return new JSendResponse<PurchaseOrderDTO>(Constants.SUCCESS, purchaseOrderDTOData);
    	}
  	}
	
	//For add and update purchaseOrder both
	@RequestMapping(value= "/company/purchaseOrder/add", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<String> addPurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO, HttpServletRequest request, HttpServletResponse response) throws PurchaseOrderDataException, ItemDataException, InventoryValidationException{
		SessionModel sessionModel = initSessionModel(request);
    	String companyId = sessionModel.getCompanyId();
    	String purchaseOrderId = purchaseOrderService.addPurchaseOrder(request, response, purchaseOrderDTO, companyId);
		return jsend(purchaseOrderId);
	}
	
	@RequestMapping(value= "/company/purchaseOrder/update", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
    public @ResponseBody JSendResponse<String> editPurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO, HttpServletRequest request, HttpServletResponse response) throws PurchaseOrderDataException, ItemDataException, InventoryValidationException{
		HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
		String purchaseOrderId = purchaseOrderService.updatePurchaseOrder(request, response, purchaseOrderDTO, companyId);
		return jsend(purchaseOrderId);
    }
    
    @RequestMapping(value ="/company/purchaseOrder/remove/{id}", method = RequestMethod.DELETE, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
    public @ResponseBody JSendResponse<String> removePurchaseOrder(@PathVariable("id") String purchaseOrderNumber, HttpServletRequest request, HttpServletResponse response) throws PurchaseOrderDataException{	
    	if(StringUtils.isNotBlank(purchaseOrderNumber)){
    		HttpSession session = request.getSession();
        	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
        	String isPurchaseOrderRemoved = purchaseOrderService.removePurchaseOrder(companyId, purchaseOrderNumber);
    		return jsend(isPurchaseOrderRemoved);
		}else{
			throw new PurchaseOrderDataException("purchaseOrderId passed cant be null or empty string");
		}
    }
 
    
    @RequestMapping(value = "/company/getpurchaseOrder/{id}", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<PurchaseOrderDTO> getPurchaseOrderById(@PathVariable("id") String purchaseOrderNumber, HttpServletRequest request, HttpServletResponse response) throws PurchaseOrderDataException {
    	HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(purchaseOrderNumber) && StringUtils.isNotBlank(companyId)){
    		PurchaseOrderDTO purchaseOrderDTO = purchaseOrderService.getPurchaseOrderByPurchaseOrderNumber(companyId, purchaseOrderNumber);
			return jsend(purchaseOrderDTO);
		}else{
			throw new PurchaseOrderDataException("purchaseOrderId passed cant be null or empty string");
		}
		
	}
    
    @RequestMapping(value = "/company/getallpurchaseOrders", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<List<PurchaseOrderDTO>> getAllPurchaseOrders(HttpServletRequest request, HttpServletResponse response) throws PurchaseOrderDataException {
    	HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(companyId)){
        	List<PurchaseOrderDTO> purchaseOrderList = purchaseOrderService.getAllPurchaseOrdersBasedOnCompanyId(companyId);
			return jsend(purchaseOrderList);
		}else{
			throw new PurchaseOrderDataException("purchaseOrderId passed cant be null or empty string");
		}
	}
    
    @RequestMapping(value = "/company/getallpurchaseOrders/year/{year}/month/{month}", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<List<PurchaseOrderDTO>> getAllPurchaseOrdersInAMonth(@PathVariable("year") String year, @PathVariable("month") String month, HttpServletRequest request, HttpServletResponse response) throws PurchaseOrderDataException {
    	HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(companyId)){
        	List<PurchaseOrderDTO> purchaseOrderList = purchaseOrderService.getAllPurchaseOrdersInAMonth(companyId, year, month);
			return jsend(purchaseOrderList);
		}else{
			throw new PurchaseOrderDataException("purchaseOrderId passed cant be null or empty string");
		}
	}
    
    @RequestMapping(value = "/company/getallpurchaseOrders/year/{year}", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody JSendResponse<List<PurchaseOrderDTO>> getAllPurchaseOrdersInAMonth(@PathVariable("year") String year, HttpServletRequest request, HttpServletResponse response) throws PurchaseOrderDataException {
    	HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(companyId)){
        	List<PurchaseOrderDTO> purchaseOrderList = purchaseOrderService.getAllPurchaseOrdersInAnYear(companyId, year);
			return jsend(purchaseOrderList);
		}else{
			throw new PurchaseOrderDataException("CompanyId passed cant be null or empty string");
		}
	}

}
