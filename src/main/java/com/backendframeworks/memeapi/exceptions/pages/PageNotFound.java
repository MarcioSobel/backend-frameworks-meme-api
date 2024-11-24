package com.backendframeworks.memeapi.exceptions.pages;

import com.backendframeworks.memeapi.exceptions.NotFound;

public class PageNotFound extends NotFound {
	public PageNotFound() {
		super("Page not found");
	}
}
