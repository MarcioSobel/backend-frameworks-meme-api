package com.backendframeworks.memeapi.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendframeworks.memeapi.dtos.memes.CreateMemeDto;
import com.backendframeworks.memeapi.dtos.memes.UpdateMemeDto;
import com.backendframeworks.memeapi.models.Meme;
import com.backendframeworks.memeapi.models.User;
import com.backendframeworks.memeapi.services.memes.DeleteMemeUseCase;
import com.backendframeworks.memeapi.services.memes.DislikeMemeUseCase;
import com.backendframeworks.memeapi.services.auth.TokenService;
import com.backendframeworks.memeapi.services.memes.CreateMemeUseCase;
import com.backendframeworks.memeapi.services.memes.GetAllMemesUseCase;
import com.backendframeworks.memeapi.services.memes.GetMemeByIdUseCase;
import com.backendframeworks.memeapi.services.memes.LikeMemeUseCase;
import com.backendframeworks.memeapi.services.memes.UpdateMemeUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/memes")
public class MemeController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private LikeMemeUseCase likeMemeUseCase;

    @Autowired
    private DislikeMemeUseCase dislikeMemeUseCase;

    @Autowired
    private GetAllMemesUseCase getAllMemesUseCase;

    @Autowired
    private GetMemeByIdUseCase getMemeByIdUseCase;

    @Autowired
    private CreateMemeUseCase createMemeUseCase;

    @Autowired
    private UpdateMemeUseCase updateMemeUseCase;

    @Autowired
    private DeleteMemeUseCase deleteMemeUseCase;

    @PostMapping("/likes/{memeId}/{userId}")
    @ApiResponse(responseCode = "204")
    @Operation(summary = "like a meme", method = "POST")
    public ResponseEntity<Object> likeMemeByUserId(@PathVariable UUID memeId, @PathVariable UUID userId) {
        likeMemeUseCase.execute(memeId, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/likes/{memeId}")
    @ApiResponse(responseCode = "204")
    @Operation(summary = "like a meme", method = "POST")
    public ResponseEntity<Object> likeMemeByToken(@RequestHeader("Authorization") String token,
            @PathVariable UUID memeId) {
        User user = tokenService.getUserByToken(token);
        likeMemeUseCase.execute(memeId, user.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/likes/{memeId}/{userId}")
    @ApiResponse(responseCode = "204")
    @Operation(summary = "dislike a meme", method = "Delete")
    public ResponseEntity<Object> dislikeMemeByUserId(@PathVariable UUID memeId, @PathVariable UUID userId) {
        dislikeMemeUseCase.execute(memeId, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/likes/{memeId}")
    @ApiResponse(responseCode = "204")
    @Operation(summary = "dislike a meme", method = "DELETE")
    public ResponseEntity<Object> dislikeMemeByToken(@RequestHeader("Authorization") String token,
            @PathVariable UUID memeId) {
        User user = tokenService.getUserByToken(token);

        dislikeMemeUseCase.execute(memeId, user.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    @ApiResponse(responseCode = "200")
    @Operation(summary = "list all memes", method = "GET")
    public ResponseEntity<List<Meme>> getAllMemes() {
        log.info("Fetching all memes");
        List<Meme> memes = getAllMemesUseCase.execute();
        return ResponseEntity.ok(memes);
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200")
    @Operation(summary = "get meme by id", method = "GET")
    public ResponseEntity<Meme> getMemeById(@PathVariable UUID id) {
        log.info("Fetching meme with id: {}", id);
        Meme meme = getMemeByIdUseCase.execute(id);
        if (meme == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(meme);
    }

    @PostMapping
    @ApiResponse(responseCode = "200")
    @Operation(summary = "create a meme", method = "POST")
    public ResponseEntity<Meme> createMeme(@RequestBody CreateMemeDto meme) {
        log.info("Creating meme");
        Meme createdMeme = createMemeUseCase.execute(meme);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMeme);
    }

    @PutMapping("/{id}")
    @ApiResponse(responseCode = "200")
    @Operation(summary = "update a meme", method = "PUT")
    public ResponseEntity<Meme> updateMeme(@PathVariable UUID id, @RequestBody UpdateMemeDto dto) {
        log.info("Updating meme with id: {}", id);
        Meme updatedMeme = updateMemeUseCase.execute(id, dto);
        if (updatedMeme == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedMeme);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "200")
    @Operation(summary = "delete a meme", method = "DELETE")
    public ResponseEntity<Void> deleteMeme(@PathVariable UUID id) {
        log.info("Deleting meme with id: {}", id);
        deleteMemeUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
