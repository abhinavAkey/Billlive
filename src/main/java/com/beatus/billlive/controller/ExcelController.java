package com.beatus.billlive.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.beatus.billlive.domain.model.ExcelData;
import com.beatus.billlive.domain.model.ExcelPieChart;
import com.beatus.billlive.domain.model.JSendResponse;
import com.beatus.billlive.service.ExcelService;
import com.beatus.billlive.utils.BillliveMediaType;
import com.beatus.billlive.utils.Constants;

@Controller
public class ExcelController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ExcelController.class);

	private JSendResponse<List<ExcelPieChart>> jsend(List<ExcelPieChart> excelDataList) {
		if (excelDataList == null || excelDataList.size() == 0) {
			return new JSendResponse<List<ExcelPieChart>>(Constants.FAILURE, excelDataList);
		} else {
			return new JSendResponse<List<ExcelPieChart>>(Constants.SUCCESS, excelDataList);
		}
	}
	
	private JSendResponse<List<ExcelData>> jsend1(List<ExcelData> excelDataList) {
		if (excelDataList == null || excelDataList.size() == 0) {
			return new JSendResponse<List<ExcelData>>(Constants.FAILURE, excelDataList);
		} else {
			return new JSendResponse<List<ExcelData>>(Constants.SUCCESS, excelDataList);
		}
	}
	
    @Resource(name = "excelService")
	private ExcelService excelService;
	
	@RequestMapping(value="/company/loadexcel", method=RequestMethod.POST, produces = { BillliveMediaType.APPLICATION_JSON })
    public  @ResponseBody JSendResponse<List<ExcelData>> LoadExcel(@RequestParam("uploadFile") MultipartFile file,HttpServletRequest request,
			HttpServletResponse response, @RequestParam("companyId") String companyId){
		List<ExcelData> itemsData = null;
		//String companyId = (String) request.getParameter(Constants.COMPANY_ID);
		//String uid = (String) request.getParameter(Constants.UID);
		if (!file.isEmpty()) {
        	LOG.debug("In here " + file);
        	LOG.debug("File size"+file.getSize());
        	
            try {
            	itemsData = excelService.readExcelFile(file,companyId);
            } catch (Exception e) {
            	 e.printStackTrace();
            }
        } else {
            return null;
        }
		LOG.info("FileData " + itemsData);
		return jsend1(itemsData);
    }
	
	@RequestMapping(value="/company/getexcel", method=RequestMethod.GET, produces = { BillliveMediaType.APPLICATION_JSON })
    public  @ResponseBody JSendResponse<List<ExcelData>> getExcel(HttpServletRequest request,
			HttpServletResponse response, String excepReportId){
		List<ExcelData> itemsData = null;
		String companyId = (String) request.getParameter(Constants.COMPANY_ID);
		
		try {
        	itemsData = excelService.getExcelData(companyId,excepReportId);
		} catch (Exception e) {
       	 e.printStackTrace();
       }
		return jsend1(itemsData);
		
	}
	///itemname/{itemname}/state/{state}/district/{district}/year/{year}
	@RequestMapping(value="/company/getexcelreport", method=RequestMethod.GET, produces = { BillliveMediaType.APPLICATION_JSON })
    public  @ResponseBody JSendResponse<List<ExcelPieChart>> getExcelReport(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("itemname") String itemname, @RequestParam("state") String state,@RequestParam("district") String district,@RequestParam("year") String year){
		List<ExcelPieChart> itemsData = null;
		String companyId = (String) request.getParameter(Constants.COMPANY_ID);
		
		try {
        	itemsData = excelService.getExcelReport(companyId,itemname,state,district,year);
		} catch (Exception e) {
       	 e.printStackTrace();
       }
		return jsend(itemsData);
		
	}
}
