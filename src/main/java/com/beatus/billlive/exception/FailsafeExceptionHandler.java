package com.beatus.billlive.exception;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.beatus.billlive.exception.ExceptionHandlerUtils;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FailsafeExceptionHandler{
    
    //JSEND ERRORS
    public static final String BACKEND_SERVER_ERROR = "Backend.Server.Error";
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
        logger.error("Handling Exception: " + ex, ex);
        return handleExceptionInternal(ex, request);
    }
    public ResponseEntity<Object> handleExceptionInternal(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Object body = null;
        if(StringUtils.isNotBlank(ex.getMessage())){
            Map<String, String> errorMap = new HashMap<String, String>();
            errorMap.put("errorDescription", ex.getMessage());
            body = ExceptionHandlerUtils.error(BACKEND_SERVER_ERROR, errorMap);
        	
        }else{
            body = ExceptionHandlerUtils.error(BACKEND_SERVER_ERROR);
        }
        HttpHeaders headers = ExceptionHandlerUtils.defaultHeaders();
        return new ResponseEntity<Object>(body, headers, status);
    }   

}
