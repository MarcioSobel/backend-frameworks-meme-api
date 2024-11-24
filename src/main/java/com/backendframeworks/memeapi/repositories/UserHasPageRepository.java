package com.backendframeworks.memeapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendframeworks.memeapi.models.UserHasPage;

public interface UserHasPageRepository extends JpaRepository<UserHasPage, Long> {

}
