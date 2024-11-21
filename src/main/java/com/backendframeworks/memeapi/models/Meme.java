package com.backendframeworks.memeapi.models;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "memes")
public class Meme {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = true)
	private String description;

	@Column(nullable = false)
	private String imgUrl;

	@Column(nullable = false)
	private Instant createdAt;

	@Column(nullable = true)
	private Instant updatedAt;

	@PrePersist
	private void onCreate() {
		Instant now = Instant.now();
		createdAt = now;
	}

	@PreUpdate
	private void onUpdate() {
		updatedAt = Instant.now();
	}
}
