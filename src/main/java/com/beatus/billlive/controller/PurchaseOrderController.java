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

import com.beatus.billlive.domain.model.PurchaseOrderDTO;
import com.beatus.billlive.service.PurchaseOrderService;
import com.beatus.billlive.utils.BillliveMediaType;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.validation.exception.ItemDataException;
import com.beatus.billlive.validation.exception.PurchaseOrderDataException;

@Controller
public class PurchaseOrderController {
	

	@Resource(name = "purchaseOrderService")
	private PurchaseOrderService purchaseOrderService;
	
	//For add and update purchaseOrder both
	@RequestMapping(value= "/company/purchaseOrder/add", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody String addPurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO, HttpServletRequest request, HttpServletResponse response) throws PurchaseOrderDataException, ItemDataException{
		HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
		String purchaseOrderId = purchaseOrderService.addPurchaseOrder(request, response, purchaseOrderDTO, companyId);
		return purchaseOrderId;
	}
	
	@RequestMapping(value= "/company/purchaseOrder/update", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
    public @ResponseBody String editPurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO, HttpServletRequest request, HttpServletResponse response) throws PurchaseOrderDataException, ItemDataException{
		HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
		String purchaseOrderId = purchaseOrderService.updatePurchaseOrder(request, response, purchaseOrderDTO, companyId);
		return purchaseOrderId;
    }
    
    @RequestMapping(value ="/company/purchaseOrder/remove/{id}", method = RequestMethod.DELETE, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
    public @ResponseBody String removePurchaseOrder(@PathVariable("id") String purchaseOrderNumber, HttpServletRequest request, HttpServletResponse response) throws PurchaseOrderDataException{	
    	if(StringUtils.isNotBlank(purchaseOrderNumber)){
    		HttpSession session = request.getSession();
        	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
        	String isPurchaseOrderRemoved = purchaseOrderService.removePurchaseOrder(companyId, purchaseOrderNumber);
    		return isPurchaseOrderRemoved;
		}else{
			throw new PurchaseOrderDataException("purchaseOrderId passed cant be null or empty string");
		}
    }
 
    
    @RequestMapping(value = "/company/getpurchaseOrder/{id}", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody PurchaseOrderDTO getPurchaseOrderById(@PathVariable("id") String purchaseOrderNumber, HttpServletRequest request, HttpServletResponse response) throws PurchaseOrderDataException {
    	HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(purchaseOrderNumber) && StringUtils.isNotBlank(companyId)){
    		PurchaseOrderDTO purchaseOrderDTO = purchaseOrderService.getPurchaseOrderByPurchaseOrderNumber(companyId, purchaseOrderNumber);
			return purchaseOrderDTO;
		}else{
			throw new PurchaseOrderDataException("purchaseOrderId passed cant be null or empty string");
		}
		
	}
    
    @RequestMapping(value = "/company/getallpurchaseOrders", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody List<PurchaseOrderDTO> getAllPurchaseOrders(HttpServletRequest request, HttpServletResponse response) throws PurchaseOrderDataException {
    	HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(companyId)){
        	List<PurchaseOrderDTO> purchaseOrderList = purchaseOrderService.getAllPurchaseOrdersBasedOnCompanyId(companyId);
			return purchaseOrderList;
		}else{
			throw new PurchaseOrderDataException("purchaseOrderId passed cant be null or empty string");
		}
	}
    
    @RequestMapping(value = "/company/getallpurchaseOrders/year/{year}/month/{month}", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody List<PurchaseOrderDTO> getAllPurchaseOrdersInAMonth(@PathVariable("year") String year, @PathVariable("month") String month, HttpServletRequest request, HttpServletResponse response) throws PurchaseOrderDataException {
    	HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(companyId)){
        	List<PurchaseOrderDTO> purchaseOrderList = purchaseOrderService.getAllPurchaseOrdersInAMonth(companyId, year, month);
			return purchaseOrderList;
		}else{
			throw new PurchaseOrderDataException("purchaseOrderId passed cant be null or empty string");
		}
	}
    
    @RequestMapping(value = "/company/getallpurchaseOrders/year/{year}", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody List<PurchaseOrderDTO> getAllPurchaseOrdersInAMonth(@PathVariable("year") String year, HttpServletRequest request, HttpServletResponse response) throws PurchaseOrderDataException {
    	HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(companyId)){
        	List<PurchaseOrderDTO> purchaseOrderList = purchaseOrderService.getAllPurchaseOrdersInAnYear(companyId, year);
			return purchaseOrderList;
		}else{
			throw new PurchaseOrderDataException("CompanyId passed cant be null or empty string");
		}
	}

}
