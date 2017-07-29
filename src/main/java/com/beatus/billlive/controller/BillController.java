package com.beatus.billlive.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beatus.billlive.domain.model.BillDTO;
import com.beatus.billlive.domain.model.BillData;
import com.beatus.billlive.service.BillService;
import com.beatus.billlive.validation.exception.BillValidationException;

@Controller
@RequestMapping("/api")
public class BillController {
	
	@Resource(name = "billService")
	private BillService billService;

	@RequestMapping(value= "/company/bill/add", method = RequestMethod.POST)
	public @ResponseBody String addBill(@RequestBody BillDTO billDTO) throws BillValidationException{
		String isBillCreated = billService.addBill(billDTO);
		return isBillCreated;
	}
	
	@RequestMapping(value= "/company/bill/update", method = RequestMethod.POST)
    public @ResponseBody String editBill(@RequestBody BillDTO billDTO) throws BillValidationException{
    	String isBillUpdated = billService.updateBill(billDTO);
		return isBillUpdated;
    }
    
    @RequestMapping("/company/bill/remove/{id}")
    public @ResponseBody String removeBill(@PathVariable("id") String uid){		
    	String isBillRemoved = billService.removeBill(bill);
		return isBillRemoved;
    }
 
    
    @RequestMapping(value = "/company/getbill", method = RequestMethod.GET)
	public @ResponseBody BillData getBillById(String uid) throws BillValidationException {
		if(StringUtils.isNotBlank(uid)){
			BillData billData = billService.getBillsByUid(uid);
			return billData;
		}else{
			throw new BillValidationException("uId passed cant be null or empty string");
		}
	}
    
    @RequestMapping(value = "/company/getallbills", method = RequestMethod.GET)
	public @ResponseBody List<BillData> getAllBills() {
		List<BillData> billList = billService.getAllBills();
		return billList;
	}

}
