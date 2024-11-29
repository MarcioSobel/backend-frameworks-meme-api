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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendframeworks.memeapi.dtos.comments.CreateCommentDto;
import com.backendframeworks.memeapi.dtos.comments.UpdateCommentDto;
import com.backendframeworks.memeapi.models.Comment;
import com.backendframeworks.memeapi.services.comments.CommentMemeUseCase;
import com.backendframeworks.memeapi.services.comments.ListCommentsByUserIdUserCase;
import com.backendframeworks.memeapi.services.comments.ListCommentsUseCase;
import com.backendframeworks.memeapi.services.comments.RemoveCommentUseCase;
import com.backendframeworks.memeapi.services.comments.UpdateCommentUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/comments")

public class CommentController {

	@Autowired
	private CommentMemeUseCase commentMemeUseCase;

	@Autowired
	private RemoveCommentUseCase removeCommentUseCase;

	@Autowired
	private UpdateCommentUseCase updateCommentUseCase;

	@Autowired
	private ListCommentsUseCase listCommentsUseCase;

	@Autowired
	private ListCommentsByUserIdUserCase listCommentsByUserIdUserCase;

	@PostMapping
	@Operation(summary = "comment on a meme", method = "POST")
	@ApiResponse(responseCode = "201", description = "comment created successfully")
	public ResponseEntity<Comment> createPage(@RequestBody CreateCommentDto dto) {
		log.info("Creating Comment");
		Comment comment = commentMemeUseCase.execute(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(comment);
	}

	@GetMapping
	@ApiResponse(responseCode = "200")
	@Operation(summary = "list all comments", method = "GET")
	public ResponseEntity<List<Comment>> fetchComments() {
		log.info("Listing comments");
		List<Comment> comments = listCommentsUseCase.execute();
		return ResponseEntity.status(HttpStatus.OK).body(comments);
	}

	@GetMapping("/{userId}")
	@ApiResponse(responseCode = "200")
	@Operation(summary = "list all comments by userId", method = "GET")
	public ResponseEntity<List<Comment>> fetchCommentsByUserId(@PathVariable UUID userId) {
		log.info("Listing comments by user");
		List<Comment> comments = listCommentsByUserIdUserCase.execute(userId);
		return ResponseEntity.status(HttpStatus.OK).body(comments);
	}

	@DeleteMapping("/{userId}")
	@ApiResponse(responseCode = "204")
	@Operation(summary = "delete a comment", method = "DELETE")
	public ResponseEntity<Object> removeComment(@PathVariable UUID userId) {
		log.info("Removing comment");
		removeCommentUseCase.execute(userId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PutMapping("/{userId}")
	@ApiResponse(responseCode = "200")
	@Operation(summary = "update a comment", method = "PUT")
	public ResponseEntity<Comment> updateComment(@PathVariable UUID id, @RequestBody UpdateCommentDto dto) {
		log.info("Updating page");
		Comment updatedComment = updateCommentUseCase.execute(id, dto);
		return ResponseEntity.status(HttpStatus.OK).body(updatedComment);
	}
}
