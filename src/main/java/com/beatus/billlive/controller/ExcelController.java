package com.beatus.billlive.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.beatus.billlive.service.ExcelService;

public class ExcelController {
	
	/*@Resource(name = "excelService")
	private ExcelService excelService;
	
	@RequestMapping(value = "/loadExcel", method = RequestMethod.GET)
	public String LoadExcel(Receipt receipt) {
		excelService.LoadExcel(receipt);
		return "Y";
	}
	@RequestMapping(value="/loadexcel", method=RequestMethod.POST)
    public  @ResponseBody String LoadExcel(@RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
            	List<Object> itemList;
            	itemList = excelService.LoadExcel(file);
				return "You successfully uploaded " + itemList;
            } catch (Exception e) {
            	 e.printStackTrace();
            }
        } else {
            return "Failed to load data, because the file was empty.";
        }
		return null;
    }*/
}
