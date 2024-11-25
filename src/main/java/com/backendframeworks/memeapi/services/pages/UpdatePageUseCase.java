package com.backendframeworks.memeapi.services.pages;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.dtos.pages.UpdatePageDto;
import com.backendframeworks.memeapi.exceptions.pages.PageNotFound;
import com.backendframeworks.memeapi.models.Page;
import com.backendframeworks.memeapi.repositories.PageRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UpdatePageUseCase {

	@Autowired
	private PageRepository pageRepository;

	public Page execute(UUID id, UpdatePageDto dto) throws PageNotFound {
		log.info("Searching for page...");
		Optional<Page> page = pageRepository.findById(id);
		if (page.isEmpty()) {
			log.error("Page doesn't exist, returning error");
			throw new PageNotFound();
		}

		log.info("Page exists, updating...");
		Page updatedPage = page.get();
		BeanUtils.copyProperties(dto, updatedPage);

		Page savedPage = pageRepository.save(updatedPage);

		log.info("Page updated, returning");
		return savedPage;
	}
}
