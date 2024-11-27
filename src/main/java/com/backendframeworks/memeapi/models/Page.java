// Page.java
package com.backendframeworks.memeapi.models;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pages")
public class Page {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false, unique = true)
	private String name;

	@ManyToMany(mappedBy = "createdPages", fetch = FetchType.LAZY)
	private Set<User> createdByUsers;

	@ManyToMany(mappedBy = "followedPages", fetch = FetchType.LAZY)
	private Set<User> followedByUsers;

	@OneToMany(mappedBy = "page", cascade = { CascadeType.REMOVE }, orphanRemoval = true)
	private Set<Meme> memes;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Page page = (Page) o;
		return Objects.equals(id, page.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@PreRemove
	private void preRemove() {
		for (User user : createdByUsers) {
			user.getCreatedPages().remove(this);
		}
		for (User user : followedByUsers) {
			user.getFollowedPages().remove(this);
		}
	}

}
