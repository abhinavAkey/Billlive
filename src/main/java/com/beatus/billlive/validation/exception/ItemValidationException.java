package com.beatus.billlive.validation.exception;

public class ItemValidationException extends Exception {

	/**
	 * @author vakey15
	 * This is a exception that is thrown when there is a bad data in the request parameters
	 */
	private static final long serialVersionUID = 1L;

	public ItemValidationException(String message) {
        super(message);
    }

    public ItemValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
