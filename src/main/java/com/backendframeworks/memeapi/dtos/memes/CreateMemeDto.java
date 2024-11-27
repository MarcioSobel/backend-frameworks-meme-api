package com.backendframeworks.memeapi.dtos.memes;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record CreateMemeDto(@NotBlank String description, @NotBlank String imgUrl, @NotBlank UUID pageId) {

}
