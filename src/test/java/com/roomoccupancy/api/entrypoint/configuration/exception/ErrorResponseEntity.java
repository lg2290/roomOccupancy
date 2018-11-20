package com.roomoccupancy.api.entrypoint.configuration.exception;

/**
 * Api Standard Error Response
 * 
 * @author luis
 *
 */
public class ErrorResponseEntity {

	private String error;

	private Integer httpStatusCode;

	public ErrorResponseEntity() {
	}

	public ErrorResponseEntity(String error, Integer httpStatusCode) {
		this.error = error;
		this.httpStatusCode = httpStatusCode;
	}

	public static ErrorResponseEntity of(String error, Integer httpStatusCode) {
		return new ErrorResponseEntity(error, httpStatusCode);
	}

	public String getError() {
		return error;
	}

	public Integer getHttpStatusCode() {
		return httpStatusCode;
	}
}
