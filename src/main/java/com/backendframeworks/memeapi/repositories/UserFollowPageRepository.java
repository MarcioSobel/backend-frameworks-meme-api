package com.backendframeworks.memeapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendframeworks.memeapi.models.UserFollowPage;

public interface UserFollowPageRepository extends JpaRepository<UserFollowPage, Long> {

	public void deleteByPageId(UUID pageId);

	public void deleteByUserId(UUID userId);
}
