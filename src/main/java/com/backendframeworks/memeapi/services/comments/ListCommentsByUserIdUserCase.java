package com.backendframeworks.memeapi.services.comments;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.exceptions.users.UserNotFoundError;
import com.backendframeworks.memeapi.models.Comment;
import com.backendframeworks.memeapi.repositories.CommentsRepository;
import com.backendframeworks.memeapi.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class ListCommentsByUserIdUserCase {


    @Autowired
    private CommentsRepository commentsRepository;

	public List<Comment> execute(UUID userId) throws UserNotFoundError {
		Boolean userExists = UserRepository.existsById(userId);
		if (!userExists) {
			throw new UserNotFoundError();
		}

		List<Comments> UserComments = CommentsRepository.findAllByUserId(userId);
		
	List<Comments> CommentsbyId = UserComments.stream().filter().toList();
	return CommentsbyId;
	
	}
    
}

/* 



	List<UserFollowPage> relations = userFollowPageRepository.findAllByUserId(userId);
	List<Page> pages = relations.stream().map(r -> r.getPage()).toList();
	return pages;
}
}
*/