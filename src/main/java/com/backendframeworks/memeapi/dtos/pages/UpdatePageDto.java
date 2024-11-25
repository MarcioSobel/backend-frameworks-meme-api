package com.backendframeworks.memeapi.dtos.pages;

import jakarta.validation.constraints.NotBlank;

public record UpdatePageDto(@NotBlank String name) {

}
