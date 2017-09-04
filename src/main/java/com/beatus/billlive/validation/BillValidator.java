package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.BillDTO;
import com.beatus.billlive.domain.model.BillItemDTO;
import com.beatus.billlive.validation.exception.BillliveClientValidationException;

@Component("billValidator")
public class BillValidator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BillValidator.class);
	
	public boolean validateBillData(BillDTO bill) throws BillliveClientValidationException{
		/*if(bill == null || StringUtils.isBlank(bill.getUid())){
			throw new BillDataException("Bill data is null");
		}
		if(StringUtils.isBlank(bill.getDateOfBill())){
			throw new BillDataException("Bill data the date field is not available " + bill.getUid());
		}
		if(StringUtils.isBlank(bill.getDueDate())){
			throw new BillDataException("Bill data the due date field is not available " + bill.getUid());
		}
		if(bill.getItems() == null && bill.getItems().size() > 0){
			for(BillItemDTO item : bill.getItems()){
				if(StringUtils.isBlank(item.getItemId())){
					throw new BillDataException("Item Id can't be null");
				}
				if(StringUtils.isBlank(item.getIsTaxeble())){
					throw new BillDataException("Is taxeble can't be null " + item.getItemId());
				}
				if(StringUtils.isBlank(item.getTaxId())){
					throw new BillDataException("Tax Id can't be null " + item.getItemId());
				}
			}
		}else {
			throw new BillDataException("In Bill the items data is not available");
		}		*/
		return true;
	}
	public boolean validateBillItemDTO(BillItemDTO billItemDTO) throws BillliveClientValidationException{
		LOGGER.info(" Validating BillItemDTO " + billItemDTO);
		if(billItemDTO == null || StringUtils.isBlank(billItemDTO.getItemId())){
			throw new BillliveClientValidationException("billItemDTO","BillItemDTO, billItemDTO is null");
		}
		if(StringUtils.isBlank(billItemDTO.getInventoryId())){
			throw new BillliveClientValidationException("inventoryId","BillItemDTO, the inventoryId field is not available, for the bill number " + billItemDTO.getItemId());
		}
		if(billItemDTO.getProductValue() == null){
			throw new BillliveClientValidationException("productValue","BillItemDTO, the productValue field is not available, for the bill number " + billItemDTO.getItemId());
		}
		if(billItemDTO.getQuantityType() == null){
			throw new BillliveClientValidationException("quantityType","BillItemDTO, the quantityType field is not available, for the bill number " + billItemDTO.getItemId());
		}
		if(billItemDTO.getQuantity() == null){
			throw new BillliveClientValidationException("quantity","BillItemDTO, the quantity field is not available, for the bill number " + billItemDTO.getItemId());
		}
		if(billItemDTO.getActualUnitPrice() == null){
			throw new BillliveClientValidationException("actualUnitPrice","BillItemDTO, the actualUnitPrice field is not available, for the bill number " + billItemDTO.getItemId());
		}
		if(billItemDTO.getAmountBeforeTax() == null){
			throw new BillliveClientValidationException("amountBeforeTax","BillItemDTO, the amountBeforeTax field is not available, for the bill number " + billItemDTO.getItemId());
		}
		if(billItemDTO.getTaxAmountForItem() == null){
			throw new BillliveClientValidationException("taxAmountForItem","BillItemDTO, the taxAmountForItem field is not available, for the bill number " + billItemDTO.getItemId());
		}
		if(billItemDTO.getTotalCGST() == null){
			throw new BillliveClientValidationException("totalCGST","BillItemDTO, the totalCGST field is not available, for the bill number " + billItemDTO.getItemId());
		}
		if(billItemDTO.getTotalSGST() == null){
			throw new BillliveClientValidationException("totalSGST","BillItemDTO, the totalSGST field is not available, for the bill number " + billItemDTO.getItemId());
		}
		if(billItemDTO.getTotalIGST() != null){
			throw new BillliveClientValidationException("totalIGST","BillItemDTO, the totalIGST field is not available, for the bill number " + billItemDTO.getItemId());
		}
		if(billItemDTO.getAmountAfterTax() == null){
			throw new BillliveClientValidationException("amountAfterTax","BillItemDTO, the amountAfterTax field is not available, for the bill number " + billItemDTO.getItemId());
		}
		if(billItemDTO.getMarginAmount() == null){
			throw new BillliveClientValidationException("marginAmount","BillItemDTO, the marginAmount field is not available, for the bill number " + billItemDTO.getItemId());
		}
		if(StringUtils.isBlank(billItemDTO.getTaxId())){
			throw new BillliveClientValidationException("taxId","BillDTO, the taxId field is not available, for the bill number " + billItemDTO.getItemId());
		}
		return true;
	}

	public boolean validateBillDTO(BillDTO billDTO) throws BillliveClientValidationException {
		LOGGER.info(" Validating billDTO " + billDTO);
		if(billDTO.getIsUpdated().equals('Y')){
			if(billDTO == null || StringUtils.isBlank(billDTO.getBillNumber())){
				throw new BillliveClientValidationException("billDTO","BillDTO is null ot");
			}
		}
		if(StringUtils.isBlank(billDTO.getBillFromContactId())){
			throw new BillliveClientValidationException("billFromContactId","BillDTO, the billFromContactId field is not available, for the bill number " + billDTO.getBillNumber());
		}
		if(StringUtils.isBlank(billDTO.getBillToContactId())){
			throw new BillliveClientValidationException("BillToContactId","BillDTO, the BillToContactId field is not available, for the bill number " + billDTO.getBillNumber());
		}
		if(StringUtils.isBlank(billDTO.getCompanyId())){
			throw new BillliveClientValidationException("CompanyId","BillDTO, the CompanyId field is not available, for the bill number " + billDTO.getBillNumber());
		}
		if(StringUtils.isBlank(billDTO.getUid())){
			throw new BillliveClientValidationException("uid","BillDTO, the uid field is not available, for the bill number " + billDTO.getBillNumber());
		}
		if(StringUtils.isBlank(billDTO.getDateOfBill())){
			throw new BillliveClientValidationException("dateOfBill","BillDTO, the dateOfBill field is not available, for the bill number " + billDTO.getBillNumber());
		}
		if(StringUtils.isBlank(billDTO.getDueDate())){
			throw new BillliveClientValidationException("dueDate","BillDTO, the dueDate field is not available, for the bill number " + billDTO.getBillNumber());
		}
		if(billDTO.getTotalAmount() == null){
			throw new BillliveClientValidationException("totalAmount","BillDTO, the totalAmount field is not available, for the bill number " + billDTO.getBillNumber());
		}
		if(billDTO.getTotalTax() == null){
			throw new BillliveClientValidationException("totalTax","BillDTO, the totalTax field is not available, for the bill number " + billDTO.getBillNumber());
		}
		if(billDTO.getTotalCGST() == null){
			throw new BillliveClientValidationException("totalCGST","BillDTO, the totalCGST field is not available, for the bill number " + billDTO.getBillNumber());
		}
		if(billDTO.getTotalSGST() == null){
			throw new BillliveClientValidationException("totalSGST","BillDTO, the totalSGST field is not available, for the bill number " + billDTO.getBillNumber());
		}
		if(billDTO.getTotalIGST() != null){
			throw new BillliveClientValidationException("totalIGST","BillDTO, the totalIGST field is not available, for the bill number " + billDTO.getBillNumber());
		}
		for(BillItemDTO itemDTO : billDTO.getItems()){
			validateBillItemDTO(itemDTO);
		}
		return true;
	}
}
