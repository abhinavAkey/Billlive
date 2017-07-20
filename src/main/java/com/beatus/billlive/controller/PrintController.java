package com.beatus.billlive.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.beatus.billlive.domain.model.Receipt;
import com.beatus.billlive.service.PrinterService;

public class PrintController {
	
	@Resource(name = "printService")
	private PrinterService printService;
	
	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public String print(Receipt receipt) {
		printService.printDocument(receipt);
		return "Y";
	}

}
