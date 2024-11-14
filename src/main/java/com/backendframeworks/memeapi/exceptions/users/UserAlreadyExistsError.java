package com.backendframeworks.memeapi.exceptions.users;

import com.backendframeworks.memeapi.exceptions.Conflict;

public class UserAlreadyExistsError extends Conflict {
	public UserAlreadyExistsError() {
		super("User already exists!");
	}
}
