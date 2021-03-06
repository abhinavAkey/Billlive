package com.beatus.billlive.exception;

public class InventoryValidationException extends Exception {

	/**
	 * @author vakey15
	 * This is a exception that is thrown when there is a bad data in the request parameters
	 */
	private static final long serialVersionUID = 1L;

	public InventoryValidationException(String message) {
        super(message);
    }

    public InventoryValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
