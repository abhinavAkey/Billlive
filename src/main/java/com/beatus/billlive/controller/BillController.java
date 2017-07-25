package com.beatus.billlive.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beatus.billlive.service.BillService;
import com.beatus.billlive.validation.BillValidator;

@Controller
@RequestMapping("/api")
public class BillController {
	
	@Resource(name = "billService")
	private BillService billService;
	
	@Resource(name = "billValidator")
	private BillValidator billValidator;

}
