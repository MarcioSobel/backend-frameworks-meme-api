package com.backendframeworks.memeapi.services.comments;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.dtos.comments.CreateCommentDto;
import com.backendframeworks.memeapi.exceptions.memes.MemeNotFound;
import com.backendframeworks.memeapi.exceptions.users.UserNotFoundError;
import com.backendframeworks.memeapi.models.Comment;
import com.backendframeworks.memeapi.models.Meme;
import com.backendframeworks.memeapi.models.User;
import com.backendframeworks.memeapi.repositories.CommentsRepository;
import com.backendframeworks.memeapi.repositories.MemeRepository;
import com.backendframeworks.memeapi.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class CommentMemeUseCase {

	@Autowired
	private CommentsRepository CommentsRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private  MemeRepository memeRepository;

    public Comment execute(CreateCommentDto dto) throws UserNotFoundError, MemeNotFound{
		log.info("Checking if user exists...");
		Optional<User> user = userRepository.findById(dto.userId());
		if (user.isEmpty()) {
			log.error("userId is invalid, returning error");
			throw new UserNotFoundError();
		}
		log.info("userId is valid, found user:", user);


		log.info("Checking if meme exists...");
		Optional<Meme> meme = memeRepository.findById(dto.userId());
		if (meme.isEmpty()) {
			log.error("meme is invalid, returning error");
			throw new MemeNotFound();
		}
		

		log.info("Creating Comment...");
		Comment comment = new Comment();
		comment.setComment(dto.name());

		Comment savedComment = CommentsRepository.save(comment);

		
		return savedComment;
	}	 
	}
	