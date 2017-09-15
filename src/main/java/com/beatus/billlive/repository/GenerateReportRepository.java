package com.beatus.billlive.repository;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.BillDTO;
import com.beatus.billlive.domain.model.ReportData;
import com.beatus.billlive.service.BillService;
import com.google.firebase.database.DatabaseReference;


@Component("generateReportRepository")
public class GenerateReportRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(GenerateReportRepository.class);

	@Autowired
    @Qualifier(value = "databaseReference")
    private DatabaseReference databaseReference;
	
	@Resource(name = "billService")
	private BillService billService;
	
	public ReportData getAllBillsBasedOnCompanyId(String companyId) {
		logger.info("In getAllBillsBasedOnCompanyId");
		List<BillDTO> bills = billService.getAllBillsBasedOnCompanyId(companyId);
		Double totalAmount = 0.0;
		Double totalTax = 0.0;
		for(BillDTO bill : bills){
			if(bill != null){
				totalAmount += bill.getTotalAmount();
				totalTax += bill.getTotalTax();
			}
		}
		ReportData report = new ReportData();
		report.setBills(bills);
		report.setTotalAmountIncludingTax(totalAmount);
		report.setTotalTax(totalTax);
		report.setCompanyId(companyId);
		return report;
	}
	
	public ReportData getAllBillsInAMonth(String companyId, String year, String month) {
		logger.info("In getAllBillsInAMonth");
		List<BillDTO> bills = billService.getAllBillsInAMonth(companyId, year, month);
		Double totalAmount = 0.0;
		Double totalTax = 0.0;
		for(BillDTO bill : bills){
			if(bill != null){
				totalAmount += bill.getTotalAmount();
				totalTax += bill.getTotalTax();
			}
		}
		ReportData report = new ReportData();
		report.setBills(bills);
		report.setTotalAmountIncludingTax(totalAmount);
		report.setTotalTax(totalTax);
		report.setCompanyId(companyId);
		return report;
	}
	
	public ReportData getAllBillsInAYear(String companyId, String year) {
		logger.info("In getAllBillsInAYear");
		List<BillDTO> bills = billService.getAllBillsInAnYear(companyId, year);
		Double totalAmount = 0.0;
		Double totalTax = 0.0;
		for(BillDTO bill : bills){
			if(bill != null){
				totalAmount += bill.getTotalAmount();
				totalTax += bill.getTotalTax();
			}
		}
		ReportData report = new ReportData();
		report.setBills(bills);
		report.setTotalAmountIncludingTax(totalAmount);
		report.setTotalTax(totalTax);
		report.setCompanyId(companyId);
		return report;
	}
	
	public ReportData getAllBillsInADay(String companyId, String year, String month, String day) {
		logger.info("In getAllBillsInADay");
		List<BillDTO> bills = billService.getAllBillsInADay(companyId, year, month, day);
		Double totalAmount = 0.0;
		Double totalTax = 0.0;
		for(BillDTO bill : bills){
			if(bill != null){
				totalAmount += bill.getTotalAmount();
				totalTax += bill.getTotalTax();
			}
		}
		ReportData report = new ReportData();
		report.setBills(bills);
		report.setTotalAmountIncludingTax(totalAmount);
		report.setTotalTax(totalTax);
		report.setCompanyId(companyId);
		return report;
	}
	
	public ReportData getAllBillsBasedOnTaxId(String companyId, String year, String month, String day) {
		logger.info("In getAllBillsInADay");
		List<BillDTO> bills = billService.getAllBillsInADay(companyId, year, month, day);
		Double totalAmount = 0.0;
		Double totalTax = 0.0;
		for(BillDTO bill : bills){
			if(bill != null){
				totalAmount += bill.getTotalAmount();
				totalTax += bill.getTotalTax();
			}
		}
		ReportData report = new ReportData();
		report.setBills(bills);
		report.setTotalAmountIncludingTax(totalAmount);
		report.setTotalTax(totalTax);
		report.setCompanyId(companyId);
		return report;
	}

	
}