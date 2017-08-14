package com.beatus.billlive.service.exception;

public class BillliveServiceException extends Exception {
	
	/**
	 * @author vakey15
	 * This is a exception that is thrown when there is a bad data in the request parameters
	 */
	private static final long serialVersionUID = 1L;

	public BillliveServiceException(String message) {
        super(message);
    }

    public BillliveServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
