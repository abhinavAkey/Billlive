package com.beatus.billlive.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javax.annotation.Resource;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.beatus.billlive.repository.BillRepository;
import com.beatus.billlive.service.ExcelService;

@Controller
public class HomePageController {
	
	@Resource(name ="billRepository")
	BillRepository billRepository;
	private static final Logger LOG = LoggerFactory.getLogger(HomePageController.class);

	@Resource(name = "excelService")
	private ExcelService excelService;
	
	@RequestMapping(value= "/test/compsny/uploadfile", method = RequestMethod.POST)
    public String addBill() throws EncryptedDocumentException, InvalidFormatException, IOException, ParseException{
		File file = new File("C:/Users/anudeep.akey/Downloads/test_data.xlsx");
		
		excelService.readExcelFile1(file,"1234");
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
