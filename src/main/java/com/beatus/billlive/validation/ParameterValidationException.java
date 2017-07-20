package com.beatus.billlive.validation;

import org.springframework.security.core.AuthenticationException;

public class ParameterValidationException extends AuthenticationException {

	/**
	 * @author vakey15
	 * This is a exception that is thrown when there is a bad data in the request parameters
	 */
	private static final long serialVersionUID = 6180939424026283937L;

	public ParameterValidationException(String message) {
        super(message);
    }

    public ParameterValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
