package com.beatus.billlive.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.beatus.billlive.domain.model.ExcelData;
import com.beatus.billlive.domain.model.JSendResponse;
import com.beatus.billlive.service.ExcelService;
import com.beatus.billlive.utils.BillliveMediaType;
import com.beatus.billlive.utils.Constants;

@Controller
public class ExcelController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ExcelController.class);

	private JSendResponse<List<ExcelData>> jsend(List<ExcelData> excelDataList) {
		if (excelDataList == null || excelDataList.size() == 0) {
			return new JSendResponse<List<ExcelData>>(Constants.FAILURE, excelDataList);
		} else {
			return new JSendResponse<List<ExcelData>>(Constants.SUCCESS, excelDataList);
		}
	}
	
    @Resource(name = "excelService")
	private ExcelService excelService;
	
	@RequestMapping(value="/company/loadexcel", method=RequestMethod.POST, produces = { BillliveMediaType.APPLICATION_JSON })
    public  @ResponseBody JSendResponse<List<ExcelData>> LoadExcel(@RequestParam("uploadFile") MultipartFile file){
		List<ExcelData> itemsData = null;
		if (!file.isEmpty()) {
        	LOG.debug("In here " + file);
        	LOG.debug("File size"+file.getSize());
        	
            try {
            	itemsData = excelService.readExcelFile(file);
            } catch (Exception e) {
            	 e.printStackTrace();
            }
        } else {
            return null;
        }
		LOG.info("FileData " + itemsData);
		return jsend(itemsData);
    }
}
