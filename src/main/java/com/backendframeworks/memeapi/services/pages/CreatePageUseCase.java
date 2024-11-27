package com.backendframeworks.memeapi.services.pages;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.dtos.pages.CreatePageDto;
import com.backendframeworks.memeapi.exceptions.pages.PageAlreadyExists;
import com.backendframeworks.memeapi.exceptions.users.UserNotFoundError;
import com.backendframeworks.memeapi.models.Page;
import com.backendframeworks.memeapi.models.User;
import com.backendframeworks.memeapi.repositories.PageRepository;
import com.backendframeworks.memeapi.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CreatePageUseCase {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PageRepository pageRepository;

	public Page execute(CreatePageDto dto) throws UserNotFoundError, PageAlreadyExists {
		log.info("Checking if user exists...");
		Optional<User> user = userRepository.findById(dto.userId());
		if (user.isEmpty()) {
			log.error("userId is invalid, returning error");
			throw new UserNotFoundError();
		}

		log.info("userId is valid, found user:", user);

		log.info("Checking if page name is in use...");
		Page pageAlreadyExists = pageRepository.findByName(dto.name());
		if (pageAlreadyExists != null) {
			log.error("Page name already in use, returning error");
			throw new PageAlreadyExists();
		}

		log.info("Creating page...");
		Page page = new Page();
		page.setName(dto.name());

		Page savedPage = pageRepository.save(page);

		log.info("Associating page to user");
		user.get().getCreatedPages().add(page);
		userRepository.save(user.get());

		log.info("Page created, returning");
		return savedPage;
	}

}
