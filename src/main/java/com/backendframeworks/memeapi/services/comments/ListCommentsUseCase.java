package com.backendframeworks.memeapi.services.comments;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.models.Comment;
import com.backendframeworks.memeapi.repositories.CommentsRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ListCommentsUseCase {

	@Autowired
    private CommentsRepository commentsRepository;

	public List<Comment> execute(){
		List<Comment> comments = commentsRepository.findAll();
		return comments;
	}
}
