package com.beatus.billlive.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.beatus.billlive.repository.BillRepository;

@Controller
public class HomePageController {
	
	@Resource(name ="billRepository")
	BillRepository billRepository;
	private static final Logger LOG = LoggerFactory.getLogger(HomePageController.class);


	@RequestMapping(value= "/", method = RequestMethod.GET)
    public String addBill(){
		/*LOG.info("In addBill method of HomePageController");
		if (billData != null) {*/
			//billRepository.addBill(billData);
		LOG.info("addBill HomePageController");
			return "index";
		/*} else {
			LOG.error(
					"Billlive Service Exception in the addBill() {} of HomePageController,  Bill data passed cant be null or empty string");
			throw new BillliveServiceException("Bill data passed cant be null or empty string");
		}*/
    }
}
