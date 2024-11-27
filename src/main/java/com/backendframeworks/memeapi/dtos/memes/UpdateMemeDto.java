package com.backendframeworks.memeapi.dtos.memes;

import jakarta.validation.constraints.NotBlank;

public record UpdateMemeDto(@NotBlank String description) {

}
