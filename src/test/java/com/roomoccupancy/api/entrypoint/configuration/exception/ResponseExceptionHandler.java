package com.roomoccupancy.api.entrypoint.configuration.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.roomoccupancy.api.core.exception.BusinessException;

/**
 * Class to handle the exceptions thrown during the requests processing
 * 
 * @author luis
 *
 */
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handles exceptions of type {@link BusinessException}
	 * 
	 * @param ex
	 *            the exception
	 * @param request
	 *            the current request
	 * @return a {@code ResponseEntity} instance
	 */
	@ExceptionHandler({ BusinessException.class })
	public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
		HttpStatus responseStatus = HttpStatus.BAD_REQUEST;

		return new ResponseEntity<Object>(ErrorResponseEntity.of(ex.getMessage(), responseStatus.value()),
				responseStatus);
	}

	/**
	 * Overrides the method
	 * {@link ResponseEntityExceptionHandler#handleExceptionInternal}, to add an
	 * object of {@link ErrorResponseEntity} to the body of the request response.
	 */
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return super.handleExceptionInternal(ex, ErrorResponseEntity.of(ex.getMessage(), status.value()), headers,
				status, request);
	}

}
