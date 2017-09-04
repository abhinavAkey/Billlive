package com.beatus.billlive.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beatus.billlive.domain.model.PurchaseOrderDTO;
import com.beatus.billlive.domain.model.PurchaseOrderItemDTO;
import com.beatus.billlive.exception.InventoryValidationException;
import com.beatus.billlive.exception.ItemDataException;
import com.beatus.billlive.validation.exception.BillliveClientValidationException;

@Component("purchaseOrderValidator")
public class PurchaseOrderValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderValidator.class);
	@Autowired
	private ItemValidator itemValidator;
	public boolean validatePurchaseOrderData(PurchaseOrderDTO purchaseOrderDTO) throws BillliveClientValidationException, ItemDataException, InventoryValidationException {
		LOGGER.info(" Validating validatePurchaseOrderData " + purchaseOrderDTO);
		if(purchaseOrderDTO == null || StringUtils.isBlank(purchaseOrderDTO.getPurchaseOrderNumber())){
			throw new BillliveClientValidationException("purchaseOrderDTO","PurchaseOrderDTO, purchaseOrderDTO is null");
		}
		if( StringUtils.isBlank(purchaseOrderDTO.getPurchaseFromContactId())){
			throw new BillliveClientValidationException("purchaseFromContactId","PurchaseOrderDTO, the purchaseFromContactId field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(StringUtils.isBlank(purchaseOrderDTO.getPurchaseToContactId())){
			throw new BillliveClientValidationException("purchaseToContactId","PurchaseOrderDTO, the purchaseToContactId field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(StringUtils.isBlank(purchaseOrderDTO.getPurchaseDate())){
			throw new BillliveClientValidationException("purchaseDate","PurchaseOrderDTO, the purchaseDate field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(StringUtils.isBlank(purchaseOrderDTO.getDateOfPurchaseOrder())){
			throw new BillliveClientValidationException("DateOfPurchaseOrder","PurchaseOrderDTO, the DateOfPurchaseOrder field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(StringUtils.isBlank(purchaseOrderDTO.getDueDate())){
			throw new BillliveClientValidationException("DueDate","PurchaseOrderDTO, the DueDate field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(StringUtils.isBlank(purchaseOrderDTO.getUid())){
			throw new BillliveClientValidationException("Uid","PurchaseOrderDTO, the Uid field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(purchaseOrderDTO.getItemsInPurchaseOrder() != null && purchaseOrderDTO.getItemsInPurchaseOrder().size() > 0){
			for(PurchaseOrderItemDTO purchaseOrderItemDTO :purchaseOrderDTO.getItemsInPurchaseOrder()){
				itemValidator.validateItemData(purchaseOrderItemDTO.getItemData());
			}
		}
		if(StringUtils.isBlank(purchaseOrderDTO.getTotalAmount())){
			throw new BillliveClientValidationException("TotalAmount","PurchaseOrderDTO, the TotalAmount field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(purchaseOrderDTO.getTotalTax() != null){
			throw new BillliveClientValidationException("TotalTax","PurchaseOrderDTO, the TotalTax field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(StringUtils.isBlank(purchaseOrderDTO.getUid())){
			throw new BillliveClientValidationException("Uid","PurchaseOrderDTO, the Uid field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(purchaseOrderDTO.getTotalCGST() != null){
			throw new BillliveClientValidationException("totalCGST","PurchaseOrderDTO, the totalCGST field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(purchaseOrderDTO.getTotalSGST() != null){
			throw new BillliveClientValidationException("totalSGST","PurchaseOrderDTO, the totalSGST field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(purchaseOrderDTO.getTotalIGST() != null){
			throw new BillliveClientValidationException("totalIGST","PurchaseOrderDTO, the totalIGST field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		return true;
	}

}
