package com.backendframeworks.memeapi.services.pages;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.exceptions.pages.PageNotFound;
import com.backendframeworks.memeapi.exceptions.users.UserNotFoundError;
import com.backendframeworks.memeapi.models.Page;
import com.backendframeworks.memeapi.models.User;
import com.backendframeworks.memeapi.models.UserFollowPage;
import com.backendframeworks.memeapi.repositories.PageRepository;
import com.backendframeworks.memeapi.repositories.UserFollowPageRepository;
import com.backendframeworks.memeapi.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FollowPageUseCase {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PageRepository pageRepository;

	@Autowired
	private UserFollowPageRepository userFollowPageRepository;

	public void execute(UUID userId, UUID pageId) throws UserNotFoundError, PageNotFound {
		log.info("Checking if user exists...");
		Optional<User> user = userRepository.findById(userId);
		if (user.isEmpty()) {
			log.error("User does not exist, returning error");
			throw new UserNotFoundError();
		}

		log.info("User is valid");
		log.info("Checking if page exists...");
		Optional<Page> page = pageRepository.findById(pageId);
		if (page.isEmpty()) {
			log.error("Page does not exist, returning error");
			throw new PageNotFound();
		}

		log.info("Check if user already follows page");
		Optional<UserFollowPage> userAlreadyFollowPage = userFollowPageRepository.findByUserIdAndPageId(userId, pageId);
		if (userAlreadyFollowPage.isPresent()) {
			log.info("User already follows page, no action needed.");
			return;
		}

		log.info("Page is valid");
		log.info("Creating relation");
		UserFollowPage userFollowPage = new UserFollowPage();
		userFollowPage.setPage(page.get());
		userFollowPage.setUser(user.get());

		userFollowPageRepository.save(userFollowPage);
	}
}
