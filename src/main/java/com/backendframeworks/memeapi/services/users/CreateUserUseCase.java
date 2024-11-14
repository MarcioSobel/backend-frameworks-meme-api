package com.backendframeworks.memeapi.services.users;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.dtos.users.CreateUserDto;
import com.backendframeworks.memeapi.exceptions.users.UserAlreadyExistsError;
import com.backendframeworks.memeapi.models.User;
import com.backendframeworks.memeapi.repositories.UserRepository;

@Service
public class CreateUserUseCase {

	@Autowired
	private UserRepository userRepository;

	public User execute(CreateUserDto userDto) {
		User userAlreadyExists = userRepository.findByEmail(userDto.email());
		if (userAlreadyExists != null) {
			throw new UserAlreadyExistsError();
		}

		User user = new User();
		BeanUtils.copyProperties(userDto, user);

		String hashedPassword = new BCryptPasswordEncoder().encode(userDto.password());
		user.setPassword(hashedPassword);

		User savedUser = userRepository.save(user);
		return savedUser;
	}
}
