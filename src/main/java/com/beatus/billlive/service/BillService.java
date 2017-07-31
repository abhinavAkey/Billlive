package com.beatus.billlive.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.beatus.billlive.config.ApplicationConfiguration;
import com.beatus.billlive.domain.model.BillDTO;
import com.beatus.billlive.domain.model.BillData;
import com.beatus.billlive.domain.model.BillItemData;
import com.beatus.billlive.domain.model.ItemDTO;
import com.beatus.billlive.domain.model.ItemData;
import com.beatus.billlive.domain.model.Tax;
import com.beatus.billlive.repository.BillRepository;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.utils.Utils;
import com.beatus.billlive.validation.BillValidator;
import com.beatus.billlive.validation.exception.BillDataException;

@Service
@Component("billService")
public class BillService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfiguration.class);

	@Resource(name = "billRepository")
	private BillRepository billRepository;
	
	@Resource(name = "billValidator")
	private BillValidator billValidator;
	
	@Resource(name = "taxService")
	private TaxService taxService;
	
	@Resource(name = "itemService")
	private ItemService itemService;


	public String addBill(HttpServletRequest request, HttpServletResponse response, BillDTO billDTO, String companyId) throws BillDataException{
		
		if(billDTO == null){
			throw new BillDataException("Bill data cant be null");
		}
		try {
			//Revisit validator
			boolean isValidated = billValidator.validateBillData(billDTO);
			if(isValidated){
				if(StringUtils.isBlank(companyId)){
					companyId = billDTO.getCompanyId();
				}
				BillData existingBill = null;
				if(StringUtils.isNotBlank(billDTO.getBillNumber())){
					existingBill = billRepository.getBillByBillNumber(companyId, billDTO.getBillNumber());
					return updateBill(request, response, billDTO, companyId);
				}
				BillData billData = populateBillData(billDTO, existingBill, companyId);				
				return billRepository.addBill(billData);
			}
		} catch (BillDataException billException) {
			LOGGER.info("Bill validation Exception in the addBillService() {} ", billException.getMessage());
			throw billException;
		}	
		return "N";		
	}

	public String updateBill(HttpServletRequest request, HttpServletResponse response, BillDTO billDTO, String companyId) throws BillDataException{
		
		if(billDTO == null){
			throw new BillDataException("Bill data cant be null");
		}
		try {
			//Revisit validator
			boolean isValidated = billValidator.validateBillData(billDTO);
			if(isValidated){
				if(StringUtils.isBlank(companyId)){
					companyId = billDTO.getCompanyId();
				}
				BillData existingBill = null;
				if(StringUtils.isNotBlank(billDTO.getBillNumber()))
					existingBill = billRepository.getBillByBillNumber(companyId, billDTO.getBillNumber());
				BillData billData = populateBillData(billDTO, existingBill, companyId);			
				return billRepository.updateBill(billData);
			}
		} catch (BillDataException billException) {
			LOGGER.info("Bill validation Exception in the addBillService() {} ", billException.getMessage());
			throw billException;
		}
		
		return "Y";		
	}
	
	public String removeBill(String companyId, String billNumber) {
		if(StringUtils.isNotBlank(billNumber) && StringUtils.isNotBlank(companyId)){
			BillData billData = billRepository.getBillByBillNumber(companyId, billNumber);
			billData.setIsRemoved(Constants.YES);
			return billRepository.updateBill(billData);
		}
		return "N";
	}

	public List<BillDTO> getAllBillsBasedOnCompanyId(String companyId) {
		List<BillData> bills = billRepository.getAllBillsBasedOnCompanyId(companyId);
		List<BillDTO> billsNotRemoved = new ArrayList<BillDTO>();
		for(BillData bill : bills){
			if(!Constants.YES.equalsIgnoreCase(bill.getIsRemoved())){
				billsNotRemoved.add(populateBillDTO(bill));
			}
		}
		return billsNotRemoved;
	}

	public BillDTO getBillByBillNumber(String companyId, String billNumber) {
		BillData billData = billRepository.getBillByBillNumber(companyId, billNumber);
		if(!Constants.YES.equalsIgnoreCase(billData.getIsRemoved())){
			return populateBillDTO(billData);
		}else {
			return null;
		}
	}
	
	public List<BillDTO> getAllBillsInAMonth(String companyId, String year, String month) {
		List<BillData> bills = billRepository.getAllBillsInAMonth(companyId, year, month);
		List<BillDTO> billsNotRemoved = new ArrayList<BillDTO>();
		for(BillData bill : bills){
			if(!Constants.YES.equalsIgnoreCase(bill.getIsRemoved())){
				billsNotRemoved.add(populateBillDTO(bill));
			}
		}
		return billsNotRemoved;
	}
	
	public List<BillDTO> getAllBillsInADay(String companyId, String year, String month, String day) {
		List<BillData> bills = billRepository.getAllBillsInADay(companyId, year, month, day);
		List<BillDTO> billsNotRemoved = new ArrayList<BillDTO>();
		for(BillData bill : bills){
			if(!Constants.YES.equalsIgnoreCase(bill.getIsRemoved())){
				billsNotRemoved.add(populateBillDTO(bill));
			}
		}
		return billsNotRemoved;
	}
	
	public List<BillDTO> getAllBillsInAnYear(String companyId, String year) {
		List<BillData> bills = billRepository.getAllBillsInAYear(companyId, year);
		List<BillDTO> billsNotRemoved = new ArrayList<BillDTO>();
		for(BillData bill : bills){
			if(!Constants.YES.equalsIgnoreCase(bill.getIsRemoved())){
				billsNotRemoved.add(populateBillDTO(bill));
			}
		}
		return billsNotRemoved;
	}
	
	private BillDTO populateBillDTO(BillData bill) {
		BillDTO billDTO = new BillDTO();
		billDTO.setBillNumber(bill.getBillNumber());
		billDTO.setBillFromContactId(bill.getBillFromContactId());
		billDTO.setBillToContactId(bill.getBillToContactId());
		//billDTO.set
		return null;
	}


	private BillData populateBillData(BillDTO billDTO, BillData existingBill, String companyId) {
		BillData billData = new BillData();
		billData.setBillFromContactId(billDTO.getBillFromContactId());
		billData.setBillToContactId(billDTO.getBillToContactId());
		if(existingBill == null  && StringUtils.isBlank(billDTO.getBillNumber())){
			billData.setBillNumber(Utils.generateRandomKey(20));
		} 
		if(existingBill != null){
			billData.setBillNumber(billDTO.getBillNumber());
			billData.setYear(existingBill.getYear());
			billData.setMonth(existingBill.getMonth());
			billData.setDay(existingBill.getDay());
		}else {
			Date date = new Date();
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(date);
		    billData.setYear(String.valueOf(cal.get(Calendar.YEAR)));
		    billData.setMonth(String.valueOf(cal.get(Calendar.MONTH)));
		    billData.setDay(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
		}
		billData.setIsTaxeble(billDTO.getIsTaxeble());
		billData.setDateOfBill(billDTO.getDateOfBill());
		billData.setDueDate(billDTO.getDueDate());
		billData.setCompanyId(billDTO.getCompanyId());
		billData.setReferenceAadharCardNumber(billDTO.getReferenceAadharCardNumber());
		billData.setReferenceMobileNumber(billDTO.getReferenceMobileNumber());
		List<BillItemData> billItems = new ArrayList<BillItemData>();
		for(ItemDTO itemDTO : billDTO.getItems()){
			BillItemData billItem = new BillItemData();
			if(Constants.YES.equalsIgnoreCase(itemDTO.getIsAdded()) || Constants.YES.equalsIgnoreCase(itemDTO.getIsUpdated())){		
				//Get Item Details
				ItemData existingItem = itemService.getItemById(companyId, itemDTO.getItemId());
				
				billItem.setItemId(itemDTO.getItemId());
				billItem.setInventoryId(itemDTO.getInventoryId());
				billItem.setIsTaxeble(itemDTO.getIsTaxeble());
				billItem.setQuantity(itemDTO.getQuantity());
				billItem.setProductValue(itemDTO.getProductValue());
				billItem.setQuantityType(itemDTO.getQuantityType());
				Double amountBeforeTax = itemDTO.getProductValue()*itemDTO.getQuantity();
				if(itemDTO.getAmountBeforeTax() == null){
					billItem.setAmountBeforeTax(amountBeforeTax);
				}else {
					billItem.setAmountBeforeTax(itemDTO.getAmountBeforeTax());
				}
				Double taxAmountForItem = null;
				Tax tax = taxService.getTaxById(companyId, itemDTO.getTaxId());
				Double taxPercentage = tax.getTotalTaxPercentage();
				if(Constants.YES.equalsIgnoreCase(itemDTO.getIsTaxeble())){
					if(itemDTO.getTaxAmountForItem() == null){
						taxAmountForItem = Utils.calculateTaxAmount(amountBeforeTax,taxPercentage);
						billItem.setTaxAmountForItem(taxAmountForItem);
					}else {
						billItem.setTaxAmountForItem(itemDTO.getTaxAmountForItem());
					}
				}else {
					taxAmountForItem = 0.00;
				}
				if(itemDTO.getAmountAfterTax() == null){
					Double amountAfterTax = amountBeforeTax + taxAmountForItem;
					billItem.setAmountBeforeTax(amountAfterTax);
				}else {
					billItem.setAmountBeforeTax(itemDTO.getAmountAfterTax());
				}
				billItem.setDiscount(itemDTO.getDiscount());
				Double marginAmount = itemDTO.getMarginAmount();
				if(marginAmount == null){
					marginAmount = Utils.calculateMarginAmount(existingItem, itemDTO);
					billItem.setMarginAmount(marginAmount);
				}
				if(itemDTO.getTaxOnMargin() == null){
					billItem.setTaxOnMargin(Utils.calculateTaxOnMargin(marginAmount, taxPercentage));
				}
			}else if(billData != null && Constants.YES.equalsIgnoreCase(itemDTO.getIsDeleted())){
				for(BillItemData existingBillItem : billData.getBillItems()){
					if(existingBillItem.getItemId().equalsIgnoreCase(itemDTO.getItemId())){
						billItem = existingBillItem;
						billItem.setIsRemoved(Constants.YES);
					}
				}
			}
			billItems.add(billItem);
		}
		return billData;
	}

}
