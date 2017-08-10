package com.beatus.billlive.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.beatus.billlive.domain.model.BillDTO;
import com.beatus.billlive.domain.model.BillData;
import com.beatus.billlive.repository.BillRepository;
import com.beatus.billlive.utils.BillliveMediaType;

@Controller
public class HomePageController {
	
	@Resource(name ="billRepository")
	BillRepository billRepository;

	@RequestMapping(value= "/company/bill/add", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
    public String addBill(@RequestBody BillData billData){
    	billRepository.addBill(billData);
		return "index";
    }
}
