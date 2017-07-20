package com.beatus.billlive.session.management;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration("sessionConfiguration")
public class SessionConfiguration {
	
    public static final String 
    	DELETE_USER_SESSION = "deleteUserSession",
    	VERIFIER_PARAMETER_NAME = "v",
    	MDC_SESSION_ID = "session.id",
    	SESSION_ID = "sessionId",
    	REQUEST_SESSION_ID = "reqSesId",
    	SESSION_OWNER = "account",
    	COOKIE_NAME = "AM_SS",
    	COOKIE_KEY_NAME = "x";
    
    public static final int 
		PAGE_TTL_SECONDS = 600, // 10 minutes in seconds
		SESSION_TTL_SECONDS = 1800; // 30 minutes in seconds
    
	@Value("${session.owner:" + SESSION_OWNER + "}") 
    private String sessionOwner = SESSION_OWNER;
	
	@Value("${param.name:" + VERIFIER_PARAMETER_NAME + "}") 
    private String paramName = VERIFIER_PARAMETER_NAME;
	
	@Value("${cookie.name:" + COOKIE_NAME + "}") 
    private String cookieName = COOKIE_NAME;
	
	@Value("${cookie.key.name:" + COOKIE_KEY_NAME + "}") 
    private String cookieKeyName = COOKIE_KEY_NAME;
	
	@Value("${session.ttl:" + SESSION_TTL_SECONDS + "}") 
    private Integer sessionTimeToLive = SESSION_TTL_SECONDS;
	
	@Value("${page.ttl:" + PAGE_TTL_SECONDS + "}") 
    private Integer pageTimeToLive = PAGE_TTL_SECONDS;
	
    /*@Resource(name="redirectRequestMapping")
    private String redirectRequestMapping = "";
    
    @Resource(name="ajaxRedirectRequestMapping")
    private String ajaxRedirectRequestMapping = "";
    
    @Resource(name="ignoredRequestMappingList")
    private List<String> ignoredRequestMapping = new ArrayList<String>();
    
    @Resource(name="ajaxRequestMappingList")
    private List<String> ajaxRequestMappingList = new ArrayList<String>();
    
    @Resource(name="modelAttributeMap")
    private Map<String, String> modelAttribute = new HashMap<String, String>();
    
	@PostConstruct
	public void init() {
    	if(isBlank(redirectRequestMapping)) {
            redirectRequestMapping = "/account/error";
    	}
    	if(isBlank(ajaxRedirectRequestMapping)) {
    		ajaxRedirectRequestMapping = "/account/ajaxerror";
    	}
    	if(ignoredRequestMapping == null || ignoredRequestMapping.isEmpty()) {
    		ignoredRequestMapping = Arrays.asList(new String[] {
                "/account/totp/return", "/account/error"
        	});
    	}
    	if(ajaxRequestMappingList == null || ajaxRequestMappingList.isEmpty()) {
    		ajaxRequestMappingList = Arrays.asList(new String[] {
    	    		"/account/totp/setup/resendcode"
        	});
    	}
    	if(modelAttribute == null || modelAttribute.isEmpty()) {
    		modelAttribute = new LinkedHashMap<String, String>();
    	}
	}*/

	public String getSessionOwner() {
		return sessionOwner;
	}

	public String getParamName() {
		return paramName;
	}

	public String getCookieName() {
		return cookieName;
	}
	
	public String getCookieKeyName() {
		return cookieKeyName;
	}
	
	public Integer getSessionTimeToLive() {
		return sessionTimeToLive;
	}

	public Integer getPageTimeToLive() {
		return pageTimeToLive;
	}
/*
	public String getRedirectRequestMapping() {
		return redirectRequestMapping;
	}

	public String getAjaxRedirectRequestMapping() {
		return ajaxRedirectRequestMapping;
	}

	public List<String> getIgnoredRequestMapping() {
		return ignoredRequestMapping;
	}

	public List<String> getAjaxRequestMappingList() {
		return ajaxRequestMappingList;
	}
	
	public Map<String, String> getModelAttribute() {
		return modelAttribute;
	}*/
	
}
