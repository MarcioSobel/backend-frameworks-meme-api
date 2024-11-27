package com.backendframeworks.memeapi.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.backendframeworks.memeapi.models.User;
import com.backendframeworks.memeapi.repositories.UserRepository;
import com.backendframeworks.memeapi.services.users.RemoveUserUseCase;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RemoveUserUseCase removeUserUseCase;

	@GetMapping
	public ResponseEntity<List<User>> createUser() {
		List<User> users = userRepository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
		log.info("Requst received to delete user with ID: {}", userId);
		try{
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


