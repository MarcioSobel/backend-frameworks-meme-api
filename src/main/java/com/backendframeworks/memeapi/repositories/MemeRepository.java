package com.backendframeworks.memeapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backendframeworks.memeapi.models.Meme;

@Repository
public interface MemeRepository extends JpaRepository<Meme, UUID> {

}
