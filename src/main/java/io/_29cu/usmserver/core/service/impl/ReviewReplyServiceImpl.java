package io._29cu.usmserver.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.Review;
import io._29cu.usmserver.core.model.entities.ReviewReply;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.repositories.ReviewReplyRepository;
import io._29cu.usmserver.core.service.ReviewReplyService;
import io._29cu.usmserver.core.service.exception.ReviewReplyDoesNotExistException;

@Component
public class ReviewReplyServiceImpl implements ReviewReplyService {
	
	@Autowired
	private ReviewReplyRepository reviewReplyRepository;


	@Override
	public ReviewReply createReviewReply(ReviewReply reviewReply,Review review,User user) {
		reviewReply.setReview(review);
		reviewReply.setDeveloper(user);
		reviewReply.setCreateBy(user.getUsername());
		return reviewReplyRepository.save(reviewReply);
	}

	@Override
	public void removeReviewReply(Long reviewReplyId) throws ReviewReplyDoesNotExistException {
		if(reviewReplyRepository.exists(reviewReplyId)){
		reviewReplyRepository.delete(reviewReplyId);
		}else{
			throw new ReviewReplyDoesNotExistException("Review Reply Does not exist");
		}
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
