package com.backendframeworks.memeapi.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class HttpError {

	private HttpStatus status;
	private String message;

	public HttpError(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
}
