package com.backendframeworks.memeapi.exceptions;

public class BadRequest extends RuntimeException {

	public BadRequest(String message) {
		super(message);
	}
}
