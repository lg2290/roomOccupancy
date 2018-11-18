package com.roomoccupancy.api.core.exception;

/**
 * Exception to be thrown when any Business Rule is violated
 * 
 * @author luis
 *
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -6525874132480299240L;

	public BusinessException(String message) {
		super(message);
	}
}
