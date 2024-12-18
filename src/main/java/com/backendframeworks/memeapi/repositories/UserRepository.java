package com.backendframeworks.memeapi.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.backendframeworks.memeapi.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<UserDetails> findByEmail(String email);

	Optional<User> findByHandle(String handle);
}
