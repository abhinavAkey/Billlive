package com.beatus.billlive.exception;

public class ItemSoldValidationException extends Exception {

	/**
	 * @author vakey15
	 * This is a exception that is thrown when there is a bad data in the request parameters
	 */
	private static final long serialVersionUID = 1L;

	public ItemSoldValidationException(String message) {
        super(message);
    }

    public ItemSoldValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
