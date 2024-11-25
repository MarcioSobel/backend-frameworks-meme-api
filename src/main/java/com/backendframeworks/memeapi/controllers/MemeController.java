package com.backendframeworks.memeapi.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendframeworks.memeapi.models.User;
import com.backendframeworks.memeapi.services.auth.TokenService;
import com.backendframeworks.memeapi.services.memes.LikeMemeUseCase;
import com.backendframeworks.memeapi.services.memes.UnlikeMemeUseCase;

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
	private UnlikeMemeUseCase unlikeMemeUseCase;

	@PostMapping("/likes/{memeId}/{userId}")
	public ResponseEntity<Object> likeMemeByUserId(@PathVariable UUID memeId, @PathVariable UUID userId) {
		likeMemeUseCase.execute(memeId, userId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping("/likes/{memeId}")
	public ResponseEntity<Object> likeMemeByToken(@RequestHeader("Authorization") String token,
			@PathVariable UUID memeId) {
		User user = tokenService.getUserByToken(token);
		likeMemeUseCase.execute(memeId, user.getId());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping("/likes/{memeId}/{userId}")
	public ResponseEntity<Object> unlikeMemeByUserId(@PathVariable UUID memeId, @PathVariable UUID userId) {
		unlikeMemeUseCase.execute(memeId, userId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping("/likes/{memeId}")
	public ResponseEntity<Object> unlikeMemeByToken(@RequestHeader("Authorization") String token,
			@PathVariable UUID memeId) {
		User user = tokenService.getUserByToken(token);

		unlikeMemeUseCase.execute(memeId, user.getId());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
