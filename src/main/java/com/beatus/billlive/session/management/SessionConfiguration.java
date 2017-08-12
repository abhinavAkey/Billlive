package com.beatus.billlive.session.management;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration("sessionConfiguration")
public class SessionConfiguration {
	
    public static final String 
    	DELETE_USER_SESSION = "deleteUserSession",
    	VERIFIER_PARAMETER_NAME = "v",
        COMPANY_ID_PARAMETER_NAME = "companyId",
        UID_PARAMETER_NAME = "uid",
    	MDC_SESSION_ID = "session.id",
    	SESSION_ID = "sessionId",
    	REQUEST_SESSION_ID = "reqSesId",
    	SESSION_OWNER = "billlive",
    	COOKIE_NAME = "BL_SC",
    	COOKIE_KEY_NAME = "x";
    
    public static final int 
		PAGE_TTL_SECONDS = 600, // 10 minutes in seconds
		SESSION_TTL_SECONDS = 1800; // 30 minutes in seconds
    
	@Value("${session.owner:" + SESSION_OWNER + "}") 
    private String sessionOwner = SESSION_OWNER;
	
	@Value("${param.name:" + VERIFIER_PARAMETER_NAME + "}") 
    private String verifierParamName = VERIFIER_PARAMETER_NAME;
	
	@Value("${param.name:" + COMPANY_ID_PARAMETER_NAME + "}") 
    private String companyIdParamName = COMPANY_ID_PARAMETER_NAME;
	
	@Value("${param.name:" + UID_PARAMETER_NAME + "}") 
    private String uidParamName = UID_PARAMETER_NAME;
	
	@Value("${cookie.name:" + COOKIE_NAME + "}") 
    private String cookieName = COOKIE_NAME;
	
	@Value("${cookie.key.name:" + COOKIE_KEY_NAME + "}") 
    private String cookieKeyName = COOKIE_KEY_NAME;
	
	@Value("${session.ttl:" + SESSION_TTL_SECONDS + "}") 
    private Integer sessionTimeToLive = SESSION_TTL_SECONDS;
	
	@Value("${page.ttl:" + PAGE_TTL_SECONDS + "}") 
    private Integer pageTimeToLive = PAGE_TTL_SECONDS;

	public String getSessionOwner() {
		return sessionOwner;
	}

	public String getVerifierParamName() {
		return verifierParamName;
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
	public String getCompanyIdParamName() {
		return companyIdParamName;
	}

	public String getUidParamName() {
		return uidParamName;
	}
	
}
