package com.backendframeworks.memeapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backendframeworks.memeapi.dtos.auth.LoginDto;
import com.backendframeworks.memeapi.dtos.users.CreateUserDto;
import com.backendframeworks.memeapi.exceptions.auth.InvalidCredentials;
import com.backendframeworks.memeapi.models.User;
import com.backendframeworks.memeapi.services.auth.TokenService;
import com.backendframeworks.memeapi.services.users.CreateUserUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CreateUserUseCase createUserUseCase;

	@Autowired
	private TokenService tokenService;

	@PostMapping("/auth")
	@Operation(summary = "authenticate", method = "POST")
	@ApiResponse(responseCode = "200", description = "authenticated successfully")
	public ResponseEntity<String> login(@RequestBody @Valid LoginDto loginDto) {
		log.info("Received login request");
		UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
				loginDto.email(),
				loginDto.password());
		log.info("Created username password authentication token");
		log.info("Credentials:", credentials.getPrincipal(), credentials.getCredentials());

		log.info("Creating authentication...");

		Authentication auth;
		try {
			auth = authenticationManager.authenticate(credentials);
		} catch (Exception e) {
			log.error("Invalid credentials, returning error");
			throw new InvalidCredentials();
		}

		log.info("Authentication created:", auth);

		log.info("Generating token...");
		String token = tokenService.generateToken((User) auth.getPrincipal());
		log.info("Token generated:", token);

		return ResponseEntity.status(HttpStatus.OK).body(token);
	}

	@PostMapping("/register")
	@Operation(summary = "create an account in the database", method = "POST")
	@ApiResponse(responseCode = "201", description = "registered successfully")
	public ResponseEntity<Object> register(@RequestBody @Valid CreateUserDto userDto) {
		log.info("Calling create user use case...");
		User user = createUserUseCase.execute(userDto);
		log.info("User created sucessfully.");
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
}
