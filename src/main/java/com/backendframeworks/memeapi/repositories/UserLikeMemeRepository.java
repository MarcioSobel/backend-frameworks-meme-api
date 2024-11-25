package com.backendframeworks.memeapi.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendframeworks.memeapi.models.UserLikeMeme;

public interface UserLikeMemeRepository extends JpaRepository<UserLikeMeme, Long> {

	public void deleteByMemeId(UUID memeId);

	public void deleteByUserId(UUID userId);

	public void deleteByUserIdAndMemeId(UUID userId, UUID memeId);

	public List<UserLikeMeme> findAllByMemeId(UUID memeId);

	public List<UserLikeMeme> findAllByUserId(UUID userId);

	public Optional<UserLikeMeme> findByUserIdAndMemeId(UUID userId, UUID memeId);
}
