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

import com.beatus.billlive.domain.model.Inventory;
import com.beatus.billlive.domain.model.ItemData;
import com.beatus.billlive.domain.model.PurchaseOrderDTO;
import com.beatus.billlive.domain.model.PurchaseOrderData;
import com.beatus.billlive.domain.model.PurchaseOrderItemDTO;
import com.beatus.billlive.repository.PurchaseOrderRepository;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.utils.Utils;
import com.beatus.billlive.validation.PurchaseOrderValidator;
import com.beatus.billlive.validation.exception.ItemDataException;
import com.beatus.billlive.validation.exception.PurchaseOrderDataException;

@Service
@Component("purchaseOrderService")
public class PurchaseOrderService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderService.class);

	@Resource(name = "purchaseOrderRepository")
	private PurchaseOrderRepository purchaseOrderRepository;
	
	@Resource(name = "purchaseOrderValidator")
	private PurchaseOrderValidator purchaseOrderValidator;
	
	@Resource(name = "taxService")
	private TaxService taxService;
	
	@Resource(name = "itemService")
	private ItemService itemService;


	public String addPurchaseOrder(HttpServletRequest request, HttpServletResponse response, PurchaseOrderDTO purchaseOrderDTO, String companyId) throws PurchaseOrderDataException, ItemDataException{
		
		if(purchaseOrderDTO == null){
			throw new PurchaseOrderDataException("PurchaseOrder data cant be null");
		}
		try {
			//Revisit validator
			boolean isValidated = purchaseOrderValidator.validatePurchaseOrderData(purchaseOrderDTO);
			if(isValidated){
				if(StringUtils.isBlank(companyId)){
					companyId = purchaseOrderDTO.getCompanyId();
				}
				PurchaseOrderData existingPurchaseOrder = null;
				if(StringUtils.isNotBlank(purchaseOrderDTO.getPurchaseOrderNumber())){
					existingPurchaseOrder = purchaseOrderRepository.getPurchaseOrderByPurchaseOrderNumber(companyId, purchaseOrderDTO.getPurchaseOrderNumber());
					return updatePurchaseOrder(request, response, purchaseOrderDTO, companyId);
				}
				PurchaseOrderData purchaseOrderData = populatePurchaseOrderData(purchaseOrderDTO, existingPurchaseOrder, companyId);				
				return purchaseOrderRepository.addPurchaseOrder(purchaseOrderData);
			}
		} catch (PurchaseOrderDataException purchaseOrderException) {
			LOGGER.info("PurchaseOrder validation Exception in the addPurchaseOrderService() {} ", purchaseOrderException.getMessage());
			throw purchaseOrderException;
		}	
		return null;		
	}

	public String updatePurchaseOrder(HttpServletRequest request, HttpServletResponse response, PurchaseOrderDTO purchaseOrderDTO, String companyId) throws PurchaseOrderDataException, ItemDataException{
		
		if(purchaseOrderDTO == null){
			throw new PurchaseOrderDataException("PurchaseOrder data cant be null");
		}
		//Revisit validator
		boolean isValidated = purchaseOrderValidator.validatePurchaseOrderData(purchaseOrderDTO);
		if(isValidated){
			if(StringUtils.isBlank(companyId)){
				companyId = purchaseOrderDTO.getCompanyId();
			}
			PurchaseOrderData existingPurchaseOrder = null;
			if(StringUtils.isNotBlank(purchaseOrderDTO.getPurchaseOrderNumber()))
				existingPurchaseOrder = purchaseOrderRepository.getPurchaseOrderByPurchaseOrderNumber(companyId, purchaseOrderDTO.getPurchaseOrderNumber());
			PurchaseOrderData purchaseOrderData = populatePurchaseOrderData(purchaseOrderDTO, existingPurchaseOrder, companyId);			
			return purchaseOrderRepository.updatePurchaseOrder(purchaseOrderData);
		}
		
		return null;		
	}
	
	public String removePurchaseOrder(String companyId, String purchaseOrderNumber) {
		if(StringUtils.isNotBlank(purchaseOrderNumber) && StringUtils.isNotBlank(companyId)){
			PurchaseOrderData purchaseOrderData = purchaseOrderRepository.getPurchaseOrderByPurchaseOrderNumber(companyId, purchaseOrderNumber);
			purchaseOrderData.setIsRemoved(Constants.YES);
			return purchaseOrderRepository.updatePurchaseOrder(purchaseOrderData);
		}
		return null;
	}

	public List<PurchaseOrderDTO> getAllPurchaseOrdersBasedOnCompanyId(String companyId) {
		List<PurchaseOrderData> purchaseOrders = purchaseOrderRepository.getAllPurchaseOrdersBasedOnCompanyId(companyId);
		List<PurchaseOrderDTO> purchaseOrdersNotRemoved = new ArrayList<PurchaseOrderDTO>();
		for(PurchaseOrderData purchaseOrder : purchaseOrders){
			if(!Constants.YES.equalsIgnoreCase(purchaseOrder.getIsRemoved())){
				purchaseOrdersNotRemoved.add(populatePurchaseOrderDTO(purchaseOrder));
			}
		}
		return purchaseOrdersNotRemoved;
	}

	public PurchaseOrderDTO getPurchaseOrderByPurchaseOrderNumber(String companyId, String purchaseOrderNumber) {
		PurchaseOrderData purchaseOrderData = purchaseOrderRepository.getPurchaseOrderByPurchaseOrderNumber(companyId, purchaseOrderNumber);
		if(!Constants.YES.equalsIgnoreCase(purchaseOrderData.getIsRemoved())){
			return populatePurchaseOrderDTO(purchaseOrderData);
		}else {
			return null;
		}
	}
	
	public List<PurchaseOrderDTO> getAllPurchaseOrdersInAMonth(String companyId, String year, String month) {
		List<PurchaseOrderData> purchaseOrders = purchaseOrderRepository.getAllPurchaseOrdersInAMonth(companyId, year, month);
		List<PurchaseOrderDTO> purchaseOrdersNotRemoved = new ArrayList<PurchaseOrderDTO>();
		for(PurchaseOrderData purchaseOrder : purchaseOrders){
			if(!Constants.YES.equalsIgnoreCase(purchaseOrder.getIsRemoved())){
				purchaseOrdersNotRemoved.add(populatePurchaseOrderDTO(purchaseOrder));
			}
		}
		return purchaseOrdersNotRemoved;
	}
	
	public List<PurchaseOrderDTO> getAllPurchaseOrdersInADay(String companyId, String year, String month, String day) {
		List<PurchaseOrderData> purchaseOrders = purchaseOrderRepository.getAllPurchaseOrdersInADay(companyId, year, month, day);
		List<PurchaseOrderDTO> purchaseOrdersNotRemoved = new ArrayList<PurchaseOrderDTO>();
		for(PurchaseOrderData purchaseOrder : purchaseOrders){
			if(!Constants.YES.equalsIgnoreCase(purchaseOrder.getIsRemoved())){
				purchaseOrdersNotRemoved.add(populatePurchaseOrderDTO(purchaseOrder));
			}
		}
		return purchaseOrdersNotRemoved;
	}
	
	public List<PurchaseOrderDTO> getAllPurchaseOrdersInAnYear(String companyId, String year) {
		List<PurchaseOrderData> purchaseOrders = purchaseOrderRepository.getAllPurchaseOrdersInAYear(companyId, year);
		List<PurchaseOrderDTO> purchaseOrdersNotRemoved = new ArrayList<PurchaseOrderDTO>();
		for(PurchaseOrderData purchaseOrder : purchaseOrders){
			if(!Constants.YES.equalsIgnoreCase(purchaseOrder.getIsRemoved())){
				purchaseOrdersNotRemoved.add(populatePurchaseOrderDTO(purchaseOrder));
			}
		}
		return purchaseOrdersNotRemoved;
	}
	
	private PurchaseOrderDTO populatePurchaseOrderDTO(PurchaseOrderData purchaseOrder) {
		PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
		purchaseOrderDTO.setPurchaseOrderNumber(purchaseOrder.getPurchaseOrderNumber());
		purchaseOrderDTO.setPurchaseFromContactId(purchaseOrder.getPurchaseFromContactId());
		purchaseOrderDTO.setPurchaseToContactId(purchaseOrder.getPurchaseToContactId());
		purchaseOrderDTO.setCompanyId(purchaseOrder.getCompanyId());
		purchaseOrderDTO.setUid(purchaseOrder.getUid());
		purchaseOrderDTO.setDateOfPurchaseOrder(purchaseOrder.getDateOfPurchaseOrder());
		purchaseOrderDTO.setDueDate(purchaseOrder.getDueDate());
		purchaseOrderDTO.setTotalAmount(purchaseOrder.getTotalAmount());
		purchaseOrderDTO.setReferenceMobileNumber(purchaseOrder.getReferenceMobileNumber());
		purchaseOrderDTO.setReferenceAadharCardNumber(purchaseOrder.getReferenceAadharCardNumber());
		purchaseOrderDTO.setIsTaxeble(purchaseOrder.getIsTaxeble());
		purchaseOrderDTO.setTotalTax(purchaseOrder.getTotalTax());
		purchaseOrderDTO.setTotalCGST(purchaseOrder.getTotalCGST());
		purchaseOrderDTO.setTotalSGST(purchaseOrder.getTotalSGST());
		purchaseOrderDTO.setTotalIGST(purchaseOrder.getTotalIGST());
		List<PurchaseOrderItemDTO> purchaseOrderItems = new ArrayList<PurchaseOrderItemDTO>();
		for(ItemData itemData : purchaseOrder.getItemsInPurchaseOrder()){
			PurchaseOrderItemDTO itemDTO = new PurchaseOrderItemDTO();
			itemDTO.setItemData(itemData);
			purchaseOrderItems.add(itemDTO);
		}
		purchaseOrderDTO.setItemsInPurchaseOrder(purchaseOrderItems);
		return purchaseOrderDTO;
	}


	private PurchaseOrderData populatePurchaseOrderData(PurchaseOrderDTO purchaseOrderDTO, PurchaseOrderData existingPurchaseOrder, String companyId) throws ItemDataException {
		PurchaseOrderData purchaseOrderData = new PurchaseOrderData();
		purchaseOrderData.setPurchaseFromContactId(purchaseOrderDTO.getPurchaseFromContactId());
		purchaseOrderData.setPurchaseToContactId(purchaseOrderDTO.getPurchaseToContactId());
		String purchaseOrderNumber = "";
		if(existingPurchaseOrder == null  && StringUtils.isBlank(purchaseOrderDTO.getPurchaseOrderNumber())){
			purchaseOrderNumber = Utils.generateRandomKey(20);
			Date date = new Date();
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(date);
		    purchaseOrderData.setYear(String.valueOf(cal.get(Calendar.YEAR)));
		    purchaseOrderData.setMonth(String.valueOf(cal.get(Calendar.MONTH)));
		    purchaseOrderData.setDay(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
		}else if(existingPurchaseOrder != null){
			purchaseOrderNumber = purchaseOrderDTO.getPurchaseOrderNumber();
			purchaseOrderData.setYear(existingPurchaseOrder.getYear());
			purchaseOrderData.setMonth(existingPurchaseOrder.getMonth());
			purchaseOrderData.setDay(existingPurchaseOrder.getDay());
		}
		purchaseOrderData.setPurchaseOrderNumber(purchaseOrderNumber);
		purchaseOrderData.setIsTaxeble(purchaseOrderDTO.getIsTaxeble());
		purchaseOrderData.setDateOfPurchaseOrder(purchaseOrderDTO.getDateOfPurchaseOrder());
		purchaseOrderData.setDueDate(purchaseOrderDTO.getDueDate());
		purchaseOrderData.setCompanyId(purchaseOrderDTO.getCompanyId());
		purchaseOrderData.setReferenceAadharCardNumber(purchaseOrderDTO.getReferenceAadharCardNumber());
		purchaseOrderData.setReferenceMobileNumber(purchaseOrderDTO.getReferenceMobileNumber());
		List<ItemData> purchaseOrderItems = new ArrayList<ItemData>();
		for(PurchaseOrderItemDTO itemDTO : purchaseOrderDTO.getItemsInPurchaseOrder()){
			if(Constants.YES.equalsIgnoreCase(itemDTO.getIsAdded()) || Constants.YES.equalsIgnoreCase(itemDTO.getIsUpdated())){		
				ItemData purchaseOrderItem = itemDTO.getItemData();

				List<Inventory> itemInventories = purchaseOrderItem.getInventories();
				for(Inventory inv : itemInventories){
					if(StringUtils.isBlank(inv.getInventoryId())){
						inv.setPurchaseOrderNumber(purchaseOrderNumber);
					}
				}
				if(itemInventories != null && itemInventories.size() == 1 && itemInventories.get(0) != null && StringUtils.isNotBlank(itemInventories.get(0).getInventoryId())){
					itemInventories.get(0).setPurchaseOrderNumber(purchaseOrderNumber);
				}
				ItemData item = itemService.updateItem(purchaseOrderItem, purchaseOrderData.getCompanyId());			
				purchaseOrderItems.add(item);
			}	
		}
		return purchaseOrderData;
	}

}
