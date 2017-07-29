package com.beatus.billlive.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.beatus.billlive.config.ApplicationConfiguration;
import com.beatus.billlive.domain.model.BillDTO;
import com.beatus.billlive.domain.model.BillData;
import com.beatus.billlive.domain.model.BillItemData;
import com.beatus.billlive.domain.model.ItemDTO;
import com.beatus.billlive.repository.BillRepository;
import com.beatus.billlive.utils.Utils;
import com.beatus.billlive.validation.BillValidator;
import com.beatus.billlive.validation.exception.BillValidationException;

@Service
@Component("billService")
public class BillService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfiguration.class);

	@Resource(name = "billRepository")
	private BillRepository billRepository;
	
	@Resource(name = "billValidator")
	private BillValidator billValidator;

	public String addBill(BillDTO billDTO) throws BillValidationException{
		
		if(billDTO == null){
			throw new BillValidationException("Bill data cant be null");
		}
		try {
			//Revisit validator
			boolean isValidated = billValidator.validateBillData(billDTO);
			if(isValidated){
				BillData billData = parse(billDTO);
				
			}
		} catch (BillValidationException billException) {
			LOGGER.info("Bill validation Exception in the addBillService() {} ", billException.getMessage());
			throw billException;
		}
		
		return "Y";		
	}

	private BillData parse(BillDTO billDTO) {
		BillData billData = new BillData();
		billData.setBillFromContactId(billDTO.getBillFromContactId());
		billData.setBillToContactId(billDTO.getBillToContactId());
		billData.setBillNumber(Utils.generateRandomKey(12));
		billData.setIsTaxeble(billDTO.getIsTaxeble());
		billData.setDateOfBill(billDTO.getDateOfBill());
		billData.setDueDate(billDTO.getDueDate());
		billData.setCompanyId(billDTO.getCompanyId());
		List<BillItemData> billItems = new ArrayList<BillItemData>();
		for(ItemDTO itemDTO : billDTO.getItems()){
			BillItemData billItem = new BillItemData();
			billItem.setItemId(itemDTO.getItemId());
			billItem.setInventoryId(itemDTO.getItemId());
			billItem.setIsTaxeble(itemDTO.getIsTaxeble());
			billItem.setQuantity(itemDTO.getQuantity());
			billItem.setProductValue(itemDTO.getProductValue());
			billItem.setQuantityType(itemDTO.getQuantityType());
			if(StringUtils.isEmpty(itemDTO.getAmountBeforeTax())){
				billItem.set
			}else {
				billItem.setAmountBeforeTax(itemDTO.getAmountBeforeTax());
			}
			if(StringUtils.isEmpty(itemDTO.getAmountAfterTax())){
				billItem.set
			}else {
				billItem.setAmountBeforeTax(itemDTO.getAmountAfterTax());
			}
			
		}
		return null;
	}

}
