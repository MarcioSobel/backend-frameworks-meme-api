package com.backendframeworks.memeapi.services.memes;

import java.util.Optional;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.exceptions.memes.MemeNotFound;
import com.backendframeworks.memeapi.exceptions.users.UserNotFoundError;
import com.backendframeworks.memeapi.models.Meme;
import com.backendframeworks.memeapi.models.User;
import com.backendframeworks.memeapi.repositories.MemeRepository;
import com.backendframeworks.memeapi.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class DislikeMemeUseCase {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MemeRepository memeRepository;

	public void execute(UUID memeId, UUID userId) {
		log.info("Checking if userId is valid...");
		Optional<User> userOpt = userRepository.findById(userId);
		if (userOpt.isEmpty()) {
			log.error("User not found, returning error");
			throw new UserNotFoundError();
		}
		User user = userOpt.get();
		Hibernate.initialize(user.getLikedMemes());

		log.info("User is valid");
		log.info("Checking if meme is valid...");
		Optional<Meme> memeOpt = memeRepository.findById(memeId);
		if (memeOpt.isEmpty()) {
			log.error("Meme not found, returning error");
			throw new MemeNotFound();
		}
		Meme meme = memeOpt.get();

		log.info("Meme is valid");
		log.info("Checking if user already liked meme");
		Boolean userAlreadyLikedMeme = user.getLikedMemes().contains(meme);
		if (!userAlreadyLikedMeme) {
			log.info("User does not have a like record on this meme, no action needed");
			return;
		}

		log.info("Deleting relation");
		user.getLikedMemes().remove(meme);
		userRepository.save(user);
	}
}
