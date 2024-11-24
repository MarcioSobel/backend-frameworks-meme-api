package com.backendframeworks.memeapi.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendframeworks.memeapi.dtos.pages.CreatePageDto;
import com.backendframeworks.memeapi.models.Page;
import com.backendframeworks.memeapi.services.pages.CreatePageUseCase;
import com.backendframeworks.memeapi.services.pages.RemovePageUseCase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pages")
public class PageController {

	@Autowired
	private CreatePageUseCase createPageUseCase;

	@Autowired
	private RemovePageUseCase removePageUseCase;

	@PostMapping
	public ResponseEntity<Page> createPage(@RequestBody CreatePageDto dto) {
		log.info("Creating page");
		Page page = createPageUseCase.execute(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(page);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> removePage(@PathVariable UUID id) {
		log.info("Removing page");
		removePageUseCase.execute(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
