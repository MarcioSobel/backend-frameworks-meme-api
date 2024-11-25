package com.backendframeworks.memeapi.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendframeworks.memeapi.models.UserFollowPage;

public interface UserFollowPageRepository extends JpaRepository<UserFollowPage, Long> {

	public void deleteByPageId(UUID pageId);

	public void deleteByUserId(UUID userId);

	public void deleteByUserIdAndPageId(UUID userId, UUID pageId);

	public List<UserFollowPage> findAllByPageId(UUID pageId);

	public List<UserFollowPage> findAllByUserId(UUID userId);

	public Optional<UserFollowPage> findByUserIdAndPageId(UUID userId, UUID pageId);

}
