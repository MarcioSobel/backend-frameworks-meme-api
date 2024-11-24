package com.backendframeworks.memeapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendframeworks.memeapi.models.UserHasPage;

public interface UserHasPageRepository extends JpaRepository<UserHasPage, Long> {

	public void deleteByPageId(UUID pageId);

	public void deleteByUserId(UUID userId);
}
