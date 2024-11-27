package com.backendframeworks.memeapi.models;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
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

	@ManyToMany(mappedBy = "likedMemes", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	private Set<User> likes;

	@ManyToOne
	@JoinColumn(name = "page_id", nullable = false)
	private Page page;

	@OneToMany(mappedBy = "meme", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Comment> comments;

	@PrePersist
	private void onCreate() {
		Instant now = Instant.now();
		createdAt = now;
	}

	@PreUpdate
	private void onUpdate() {
		updatedAt = Instant.now();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Meme meme = (Meme) o;
		return Objects.equals(id, meme.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@PreRemove
	private void preRemove() {
		for (User user : likes) {
			user.getLikedMemes().remove(this);
		}
	}
}
