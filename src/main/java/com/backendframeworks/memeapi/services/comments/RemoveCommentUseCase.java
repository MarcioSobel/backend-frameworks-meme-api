package com.backendframeworks.memeapi.services.comments;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backendframeworks.memeapi.exceptions.memes.MemeNotFound;
import com.backendframeworks.memeapi.repositories.CommentsRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class RemoveCommentUseCase {

	@Autowired
	private CommentsRepository commentRepository;

	public void execute(UUID id) throws MemeNotFound {
		log.info("Checking if meme exists...");
		Boolean commentExists = commentRepository.existsById(id);
		if (!commentExists) {
			log.error("Meme does not exists, returning error");
			throw new MemeNotFound();
		}

		log.info("Deleting comment...");
		commentRepository.deleteById(id);

		log.info("Comment deleted, returning");
	}
}