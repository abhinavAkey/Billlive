package com.beatus.billlive.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.beatus.billlive.config.ApplicationConfiguration;
import com.beatus.billlive.domain.model.BillDTO;
import com.beatus.billlive.repository.BillRepository;
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

	public boolean addBillService(BillDTO bill) throws BillValidationException{
		
		if(bill == null || StringUtils.isBlank(bill.getBillFrom())){
			return false;
		}
		try {
			billValidator.validateBillData(bill);
		} catch (BillValidationException billException) {
			LOGGER.info("Bill validation Exception in the addBillService() " + billException.getMessage());
			throw billException;
		}
		
		return false;		
	}

}
