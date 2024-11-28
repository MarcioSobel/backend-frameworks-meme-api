package com.backendframeworks.memeapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backendframeworks.memeapi.dtos.users.CreateUserDto;
import com.backendframeworks.memeapi.exceptions.users.UserAlreadyExistsError;
import com.backendframeworks.memeapi.models.User;
import com.backendframeworks.memeapi.repositories.UserRepository;
import com.backendframeworks.memeapi.services.users.CreateUserUseCase;

@ExtendWith(MockitoExtension.class)
class CreateUserUserCaseTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private CreateUserUseCase createUserUseCase;

	@Test
	void shouldBeAbleToCreateUser() {
		User user = createUser();
		when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

		CreateUserDto dto = new CreateUserDto(user.getName(), user.getHandle(), user.getEmail(), user.getPassword());
		User returnedUser = createUserUseCase.execute(dto);

		assertEquals(returnedUser.getId(), user.getId());
	}

	@Test
	void shouldNotBeAbleToCreateUserIfEmailIsAlreadyInUse() {
		User existingUser = createUser();
		when(userRepository.findByEmail(any())).thenReturn(Optional.of(existingUser));

		CreateUserDto dto = new CreateUserDto("user", "handle", existingUser.getEmail(), "password");

		assertThrows(UserAlreadyExistsError.class, () -> createUserUseCase.execute(dto));
	}

	private User createUser() {
		UUID uuid = UUID.randomUUID();
		User user = new User();
		user.setEmail("test@email.com");
		user.setHandle("testHandle");
		user.setName("test");
		user.setId(uuid);
		user.setPassword("password");

		return user;
	}
}
