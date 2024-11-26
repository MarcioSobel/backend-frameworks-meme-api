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
//import com.backendframeworks.memeapi.dtos.comments.UpdateCommentDto;
import com.backendframeworks.memeapi.models.Comment;
import com.backendframeworks.memeapi.services.comments.CommentMemeUseCase;
import com.backendframeworks.memeapi.services.comments.ListCommentsByUserIdUserCase;
import com.backendframeworks.memeapi.services.comments.ListCommentsUseCase;
//import com.backendframeworks.memeapi.services.comments.RemoveCommentsUseCase;
//import com.backendframeworks.memeapi.services.comments.UpdateCommentsUseCase;
import com.backendframeworks.memeapi.services.pages.ListPagesFollowedByUserUseCase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/comments")

public class CommentController {
    
    @Autowired
	private CommentMemeUseCase commentMemeUseCase;

    //@Autowired
	//private RemoveCommentsUseCase removeCommentsUseCase;

    //@Autowired
	//private UpdateCommentsUseCase updateCommentsUseCase;

    @Autowired
	private ListCommentsUseCase listCommentsUseCase;

	@Autowired
	private ListCommentsByUserIdUserCase listCommentsByUserIdUserCase;


    @PostMapping
	public ResponseEntity<Comment> createPage(@RequestBody CreateCommentDto dto) {
		log.info("Creating Comment");
		Comment comment = commentMemeUseCase.execute(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(comment);
	}

    @GetMapping
	public ResponseEntity<List<Comment>> fetchComments() {
		log.info("Listing comments");
		List<Comment> comments = listCommentsUseCase.execute();
		return ResponseEntity.status(HttpStatus.OK).body(comments);
	}

	@GetMapping("/followedBy/{userId}")
	public ResponseEntity<List<Comment>> fetchCommentsByUserId(@PathVariable UUID userId) {
		log.info("Listing comments by user");
		List<Comment> comments = listCommentsByUserIdUserCase.execute(userId);
		return ResponseEntity.status(HttpStatus.OK).body(comments);
	}
}


/*
 * import java.util.List;
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

import com.backendframeworks.memeapi.dtos.pages.CreatePageDto;
import com.backendframeworks.memeapi.dtos.pages.UpdatePageDto;
import com.backendframeworks.memeapi.models.Page;
import com.backendframeworks.memeapi.services.pages.CreatePageUseCase;
import com.backendframeworks.memeapi.services.pages.ListAllPagesUseCase;
import com.backendframeworks.memeapi.services.pages.RemovePageUseCase;
import com.backendframeworks.memeapi.services.pages.UpdatePageUseCase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pages")
public class PageController {

	@Autowired
	private CreatePageUseCase createPageUseCase;

	@Autowired
	private RemovePageUseCase removePageUseCase;

	@Autowired
	private UpdatePageUseCase updatePageUseCase;

	@Autowired
	private ListAllPagesUseCase listAllPagesUseCase;

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

	@PutMapping("/{id}")
	public ResponseEntity<Page> updatePage(@PathVariable UUID id, @RequestBody UpdatePageDto dto) {
		log.info("Updating page");
		Page updatedPage = updatePageUseCase.execute(id, dto);
		return ResponseEntity.status(HttpStatus.OK).body(updatedPage);
	}

	@GetMapping
	public ResponseEntity<List<Page>> fetchPages() {
		log.info("Listing pages");
		List<Page> pages = listAllPagesUseCase.execute();
		return ResponseEntity.status(HttpStatus.OK).body(pages);
	}
}

 */