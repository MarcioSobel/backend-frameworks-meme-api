package com.backendframeworks.memeapi.exceptions.auth;

import com.backendframeworks.memeapi.exceptions.BadRequest;

public class InvalidCredentials extends BadRequest {

	public InvalidCredentials() {
		super("Invalid credentials");
	}
}
