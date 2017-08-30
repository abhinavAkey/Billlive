package com.beatus.billlive.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.beatus.billlive.domain.model.BillDTO;
import com.beatus.billlive.service.BillService;
import com.beatus.billlive.utils.BillliveMediaType;
import com.beatus.billlive.validation.exception.BillValidationException;

@Controller
public class HomePageController {
	
	@Resource(name ="billService")
	BillService billService;

	@RequestMapping(value= "test/company/bill/add", method = RequestMethod.POST, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
    public String addBill(@RequestBody BillDTO billData, HttpServletRequest request, HttpServletResponse response) throws BillValidationException{
		billService.addBill(request, response, billData);
		return "index";
    }
}
