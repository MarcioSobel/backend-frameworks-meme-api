package com.backendframeworks.memeapi.services.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.exceptions.auth.InvalidCredentials;
import com.backendframeworks.memeapi.repositories.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, InvalidCredentials {
		Optional<UserDetails> user = userRepository.findByEmail(email);
		if (user.isEmpty()) {
			throw new InvalidCredentials();
		}

		return user.get();
	}
}
