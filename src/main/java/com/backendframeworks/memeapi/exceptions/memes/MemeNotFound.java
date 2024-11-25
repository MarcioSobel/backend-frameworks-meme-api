package com.backendframeworks.memeapi.exceptions.memes;

import com.backendframeworks.memeapi.exceptions.NotFound;

public class MemeNotFound extends NotFound {
	public MemeNotFound() {
		super("Meme not found");
	}
}
