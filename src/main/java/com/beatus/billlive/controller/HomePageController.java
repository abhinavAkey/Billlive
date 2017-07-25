package com.beatus.billlive.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beatus.billlive.domain.model.BillData;
import com.beatus.billlive.repository.BillRepository;

@Controller
public class HomePageController {
	
	@Resource(name ="billRepository")
	BillRepository billRepository;

    @RequestMapping("/addBill")
    public String addBill(){
    	billRepository.addBill(new BillData());
		return "index";
    }
}
