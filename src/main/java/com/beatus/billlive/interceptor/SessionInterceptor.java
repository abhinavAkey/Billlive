package com.beatus.billlive.interceptor;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.beatus.billlive.session.management.SessionConfiguration;
import com.beatus.billlive.session.management.SessionManager;
import com.beatus.billlive.session.management.SessionModel;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.utils.CookieManager;

public class SessionInterceptor extends HandlerInterceptorAdapter {
	
    private static final Logger LOG = LoggerFactory.getLogger(SessionInterceptor.class);
    
    private static final String 
    	INTERCEPTOR_LOG_START = "{} request to the session interceptor execution start on {} {}.",
    	INTERCEPTOR_LOG_END = "The session interceptor execution finish on {} {}, {}.";

    
    @Resource(name="sessionConfiguration")
	private SessionConfiguration configuration;
    
    @Resource(name="sessionManager")
    private SessionManager sessionManager;
    
    @Resource(name="cookieManager")
	private CookieManager cookieManager;
    
    @PostConstruct
    public void init() {
    	
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
    	final String requestMethod = request.getMethod(), servletPath = request.getRequestURI();
    	LOG.debug(INTERCEPTOR_LOG_START, "Incoming", requestMethod, servletPath );
    	SessionModel sessionModel = sessionManager.initSessionModel(request, response);
    	LOG.debug("Init session model in interceptor pre with session model: {}", sessionModel.toString());
		boolean isPageValid = sessionManager.isPageValid(sessionModel);
		if("POST".equalsIgnoreCase(requestMethod) && !isPageValid) {
        	boolean continueExecution = cleanUpSessionAndCookies(request, response, sessionModel);
			LOG.error("Validate page verifier failed");
			return continueExecution;
        }
        boolean isValid = sessionManager.isCompanyIdAndUidValid(sessionModel);
        if (!isValid) {
        	boolean continueExecution = cleanUpSessionAndCookies(request, response, sessionModel);
			LOG.error("Validate CompanyId And Uid.");
        	return continueExecution;
		}
        request.setAttribute(Constants.SESSION_MODEL, sessionModel);
        LOG.debug("Exiting interceptor pre with session model: {}", sessionModel.toString());
        LOG.debug(INTERCEPTOR_LOG_END, requestMethod, servletPath, "continuing the execution chain" );
        // Proceed in any case.
        return true;
    }

	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    	final String requestMethod = request.getMethod(), servletPath = request.getRequestURI();
    	LOG.debug(INTERCEPTOR_LOG_START, "Outgoing", requestMethod, servletPath);
        SessionModel sessionModel = sessionManager.initSessionModel(request, response);
        LOG.debug("Init session model in interceptor post with session model: {}", sessionModel.toString());
        setRequestAttributeContent(request, response, sessionModel);
        sessionManager.storeSessionModel(sessionModel, request, response);
        LOG.debug("Exiting interceptor post with session model: {}", sessionModel.toString());
    }
	
	
	@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
    		Object handler, Exception ex) throws Exception {
    	final String requestMethod = request.getMethod(), servletPath = request.getRequestURI();
    	LOG.debug(INTERCEPTOR_LOG_START, "After Completion", requestMethod, servletPath);
    	super.afterCompletion(request, response, handler, ex);
    }
	
	private void setRequestAttributeContent(HttpServletRequest request, HttpServletResponse response, SessionModel sessionModel) {
		Object model = null;
		model = request.getAttribute(SessionConfiguration.DELETE_USER_SESSION);
	    boolean deleteUserSession = (model != null && model instanceof Boolean) ? (Boolean)model : false;
	    if(deleteUserSession) {
	    	sessionManager.cleanUpSessionAndCookies(request, response, sessionModel);
	    	sessionManager.storeSessionModel(null, request, response);
	    }
	    sessionManager.seedVerifierAndAuthToken(request, sessionModel);
	   	request.setAttribute(configuration.getVerifierParamName(), sessionModel.getVerifier());
	}
	
	private  boolean cleanUpSessionAndCookies(HttpServletRequest request, HttpServletResponse response, SessionModel sessionModel) {
		sessionManager.cleanUpSessionAndCookies(request, response, sessionModel);	
		return false;
	}
}
