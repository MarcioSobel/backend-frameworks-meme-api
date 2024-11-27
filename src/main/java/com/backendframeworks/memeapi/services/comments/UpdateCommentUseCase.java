package com.backendframeworks.memeapi.services.comments;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.dtos.comments.UpdateCommentDto;
import com.backendframeworks.memeapi.exceptions.memes.MemeNotFound;
import com.backendframeworks.memeapi.models.Comment;
import com.backendframeworks.memeapi.repositories.CommentsRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UpdateCommentUseCase {

	@Autowired
	private CommentsRepository commentsRepository;

	public Comment execute(UUID id, UpdateCommentDto dto) throws MemeNotFound {
		log.info("Searching for page...");
		Optional<Comment> comment = commentsRepository.findById(id);
		if (comment.isEmpty()) {
			log.error("Meme doesn't exist, returning error");
			throw new MemeNotFound();
		}

		log.info("Meme exists, updating comment...");
		Comment updatedComment = comment.get();
		BeanUtils.copyProperties(dto, updatedComment);

		Comment savedComment = commentsRepository.save(updatedComment);

		log.info("Comment updated, returning");
		return savedComment;
	}
}
