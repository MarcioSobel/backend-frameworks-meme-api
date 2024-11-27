package com.backendframeworks.memeapi.services.pages;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backendframeworks.memeapi.exceptions.pages.PageNotFound;
import com.backendframeworks.memeapi.exceptions.users.UserNotFoundError;
import com.backendframeworks.memeapi.models.Page;
import com.backendframeworks.memeapi.models.User;
import com.backendframeworks.memeapi.repositories.PageRepository;
import com.backendframeworks.memeapi.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class UnfollowPageUseCase {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PageRepository pageRepository;

	public void execute(UUID userId, UUID pageId) throws UserNotFoundError, PageNotFound {
		log.info("Checking if user exists...");
		Optional<User> userOpt = userRepository.findById(userId);
		if (userOpt.isEmpty()) {
			log.error("User does not exist, returning error");
			throw new UserNotFoundError();
		}
		User user = userOpt.get();

		log.info("User is valid");
		log.info("Checking if page exists...");
		Optional<Page> pageOpt = pageRepository.findById(pageId);
		if (pageOpt.isEmpty()) {
			log.error("Page does not exist, returning error");
			throw new PageNotFound();
		}
		Page page = pageOpt.get();

		log.info("Check if user already follows page");
		Boolean userAlreadyFollowsPage = user.getFollowedPages().contains(page);
		if (userAlreadyFollowsPage) {
			log.info("User already follows page, no action needed.");
			return;
		}

		log.info("Page is valid");
		log.info("Creating relation");
		user.getFollowedPages().remove(page);
		userRepository.save(user);
	}
}
