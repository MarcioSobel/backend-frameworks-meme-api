package com.backendframeworks.memeapi.dtos.users;

import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(@NotBlank String name, @NotBlank String handle, @NotBlank String email,
		@NotBlank String password) {
}
