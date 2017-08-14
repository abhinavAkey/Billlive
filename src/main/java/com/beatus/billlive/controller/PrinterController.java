package com.beatus.billlive.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.beatus.billlive.domain.model.JSendResponse;
import com.beatus.billlive.domain.model.Receipt;
import com.beatus.billlive.service.PrinterService;
import com.beatus.billlive.utils.BillliveMediaType;
import com.beatus.billlive.utils.Constants;

@Controller
public class PrinterController extends BaseController{
	
	@Resource(name = "printerService")
	private PrinterService printerService;
	
	@RequestMapping(value = "/print" , method = RequestMethod.GET, consumes = {BillliveMediaType.APPLICATION_JSON}, produces = {BillliveMediaType.APPLICATION_JSON})
	public JSendResponse<String> print(Receipt receipt) {
		printerService.printDocument(receipt);
		return jsend(Constants.YES);
	}

}
