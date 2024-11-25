package com.backendframeworks.memeapi.services.memes;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.exceptions.memes.MemeNotFound;
import com.backendframeworks.memeapi.exceptions.users.UserNotFoundError;
import com.backendframeworks.memeapi.models.Meme;
import com.backendframeworks.memeapi.models.User;
import com.backendframeworks.memeapi.models.UserLikeMeme;
import com.backendframeworks.memeapi.repositories.MemeRepository;
import com.backendframeworks.memeapi.repositories.UserLikeMemeRepository;
import com.backendframeworks.memeapi.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UnlikeMemeUseCase {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MemeRepository memeRepository;

	@Autowired
	private UserLikeMemeRepository userLikeMemeRepository;

	public void execute(UUID memeId, UUID userId) {
		log.info("Checking if userId is valid...");
		Optional<User> user = userRepository.findById(userId);
		if (user.isEmpty()) {
			log.error("User not found, returning error");
			throw new UserNotFoundError();
		}

		log.info("User is valid");
		log.info("Checking if meme is valid...");
		Optional<Meme> meme = memeRepository.findById(memeId);
		if (meme.isEmpty()) {
			log.error("Meme not found, returning error");
			throw new MemeNotFound();
		}

		log.info("Meme is valid");
		log.info("Checking if user already liked meme");
		Optional<UserLikeMeme> alreadyLiked = userLikeMemeRepository.findByUserIdAndMemeId(userId, memeId);
		if (alreadyLiked.isPresent()) {
			log.info("User does not have a like record on this meme, no action needed");
			return;
		}

		log.info("Deleting relation");
		userLikeMemeRepository.deleteById(alreadyLiked.get().getId());
	}
}
