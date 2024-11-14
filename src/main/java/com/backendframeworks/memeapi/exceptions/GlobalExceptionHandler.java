package com.backendframeworks.memeapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Conflict.class)
	public ResponseEntity<HttpError> handleConflictException(Conflict exception) {
		HttpError httpError = new HttpError(HttpStatus.CONFLICT, exception.getMessage());
		return ResponseEntity.status(httpError.getStatus()).body(httpError);
	}
}
