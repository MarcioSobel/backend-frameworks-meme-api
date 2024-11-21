package com.backendframeworks.memeapi.exceptions;

public class Unauthorized extends RuntimeException {

	public Unauthorized(String message) {
		super(message);
	}
}
