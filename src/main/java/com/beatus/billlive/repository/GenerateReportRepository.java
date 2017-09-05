package com.beatus.billlive.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.BillDTO;
import com.beatus.billlive.domain.model.BillData;
import com.beatus.billlive.domain.model.ReportData;
import com.beatus.billlive.service.BillService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


@Component("generateReportRepository")
public class GenerateReportRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(GenerateReportRepository.class);

	@Autowired
    @Qualifier(value = "databaseReference")
    private DatabaseReference databaseReference;
	
	@Resource(name = "billService")
	private BillService billService;
	
	private BillData billData = null;
	
	List<BillData> billsList = new ArrayList<BillData>();
	
	public ReportData getAllBillsBasedOnCompanyId(String companyId) {
		List<BillDTO> bills = billService.getAllBills(companyId);
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
	
	public List<BillData> getAllBillsInAMonth(String companyId, String year, String month) {
		DatabaseReference billDataRef = databaseReference.child("bills").child(companyId);
		billDataRef.orderByChild("year").equalTo(year).orderByChild("month").equalTo(month).addValueEventListener(new ValueEventListener() {
		    public void onDataChange(DataSnapshot billSnapshot) {
		    	billsList.clear();
		        for (DataSnapshot billPostSnapshot: billSnapshot.getChildren()) {
		            BillData billData = billPostSnapshot.getValue(BillData.class);
		            billsList.add(billData);
		        }
		    }
		    
			@Override
			public void onCancelled(DatabaseError error) {
				
			}
		});
		return billsList;
	}
	
	public List<BillData> getAllBillsInAYear(String companyId, String year) {
		DatabaseReference billDataRef = databaseReference.child("bills").child(companyId);
		billDataRef.orderByChild("year").equalTo(year).addValueEventListener(new ValueEventListener() {
		    public void onDataChange(DataSnapshot billSnapshot) {
		    	billsList.clear();
		        for (DataSnapshot billPostSnapshot: billSnapshot.getChildren()) {
		            BillData billData = billPostSnapshot.getValue(BillData.class);
		            billsList.add(billData);
		        }
		    }
		    
			@Override
			public void onCancelled(DatabaseError error) {
				
			}
		});
		return billsList;
	}
	
	public List<BillData> getAllBillsInADay(String companyId, String year, String month, String day) {
		DatabaseReference billDataRef = databaseReference.child("bills").child(companyId);
		billDataRef.orderByChild("year").equalTo(year).orderByChild("month").equalTo(month).orderByChild("day").equalTo(day).addValueEventListener(new ValueEventListener() {
		    public void onDataChange(DataSnapshot billSnapshot) {
		    	billsList.clear();
		        for (DataSnapshot billPostSnapshot: billSnapshot.getChildren()) {
		            BillData billData = billPostSnapshot.getValue(BillData.class);
		            billsList.add(billData);
		        }
		    }
		    
			@Override
			public void onCancelled(DatabaseError error) {
				
			}
		});
		return billsList;
	}

	
}