package com.beatus.billlive.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beatus.billlive.domain.model.ItemData;
import com.beatus.billlive.service.ItemService;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.validation.exception.ItemDataException;

@Controller
@RequestMapping("/api")
public class ItemController {
	

	@Resource(name = "itemService")
	private ItemService itemService;
	
	//For add and update item both
	@RequestMapping(value= "/company/item/add", method = RequestMethod.POST)
	public @ResponseBody String addItem(@RequestBody ItemData itemData, HttpServletRequest request, HttpServletResponse response) throws ItemDataException{
		
		String isItemCreated = itemService.addItem(itemData);
		return isItemCreated;
	}
	
	@RequestMapping(value= "/company/item/update", method = RequestMethod.POST)
    public @ResponseBody String editItem(@RequestBody ItemData itemData, HttpServletRequest request, HttpServletResponse response) throws ItemDataException{
    	String isItemUpdated = itemService.updateItem(itemData);
		return isItemUpdated;
    }
    
    @RequestMapping("/company/item/remove/{id}")
    public @ResponseBody String removeItem(@PathVariable("id") String itemId, HttpServletRequest request, HttpServletResponse response) throws ItemDataException{	
    	if(StringUtils.isNotBlank(itemId)){
    		HttpSession session = request.getSession();
        	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
        	String isItemRemoved = itemService.removeItem(companyId, itemId);
    		return isItemRemoved;
		}else{
			throw new ItemDataException("itemId passed cant be null or empty string");
		}
    }
 
    
    @RequestMapping(value = "/company/getitem/{id}", method = RequestMethod.GET)
	public @ResponseBody ItemData getItemById(@PathVariable("id") String itemId, HttpServletRequest request, HttpServletResponse response) throws ItemDataException {
    	HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(itemId) && StringUtils.isNotBlank(itemId)){
	    	ItemData itemData = itemService.getItemById(companyId, itemId);
			return itemData;
		}else{
			throw new ItemDataException("itemId passed cant be null or empty string");
		}
		
	}
    
    @RequestMapping(value = "/company/getallitems", method = RequestMethod.GET)
	public @ResponseBody List<ItemData> getAllItems(HttpServletRequest request, HttpServletResponse response) throws ItemDataException {
    	HttpSession session = request.getSession();
    	String companyId = (String) session.getAttribute(Constants.COMPANY_ID);
    	if(StringUtils.isNotBlank(companyId)){
        	List<ItemData> itemList = itemService.getAllItems(companyId);
			return itemList;
		}else{
			throw new ItemDataException("itemId passed cant be null or empty string");
		}
	}

}
