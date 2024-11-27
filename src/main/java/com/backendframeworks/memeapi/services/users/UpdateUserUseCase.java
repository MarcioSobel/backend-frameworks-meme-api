package com.backendframeworks.memeapi.services.users;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.dtos.users.UpdateUserDto;
import com.backendframeworks.memeapi.exceptions.users.EmailAlreadyInUseException;
import com.backendframeworks.memeapi.exceptions.users.UserNotFoundError;
import com.backendframeworks.memeapi.models.User;
import com.backendframeworks.memeapi.repositories.UserRepository;

@Service
public class UpdateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    public User execute(UUID id, UpdateUserDto dto) {
        // Verifica se o usuário existe
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isEmpty()) {
            throw new UserNotFoundError();
        }
        User existingUser = existingUserOpt.get();

        // Verifica se o e-mail já está em uso por outro usuário
        Optional<UserDetails> userWithEmail = userRepository.findByEmail(dto.getEmail());
        if (userWithEmail.isPresent()) {
            throw new EmailAlreadyInUseException("O e-mail já está em uso por outro usuário.");
        }

        // Atualiza os campos permitidos
        BeanUtils.copyProperties(dto, existingUser, "id", "password");

        // Atualiza a senha se fornecida
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            String hashedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
            existingUser.setPassword(hashedPassword);
        }

        // Salva e retorna o usuário atualizado
        return userRepository.save(existingUser);
    }
}
