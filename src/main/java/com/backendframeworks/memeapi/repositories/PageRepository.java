package com.backendframeworks.memeapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backendframeworks.memeapi.models.Page;

@Repository
public interface PageRepository extends JpaRepository<Page, UUID> {

	public Page findByName(String name);
}
