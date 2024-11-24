package com.backendframeworks.memeapi.exceptions.users;

import com.backendframeworks.memeapi.exceptions.NotFound;

public class UserNotFoundError extends NotFound {

	public UserNotFoundError() {
		super("User not found");
	}
}
