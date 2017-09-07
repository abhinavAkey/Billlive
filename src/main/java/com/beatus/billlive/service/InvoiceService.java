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

import com.beatus.billlive.domain.model.InvoiceDTO;
import com.beatus.billlive.domain.model.InvoiceData;
import com.beatus.billlive.domain.model.InvoiceItemData;
import com.beatus.billlive.domain.model.ItemDTO;
import com.beatus.billlive.domain.model.ItemData;
import com.beatus.billlive.domain.model.Tax;
import com.beatus.billlive.repository.InvoiceRepository;
import com.beatus.billlive.repository.data.listener.OnGetDataListener;
import com.beatus.billlive.service.exception.BillliveServiceException;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.utils.Utils;
import com.beatus.billlive.validation.InvoiceDataValidator;
import com.beatus.billlive.validation.exception.BillliveClientValidationException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

@Service
@Component("invoiceService")
public class InvoiceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceService.class);

	@Resource(name = "invoiceRepository")
	private InvoiceRepository invoiceRepository;

	@Resource(name = "invoiceDataValidator")
	private InvoiceDataValidator invoiceValidator;

	@Resource(name = "taxService")
	private TaxService taxService;

	@Resource(name = "itemService")
	private ItemService itemService;
	
	private InvoiceData invoiceData = null;
	
	private List<InvoiceData> invoicesList = new ArrayList<InvoiceData>();

	public String addInvoice(HttpServletRequest request, HttpServletResponse response, InvoiceDTO invoiceDTO)
			throws BillliveClientValidationException {
		if (invoiceDTO == null) {
			throw new BillliveClientValidationException("InvoiceDTO","Invoice data cant be null");
		}
		try {
			// Revisit validator
			boolean isValidated = invoiceValidator.validateInvoiceDTO(invoiceDTO);
			if (isValidated) {
				String companyId = invoiceDTO.getCompanyId();
				InvoiceDTO existingInvoice = null;
				if (StringUtils.isNotBlank(invoiceDTO.getInvoiceNumber())) {
					existingInvoice = getInvoiceByInvoiceNumber(companyId, invoiceDTO.getInvoiceNumber());
					return updateInvoice(request, response, invoiceDTO);
				}
				InvoiceData invoiceData = populateInvoiceData(invoiceDTO, existingInvoice, companyId);
				return invoiceRepository.addInvoice(invoiceData);
			}
		} catch (BillliveServiceException invoiceException) {
			LOGGER.error("Invoicelive Service Exception in the updateInvoiceService() {} ", invoiceException.getMessage());
			throw invoiceException;
		}
		return "N";
	}

	public String updateInvoice(HttpServletRequest request, HttpServletResponse response, InvoiceDTO invoiceDTO)
			throws BillliveClientValidationException {

		if (invoiceDTO == null) {
			throw new BillliveClientValidationException("InvoiceDTO", "Invoice data cant be null");
		}
		try {
			// Revisit validator
			boolean isValidated = invoiceValidator.validateInvoiceDTO(invoiceDTO);
			if (isValidated) {
				String companyId = invoiceDTO.getCompanyId();
				InvoiceDTO existingInvoice = null;
				if (StringUtils.isNotBlank(invoiceDTO.getInvoiceNumber()))
					existingInvoice = getInvoiceByInvoiceNumber(companyId, invoiceDTO.getInvoiceNumber());
				InvoiceData invoiceData = populateInvoiceData(invoiceDTO, existingInvoice, companyId);
				return invoiceRepository.updateInvoice(invoiceData);
			}
		} catch (BillliveServiceException invoiceException) {
			LOGGER.error("Invoicelive Service Exception in the updateInvoiceService() {} ", invoiceException.getMessage());
			throw invoiceException;
		}

		return "N";
	}

	public String removeInvoice(String companyId, String uid, String invoiceNumber)
			throws BillliveServiceException, BillliveClientValidationException {
		LOGGER.info("In removeInvoice method of Invoice Service");
		if (StringUtils.isNotBlank(invoiceNumber) && StringUtils.isNotBlank(companyId)) {
			InvoiceDTO invoiceDTO = getInvoiceByInvoiceNumber(companyId, invoiceNumber);
			InvoiceData invoiceData = populateInvoiceData(invoiceDTO, invoiceDTO, companyId);
			invoiceData.setUid(uid);
			invoiceData.setIsRemoved(Constants.YES);
			return invoiceRepository.updateInvoice(invoiceData);
		} else {
			LOGGER.error(
					"Invoicelive Service Exception in the removeInvoice() {},  Invoice Number or CompanyId passed cant be null or empty string");
			throw new BillliveServiceException("Invoice data passed cant be null or empty string");
		}
	}

	public List<InvoiceDTO> getAllInvoicesBasedOnCompanyId(String companyId) {
		LOGGER.info("In getAllInvoicesBasedOnCompanyId method of Invoice Service");
		if (StringUtils.isNotBlank(companyId)) {
			invoiceRepository.getAllInvoicesBasedOnCompanyId(companyId, new OnGetDataListener() {

				@Override
		        public void onStart() {
		        }

		        @Override
		        public void onSuccess(DataSnapshot invoiceSnapshot) {
		        	invoicesList.clear();
			        for (DataSnapshot invoicePostSnapshot: invoiceSnapshot.getChildren()) {
			            InvoiceData invoiceData = invoicePostSnapshot.getValue(InvoiceData.class);
			            invoicesList.add(invoiceData);
			        } 
		        	LOGGER.info(" The invoice Snapshot Key is " + invoiceSnapshot.getKey());
		        }

		        @Override
		        public void onFailed(DatabaseError databaseError) {
		           LOGGER.info("Error retrieving data");
		           throw new BillliveServiceException(databaseError.getMessage());
		        }
		    });
			List<InvoiceDTO> invoicesNotRemoved = new ArrayList<InvoiceDTO>();
			for (InvoiceData invoice : invoicesList) {
				if (invoice!= null && !Constants.YES.equalsIgnoreCase(invoice.getIsRemoved())) {
					invoicesNotRemoved.add(populateInvoiceDTO(invoice));
				}
			}
			return invoicesNotRemoved;
		} else {
			LOGGER.error(
					"Invoicelive Service Exception in the getAllInvoicesBasedOnCompanyId() {},  CompanyId passed cant be null or empty string");
			throw new BillliveServiceException("Company Id passed cant be null or empty string");
		}
	}

	public InvoiceDTO getInvoiceByInvoiceNumber(String companyId, String invoiceNumber) {
		LOGGER.info("In getInvoiceByInvoiceNumber method of Invoice Service");
		if (StringUtils.isNotBlank(invoiceNumber) && StringUtils.isNotBlank(companyId)) {

			invoiceRepository.getInvoiceByInvoiceNumber(companyId, invoiceNumber, new OnGetDataListener() {

				@Override
		        public void onStart() {
		        }

		        @Override
		        public void onSuccess(DataSnapshot invoiceSnapshot) {
		            InvoiceData invoiceData = invoiceSnapshot.getValue(InvoiceData.class);
		            invoicesList.add(invoiceData);
		        	LOGGER.info(" The invoice Snapshot Key is " + invoiceSnapshot.getKey());
		        }

		        @Override
		        public void onFailed(DatabaseError databaseError) {
		           LOGGER.info("Error retrieving data");
		           throw new BillliveServiceException(databaseError.getMessage());
		        }
		    });
			if (invoiceData != null && !Constants.YES.equalsIgnoreCase(invoiceData.getIsRemoved())) {
				return populateInvoiceDTO(invoiceData);
			} else {
				return null;
			}
		} else {
			LOGGER.error(
					"Invoicelive Service Exception in the getInvoiceByInvoiceNumber() {},  CompanyId or Invoicenumber passed cant be null or empty string");
			throw new BillliveServiceException("Company Id or Invoice Number passed cant be null or empty string");
		}
	}

	public List<InvoiceDTO> getAllInvoices(String companyId) {
		LOGGER.info("In getAllInvoices method of Invoice Service");
		if (StringUtils.isNotBlank(companyId)) {

			invoiceRepository.getAllInvoices(companyId, new OnGetDataListener() {

				@Override
		        public void onStart() {
		        }

		        @Override
		        public void onSuccess(DataSnapshot invoiceSnapshot) {
		        	invoicesList.clear();
			        for (DataSnapshot invoicePostSnapshot: invoiceSnapshot.getChildren()) {
			            InvoiceData invoiceData = invoicePostSnapshot.getValue(InvoiceData.class);
			            invoicesList.add(invoiceData);
			        } 
		        	LOGGER.info(" The invoice Snapshot Key is " + invoiceSnapshot.getKey());
		        }

		        @Override
		        public void onFailed(DatabaseError databaseError) {
		           LOGGER.info("Error retrieving data");
		           throw new BillliveServiceException(databaseError.getMessage());
		        }
		    });
			List<InvoiceDTO> invoicesNotRemoved = new ArrayList<InvoiceDTO>();
			for (InvoiceData invoice : invoicesList) {
				if (invoice!= null && !Constants.YES.equalsIgnoreCase(invoice.getIsRemoved())) {
					invoicesNotRemoved.add(populateInvoiceDTO(invoice));
				}
			}
			return invoicesNotRemoved;
		} else {
			LOGGER.error(
					"Invoicelive Service Exception in the getAllInvoices() {},  CompanyId passed cant be null or empty string");
			throw new BillliveServiceException("Company Id passed cant be null or empty string");
		}
	}

	public List<InvoiceDTO> getAllInvoicesInAMonth(String companyId, String year, String month) {
		LOGGER.info("In getAllInvoicesInAMonth method of Invoice Service");
		if (StringUtils.isNotBlank(companyId) && StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month)) {
			invoiceRepository.getAllInvoicesInAMonth(companyId, year, month, new OnGetDataListener() {

				@Override
		        public void onStart() {
		        }

		        @Override
		        public void onSuccess(DataSnapshot invoiceSnapshot) {
		        	invoicesList.clear();
			        for (DataSnapshot invoicePostSnapshot: invoiceSnapshot.getChildren()) {
			            InvoiceData invoiceData = invoicePostSnapshot.getValue(InvoiceData.class);
			            invoicesList.add(invoiceData);
			        } 
		        	LOGGER.info(" The invoice Snapshot Key is " + invoiceSnapshot.getKey());
		        }

		        @Override
		        public void onFailed(DatabaseError databaseError) {
		           LOGGER.info("Error retrieving data");
		           throw new BillliveServiceException(databaseError.getMessage());
		        }
		    });
			List<InvoiceDTO> invoicesNotRemoved = new ArrayList<InvoiceDTO>();
			for (InvoiceData invoice : invoicesList) {
				if (invoice!= null && !Constants.YES.equalsIgnoreCase(invoice.getIsRemoved())) {
					invoicesNotRemoved.add(populateInvoiceDTO(invoice));
				}
			}
			return invoicesNotRemoved;
		} else {
			LOGGER.error(
					"Invoicelive Service Exception in the getAllInvoicesInAMonth() {},  CompanyId or Year or Month passed cant be null or empty string");
			throw new BillliveServiceException("Company Id or Year or Month passed cant be null or empty string");
		}
	}

	public List<InvoiceDTO> getAllInvoicesInADay(String companyId, String year, String month, String day) {
		LOGGER.info("In getAllInvoicesInADay method of Invoice Service");
		if (StringUtils.isNotBlank(companyId) && StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month)
				&& StringUtils.isNotBlank(day)) {
			invoiceRepository.getAllInvoicesInADay(companyId, year, month, day, new OnGetDataListener() {

				@Override
		        public void onStart() {
		        }

		        @Override
		        public void onSuccess(DataSnapshot invoiceSnapshot) {
		        	invoicesList.clear();
			        for (DataSnapshot invoicePostSnapshot: invoiceSnapshot.getChildren()) {
			            InvoiceData invoiceData = invoicePostSnapshot.getValue(InvoiceData.class);
			            invoicesList.add(invoiceData);
			        } 
		        	LOGGER.info(" The invoice Snapshot Key is " + invoiceSnapshot.getKey());
		        }

		        @Override
		        public void onFailed(DatabaseError databaseError) {
		           LOGGER.info("Error retrieving data");
		           throw new BillliveServiceException(databaseError.getMessage());
		        }
		    });
			List<InvoiceDTO> invoicesNotRemoved = new ArrayList<InvoiceDTO>();
			for (InvoiceData invoice : invoicesList) {
				if (invoice!= null && !Constants.YES.equalsIgnoreCase(invoice.getIsRemoved())) {
					invoicesNotRemoved.add(populateInvoiceDTO(invoice));
				}
			}
			return invoicesNotRemoved;
		} else {
			LOGGER.error(
					"Invoicelive Service Exception in the getAllInvoicesInAMonth() {},  CompanyId or Year or Month or day passed cant be null or empty string");
			throw new BillliveServiceException(
					"Company Id or Year or Month or day passed cant be null or empty string");
		}
	}

	public List<InvoiceDTO> getAllInvoicesInAnYear(String companyId, String year) {
		LOGGER.info("In getAllInvoicesInAnYear method of Invoice Service");
		if (StringUtils.isNotBlank(companyId) && StringUtils.isNotBlank(year)) {
			invoiceRepository.getAllInvoicesInAYear(companyId, year, new OnGetDataListener() {

				@Override
		        public void onStart() {
		        }

		        @Override
		        public void onSuccess(DataSnapshot invoiceSnapshot) {
		        	invoicesList.clear();
			        for (DataSnapshot invoicePostSnapshot: invoiceSnapshot.getChildren()) {
			            InvoiceData invoiceData = invoicePostSnapshot.getValue(InvoiceData.class);
			            invoicesList.add(invoiceData);
			        } 
		        	LOGGER.info(" The invoice Snapshot Key is " + invoiceSnapshot.getKey());
		        }

		        @Override
		        public void onFailed(DatabaseError databaseError) {
		           LOGGER.info("Error retrieving data");
		           throw new BillliveServiceException(databaseError.getMessage());
		        }
		    });
			List<InvoiceDTO> invoicesNotRemoved = new ArrayList<InvoiceDTO>();
			for (InvoiceData invoice : invoicesList) {
				if (invoice!= null && !Constants.YES.equalsIgnoreCase(invoice.getIsRemoved())) {
					invoicesNotRemoved.add(populateInvoiceDTO(invoice));
				}
			}
			return invoicesNotRemoved;
		} else {
			LOGGER.error("Invoicelive Service Exception in the getAllInvoicesInAnYear() {},  CompanyId or Year");
			throw new BillliveServiceException("Company Id or Year passed cant be null or empty string");
		}
	}

	private InvoiceDTO populateInvoiceDTO(InvoiceData invoice) {
		InvoiceDTO invoiceDTO = new InvoiceDTO();
		List<ItemDTO> listItems = new ArrayList<ItemDTO>();
		invoiceDTO.setInvoiceNumber(invoice.getInvoiceNumber());
		invoiceDTO.setInvoiceFromContactId(invoice.getInvoiceFromContactId());
		invoiceDTO.setInvoiceToContactId(invoice.getInvoiceToContactId());
		invoiceDTO.setCompanyId(invoice.getCompanyId());
		invoiceDTO.setUid(invoice.getUid());
		invoiceDTO.setDateOfInvoice(invoice.getDateOfInvoice());
		invoiceDTO.setDueDate(invoice.getDueDate());
		invoiceDTO.setTotalAmount(invoice.getTotalAmount());
		invoiceDTO.setReferenceMobileNumber(invoice.getReferenceMobileNumber());
		invoiceDTO.setReferenceAadharCardNumber(invoice.getReferenceAadharCardNumber());
		invoiceDTO.setIsTaxeble(invoice.getIsTaxeble());
		invoiceDTO.setTotalTax(invoice.getTotalTax());
		invoiceDTO.setTotalCGST(invoice.getTotalCGST());
		invoiceDTO.setTotalSGST(invoice.getTotalSGST());
		invoiceDTO.setTotalIGST(invoice.getTotalIGST());

		for (InvoiceItemData invoiceItem : invoice.getInvoiceItems()) {
			ItemDTO itemDTO = new ItemDTO();
			itemDTO.setItemId(invoiceItem.getItemId());
			itemDTO.setInventoryId(invoiceItem.getInventoryId());
			itemDTO.setIsTaxeble(invoiceItem.getIsTaxeble());
			itemDTO.setQuantity(invoiceItem.getQuantity());
			itemDTO.setProductValue(invoiceItem.getProductValue());
			itemDTO.setQuantityType(invoiceItem.getQuantityType());

			// itemDTO.setActualUnitPrice(invoiceItem.getA());
			itemDTO.setAmountBeforeTax(invoiceItem.getAmountBeforeTax());
			itemDTO.setTaxAmountForItem(invoiceItem.getTaxAmountForItem());
			itemDTO.setTotalCGST(invoiceItem.getTotalCGST());
			itemDTO.setTotalSGST(invoiceItem.getTotalSGST());
			itemDTO.setTotalIGST(invoiceItem.getTotalIGST());
			itemDTO.setAmountAfterTax(invoiceItem.getAmountAfterTax());
			itemDTO.setDiscount(invoiceItem.getDiscount());
			itemDTO.setMarginAmount(invoiceItem.getMarginAmount());
			itemDTO.setTaxOnMargin(invoiceItem.getTaxOnMargin());
			itemDTO.setTaxId(invoiceItem.getTaxId());

			listItems.add(itemDTO);
		}
		invoiceDTO.setItems(listItems);
		return invoiceDTO;

	}

	private InvoiceData populateInvoiceData(InvoiceDTO invoiceDTO, InvoiceDTO existingInvoice, String companyId) {
		InvoiceData invoiceData = new InvoiceData();
		invoiceData.setInvoiceFromContactId(invoiceDTO.getInvoiceFromContactId());
		invoiceData.setInvoiceToContactId(invoiceDTO.getInvoiceToContactId());
		if (existingInvoice == null && StringUtils.isBlank(invoiceDTO.getInvoiceNumber())) {
			invoiceData.setInvoiceNumber(Utils.generateRandomKey(20));
			invoiceDTO.setInvoiceNumber(Utils.generateRandomKey(20));
		}
		if (existingInvoice != null) {
			invoiceData.setInvoiceNumber(invoiceDTO.getInvoiceNumber());
			invoiceData.setYear(existingInvoice.getYear());
			invoiceData.setMonth(existingInvoice.getMonth());
			invoiceData.setDay(existingInvoice.getDay());
		} else {
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			invoiceData.setYear(String.valueOf(cal.get(Calendar.YEAR)));
			invoiceData.setMonth(String.valueOf(cal.get(Calendar.MONTH)));
			invoiceData.setDay(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
		}
		invoiceData.setIsTaxeble(invoiceDTO.getIsTaxeble());
		invoiceData.setDateOfInvoice(invoiceDTO.getDateOfInvoice());
		invoiceData.setDueDate(invoiceDTO.getDueDate());
		invoiceData.setCompanyId(invoiceDTO.getCompanyId());
		invoiceData.setReferenceAadharCardNumber(invoiceDTO.getReferenceAadharCardNumber());
		invoiceData.setReferenceMobileNumber(invoiceDTO.getReferenceMobileNumber());
		invoiceData.setTotalTax(invoiceDTO.getTotalTax());
		invoiceData.setTotalCGST(invoiceDTO.getTotalCGST());
		invoiceData.setTotalSGST(invoiceDTO.getTotalSGST());
		invoiceData.setTotalIGST(invoiceDTO.getTotalIGST());
		List<InvoiceItemData> invoiceItems = new ArrayList<InvoiceItemData>();
		for (ItemDTO itemDTO : invoiceDTO.getItems()) {
			InvoiceItemData invoiceItem = new InvoiceItemData();
			if (Constants.YES.equalsIgnoreCase(itemDTO.getIsAdded())
					|| Constants.YES.equalsIgnoreCase(itemDTO.getIsUpdated())) {
				// Get Item Details
				ItemData existingItem = itemService.getItemById(companyId, itemDTO.getItemId());
				invoiceItem.setItemId(itemDTO.getItemId());
				invoiceItem.setInventoryId(itemDTO.getInventoryId());
				invoiceItem.setIsTaxeble(itemDTO.getIsTaxeble());
				invoiceItem.setQuantity(itemDTO.getQuantity());
				invoiceItem.setProductValue(itemDTO.getProductValue());
				invoiceItem.setQuantityType(itemDTO.getQuantityType());
				invoiceItem.setTotalCGST(itemDTO.getTotalCGST());
				invoiceItem.setTotalSGST(itemDTO.getTotalSGST());
				invoiceItem.setTotalIGST(itemDTO.getTotalIGST());
				Double amountBeforeTax = itemDTO.getProductValue() * itemDTO.getQuantity();
				if (itemDTO.getAmountBeforeTax() == null) {
					invoiceItem.setAmountBeforeTax(amountBeforeTax);
				} else {
					invoiceItem.setAmountBeforeTax(itemDTO.getAmountBeforeTax());
				}
				Double taxAmountForItem = null;
				Double taxPercentage = 0.0;
				if(itemDTO.getTaxPercentage() == null){
					Tax tax = taxService.getTaxById(companyId, itemDTO.getTaxId());
					if(tax != null)
						taxPercentage = tax.getTotalTaxPercentage();
				}else {
					taxPercentage = itemDTO.getTaxPercentage();
				}
				if (Constants.YES.equalsIgnoreCase(itemDTO.getIsTaxeble())) {
					if (itemDTO.getTaxAmountForItem() == null) {
						taxAmountForItem = Utils.calculateTaxAmount(amountBeforeTax, taxPercentage);
						invoiceItem.setTaxAmountForItem(taxAmountForItem);
					} else {
						invoiceItem.setTaxAmountForItem(itemDTO.getTaxAmountForItem());
					}
				} else {
					taxAmountForItem = 0.00;
				}
				if (itemDTO.getAmountAfterTax() == null) {
					Double amountAfterTax = amountBeforeTax + taxAmountForItem;
					invoiceItem.setAmountBeforeTax(amountAfterTax);
				} else {
					invoiceItem.setAmountBeforeTax(itemDTO.getAmountAfterTax());
				}
				invoiceItem.setDiscount(itemDTO.getDiscount());
				Double marginAmount = itemDTO.getMarginAmount();
				if (marginAmount == null) {
					marginAmount = Utils.calculateMarginAmount(existingItem, itemDTO);
					invoiceItem.setMarginAmount(marginAmount);
				}
				if (itemDTO.getTaxOnMargin() == null) {
					invoiceItem.setTaxOnMargin(Utils.calculateTaxOnMargin(marginAmount, taxPercentage));
				}
			} else if (invoiceData != null && Constants.YES.equalsIgnoreCase(itemDTO.getIsDeleted())) {
				for (InvoiceItemData existingInvoiceItem : invoiceData.getInvoiceItems()) {
					if (existingInvoiceItem.getItemId().equalsIgnoreCase(itemDTO.getItemId())) {
						invoiceItem = existingInvoiceItem;
						invoiceItem.setIsRemoved(Constants.YES);
					}
				}
			}
			invoiceItems.add(invoiceItem);
		}
		invoiceData.setInvoiceItems(invoiceItems);
		return invoiceData;
	}
	

}
