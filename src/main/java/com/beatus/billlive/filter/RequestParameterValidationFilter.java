package com.beatus.billlive.filter;

public class RequestParameterValidationFilter {/*

	private static final Logger LOG = LoggerFactory.getLogger(RequestParameterValidationFilter.class);
	
	private Map<String, AuthenticationEntryPoint> securityRedirectMap = new HashMap<String, AuthenticationEntryPoint>();
    private static final String COULD_NOT_SET_IDENTITY_TX_HEADER = "Could not set the response header.";

	private FilterConfig config;
	
	@Resource(name = "validatorFactory")
    private ValidatorFactory validatorFactory;

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletRequest request = (HttpServletRequest) req;
//		HttpServletResponse response = (HttpServletResponse) res;
//		JSONObject jsonObject = null;
//        Map<String, String>  jsonParameters = getRequestParameters(request);
//		if(jsonParameters != null && !jsonParameters.isEmpty()){
//			jsonObject = new JSONObject(jsonParameters);	
//		}
//		
//		if(jsonObject != null){
//			RequestParameterValidationData validationData = constructRequestParameterValidationData(jsonObject);
//		    try{
//		    	validatorFactory.validate(validationData);
//		    }catch(ParameterValidationException ex){
//				LOG.error("Request Parameter validation failure " + ex);
//		    	AuthenticationEntryPoint entryPoint = securityRedirectMap.get(FilterConstants.SECURITY_ERROR);
//				commenceEntryPoint(request, response, ex, entryPoint);
//				return;
//		    }
//		}
//		res = addResponseHeaders(response);
//		chain.doFilter(req, res);
		
	}

	public void init(FilterConfig config) throws ServletException {
        this.config = config;		
	}

	private HttpServletResponse addResponseHeaders(HttpServletResponse response) {
		LOG.info("In addResponseHeaders()");
		try { 
			if(response.getHeader(Constants.X_FRAME_OPTIONS) == null)
        	   response.addHeader(Constants.X_FRAME_OPTIONS, Constants.X_FRAME_OPTIONS_VALUE);
        	response.addHeader(Constants.X_XSS_PROTECTION, Constants.X_XSS_PROTECTION_VALUE);
        	response.addHeader(Constants.STRICT_TRANSPORT_SECURITY, Constants.STRICT_TRANSPORT_SECURITY_VALUE);
        } catch (Throwable throwable) {
            LOG.error(COULD_NOT_SET_IDENTITY_TX_HEADER, throwable);
        }

		return response;
	}

	private RequestParameterValidationData constructRequestParameterValidationData(JSONObject jsonObject) {
		RequestParameterValidationData validationData = new RequestParameterValidationData();
		validationData.setFirstName(jsonObject.tryGetString(Constants.FIRST_NAME));
		validationData.setLastName(jsonObject.tryGetString(Constants.LAST_NAME));
		validationData.setBillNumber(jsonObject.tryGetString(Constants.BILL_NUMBER));
		validationData.setItemId(jsonObject.tryGetString(Constants.ITEM_ID));
		validationData.setDateOfBill(jsonObject.tryGetString(Constants.DATE_OF_BILL));
		validationData.setDueDate(jsonObject.tryGetString(Constants.DUE_DATE));
		validationData.setTotalAmount(jsonObject.tryGetString(Constants.TOTAL_AMOUNT));
		validationData.setHsnCode(jsonObject.tryGetString(Constants.HSN_CODE));
		validationData.setGstItemCode(jsonObject.tryGetString(Constants.GST_ITEM_CODE));
		validationData.setItemName(jsonObject.tryGetString(Constants.ITEM_NAME));
		validationData.setItemDesc(jsonObject.tryGetString(Constants.ITEM_DESC));
		validationData.setUnitPrice(jsonObject.tryGetString(Constants.UNIT_PRICE));
		validationData.setIsTaxeble(jsonObject.tryGetString(Constants.IS_TAXEBLE));
		validationData.setAmount(jsonObject.tryGetString(Constants.AMOUNT));
		validationData.setUid(jsonObject.tryGetString(Constants.UID));
		validationData.setAddressLine1(jsonObject.tryGetString(Constants.ADDRESS_LINE_1));
		validationData.setAddressLine2(jsonObject.tryGetString(Constants.ADDRESS_LINE_2));
		validationData.setCity(jsonObject.tryGetString(Constants.CITY));
		validationData.setState(jsonObject.tryGetString(Constants.STATE));
		validationData.setCountry(jsonObject.tryGetString(Constants.COUNTRY));
		validationData.setPhoneNumber(jsonObject.tryGetString(Constants.PHONE_NUMBER));
		validationData.setTaxId(jsonObject.tryGetString(Constants.TAX_ID));
		validationData.setTaxDesc(jsonObject.tryGetString(Constants.TAX_DESC));
		validationData.setTaxPercentageCGST(jsonObject.tryGetString(Constants.TAX_PERCENTAGE_CGST));
		validationData.setTaxPercentageSGST(jsonObject.tryGetString(Constants.TAX_PERCENTAGE_SGST));
		validationData.setTaxPercentageIGST(jsonObject.tryGetString(Constants.TAX_PERCENTAGE_IGST));
		validationData.setCategoryId(jsonObject.tryGetString(Constants.CATEGORY_ID));
		validationData.setCategoryName(jsonObject.tryGetString(Constants.CATEGORY_NAME));
		validationData.setCategoryDesc(jsonObject.tryGetString(Constants.CATEGORY_DESC));
		validationData.setCategoryType(jsonObject.tryGetString(Constants.CATEGORY_TYPE));	
		validationData.setInventoryId(jsonObject.tryGetString(Constants.INVENTORY_ID));
		validationData.setPurchaseId(jsonObject.tryGetString(Constants.PURCHASE_ID));
		validationData.setPurchaseFrom(jsonObject.tryGetString(Constants.PURCHASE_FROM));
		validationData.setPurchaseTo(jsonObject.tryGetString(Constants.PURCHASE_TO));
		validationData.setPurchaseDate(jsonObject.tryGetString(Constants.PURCHASE_DATE));

		validationData.setEmail(jsonObject.tryGetString(Constants.EMAIL));
		
		return validationData;
	}

	private Map<String, String> getRequestParameters(HttpServletRequest request) {
    	Map<String, String>  requestParameters = new HashMap<String, String>();
    	String entryKey = "";
		Map<String, String[]>  entryParameters = (Map<String, String[]>)request.getParameterMap();
    	for(Map.Entry<String, String[]> entry : entryParameters.entrySet()) {
    		for(String entryValue : entry.getValue()) {
    			entryKey = entry.getKey();
    			if(entry.getValue().length == 1){
	    			requestParameters.put(entryKey, entryValue);
    			}else {
		            LOG.error("PARAMETER_VALIDATION_DUPLICATE_PARAMETER_ENTRY " + entryKey + " entryValue " + entry.getValue());
					throw new ParameterValidationException("The URL has multiple parameters with same name. PARAMETER_VALIDATION_DUPLICATE_PARAMETER_ENTRY ");
    			}
    		}
    	}
    	return requestParameters;
    }
	
	private void commenceEntryPoint(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception,
			AuthenticationEntryPoint entryPoint) throws IOException,
			ServletException {
		entryPoint.commence(request, response, exception);
	}

	public void destroy() {
		
	}*/
}
