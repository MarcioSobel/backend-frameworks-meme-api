package com.backendframeworks.memeapi.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backendframeworks.memeapi.models.Comment;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, UUID> {

    public List<Comment> findAllByUserId(UUID userId);
}