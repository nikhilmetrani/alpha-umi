package io._29cu.usmserver.core.service;

import java.util.List;

import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.ReviewReply;

@Component
public interface ReviewReplyService {
	
	public List<ReviewReply> findReviewRepliesByReviewId(Long reviewId);
	
	public ReviewReply createReviewReply(ReviewReply reviewReply);
	
	public void removeReviewReply(Long reviewReplyId);
	
	public ReviewReply findReviewReply(Long reviewReplyId);

}
