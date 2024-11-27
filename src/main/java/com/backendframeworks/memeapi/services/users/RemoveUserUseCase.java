package com.backendframeworks.memeapi.services.users;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backendframeworks.memeapi.exceptions.users.UserNotFoundError;
import com.backendframeworks.memeapi.exceptions.users.UserNotFoundException; // Nome corrigido
import com.backendframeworks.memeapi.models.User;
import com.backendframeworks.memeapi.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class RemoveUserUseCase {

    @Autowired
    private UserRepository userRepository;

    public void execute(UUID userId) {
        if (userId == null) {
            log.error("UserId is null. Cannot proceed with deletion.");
            throw new IllegalArgumentException("UserId cannot be null");
        }

        try {
            log.info("Deleting user relations for userId: {}", userId);
            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()) {
                throw new UserNotFoundError();
            }

            user.get().getCreatedPages().clear();
            user.get().getLikedMemes().clear();
            user.get().getFollowedPages().clear();

            log.info("Deleting user with ID: {}", userId);
            userRepository.deleteById(userId);

            log.info("User deleted successfully: {}", userId);
        } catch (EmptyResultDataAccessException e) {
            log.error("User with ID {} not found during deletion.", userId);
            throw new UserNotFoundException("User with ID " + userId + " does not exist.", e);
        } catch (Exception e) {
            log.error("Unexpected error while deleting user with ID {}: {}", userId, e.getMessage());
            throw new RuntimeException("Error while deleting user with ID: " + userId, e);
        }
    }
}