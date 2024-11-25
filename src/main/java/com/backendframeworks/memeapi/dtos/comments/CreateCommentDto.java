package com.backendframeworks.memeapi.dtos.comments;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record CreateCommentDto (@NotBlank UUID userId, @NotBlank String comment){

}



