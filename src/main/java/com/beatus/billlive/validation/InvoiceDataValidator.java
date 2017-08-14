package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.InvoiceData;
import com.beatus.billlive.validation.exception.InvoiceDataValidationException;

@Component("invoiceDataValidator")
public class InvoiceDataValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceDataValidator.class);

	public boolean validateInvoiceDataData(InvoiceData invoiceData) throws InvoiceDataValidationException{
		LOGGER.info(" Validating InvoiceData " + invoiceData);

		if(invoiceData == null){
			throw new InvoiceDataValidationException("InvoiceData data is null");
		}
		if(invoiceData.getIsUpdated().equals('Y')){
			if(StringUtils.isBlank(invoiceData.getInvoiceNumber())){
				throw new InvoiceDataValidationException("invoiceNumber is null");
			}
		}
		if(invoiceData.getInvoiceFrom() != null){
			throw new InvoiceDataValidationException("InvoiceData, the invoiceFrom field is not available, for the invoiceData with id =  " + invoiceData.getInvoiceNumber());
		}
		if(invoiceData.getInvoiceTo() != null){
			throw new InvoiceDataValidationException("InvoiceData, the invoiceTo field is not available, for the invoiceData with id =  " + invoiceData.getInvoiceNumber());
		}
		if(invoiceData.getUid() != null){
			throw new InvoiceDataValidationException("InvoiceData, the uid field is not available, for the invoiceData with id =  " + invoiceData.getInvoiceNumber());
		}
		if(invoiceData.getItemId() != null){
			throw new InvoiceDataValidationException("InvoiceData, the itemId field is not available, for the invoiceData with id =  " + invoiceData.getInvoiceNumber());
		}
	
		if(StringUtils.isBlank(invoiceData.getDateOfBill())){
			throw new InvoiceDataValidationException("InvoiceData, the dateOfBill field is not available, for the invoiceData with id =  " + invoiceData.getInvoiceNumber());
		}
		if(StringUtils.isBlank(invoiceData.getDueDate())){
			throw new InvoiceDataValidationException("InvoiceData, the dueDate field is not available, for the invoiceData with id =  " + invoiceData.getInvoiceNumber());
		}
		if(StringUtils.isBlank(invoiceData.getInventoryId())){
			throw new InvoiceDataValidationException("InvoiceData, the inventoryId field is not available, for the invoiceData with id =  " + invoiceData.getInvoiceNumber());
		}
		if(StringUtils.isBlank(invoiceData.getUnitPrice())){
			throw new InvoiceDataValidationException("InvoiceData, the unitPrice field is not available, for the invoiceData with id =  " + invoiceData.getInvoiceNumber());
		}
		if(StringUtils.isBlank(invoiceData.getTotalAmount())){
			throw new InvoiceDataValidationException("InvoiceData, the totalAmount field is not available, for the invoiceData with id =  " + invoiceData.getInvoiceNumber());
		}
		if(StringUtils.isBlank(invoiceData.getTaxId())){
			throw new InvoiceDataValidationException("InvoiceData, the taxId field is not available, for the invoiceData with id =  " + invoiceData.getInvoiceNumber());
		}
		return true;
		
	}

}
