package com.beatus.billlive.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.beatus.billlive.domain.model.BillData;
import com.beatus.billlive.repository.BillRepository;
import com.beatus.billlive.service.exception.BillliveServiceException;
import com.beatus.billlive.utils.BillliveMediaType;

@Controller
public class HomePageController {
	
	@Resource(name ="billRepository")
	BillRepository billRepository;
	private static final Logger LOG = LoggerFactory.getLogger(HomePageController.class);


	@RequestMapping(value= "test/company/bill/add", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
    public String addBill(@RequestBody BillData billData){
		LOG.info("In addBill method of HomePageController");
		if (billData != null) {
			billRepository.addBill(billData);
			return "index";
		} else {
			LOG.error(
					"Billlive Service Exception in the addBill() {} of HomePageController,  Bill data passed cant be null or empty string");
			throw new BillliveServiceException("Bill data passed cant be null or empty string");
		}
    }
}
