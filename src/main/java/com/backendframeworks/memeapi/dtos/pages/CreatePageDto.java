package com.backendframeworks.memeapi.dtos.pages;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record CreatePageDto(@NotBlank UUID userId, @NotBlank String name) {

}
