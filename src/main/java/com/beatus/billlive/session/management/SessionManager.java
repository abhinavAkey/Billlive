package com.beatus.billlive.session.management;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;

import com.beatus.billlive.auth.FirebaseUserConnection;
import com.beatus.billlive.service.UserService;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.utils.CookieManager;
import com.beatus.billlive.utils.Utils;
import com.beatus.billlive.validation.exception.ParameterValidationException;
import com.google.common.collect.Maps;

@Component(value="sessionManager")
public class SessionManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(SessionManager.class);

    @Resource(name="sessionConfiguration")
	private SessionConfiguration configuration;

	@Resource(name = "cookieManager")
	private CookieManager cookieManager;
	
	@Resource(name = "firebaseUserConnection")
	private FirebaseUserConnection firebaseUserConnection;
	
	@Resource(name = "userService")
	private UserService userService;
	
    public SessionModel initSessionModel(HttpServletRequest request, HttpServletResponse response) {
	    SessionModel reqSessionModel = (SessionModel)request.getAttribute(Constants.SESSION_MODEL);
	    if(reqSessionModel != null) {
	    	return reqSessionModel;
	    }
    	SessionModel model = new SessionModel();
    	model.setRequestMethod(request.getMethod());
    	model.setServletPath(request.getRequestURI());
    	model.setContextPath(request.getContextPath());
    	model.setSecureRequest(request.isSecure());
    	model.setParameters(getRequestParameters(request));
    	String verifier = ServletRequestUtils.getStringParameter(request, configuration.getVerifierParamName(), "");
    	String companyId = ServletRequestUtils.getStringParameter(request, configuration.getCompanyIdParamName(), "");
    	
    	if(isNotBlank(verifier)) {
	    	model.setVerifier(verifier);
    	}
    	
    	if(isNotBlank(companyId)) {
	    	model.setCompanyId(companyId);
    	}
    	
		Map<String, String> cookieContentMap = cookieManager.getExistingCookie(request, response, configuration.getCookieName());
		if(cookieContentMap != null) {
			model.setCookieContent(cookieContentMap);
		}
    	setRequestSessionIdToMdc(model);
    	LOG.debug("Init new session model : {}", model.toString());
    	return model;
    }
    
    public void storeSessionModel(SessionModel model, HttpServletRequest request, HttpServletResponse response) {
    	LOG.debug("Store secure cookie [{}] started", configuration.getCookieName());
		// delete existing cookie
		if(model == null || model.getCookieContent() == null || model.getCookieContent().isEmpty()) {
			cookieManager.addCookie(response, configuration.getCookieName(), "", model.isSecureRequest(), true);
			LOG.debug("Secure session cookie [{}] deleted", configuration.getCookieName());
			return;
		}

		// create new cookie
		String cookieValue = cookieManager.encryptCookieContent(model.getCookieContent());
		cookieManager.addCookie(response, configuration.getCookieName(), cookieValue, model.isSecureRequest(), false);
		LOG.debug("Secure session cookie [{}] store, for session ID [{}]",
				configuration.getCookieName(), model.getSessionId());
    }
    
	public void seedVerifierAndAuthToken(HttpServletRequest request, SessionModel model) {
    	if(model == null) {
    		return;
    	}
		String verifier = randomAlphanumeric(16);
		model.setVerifier(verifier);
		
		String authToken = getRequestParameters(request).get(Constants.AUTH_TOKEN);
		if(StringUtils.isNotBlank(authToken)){
			model.setAuthToken(authToken);
		}
		savePageVerifierAndAuthToken(model);
    }
	
	public boolean isPageValid(SessionModel model) {
    	boolean valid = false;
		if(model == null) {
    		return valid;
    	}
    	String verifier = model.getVerifier();
		if (isBlank(verifier) || model.getCookieContent() == null
    		|| model.getCookieContent().isEmpty()) {
			clearPageVerifiersAndAuthToken(model);
    		return valid;
    	}
    	String seed = model.getCookieContent().get(verifier);
		if (isBlank(seed)) {
 			clearPageVerifiersAndAuthToken(model);
    		return valid;
    	}
    	try {
    		if (System.currentTimeMillis() < Long.parseLong(seed)) {
				valid = true;
    		}
    	} catch (NumberFormatException e) {
    		LOG.error("Corrupted page verifier seed {}", e);
    	}
    	//verifier is one time use, on non ajax request
		clearPageVerifiersAndAuthToken(model);
		LOG.debug("isPageValid return {}", valid);
    	return valid;
    }
	
	public boolean isCompanyIdAndUidValid(SessionModel model) {
    	boolean valid = false;
		if(model == null) {
    		return valid;
    	}
    	String companyId = model.getCompanyId();
		if (isBlank(companyId) || model.getCookieContent() == null
    		|| model.getCookieContent().isEmpty()) {
			clearPageVerifiersAndAuthToken(model);
    		return valid;
    	}
    	String companyIdFromCookie = model.getCookieContent().get(Constants.COMPANY_ID);
		if (isBlank(companyIdFromCookie)) {
 			clearPageVerifiersAndAuthToken(model);
    		return valid;
    	}
		String uidFromCookie = model.getCookieContent().get(Constants.UID);
		if (isBlank(uidFromCookie)) {
 			clearPageVerifiersAndAuthToken(model);
    		return valid;
    	}
    	try {
    		if (companyIdFromCookie.equalsIgnoreCase(companyId)) {
    			String authToken = model.getCookieContent().get(Constants.AUTH_TOKEN);
    			if(StringUtils.isNoneBlank(authToken)){
	    			String uid = firebaseUserConnection.verifyIdToken(authToken);
	    			boolean isUidNotBlank = false;
	    			if(StringUtils.isNotBlank(uid)){
	    				isUidNotBlank = true;
	    				model.setUid(uid);
	    			}
	    			if(isUidNotBlank){
	    				String companyIdWithUser = userService.isRegistered(uid);
	    				if(StringUtils.isNotBlank(companyIdWithUser) && companyId.equalsIgnoreCase(companyIdWithUser)){
	    					valid = true;
	    				}
	    				
	    			}
    			}
    		}
    	} catch (NumberFormatException e) {
    		LOG.error("Corrupted page verifier seed {}", e);
    	}
    	//verifier is one time use, on non ajax request
		clearPageVerifiersAndAuthToken(model);
		LOG.debug("isPageValid return {}", valid);
    	return valid;
    }
	
    
    public void save(SessionModel model, String key, String value) {
    	if (isBlank(key) || isBlank(value)) {
    		return;
    	}
    	Map<String, String> map = new HashMap<String, String>(1);
    	map.put(key, value);
    	save(model, map);
    }
    
    public void save(SessionModel model, Map<String, String> map) {
    	if (model == null || map == null || map.isEmpty()) {
    		return;
    	}
    	if (model.getCookieContent() == null) {
    		model.setCookieContent(Maps.<String, String>newHashMap());
    		setRequestSessionIdToMdc(model);
    	}
    	String sessionId = model.getSessionId();
    	if (isBlank(sessionId)) {
    		sessionId = Utils.generateRandomKey(16);
    		model.getCookieContent().put(SessionConfiguration.SESSION_ID, sessionId);
    	}
	}
    
    public void clearPageVerifiersAndAuthToken(SessionModel model) {
		if (model == null || model.getCookieContent() == null 
			|| model.getCookieContent().isEmpty()) {
			return;
		}
		Map<String, String> cookieContent = model.getCookieContent();
		Map<String, String> newContent = Maps.newHashMap();
		boolean keepEntry = false;
		for(Map.Entry<String, String> entry : cookieContent.entrySet()) {
			keepEntry = SessionConfiguration.SESSION_ID.equalsIgnoreCase(entry.getKey()) ||
					SessionConfiguration.REQUEST_SESSION_ID.equalsIgnoreCase(entry.getKey());
			if(keepEntry) {
				newContent.put(entry.getKey(), entry.getValue());
			}
		}
		model.setCookieContent(newContent);
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
    
	private void savePageVerifierAndAuthToken(SessionModel model) {
    	if(model == null) {
    		return ;
    	}
    	String verifier = model.getVerifier();
		// only done on non ajax requests
		clearPageVerifiersAndAuthToken(model);
		String seed = String.valueOf(System.currentTimeMillis() + (configuration.getPageTimeToLive() * 1000));
		model.getCookieContent().put(verifier, seed);
		if(StringUtils.isNotBlank(model.getAuthToken())){
			model.getCookieContent().put(Constants.AUTH_TOKEN, model.getAuthToken());
		}   
    }
    
    private void setRequestSessionIdToMdc(SessionModel model) {
		if (model == null) {
			return;
		}
	    String sessionId = model.getRequestSessionId();
		if (isBlank(sessionId)) {
			sessionId = UUID.randomUUID().toString();
			Map<String, String> cookieContent =  model.getCookieContent();
			if(cookieContent == null) {
				cookieContent = Maps.newHashMap();
			}
			cookieContent.put(SessionConfiguration.REQUEST_SESSION_ID, sessionId);
		}
        MDC.put(SessionConfiguration.MDC_SESSION_ID, sessionId);
    }

	public void cleanUpSessionAndCookies(HttpServletRequest request, HttpServletResponse response, SessionModel model) {
		clearPageVerifiersAndAuthToken(model);	
		
	}
    
}
