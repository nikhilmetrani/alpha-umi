package io._29cu.usmserver.core.service;

import java.util.List;

import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.Review;
import io._29cu.usmserver.core.service.exception.ReviewDoesNotExistException;

@Component
public interface ReviewService {
	
	public List<Review> findReviewsByApplicationId(String applicationId);
	
	public Review createReview(Review review);
	
	public void removeReview(Long reviewId) throws ReviewDoesNotExistException;
	
	public void featureReview(Long reviewId);
	
	public void unfeatureReview(Long reviewId);
	
	public Review findReview(Long reviewId);
}
