package com.backendframeworks.memeapi.configuration;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backendframeworks.memeapi.repositories.UserRepository;
import com.backendframeworks.memeapi.services.auth.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	private static final List<String> EXCLUDED_PATHS = List.of("/auth", "/register");

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserRepository userRepository;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {

		String path = request.getRequestURI();
		if (EXCLUDED_PATHS.contains(path) && request.getMethod().equals(HttpMethod.POST.name())) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = readToken(request);

		if (token != null) {
			String email = tokenService.validateToken(token);
			UserDetails user = userRepository.findByEmail(email);

			var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

	private String readToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null) {
			return null;
		}
		return authHeader.replace("Bearer ", "");
	}
}
