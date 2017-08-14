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
import com.beatus.billlive.exception.PurchaseOrderDataException;

@Component("purchaseOrderValidator")
public class PurchaseOrderValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderValidator.class);
	@Autowired
	private ItemValidator itemValidator;
	public boolean validatePurchaseOrderData(PurchaseOrderDTO purchaseOrderDTO) throws PurchaseOrderDataException, ItemDataException, InventoryValidationException {
		LOGGER.info(" Validating validatePurchaseOrderData " + purchaseOrderDTO);
		if(purchaseOrderDTO == null || StringUtils.isBlank(purchaseOrderDTO.getPurchaseOrderNumber())){
			throw new PurchaseOrderDataException("PurchaseOrderDTO, purchaseOrderDTO is null");
		}
		if( StringUtils.isBlank(purchaseOrderDTO.getPurchaseFromContactId())){
			throw new PurchaseOrderDataException("PurchaseOrderDTO, the purchaseFromContactId field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(StringUtils.isBlank(purchaseOrderDTO.getPurchaseToContactId())){
			throw new PurchaseOrderDataException("PurchaseOrderDTO, the purchaseToContactId field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(StringUtils.isBlank(purchaseOrderDTO.getPurchaseDate())){
			throw new PurchaseOrderDataException("PurchaseOrderDTO, the purchaseDate field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(StringUtils.isBlank(purchaseOrderDTO.getDateOfPurchaseOrder())){
			throw new PurchaseOrderDataException("PurchaseOrderDTO, the DateOfPurchaseOrder field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(StringUtils.isBlank(purchaseOrderDTO.getDueDate())){
			throw new PurchaseOrderDataException("PurchaseOrderDTO, the DueDate field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(StringUtils.isBlank(purchaseOrderDTO.getUid())){
			throw new PurchaseOrderDataException("PurchaseOrderDTO, the Uid field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(purchaseOrderDTO.getItemsInPurchaseOrder() != null && purchaseOrderDTO.getItemsInPurchaseOrder().size() > 0){
			for(PurchaseOrderItemDTO purchaseOrderItemDTO :purchaseOrderDTO.getItemsInPurchaseOrder()){
				itemValidator.validateItemData(purchaseOrderItemDTO.getItemData());
			}
		}
		if(StringUtils.isBlank(purchaseOrderDTO.getTotalAmount())){
			throw new PurchaseOrderDataException("PurchaseOrderDTO, the TotalAmount field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(purchaseOrderDTO.getTotalTax() != null){
			throw new PurchaseOrderDataException("PurchaseOrderDTO, the TotalTax field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(StringUtils.isBlank(purchaseOrderDTO.getUid())){
			throw new PurchaseOrderDataException("PurchaseOrderDTO, the Uid field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(purchaseOrderDTO.getTotalCGST() != null){
			throw new PurchaseOrderDataException("PurchaseOrderDTO, the totalCGST field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(purchaseOrderDTO.getTotalSGST() != null){
			throw new PurchaseOrderDataException("PurchaseOrderDTO, the totalSGST field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		if(purchaseOrderDTO.getTotalIGST() != null){
			throw new PurchaseOrderDataException("PurchaseOrderDTO, the totalIGST field is not available, for the bill number " + purchaseOrderDTO.getPurchaseOrderNumber());
		}
		return true;
	}

}
