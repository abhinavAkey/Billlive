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

import com.beatus.billlive.domain.model.BillDTO;
import com.beatus.billlive.service.BillService;
import com.beatus.billlive.utils.BillliveMediaType;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.validation.BillValidator;
import com.beatus.billlive.validation.exception.BillDataException;

@Controller
//@RequestMapping("/api")
public class BillController {
	

	@Resource(name = "billService")
	private BillService billService;
	
	@Resource(name = "billValidator")
	private BillValidator billValidator;
	
	//For add and update bill both
	@RequestMapping(value= "/company/bill/add", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody String addBill(@RequestBody BillDTO billDTO, HttpServletRequest request, HttpServletResponse response) throws BillDataException{
		if(billValidator.validateBillDTO(billDTO)){
			HttpSession session = request.getSession();
			String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
			String isBillCreated = billService.addBill(request, response, billDTO, companyId);
			return isBillCreated;
		}else{
			throw new BillDataException("Bill data passed cant be null or empty string");
		}
	}
	
	@RequestMapping(value= "/company/bill/update", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
    public @ResponseBody String editBill(@RequestBody BillDTO billDTO, HttpServletRequest request, HttpServletResponse response) throws BillDataException{
		if(billValidator.validateBillDTO(billDTO)){
		HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
		String isBillUpdated = billService.updateBill(request, response, billDTO, companyId);
		return isBillUpdated;
		}else{
			throw new BillDataException("Bill data passed cant be null or empty string");
		}
    }
    
    @RequestMapping(value = "/company/bill/remove/{id}", method = RequestMethod.DELETE, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
    public @ResponseBody String removeBill(@PathVariable("id") String billNumber, HttpServletRequest request, HttpServletResponse response) throws BillDataException{	
    	if(StringUtils.isNotBlank(billNumber)){
    		HttpSession session = request.getSession();
        	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
        	String isBillRemoved = billService.removeBill(companyId, billNumber);
    		return isBillRemoved;
		}else{
			throw new BillDataException("billId passed cant be null or empty string");
		}
    }
 
    
    @RequestMapping(value = "/company/getbill/{id}", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody BillDTO getBillById(@PathVariable("id") String billNumber, HttpServletRequest request, HttpServletResponse response) throws BillDataException {
    	HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(billNumber) && StringUtils.isNotBlank(companyId)){
    		BillDTO billDTO = billService.getBillByBillNumber(companyId, billNumber);
			return billDTO;
		}else{
			throw new BillDataException("billId passed cant be null or empty string");
		}
		
	}
    
    @RequestMapping(value = "/company/getallbills", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody List<BillDTO> getAllBills(HttpServletRequest request, HttpServletResponse response) throws BillDataException {
    	HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(companyId)){
        	List<BillDTO> billList = billService.getAllBillsBasedOnCompanyId(companyId);
			return billList;
		}else{
			throw new BillDataException("billId passed cant be null or empty string");
		}
	}
    
    @RequestMapping(value = "/company/getallbills/year/{year}/month/{month}", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody List<BillDTO> getAllBillsInAMonth(@PathVariable("year") String year, @PathVariable("month") String month, HttpServletRequest request, HttpServletResponse response) throws BillDataException {
    	HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(companyId)){
        	List<BillDTO> billList = billService.getAllBillsInAMonth(companyId, year, month);
			return billList;
		}else{
			throw new BillDataException("billId passed cant be null or empty string");
		}
	}
    
    @RequestMapping(value = "/company/getallbills/year/{year}", method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public @ResponseBody List<BillDTO> getAllBillsInAMonth(@PathVariable("year") String year, HttpServletRequest request, HttpServletResponse response) throws BillDataException {
    	HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(companyId)){
        	List<BillDTO> billList = billService.getAllBillsInAnYear(companyId, year);
			return billList;
		}else{
			throw new BillDataException("CompanyId passed cant be null or empty string");
		}
	}

}
