package com.backendframeworks.memeapi.services.users;

import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backendframeworks.memeapi.exceptions.users.UserNotFoundException; // Nome corrigido
import com.backendframeworks.memeapi.repositories.UserRepository;
import com.backendframeworks.memeapi.repositories.UserFollowPageRepository;
import com.backendframeworks.memeapi.repositories.UserHasPageRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class RemoveUserUseCase {

    private final UserRepository userRepository;
    private final UserHasPageRepository userHasPageRepository;
    private final UserFollowPageRepository userFollowPageRepository;

    public RemoveUserUseCase(
        UserRepository userRepository,
        UserHasPageRepository userHasPageRepository,
        UserFollowPageRepository userFollowPageRepository
    ) {
        this.userRepository = userRepository;
        this.userHasPageRepository = userHasPageRepository;
        this.userFollowPageRepository = userFollowPageRepository;
    }

    public void execute(UUID userId) {
        if (userId == null) {
            log.error("UserId is null. Cannot proceed with deletion.");
            throw new IllegalArgumentException("UserId cannot be null");
        }

        try {
            log.info("Deleting user relations for userId: {}", userId);
            userHasPageRepository.deleteByUserId(userId);
            userFollowPageRepository.deleteByUserId(userId);

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