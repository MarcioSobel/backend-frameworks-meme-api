package com.backendframeworks.memeapi.services.pages;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.exceptions.users.UserNotFoundError;
import com.backendframeworks.memeapi.models.Page;
import com.backendframeworks.memeapi.models.User;
import com.backendframeworks.memeapi.repositories.UserRepository;

@Service
public class ListPagesFollowedByUserUseCase {

	@Autowired
	private UserRepository userRepository;

	public List<Page> execute(UUID userId) throws UserNotFoundError {
		Optional<User> user = userRepository.findById(userId);
		if (user.isEmpty()) {
			throw new UserNotFoundError();
		}

		List<Page> pages = user.get().getFollowedPages().stream().toList();
		return pages;
	}
}
