package com.backendframeworks.memeapi.services.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.exceptions.pages.PageNotFound;
import com.backendframeworks.memeapi.models.Page;
import com.backendframeworks.memeapi.repositories.PageRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GetPageByNameUseCase {

	@Autowired
	private PageRepository pageRepository;

	public Page execute(String name) {
		Page page = pageRepository.findByName(name);
		if (page == null) {
			throw new PageNotFound();
		}

		return page;
	}
}
