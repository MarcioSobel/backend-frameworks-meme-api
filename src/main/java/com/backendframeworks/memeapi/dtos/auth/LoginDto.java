package com.backendframeworks.memeapi.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(@NotBlank String email, @NotBlank String password) {

}
