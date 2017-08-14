package com.beatus.billlive.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HttpMessageNotReadableExceptionHandler{
	
	/*private static final String MALFORMED_REQUEST = "Malformed syntax in request";
	
	private static final String INVALID_PROPERTY_FORMAT = "Invalid Property Format";
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private FailsafeExceptionHandler failsafeExceptionHandler;
    
    public void setFailsafeExceptionHandler(FailsafeExceptionHandler failsafeExceptionHandler) {
        this.failsafeExceptionHandler = failsafeExceptionHandler;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleException(HttpMessageNotReadableException ex, WebRequest request) {
        logger.error("Handling Exception: " + ex, ex);
        Throwable cause = ex.getCause();
        if(cause == null){
            return failsafeExceptionHandler.handleExceptionInternal(ex, request);
        }
        if(cause instanceof InvalidFormatException){
            return handleInvalidFormatException((InvalidFormatException) cause, request);
        }else if(cause instanceof UnrecognizedPropertyException){
            return handleUnrecognizedPropertyException((UnrecognizedPropertyException) cause, request);
        }else if(cause instanceof JsonParseException){
            return handleJsonParseException((JsonParseException) cause, request);
        }else if(cause instanceof JsonMappingException){
            return handleJsonMappingException((JsonMappingException) cause, request);
        }else{
            return failsafeExceptionHandler.handleExceptionInternal(ex, request);            
        }
    }

    private ResponseEntity<Object> handleUnrecognizedPropertyException(UnrecognizedPropertyException cause,
            WebRequest request) {
        Map<String, Object> body = ExceptionHandlerUtils.error("Unknown property " + cause.getPropertyName());
        return new ResponseEntity<Object>(body, ExceptionHandlerUtils.defaultHeaders(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException cause, WebRequest request) {
    	 HttpHeaders headers = oauthHeaders(ERROR_INVALID_REQUEST, INVALID_PROPERTY_FORMAT);
    	 String s = "UNKNOWN";
    	 try{
    		 s = cause.getPath().get(0).getFieldName();
    	 }catch(Exception e){
    	     logger.debug("Failed to get field for error message, using UNKNOWN", e);
    	 }
         Map<String, Object> data = new LinkedHashMap<String, Object>();
         data.put(s, "Invalid property format");
         Map<String, Object> body = ExceptionHandlerUtils.fail(data);
         return new ResponseEntity<Object>(body, headers, HttpStatus.BAD_REQUEST);
    }
    
    private ResponseEntity<Object> handleJsonParseException(JsonParseException cause,
            WebRequest request) {
    	HttpHeaders headers = oauthHeaders(ERROR_INVALID_REQUEST, MALFORMED_REQUEST);
        Map<String, Object> body = ExceptionHandlerUtils.fail(MALFORMED_REQUEST + " - " + "Unable to Parse Request at line: " + cause.getLocation().getLineNr()+ ", column: "+ cause.getLocation().getColumnNr());
        return new ResponseEntity<Object>(body, headers, HttpStatus.BAD_REQUEST);
    }
    
    private ResponseEntity<Object> handleJsonMappingException(JsonMappingException cause,
            WebRequest request) {
    	String s = "UNKNOWN";
	   	 try{
	   		 s = cause.getPath().get(0).getFieldName();
	   	 }catch(Exception e){
             logger.debug("Failed to get property that failed to bind.  Error message using UNKNOWN", e);
	   	 }
    	HttpHeaders headers = oauthHeaders(ERROR_INVALID_REQUEST, MALFORMED_REQUEST);
        Map<String, Object> body = ExceptionHandlerUtils.error(MALFORMED_REQUEST + " - " + "Unable to Map Property " + s);
        return new ResponseEntity<Object>(body, headers, HttpStatus.BAD_REQUEST);
    }*/
}
