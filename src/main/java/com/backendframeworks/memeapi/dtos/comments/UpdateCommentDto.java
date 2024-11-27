package com.backendframeworks.memeapi.dtos.comments;

import jakarta.validation.constraints.NotBlank;

public record UpdateCommentDto(@NotBlank String name) {

}