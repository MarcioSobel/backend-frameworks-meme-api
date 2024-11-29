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

import com.backendframeworks.memeapi.dtos.pages.CreatePageDto;
import com.backendframeworks.memeapi.dtos.pages.UpdatePageDto;
import com.backendframeworks.memeapi.models.Page;
import com.backendframeworks.memeapi.models.User;
import com.backendframeworks.memeapi.services.auth.TokenService;
import com.backendframeworks.memeapi.services.pages.CreatePageUseCase;
import com.backendframeworks.memeapi.services.pages.FollowPageUseCase;
import com.backendframeworks.memeapi.services.pages.GetPageByNameUseCase;
import com.backendframeworks.memeapi.services.pages.ListAllPagesUseCase;
import com.backendframeworks.memeapi.services.pages.ListPagesFollowedByUserUseCase;
import com.backendframeworks.memeapi.services.pages.RemovePageUseCase;
import com.backendframeworks.memeapi.services.pages.UnfollowPageUseCase;
import com.backendframeworks.memeapi.services.pages.UpdatePageUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pages")
public class PageController {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private CreatePageUseCase createPageUseCase;

	@Autowired
	private RemovePageUseCase removePageUseCase;

	@Autowired
	private UpdatePageUseCase updatePageUseCase;

	@Autowired
	private ListAllPagesUseCase listAllPagesUseCase;

	@Autowired
	private ListPagesFollowedByUserUseCase listPagesFollowedByUserUseCase;

	@Autowired
	private FollowPageUseCase followPageUseCase;

	@Autowired
	private UnfollowPageUseCase unfollowPageUseCase;

	@Autowired
	private GetPageByNameUseCase getPageByNameUseCase;

	@PostMapping
	@ApiResponse(responseCode = "201")
	@Operation(summary = "create a page", method = "POST")
	public ResponseEntity<Page> createPage(@RequestBody CreatePageDto dto) {
		log.info("Creating page");
		Page page = createPageUseCase.execute(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(page);
	}

	@DeleteMapping("/{id}")
	@ApiResponse(responseCode = "204")
	@Operation(summary = "delete a page", method = "DELETE")
	public ResponseEntity<Object> removePage(@PathVariable UUID id) {
		log.info("Removing page");
		removePageUseCase.execute(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PutMapping("/{id}")
	@ApiResponse(responseCode = "200")
	@Operation(summary = "update a page", method = "PUT")
	public ResponseEntity<Page> updatePage(@PathVariable UUID id, @RequestBody UpdatePageDto dto) {
		log.info("Updating page");
		Page updatedPage = updatePageUseCase.execute(id, dto);
		return ResponseEntity.status(HttpStatus.OK).body(updatedPage);
	}

	@GetMapping
	@ApiResponse(responseCode = "200")
	@Operation(summary = "list all page", method = "GET")
	public ResponseEntity<List<Page>> fetchPages() {
		log.info("Listing pages");
		List<Page> pages = listAllPagesUseCase.execute();
		return ResponseEntity.status(HttpStatus.OK).body(pages);
	}

	@GetMapping("/{name}")
	@ApiResponse(responseCode = "200")
	@Operation(summary = "get a page by name", method = "GET")
	public ResponseEntity<Page> getPageByName(@PathVariable String name) {
		Page page = getPageByNameUseCase.execute(name);
		return ResponseEntity.status(HttpStatus.OK).body(page);
	}

	@GetMapping("/followedBy/{userId}")
	@ApiResponse(responseCode = "200")
	@Operation(summary = "list all pages followed by user id", method = "GET")
	public ResponseEntity<List<Page>> fetchPagesFollowedByUserId(@PathVariable UUID userId) {
		log.info("Listing pages followed by user");
		List<Page> pages = listPagesFollowedByUserUseCase.execute(userId);
		return ResponseEntity.status(HttpStatus.OK).body(pages);
	}

	@GetMapping("/followedBy")
	@ApiResponse(responseCode = "200")
	@Operation(summary = "list all pages followed by authenticated user", method = "GET")
	public ResponseEntity<List<Page>> fetchPagesFollowedByToken(@RequestHeader("Authorization") String token) {
		log.info("Listing pages followed by authenticated user");
		User user = tokenService.getUserByToken(token);

		List<Page> pages = listPagesFollowedByUserUseCase.execute(user.getId());
		return ResponseEntity.status(HttpStatus.OK).body(pages);
	}

	@PostMapping("/follow/{pageId}/{userId}")
	@ApiResponse(responseCode = "204")
	@Operation(summary = "make user id follow page id", method = "POST")
	public ResponseEntity<Object> followPageByUserId(@PathVariable UUID userId, @PathVariable UUID pageId) {
		log.info("Following page");
		followPageUseCase.execute(userId, pageId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping("/follow/{pageId}")
	@ApiResponse(responseCode = "204")
	@Operation(summary = "make authenticated user follow page id", method = "POST")
	public ResponseEntity<Object> followPageByToken(@RequestHeader("Authorization") String token,
			@PathVariable UUID pageId) {
		log.info("Following page by token");
		User user = tokenService.getUserByToken(token);

		followPageUseCase.execute(user.getId(), pageId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping("/unfollow/{pageId}")
	@ApiResponse(responseCode = "204")
	@Operation(summary = "make authenticated user unfollow page id", method = "DELETE")
	public ResponseEntity<Object> unfollowPageByToken(@RequestHeader("Authorization") String token,
			@PathVariable UUID pageId) {
		log.info("Unfollowing page by token");
		User user = tokenService.getUserByToken(token);

		unfollowPageUseCase.execute(user.getId(), pageId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping("/unfollow/{pageId}/{userId}")
	@ApiResponse(responseCode = "204")
	@Operation(summary = "make user id unfollow page id", method = "DELETE")
	public ResponseEntity<Object> unfollowPageByUserId(@PathVariable UUID userId,
			@PathVariable UUID pageId) {
		log.info("Unfollowing page");

		unfollowPageUseCase.execute(userId, pageId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
