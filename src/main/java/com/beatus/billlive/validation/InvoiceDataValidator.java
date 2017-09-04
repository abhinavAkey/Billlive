package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.InvoiceData;
import com.beatus.billlive.validation.exception.BillliveClientValidationException;

@Component("invoiceDataValidator")
public class InvoiceDataValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceDataValidator.class);

	public boolean validateInvoiceData(InvoiceData invoiceData) throws BillliveClientValidationException{
		LOGGER.info(" Validating InvoiceData " + invoiceData);

		if(invoiceData == null){
			throw new BillliveClientValidationException("invoiceData","InvoiceData, data is null");
		}
		if(invoiceData.getIsUpdated().equals('Y')){
			if(StringUtils.isBlank(invoiceData.getInvoiceNumber())){
				throw new BillliveClientValidationException("InvoiceNumber","InvoiceNumber is null");
			}
		}
		if(invoiceData.getInvoiceFrom() != null){
			throw new BillliveClientValidationException("invoiceFrom","InvoiceData, the invoiceFrom field is not available, for the invoiceData with id =  " + invoiceData.getInvoiceNumber());
		}
		if(invoiceData.getInvoiceTo() != null){
			throw new BillliveClientValidationException("invoiceTo","InvoiceData, the invoiceTo field is not available, for the invoiceData with id =  " + invoiceData.getInvoiceNumber());
		}
		if(invoiceData.getUid() != null){
			throw new BillliveClientValidationException("uid","InvoiceData, the uid field is not available, for the invoiceData with id =  " + invoiceData.getInvoiceNumber());
		}
		if(invoiceData.getItemId() != null){
			throw new BillliveClientValidationException("itemId","InvoiceData, the itemId field is not available, for the invoiceData with id =  " + invoiceData.getInvoiceNumber());
		}
	
		if(StringUtils.isBlank(invoiceData.getDateOfBill())){
			throw new BillliveClientValidationException("dateOfBill","InvoiceData, the dateOfBill field is not available, for the invoiceData with id =  " + invoiceData.getInvoiceNumber());
		}
		if(StringUtils.isBlank(invoiceData.getDueDate())){
			throw new BillliveClientValidationException("dueDate","InvoiceData, the dueDate field is not available, for the invoiceData with id =  " + invoiceData.getInvoiceNumber());
		}
		if(StringUtils.isBlank(invoiceData.getInventoryId())){
			throw new BillliveClientValidationException("inventoryId","InvoiceData, the inventoryId field is not available, for the invoiceData with id =  " + invoiceData.getInvoiceNumber());
		}
		if(StringUtils.isBlank(invoiceData.getUnitPrice())){
			throw new BillliveClientValidationException("unitPrice","InvoiceData, the unitPrice field is not available, for the invoiceData with id =  " + invoiceData.getInvoiceNumber());
		}
		if(StringUtils.isBlank(invoiceData.getTotalAmount())){
			throw new BillliveClientValidationException("totalAmount","InvoiceData, the totalAmount field is not available, for the invoiceData with id =  " + invoiceData.getInvoiceNumber());
		}
		if(StringUtils.isBlank(invoiceData.getTaxId())){
			throw new BillliveClientValidationException("taxId","InvoiceData, the taxId field is not available, for the invoiceData with id =  " + invoiceData.getInvoiceNumber());
		}
		return true;
		
	}

}
