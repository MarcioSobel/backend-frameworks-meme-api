package com.backendframeworks.memeapi.configuration;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backendframeworks.memeapi.exceptions.Unauthorized;
import com.backendframeworks.memeapi.exceptions.auth.InvalidCredentials;
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
			throws ServletException, IOException, InvalidCredentials {

		String path = request.getRequestURI();
		if (EXCLUDED_PATHS.contains(path) && request.getMethod().equals(HttpMethod.POST.name())) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = readToken(request);

		if (token != null) {
			try {
				String email = tokenService.validateToken(token);
				Optional<UserDetails> user = userRepository.findByEmail(email);
				if (user.isEmpty()) {
					throw new InvalidCredentials();
				}

				var authentication = new UsernamePasswordAuthenticationToken(user, null, user.get().getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (Unauthorized e) {
				handleInvalidTokenException(response, e);
			}
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

	private void handleInvalidTokenException(HttpServletResponse response, Unauthorized e) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(e.getMessage());
		response.getWriter().flush();
	}
}
