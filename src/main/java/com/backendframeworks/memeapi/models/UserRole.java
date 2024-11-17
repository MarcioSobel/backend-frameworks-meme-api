package com.backendframeworks.memeapi.models;

import lombok.Getter;

@Getter
public enum UserRole {

	USER("user");

	private String role;

	UserRole(String role) {
		this.role = role;
	}
}
