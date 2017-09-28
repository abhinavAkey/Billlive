package com.beatus.billlive.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component("generateReportsService")
public class GenerateReportsService {/*
	@Resource(name = "generateReportRepository")
	private GenerateReportRepository generateReportRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GenerateReportsService.class);

	public String generateReportByBillNumber(HttpServletRequest request, HttpServletResponse response, String companyId, String billNumber) throws GenerateReportException{
		
		if(billNumber == null){
			throw new GenerateReportException("Bill number cant be null");
		}
		try {
							
				return generateReportRepository.generateReportByBillNumber(billNumber);
			
		} catch (GenerateReportException generateReportException) {
			LOGGER.info("Generate Report Exception in the generateReportByBillNumber() {} ", generateReportException.getMessage());
			throw generateReportException;
		}	
		return "N";		
	}

	public String generateReportByCompanyId(HttpServletRequest request, HttpServletResponse response, BillDTO billDTO, String companyId) throws BillDataException{
		
		if(billDTO == null){
			throw new BillDataException("Bill data cant be null");
		}
		try {
			//Revisit validator
			boolean isValidated = billValidator.validateBillData(billDTO);
			if(isValidated){
				if(StringUtils.isBlank(companyId)){
					companyId = billDTO.getCompanyId();
				}
				BillData existingBill = null;
				if(StringUtils.isNotBlank(billDTO.getBillNumber()))
					existingBill = billRepository.getBillByBillNumber(companyId, billDTO.getBillNumber());
				BillData billData = populateBillData(billDTO, existingBill, companyId);			
				return billRepository.updateBill(billData);
			}
		} catch (BillDataException billException) {
			LOGGER.info("Bill validation Exception in the addBillService() {} ", billException.getMessage());
			throw billException;
		}
		
		return "N";		
	}
	
	public String generateReportInAMonth(String companyId, String billNumber) {
		if(StringUtils.isNotBlank(billNumber) && StringUtils.isNotBlank(companyId)){
			BillData billData = billRepository.getBillByBillNumber(companyId, billNumber);
			billData.setIsRemoved(Constants.YES);
			return billRepository.updateBill(billData);
		}
		return "N";
	}

	public List<BillDTO> generateReportInAYear(String companyId) {
		List<BillData> bills = billRepository.getAllBillsBasedOnCompanyId(companyId);
		List<BillDTO> billsNotRemoved = new ArrayList<BillDTO>();
		for(BillData bill : bills){
			if(!Constants.YES.equalsIgnoreCase(bill.getIsRemoved())){
				billsNotRemoved.add(populateBillDTO(bill));
			}
		}
		return billsNotRemoved;
	}

	public BillDTO generateReportInADay(String companyId, String billNumber) {
		BillData billData = billRepository.getBillByBillNumber(companyId, billNumber);
		if(!Constants.YES.equalsIgnoreCase(billData.getIsRemoved())){
			return populateBillDTO(billData);
		}else {
			return null;
		}
	}
	
*/}
