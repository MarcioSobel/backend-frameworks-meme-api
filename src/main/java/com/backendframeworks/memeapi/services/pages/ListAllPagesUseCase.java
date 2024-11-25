package com.backendframeworks.memeapi.services.pages;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.models.Page;
import com.backendframeworks.memeapi.repositories.PageRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ListAllPagesUseCase {

	@Autowired
	private PageRepository pageRepository;

	public List<Page> execute() {
		List<Page> pages = pageRepository.findAll();
		return pages;
	}
}
