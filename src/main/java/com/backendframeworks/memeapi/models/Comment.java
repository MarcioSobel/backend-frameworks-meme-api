package com.backendframeworks.memeapi.models;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "comments")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false)
	private String comment;

	@Column(nullable = false)
	private Instant createdAt;

	@Column(nullable = true)
	private Instant updatedAt;

	@ManyToOne
	@JoinColumn(name = "meme_id", nullable = false)
	private Meme meme;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

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
