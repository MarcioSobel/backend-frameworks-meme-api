package com.backendframeworks.memeapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Conflict.class)
	public ResponseEntity<HttpError> handleConflictException(Conflict e) {
		return generateResponseEntity(HttpStatus.CONFLICT, e);
	}

	@ExceptionHandler(Unauthorized.class)
	public ResponseEntity<HttpError> handleUnauthorizedException(Unauthorized e) {
		return generateResponseEntity(HttpStatus.UNAUTHORIZED, e);
	}

	@ExceptionHandler(NotFound.class)
	public ResponseEntity<HttpError> handleNotFoundException(NotFound e) {
		return generateResponseEntity(HttpStatus.NOT_FOUND, e);
	}

	private ResponseEntity<HttpError> generateResponseEntity(HttpStatus status, RuntimeException exception) {
		HttpError httpError = new HttpError(status, exception.getMessage());
		return ResponseEntity.status(status).body(httpError);
	}
}
