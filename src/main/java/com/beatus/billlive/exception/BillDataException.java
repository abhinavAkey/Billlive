package com.beatus.billlive.exception;

public class BillDataException extends Exception {

	/**
	 * @author vakey15
	 * This is a exception that is thrown when there is a bad data in the request parameters
	 */
	private static final long serialVersionUID = 1L;

	public BillDataException(String message) {
        super(message);
    }

    public BillDataException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
