package com.beatus.billlive.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.beatus.billlive.session.management.SessionModel;
import com.beatus.billlive.utils.Constants;
import com.beatus.billlive.utils.Utils;

public class BaseController {
	
    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);
	
	public SessionModel initSessionModel(HttpServletRequest request){
		LOG.debug("Init the session store model");
    	SessionModel sessionModel = (SessionModel)request.getAttribute(Constants.SESSION_MODEL);
    	if(sessionModel == null) {
    		sessionModel = new SessionModel();
    	}
        Locale locale = (Locale)request.getAttribute(CookieLocaleResolver.LOCALE_REQUEST_ATTRIBUTE_NAME);
    	if(locale != null) {
    		sessionModel.setLocale(locale.toString());
    	} else {
    		sessionModel.setLocale("en_IN");
    	}
    	String json = Utils.convertJavaToJson(sessionModel);
    	LOG.debug("Session store model :\n{}", json);
    	return sessionModel;	
	}

}
