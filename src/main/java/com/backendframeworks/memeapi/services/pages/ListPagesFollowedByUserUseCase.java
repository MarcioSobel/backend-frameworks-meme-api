package com.backendframeworks.memeapi.services.pages;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.exceptions.users.UserNotFoundError;
import com.backendframeworks.memeapi.models.Page;
import com.backendframeworks.memeapi.models.UserFollowPage;
import com.backendframeworks.memeapi.repositories.UserFollowPageRepository;
import com.backendframeworks.memeapi.repositories.UserRepository;

@Service
public class ListPagesFollowedByUserUseCase {

	@Autowired
	private UserFollowPageRepository userFollowPageRepository;

	@Autowired
	private UserRepository userRepository;

	public List<Page> execute(UUID userId) throws UserNotFoundError {
		Boolean userExists = userRepository.existsById(userId);
		if (!userExists) {
			throw new UserNotFoundError();
		}

		List<UserFollowPage> relations = userFollowPageRepository.findAllByUserId(userId);
		List<Page> pages = relations.stream().map(r -> r.getPage()).toList();
		return pages;
	}
}
