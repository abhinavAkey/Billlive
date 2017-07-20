package com.beatus.billlive.validation;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.replace;
import static org.jsoup.Jsoup.clean;
import static org.jsoup.safety.Whitelist.none;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class XssInputValidator {
	
	private static final Logger LOG = LoggerFactory.getLogger(XssInputValidator.class);
	
	private static Map<String, String> XML_ESCAPE = new HashMap<String, String>();
	
	static {
		XML_ESCAPE.put("&apos;", "'");
		XML_ESCAPE.put("&quot;", "\"");
		XML_ESCAPE.put("&amp;", "&");
		XML_ESCAPE.put("&lt;", "<");
		XML_ESCAPE.put("&gt;", ">");
	}
	
    /**
     *  Get safe HTML from untrusted input HTML, by parsing input HTML and filtering it 
     *  through a white-list of permitted tags and attributes. The white-list allows only 
     *  text nodes: all HTML will be stripped. 
     * @param unsafeInput text input that may have HTML
     * @return text input with all HTML stripped
     */
    public String validate(final String unsafeInput) {
    	String safeInput = "";
    	if(isBlank(unsafeInput)) {
    		return safeInput;
    	}
    	String localUnsafeInput = unsafeInput;
    	for(Map.Entry<String, String> entry : XML_ESCAPE.entrySet()) {
    		localUnsafeInput = replace(localUnsafeInput, entry.getKey(), entry.getValue());
    	}
    	try {
    		safeInput = clean(localUnsafeInput, none());
		} catch (Exception ex) {
			LOG.error("XssInputValidator Unexpected failed - {}", ex.getMessage());
		}
    	LOG.debug("XSS validate input [{}] became [{}]", localUnsafeInput, safeInput);
    	return safeInput;
    }
    
}
