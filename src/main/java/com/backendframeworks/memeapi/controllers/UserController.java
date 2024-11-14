package com.backendframeworks.memeapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendframeworks.memeapi.dtos.users.CreateUserDto;
import com.backendframeworks.memeapi.models.User;
import com.backendframeworks.memeapi.services.users.CreateUserUseCase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private CreateUserUseCase createUserUseCase;

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody CreateUserDto userDto) throws RuntimeException {
		log.info("Chamando o caso de uso de criação do usuário...");
		User user = createUserUseCase.execute(userDto);
		log.info("Usuário criado com sucesso.");
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
}
