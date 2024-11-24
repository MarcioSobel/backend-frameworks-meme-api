package com.backendframeworks.memeapi.exceptions.pages;

import com.backendframeworks.memeapi.exceptions.Conflict;

public class PageAlreadyExists extends Conflict {

	public PageAlreadyExists() {
		super("Page already exists");
	}
}
