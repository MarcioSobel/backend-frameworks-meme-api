package com.backendframeworks.memeapi.services.auth;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.backendframeworks.memeapi.exceptions.Unauthorized;
import com.backendframeworks.memeapi.exceptions.users.UserNotFoundError;
import com.backendframeworks.memeapi.models.User;
import com.backendframeworks.memeapi.repositories.UserRepository;

@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	private String secret;

	@Autowired
	private UserRepository userRepository;

	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);

			return JWT.create().withIssuer("meme-api")
					.withSubject(user.getEmail())
					.withExpiresAt(getExpirationTime())
					.sign(algorithm);
		} catch (JWTCreationException e) {
			throw new RuntimeException(e);
		}
	}

	public String validateToken(String token) throws Unauthorized {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);

			return JWT.require(algorithm)
					.withIssuer("meme-api")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException e) {
			throw new Unauthorized("Invalid token!");
		}
	}

	public User getUserByToken(String token) throws UserNotFoundError, Unauthorized {
		String email = validateToken(getTokenCore(token));
		Optional<UserDetails> userDetails = userRepository.findByEmail(email);
		if (userDetails.isEmpty()) {
			throw new UserNotFoundError();
		}

		return (User) userDetails.get();
	}

	public String getTokenCore(String token) {
		return token.replace("Bearer ", "");
	}

	private Instant getExpirationTime() {
		return LocalDateTime
				.now()
				.plusDays(7)
				.toInstant(ZoneOffset.of("-03:00"));
	}
}
