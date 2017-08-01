package com.beatus.billlive.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.beatus.billlive.config.ApplicationConfiguration;
import com.beatus.billlive.domain.model.Inventory;
import com.beatus.billlive.domain.model.ItemData;
import com.beatus.billlive.repository.ItemRepository;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.utils.Utils;
import com.beatus.billlive.validation.ItemValidator;
import com.beatus.billlive.validation.exception.BillDataException;
import com.beatus.billlive.validation.exception.ItemDataException;

@Service
@Component("itemService")
public class ItemService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);
	
	@Resource(name = "itemValidator")
	private ItemValidator itemValidator;
	
	@Resource(name = "itemRepository")
	private ItemRepository itemRepository;

	public String addItem(HttpServletRequest request, HttpServletResponse response,ItemData item, String companyId) throws ItemDataException {
		if(item == null){
			throw new ItemDataException("Item data cant be null");
		}
		try {
			boolean isValidated = itemValidator.validateItemData(item);
			if(isValidated){
				if(StringUtils.isNotBlank(item.getItemId()) && StringUtils.isNotBlank(item.getCompanyId())){
					ItemData existingItem = itemRepository.getItemById(item.getCompanyId(), item.getItemId());
					if(existingItem != null){
						return updateItem(item);
					}
					return "N";
				}else {
					item.setItemId(Utils.generateRandomKey(20));
					List<Inventory> inventories = new ArrayList<Inventory>();
					for(Inventory inv : item.getInventories()){
						inv.setInventoryId(Utils.generateRandomKey(20));
						populateInventoryData(inv);
						inv.setRemainingQuantity(inv.getActualQuantity());
						inventories.add(inv);
					}
					item.setInventories(inventories);
					return itemRepository.addItem(item);
				}
			}else {
				return "N";
			}
		} catch (ItemDataException e) {
			throw e;
		}
	}


	public String updateItem(ItemData item) throws ItemDataException {
		try {
			if(itemValidator.validateItemData(item)){
				if(StringUtils.isNotBlank(item.getItemId()) && StringUtils.isNotBlank(item.getCompanyId())){
					ItemData existingItem = itemRepository.getItemById(item.getCompanyId(), item.getItemId());
					if(existingItem == null){
						return addItem(item);
					}else {
						List<Inventory> inventories = new ArrayList<Inventory>();
						boolean isExistingInventory = false;
						for(Inventory inv : item.getInventories()){
							for(Inventory existingInv : existingItem.getInventories()){
								if(existingInv.getInventoryId().equals(inv.getInventoryId())){
									if(Constants.YES.equalsIgnoreCase(inv.getIsUpdated())){
										populateInventoryData(inv);
									}
									inventories.add(inv);
									isExistingInventory = true;
									break;
								}
							}
							if(!isExistingInventory){
								inv.setInventoryId(Utils.generateRandomKey(20));
								populateInventoryData(inv);
								inventories.add(inv);
							}
						}
						item.setInventories(inventories);
					}
				}
				return itemRepository.updateItem(item);
			}else {
				return "N";
			}
		} catch (ItemDataException e) {
			throw e;
		}
	}
	
	public String removeItem(String companyId, String itemId) {	
		if(StringUtils.isNotBlank(itemId) && StringUtils.isNotBlank(companyId)){
			ItemData itemData = itemRepository.getItemById(companyId, itemId);
			itemData.setIsRemoved(Constants.YES);
			return itemRepository.updateItem(itemData);
		}
		return "N";
	}

	public List<ItemData> getAllItems(String companyId) {
		List<ItemData> items = itemRepository.getAllItems(companyId);
		List<ItemData> itemsNotRemoved = new ArrayList<ItemData>();
		for(ItemData item : items){
			if(!Constants.YES.equalsIgnoreCase(item.getIsRemoved())){
				itemsNotRemoved.add(item);
			}
		}
		return itemsNotRemoved;
	}

	public ItemData getItemById(String companyId, String itemId) {
		ItemData itemData = itemRepository.getItemById(companyId, itemId);
		if(!Constants.YES.equalsIgnoreCase(itemData.getIsRemoved())){
			return itemData;
		}else {
			return null;
		}
	}

	private void populateInventoryData(Inventory itemInventory){
		itemInventory.setDate(String.valueOf(System.currentTimeMillis()));
		Map<String, Double> buyPricesPerQuantity = new HashMap<String, Double>();
		Map<String, Double> sellPricesPerQuantity = new HashMap<String, Double>();
		switch(itemInventory.getBuyQuantityType()){
			case TONNES :  buyPricesPerQuantity.put(Constants.TONNES, itemInventory.getUnitPrice());
						   buyPricesPerQuantity.put(Constants.QUINTAL, itemInventory.getUnitPrice()/10);
						   buyPricesPerQuantity.put(Constants.KG, itemInventory.getUnitPrice()/1000);
						   buyPricesPerQuantity.put(Constants.GRAMS, itemInventory.getUnitPrice()/1000000);
						   //sell prices
						   sellPricesPerQuantity.put(Constants.TONNES, itemInventory.getSellingPrice());
						   sellPricesPerQuantity.put(Constants.QUINTAL, itemInventory.getSellingPrice()/10);
						   sellPricesPerQuantity.put(Constants.KG, itemInventory.getSellingPrice()/1000);
						   sellPricesPerQuantity.put(Constants.GRAMS, itemInventory.getSellingPrice()/1000000);
						   break;
			case QUINTAL : buyPricesPerQuantity.put(Constants.TONNES, itemInventory.getUnitPrice()*10);
			               buyPricesPerQuantity.put(Constants.QUINTAL, itemInventory.getUnitPrice());
			               buyPricesPerQuantity.put(Constants.KG, itemInventory.getUnitPrice()/100);
			               buyPricesPerQuantity.put(Constants.GRAMS, itemInventory.getUnitPrice()/100000);
			               //sell prices
						   sellPricesPerQuantity.put(Constants.TONNES, itemInventory.getSellingPrice()*10);
						   sellPricesPerQuantity.put(Constants.QUINTAL, itemInventory.getSellingPrice());
						   sellPricesPerQuantity.put(Constants.KG, itemInventory.getSellingPrice()/100);
						   sellPricesPerQuantity.put(Constants.GRAMS, itemInventory.getSellingPrice()/100000);
			               break;
			case KG :      buyPricesPerQuantity.put(Constants.TONNES, itemInventory.getUnitPrice()*1000);
			               buyPricesPerQuantity.put(Constants.QUINTAL, itemInventory.getUnitPrice()*100);
			               buyPricesPerQuantity.put(Constants.KG, itemInventory.getUnitPrice());
			               buyPricesPerQuantity.put(Constants.GRAMS, itemInventory.getUnitPrice()/1000);
			               //sell prices
						   sellPricesPerQuantity.put(Constants.TONNES, itemInventory.getSellingPrice()*1000);
						   sellPricesPerQuantity.put(Constants.QUINTAL, itemInventory.getSellingPrice()*100);
						   sellPricesPerQuantity.put(Constants.KG, itemInventory.getSellingPrice());
						   sellPricesPerQuantity.put(Constants.GRAMS, itemInventory.getSellingPrice()/1000);
			               break;
			case GRAMS :   buyPricesPerQuantity.put(Constants.TONNES, itemInventory.getUnitPrice()*1000000);
			               buyPricesPerQuantity.put(Constants.QUINTAL, itemInventory.getUnitPrice()*100000);
			               buyPricesPerQuantity.put(Constants.KG, itemInventory.getUnitPrice()*1000);
			               buyPricesPerQuantity.put(Constants.GRAMS, itemInventory.getUnitPrice());
			               //sell prices
						   sellPricesPerQuantity.put(Constants.TONNES, itemInventory.getSellingPrice()*1000000);
						   sellPricesPerQuantity.put(Constants.QUINTAL, itemInventory.getSellingPrice()*100000);
						   sellPricesPerQuantity.put(Constants.KG, itemInventory.getSellingPrice()*1000);
						   sellPricesPerQuantity.put(Constants.GRAMS, itemInventory.getSellingPrice());
			               break;
			case LITERS :  buyPricesPerQuantity.put(Constants.LITERS, itemInventory.getUnitPrice());
			               buyPricesPerQuantity.put(Constants.ML, itemInventory.getUnitPrice()/1000);
			               //sell Prices
			               sellPricesPerQuantity.put(Constants.LITERS, itemInventory.getSellingPrice());
			               sellPricesPerQuantity.put(Constants.ML, itemInventory.getSellingPrice()/1000);
			               break; 
			case ML :      buyPricesPerQuantity.put(Constants.LITERS, itemInventory.getUnitPrice()*1000);
               			   buyPricesPerQuantity.put(Constants.ML, itemInventory.getUnitPrice());
               			   //sell Prices
			               sellPricesPerQuantity.put(Constants.LITERS, itemInventory.getSellingPrice()*1000);
			               sellPricesPerQuantity.put(Constants.ML, itemInventory.getSellingPrice());
			               break; 
			case PIECES :  buyPricesPerQuantity.put(Constants.PIECES, itemInventory.getUnitPrice());
			               //sell Prices
                           sellPricesPerQuantity.put(Constants.PIECES, itemInventory.getSellingPrice());
			               break;       							  
		}
		switch(itemInventory.getOtherSellQuantityTypeOption()){
			case TONNES :  buyPricesPerQuantity.put(Constants.TONNES, itemInventory.getOtherSellOptionBuyingPrice());
						   buyPricesPerQuantity.put(Constants.QUINTAL, itemInventory.getOtherSellOptionBuyingPrice()/10);
						   buyPricesPerQuantity.put(Constants.KG, itemInventory.getOtherSellOptionBuyingPrice()/1000);
						   buyPricesPerQuantity.put(Constants.GRAMS, itemInventory.getOtherSellOptionBuyingPrice()/1000000);
						   //sell prices
						   sellPricesPerQuantity.put(Constants.TONNES, itemInventory.getOtherSellOptionSellingPrice());
						   sellPricesPerQuantity.put(Constants.QUINTAL, itemInventory.getOtherSellOptionSellingPrice()/10);
						   sellPricesPerQuantity.put(Constants.KG, itemInventory.getOtherSellOptionSellingPrice()/1000);
						   sellPricesPerQuantity.put(Constants.GRAMS, itemInventory.getOtherSellOptionSellingPrice()/1000000);
						   break;
			case QUINTAL : buyPricesPerQuantity.put(Constants.TONNES, itemInventory.getOtherSellOptionBuyingPrice()*10);
			               buyPricesPerQuantity.put(Constants.QUINTAL, itemInventory.getOtherSellOptionBuyingPrice());
			               buyPricesPerQuantity.put(Constants.KG, itemInventory.getOtherSellOptionBuyingPrice()/100);
			               buyPricesPerQuantity.put(Constants.GRAMS, itemInventory.getOtherSellOptionBuyingPrice()/100000);
			               //sell prices
						   sellPricesPerQuantity.put(Constants.TONNES, itemInventory.getOtherSellOptionSellingPrice()*10);
						   sellPricesPerQuantity.put(Constants.QUINTAL, itemInventory.getOtherSellOptionSellingPrice());
						   sellPricesPerQuantity.put(Constants.KG, itemInventory.getOtherSellOptionSellingPrice()/100);
						   sellPricesPerQuantity.put(Constants.GRAMS, itemInventory.getOtherSellOptionSellingPrice()/100000);
			               break;
			case KG :      buyPricesPerQuantity.put(Constants.TONNES, itemInventory.getOtherSellOptionBuyingPrice()*1000);
			               buyPricesPerQuantity.put(Constants.QUINTAL, itemInventory.getOtherSellOptionBuyingPrice()*100);
			               buyPricesPerQuantity.put(Constants.KG, itemInventory.getOtherSellOptionBuyingPrice());
			               buyPricesPerQuantity.put(Constants.GRAMS, itemInventory.getOtherSellOptionBuyingPrice()/1000);
			               //sell prices
						   sellPricesPerQuantity.put(Constants.TONNES, itemInventory.getOtherSellOptionSellingPrice()*1000);
						   sellPricesPerQuantity.put(Constants.QUINTAL, itemInventory.getOtherSellOptionSellingPrice()*100);
						   sellPricesPerQuantity.put(Constants.KG, itemInventory.getOtherSellOptionSellingPrice());
						   sellPricesPerQuantity.put(Constants.GRAMS, itemInventory.getOtherSellOptionSellingPrice()/1000);
			               break;
			case GRAMS :   buyPricesPerQuantity.put(Constants.TONNES, itemInventory.getOtherSellOptionBuyingPrice()*1000000);
			               buyPricesPerQuantity.put(Constants.QUINTAL, itemInventory.getOtherSellOptionBuyingPrice()*100000);
			               buyPricesPerQuantity.put(Constants.KG, itemInventory.getOtherSellOptionBuyingPrice()*1000);
			               buyPricesPerQuantity.put(Constants.GRAMS, itemInventory.getOtherSellOptionBuyingPrice());
			               //sell prices
						   sellPricesPerQuantity.put(Constants.TONNES, itemInventory.getOtherSellOptionSellingPrice()*1000000);
						   sellPricesPerQuantity.put(Constants.QUINTAL, itemInventory.getOtherSellOptionSellingPrice()*100000);
						   sellPricesPerQuantity.put(Constants.KG, itemInventory.getOtherSellOptionSellingPrice()*1000);
						   sellPricesPerQuantity.put(Constants.GRAMS, itemInventory.getOtherSellOptionSellingPrice());
			               break;
			case LITERS :  buyPricesPerQuantity.put(Constants.LITERS, itemInventory.getOtherSellOptionBuyingPrice());
			               buyPricesPerQuantity.put(Constants.ML, itemInventory.getOtherSellOptionBuyingPrice()/1000);
			               //sell Prices
			               sellPricesPerQuantity.put(Constants.LITERS, itemInventory.getOtherSellOptionSellingPrice());
			               sellPricesPerQuantity.put(Constants.ML, itemInventory.getOtherSellOptionSellingPrice()/1000);
			               break; 
			case ML :      buyPricesPerQuantity.put(Constants.LITERS, itemInventory.getOtherSellOptionBuyingPrice()*1000);
               			   buyPricesPerQuantity.put(Constants.ML, itemInventory.getOtherSellOptionBuyingPrice());
               			   //sell Prices
			               sellPricesPerQuantity.put(Constants.LITERS, itemInventory.getOtherSellOptionSellingPrice()*1000);
			               sellPricesPerQuantity.put(Constants.ML, itemInventory.getOtherSellOptionSellingPrice());
               			   break; 
			case PIECES :  buyPricesPerQuantity.put(Constants.PIECES, itemInventory.getOtherSellOptionBuyingPrice());
						   //sell Prices
	                       sellPricesPerQuantity.put(Constants.PIECES, itemInventory.getOtherSellOptionSellingPrice());
						   break;       							  
		}
		itemInventory.setBuyPricesPerQuantityType(buyPricesPerQuantity);
		itemInventory.setSellPricesPerQuantityType(sellPricesPerQuantity);
	}
	
	public String updateRemainingQuantityForItemInventory(String companyId, String itemId, String inventoryId, Double remainingQuantity) {
		if(itemId != null && inventoryId != null){
			ItemData existingItem = itemRepository.getItemById(companyId, itemId);
			if(existingItem != null){
				if(existingItem.getInventories() != null && existingItem.getInventories().size() > 0){
					for(int i=0; i< existingItem.getInventories().size(); i++ ){
						Inventory inv = existingItem.getInventories().get(i);
						if(inventoryId.equalsIgnoreCase(inv.getInventoryId())){
							inv.setRemainingQuantity(remainingQuantity);
							existingItem.getInventories().set(i, inv);
						}
					}
				}
			}			
			return itemRepository.updateItem(existingItem);
		}
		return "N";
	}

}