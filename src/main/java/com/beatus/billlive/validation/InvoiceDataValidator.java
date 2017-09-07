package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.InvoiceDTO;
import com.beatus.billlive.domain.model.ItemDTO;
import com.beatus.billlive.validation.exception.BillliveClientValidationException;

@Component("invoiceDataValidator")
public class InvoiceDataValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceDataValidator.class);

	public boolean validateInvoiceItemDTO(ItemDTO invoiceItemDTO) throws BillliveClientValidationException{
		LOGGER.info(" Validating InvoiceItemDTO " + invoiceItemDTO);
		if(invoiceItemDTO == null || StringUtils.isBlank(invoiceItemDTO.getItemId())){
			throw new BillliveClientValidationException("invoiceItemDTO","InvoiceItemDTO, invoiceItemDTO is null");
		}
		if(StringUtils.isBlank(invoiceItemDTO.getInventoryId())){
			throw new BillliveClientValidationException("inventoryId","InvoiceItemDTO, the inventoryId field is not available, for the invoice number " + invoiceItemDTO.getItemId());
		}
		if(invoiceItemDTO.getProductValue() == null){
			throw new BillliveClientValidationException("productValue","InvoiceItemDTO, the productValue field is not available, for the invoice number " + invoiceItemDTO.getItemId());
		}
		if(invoiceItemDTO.getQuantityType() == null){
			throw new BillliveClientValidationException("quantityType","InvoiceItemDTO, the quantityType field is not available, for the invoice number " + invoiceItemDTO.getItemId());
		}
		if(invoiceItemDTO.getQuantity() == null){
			throw new BillliveClientValidationException("quantity","InvoiceItemDTO, the quantity field is not available, for the invoice number " + invoiceItemDTO.getItemId());
		}
		if(invoiceItemDTO.getActualUnitPrice() == null){
			throw new BillliveClientValidationException("actualUnitPrice","InvoiceItemDTO, the actualUnitPrice field is not available, for the invoice number " + invoiceItemDTO.getItemId());
		}
		if(invoiceItemDTO.getAmountBeforeTax() == null){
			throw new BillliveClientValidationException("amountBeforeTax","InvoiceItemDTO, the amountBeforeTax field is not available, for the invoice number " + invoiceItemDTO.getItemId());
		}
		if(invoiceItemDTO.getTaxAmountForItem() == null){
			throw new BillliveClientValidationException("taxAmountForItem","InvoiceItemDTO, the taxAmountForItem field is not available, for the invoice number " + invoiceItemDTO.getItemId());
		}
		if(invoiceItemDTO.getTotalCGST() == null){
			throw new BillliveClientValidationException("totalCGST","InvoiceItemDTO, the totalCGST field is not available, for the invoice number " + invoiceItemDTO.getItemId());
		}
		if(invoiceItemDTO.getTotalSGST() == null){
			throw new BillliveClientValidationException("totalSGST","InvoiceItemDTO, the totalSGST field is not available, for the invoice number " + invoiceItemDTO.getItemId());
		}
		if(invoiceItemDTO.getTotalIGST() != null){
			throw new BillliveClientValidationException("totalIGST","InvoiceItemDTO, the totalIGST field is not available, for the invoice number " + invoiceItemDTO.getItemId());
		}
		if(invoiceItemDTO.getAmountAfterTax() == null){
			throw new BillliveClientValidationException("amountAfterTax","InvoiceItemDTO, the amountAfterTax field is not available, for the invoice number " + invoiceItemDTO.getItemId());
		}
		if(invoiceItemDTO.getMarginAmount() == null){
			throw new BillliveClientValidationException("marginAmount","InvoiceItemDTO, the marginAmount field is not available, for the invoice number " + invoiceItemDTO.getItemId());
		}
		if(StringUtils.isBlank(invoiceItemDTO.getTaxId())){
			throw new BillliveClientValidationException("taxId","InvoiceDTO, the taxId field is not available, for the invoice number " + invoiceItemDTO.getItemId());
		}
		return true;
	}

	public boolean validateInvoiceDTO(InvoiceDTO invoiceDTO) throws BillliveClientValidationException {
		LOGGER.info(" Validating invoiceDTO " + invoiceDTO);
		if(invoiceDTO.getIsUpdated().equals('Y')){
			if(invoiceDTO == null || StringUtils.isBlank(invoiceDTO.getInvoiceNumber())){
				throw new BillliveClientValidationException("invoiceDTO","InvoiceDTO is null ot");
			}
		}
		if(StringUtils.isBlank(invoiceDTO.getInvoiceFromContactId())){
			throw new BillliveClientValidationException("invoiceFromContactId","InvoiceDTO, the invoiceFromContactId field is not available, for the invoice number " + invoiceDTO.getInvoiceNumber());
		}
		if(StringUtils.isBlank(invoiceDTO.getInvoiceToContactId())){
			throw new BillliveClientValidationException("InvoiceToContactId","InvoiceDTO, the InvoiceToContactId field is not available, for the invoice number " + invoiceDTO.getInvoiceNumber());
		}
		if(StringUtils.isBlank(invoiceDTO.getCompanyId())){
			throw new BillliveClientValidationException("CompanyId","InvoiceDTO, the CompanyId field is not available, for the invoice number " + invoiceDTO.getInvoiceNumber());
		}
		if(StringUtils.isBlank(invoiceDTO.getUid())){
			throw new BillliveClientValidationException("uid","InvoiceDTO, the uid field is not available, for the invoice number " + invoiceDTO.getInvoiceNumber());
		}
		if(StringUtils.isBlank(invoiceDTO.getDateOfInvoice())){
			throw new BillliveClientValidationException("dateOfInvoice","InvoiceDTO, the dateOfInvoice field is not available, for the invoice number " + invoiceDTO.getInvoiceNumber());
		}
		if(StringUtils.isBlank(invoiceDTO.getDueDate())){
			throw new BillliveClientValidationException("dueDate","InvoiceDTO, the dueDate field is not available, for the invoice number " + invoiceDTO.getInvoiceNumber());
		}
		if(invoiceDTO.getTotalAmount() == null){
			throw new BillliveClientValidationException("totalAmount","InvoiceDTO, the totalAmount field is not available, for the invoice number " + invoiceDTO.getInvoiceNumber());
		}
		if(invoiceDTO.getTotalTax() == null){
			throw new BillliveClientValidationException("totalTax","InvoiceDTO, the totalTax field is not available, for the invoice number " + invoiceDTO.getInvoiceNumber());
		}
		if(invoiceDTO.getTotalCGST() == null){
			throw new BillliveClientValidationException("totalCGST","InvoiceDTO, the totalCGST field is not available, for the invoice number " + invoiceDTO.getInvoiceNumber());
		}
		if(invoiceDTO.getTotalSGST() == null){
			throw new BillliveClientValidationException("totalSGST","InvoiceDTO, the totalSGST field is not available, for the invoice number " + invoiceDTO.getInvoiceNumber());
		}
		if(invoiceDTO.getTotalIGST() != null){
			throw new BillliveClientValidationException("totalIGST","InvoiceDTO, the totalIGST field is not available, for the invoice number " + invoiceDTO.getInvoiceNumber());
		}
		for(ItemDTO itemDTO : invoiceDTO.getItems()){
			validateInvoiceItemDTO(itemDTO);
		}
		return true;
	}

}
