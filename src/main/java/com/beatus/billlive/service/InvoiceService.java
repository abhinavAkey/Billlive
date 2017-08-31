package com.beatus.billlive.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.beatus.billlive.domain.model.InvoiceData;
import com.beatus.billlive.repository.InvoiceRepository;
import com.beatus.billlive.service.exception.BillliveServiceException;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.validation.InvoiceDataValidator;
import com.beatus.billlive.validation.exception.BillValidationException;
import com.beatus.billlive.validation.exception.InvoiceDataValidationException;

@Service
@Component("billService")
public class InvoiceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BillService.class);

	@Resource(name = "invoiceRepository")
	private InvoiceRepository invoiceRepository;

	@Resource(name = "invoiceDataValidator")
	private InvoiceDataValidator invoiceDataValidator;

	@Resource(name = "taxService")
	private TaxService taxService;

	@Resource(name = "itemService")
	private ItemService itemService;

	public String addInvoice(HttpServletRequest request, HttpServletResponse response, InvoiceData invoiceData)
			throws BillliveServiceException, InvoiceDataValidationException {
		if (invoiceData == null) {
			throw new BillliveServiceException("Bill data cant be null");
		}
		try {
			// Revisit validator
			boolean isValidated = invoiceDataValidator.validateInvoiceData(invoiceData);
			if (isValidated) {
				String companyId = invoiceData.getCompanyId();
				InvoiceData existingInvoice = null;
				if (StringUtils.isNotBlank(invoiceData.getInvoiceNumber())) {
					existingInvoice = invoiceRepository.getInvoiceById(companyId, invoiceData.getInvoiceNumber());
					return updateInvoice(request, response, invoiceData);
				}
				return invoiceRepository.addInvoice(invoiceData);
			}
		} catch (BillliveServiceException billException) {
			LOGGER.error("Billlive Service Exception in the addInvoice() {} ", billException.getMessage());
			throw billException;
		}
		return "N";
	}

	public String updateInvoice(HttpServletRequest request, HttpServletResponse response, InvoiceData invoiceData)
			throws BillliveServiceException, InvoiceDataValidationException {

		if (invoiceData == null) {
			throw new BillliveServiceException("Bill data cant be null");
		}
		try {
			// Revisit validator
			boolean isValidated = invoiceDataValidator.validateInvoiceData(invoiceData);
			if (isValidated) {
				String companyId = invoiceData.getCompanyId();
				InvoiceData existingInvoice = null;
				if (StringUtils.isNotBlank(invoiceData.getInvoiceNumber()))
					existingInvoice = invoiceRepository.getInvoiceById(companyId, invoiceData.getInvoiceNumber());
				return invoiceRepository.updateInvoice(invoiceData);
			}
		} catch (BillliveServiceException billException) {
			LOGGER.error("Billlive Service Exception in the updateBillService() {} ", billException.getMessage());
			throw billException;
		}

		return "N";
	}

	public String removeInvoice(String companyId, String uid, String invoiceNumber)
			throws BillliveServiceException, BillValidationException {
		LOGGER.info("In removeBill method of Invoice Service");
		if (StringUtils.isNotBlank(invoiceNumber) && StringUtils.isNotBlank(companyId)) {
			InvoiceData invoiceData = invoiceRepository.getInvoiceById(companyId, invoiceNumber);
			invoiceData.setUid(uid);
			invoiceData.setIsRemoved(Constants.YES);
			return invoiceRepository.updateInvoice(invoiceData);
		} else {
			LOGGER.error(
					"Billlive Service Exception in the removeInvoice() {},  Invoice Number or CompanyId passed cant be null or empty string");
			throw new BillliveServiceException("Invoice data passed cant be null or empty string");
		}
	}

	public List<InvoiceData> getAllInvoicesBasedOnCompanyId(String companyId) {
		LOGGER.info("In getAllInvoicesBasedOnCompanyId method of Invoice Service");
		if (StringUtils.isNotBlank(companyId)) {
			List<InvoiceData> invoices = invoiceRepository.getAllInvoicesBasedOnCompanyId(companyId);
			List<InvoiceData> invoicesNotRemoved = new ArrayList<InvoiceData>();
			for (InvoiceData invoice : invoices) {
				if (!Constants.YES.equalsIgnoreCase(invoice.getIsRemoved())) {
					invoicesNotRemoved.add(invoice);
				}
			}
			return invoicesNotRemoved;
		} else {
			LOGGER.error(
					"Billlive Service Exception in the getAllInvoicesBasedOnCompanyId() {},  CompanyId passed cant be null or empty string");
			throw new BillliveServiceException("Company Id passed cant be null or empty string");
		}
	}

	public InvoiceData getInvoiceById(String companyId, String invoiceNumber) {
		LOGGER.info("In getInvoiceById method of Invoice Service");
		if (StringUtils.isNotBlank(invoiceNumber) && StringUtils.isNotBlank(companyId)) {

			InvoiceData invoiceData = invoiceRepository.getInvoiceById(companyId, invoiceNumber);
			if (!Constants.YES.equalsIgnoreCase(invoiceData.getIsRemoved())) {
				return invoiceData;
			} else {
				return null;
			}
		} else {
			LOGGER.error(
					"Billlive Service Exception in the getInvoiceById() {},  CompanyId or invoiceNumber passed cant be null or empty string");
			throw new BillliveServiceException("Company Id or invoiceNumber passed cant be null or empty string");
		}
	}

	public List<InvoiceData> getAllInvoices(String companyId) {
		LOGGER.info("In getAllInvoices method of Invoice Service");
		if (StringUtils.isNotBlank(companyId)) {

			List<InvoiceData> invoices = invoiceRepository.getAllInvoices(companyId);
			List<InvoiceData> invoicesNotRemoved = new ArrayList<InvoiceData>();
			for (InvoiceData invoice : invoices) {
				if (!Constants.YES.equalsIgnoreCase(invoice.getIsRemoved())) {
					invoicesNotRemoved.add(invoice);
				}
			}
			return invoicesNotRemoved;
		} else {
			LOGGER.error(
					"Billlive Service Exception in the getAllInvoices() {},  CompanyId passed cant be null or empty string");
			throw new BillliveServiceException("Company Id passed cant be null or empty string");
		}
	}

	public List<InvoiceData> getAllInvoicesInAMonth(String companyId, String year, String month) {
		LOGGER.info("In getAllInvoices method of Invoice Service");
		if (StringUtils.isNotBlank(companyId) && StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month)) {

			List<InvoiceData> invoices = invoiceRepository.getAllInvoicesInAMonth(companyId, year, month);
			List<InvoiceData> invoicesNotRemoved = new ArrayList<InvoiceData>();
			for (InvoiceData invoice : invoices) {
				if (!Constants.YES.equalsIgnoreCase(invoice.getIsRemoved())) {
					invoicesNotRemoved.add(invoice);
				}
			}
			return invoicesNotRemoved;
		} else {
			LOGGER.error(
					"Billlive Service Exception in the getAllInvoicesInAMonth() {},   CompanyId or Year or Month passed cant be null or empty string");
			throw new BillliveServiceException(" CompanyId or Year or Month passed cant be null or empty string");
		}
	}

	public List<InvoiceData> getAllInvoicesInADay(String companyId, String year, String month, String day) {
		LOGGER.info("In getAllInvoicesInADay method of Invoice Service");
		if (StringUtils.isNotBlank(companyId) && StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month)
				&& StringUtils.isNotBlank(day)) {

			List<InvoiceData> invoices = invoiceRepository.getAllInvoicesInADay(companyId, year, month, day);
			List<InvoiceData> invoicesNotRemoved = new ArrayList<InvoiceData>();
			for (InvoiceData invoice : invoices) {
				if (!Constants.YES.equalsIgnoreCase(invoice.getIsRemoved())) {
					invoicesNotRemoved.add(invoice);
				}
			}
			return invoicesNotRemoved;
		} else {
			LOGGER.error(
					"Billlive Service Exception in the getAllInvoicesInADay() {},   CompanyId or Year or Month or day passed cant be null or empty string");
			throw new BillliveServiceException(" CompanyId or Year or Month or daypassed cant be null or empty string");
		}
	}

	public List<InvoiceData> getAllInvoicesInAnYear(String companyId, String year) {
		LOGGER.info("In getAllInvoicesInAYear method of Invoice Service");
		if (StringUtils.isNotBlank(companyId) && StringUtils.isNotBlank(year)) {

			List<InvoiceData> invoices = invoiceRepository.getAllInvoicesInAYear(companyId, year);
			List<InvoiceData> invoicesNotRemoved = new ArrayList<InvoiceData>();
			for (InvoiceData invoice : invoices) {
				if (!Constants.YES.equalsIgnoreCase(invoice.getIsRemoved())) {
					invoicesNotRemoved.add(invoice);
				}
			}
			return invoicesNotRemoved;
		} else {
			LOGGER.error(
					"Billlive Service Exception in the getAllInvoicesInAYear() {},   CompanyId or Year passed cant be null or empty string");
			throw new BillliveServiceException(" CompanyId or Year passed cant be null or empty string");
		}
	}

}
