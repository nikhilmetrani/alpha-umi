package io._29cu.usmserver.core.service;

import java.util.List;

import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.Review;
import io._29cu.usmserver.core.model.entities.ReviewReply;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.exception.ReviewReplyDoesNotExistException;

@Component
public interface ReviewReplyService {
	
	public List<ReviewReply> findReviewRepliesByReviewId(Long reviewId);
	
	public ReviewReply createReviewReply(ReviewReply reviewReply, Review review, User user);
	
	public void removeReviewReply(Long reviewReplyId) throws ReviewReplyDoesNotExistException;
	
	public ReviewReply findReviewReply(Long reviewReplyId);

}
