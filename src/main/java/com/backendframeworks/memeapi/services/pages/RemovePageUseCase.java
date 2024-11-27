package com.backendframeworks.memeapi.services.pages;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backendframeworks.memeapi.exceptions.pages.PageNotFound;
import com.backendframeworks.memeapi.repositories.PageRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class RemovePageUseCase {

	@Autowired
	private PageRepository pageRepository;

	public void execute(UUID id) throws PageNotFound {
		log.info("Checking if page exists...");
		Boolean pageExists = pageRepository.existsById(id);
		if (!pageExists) {
			log.error("Page does not exists, returning error");
			throw new PageNotFound();
		}

		log.info("Deleting page...");
		pageRepository.deleteById(id);

		log.info("Page deleted, returning");
	}
}
