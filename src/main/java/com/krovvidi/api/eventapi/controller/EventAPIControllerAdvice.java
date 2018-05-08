package com.krovvidi.api.eventapi.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.krovvidi.api.eventapi.response.EventAPIErrorResponse;

@ControllerAdvice
public class EventAPIControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EventAPIResourceNotFoundException.class)
	public ResponseEntity<Object> resourceNotFound(EventAPIResourceNotFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String error = "Resource not found";
		return new ResponseEntity<Object>(new EventAPIErrorResponse<>(Arrays.asList(error)), HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing.";
		return new ResponseEntity<Object>(new EventAPIErrorResponse<>(Arrays.asList(error)), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Exception thrown when
	 * {@link org.springframework.validation.annotation.Validated} is used in
	 * controller.
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex,
			HttpServletRequest request) {
		try {
			List<String> messages = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
					.collect(Collectors.toList());
			return new ResponseEntity<>(new EventAPIErrorResponse<>(messages), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new EventAPIErrorResponse<>(Arrays.asList(ex.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleException(Exception ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		String error = ex.getMessage();
		return new ResponseEntity<Object>(new EventAPIErrorResponse<>(Arrays.asList(error)), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = "IllegalArgument or IllegalState Exception";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
}