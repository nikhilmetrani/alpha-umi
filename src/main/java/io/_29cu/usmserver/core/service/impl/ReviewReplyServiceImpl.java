package io._29cu.usmserver.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.ReviewReply;
import io._29cu.usmserver.core.repositories.ReviewReplyRepository;
import io._29cu.usmserver.core.service.ReviewReplyService;

@Component
public class ReviewReplyServiceImpl implements ReviewReplyService {
	
	@Autowired
	private ReviewReplyRepository reviewReplyRepository;


	@Override
	public ReviewReply createReviewReply(ReviewReply reviewReply) {
		return reviewReplyRepository.save(reviewReply);
	}

	@Override
	public void removeReviewReply(Long reviewReplyId) {
		reviewReplyRepository.delete(reviewReplyId);
	}

	@Override
	public List<ReviewReply> findReviewRepliesByReviewId(Long reviewId) {
		return reviewReplyRepository.findReviewRepliesByReviewId(reviewId);
	}

	@Override
	public ReviewReply findReviewReply(Long reviewReplyId) {
		return reviewReplyRepository.findOne(reviewReplyId);
		
	}


}
