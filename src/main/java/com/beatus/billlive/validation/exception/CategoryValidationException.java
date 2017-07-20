package com.beatus.billlive.validation.exception;

public class CategoryValidationException extends Exception {

	/**
	 * @author vakey15
	 * This is a exception that is thrown when there is a bad data in the request parameters
	 */
	private static final long serialVersionUID = 1L;

	public CategoryValidationException(String message) {
        super(message);
    }

    public CategoryValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
