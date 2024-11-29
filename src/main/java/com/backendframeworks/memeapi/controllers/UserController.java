package com.backendframeworks.memeapi.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendframeworks.memeapi.dtos.users.UpdateUserDto;
import com.backendframeworks.memeapi.exceptions.users.UserNotFoundError;
import com.backendframeworks.memeapi.models.User;
import com.backendframeworks.memeapi.repositories.UserRepository;
import com.backendframeworks.memeapi.services.users.RemoveUserUseCase;
import com.backendframeworks.memeapi.services.users.UpdateUserUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UpdateUserUseCase updateUserUseCase;

	@Autowired
	private RemoveUserUseCase removeUserUseCase;

	@GetMapping
	public ResponseEntity<List<User>> createUser() {
		List<User> users = userRepository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	@PutMapping("/{id}")
	@ApiResponse(responseCode = "200")
	@Operation(summary = "update an user", method = "POST")
	public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody UpdateUserDto dto) {
		try {
			User updatedUser = updateUserUseCase.execute(id, dto);
			return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
		} catch (UserNotFoundError e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/{userId}")
	@ApiResponse(responseCode = "204")
	@Operation(summary = "delete an user", method = "POST")
	public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
		log.info("Requst received to delete user with ID: {}", userId);
		try {
			removeUserUseCase.execute(userId);
			log.info("User with ID { deleted succesfully}", userId);
			return ResponseEntity.noContent().build();
		} catch (IllegalArgumentException e) {
			log.error("Invalid UUID provied: {}", e.getMessage());
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			log.error("Error while deleting user with ID {}: {}", userId, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
