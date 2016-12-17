package io._29cu.usmserver.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.Review;
import io._29cu.usmserver.core.repositories.ReviewRepository;
import io._29cu.usmserver.core.service.ReviewService;
import io._29cu.usmserver.core.service.exception.ReviewDoesNotExistException;

@Component
public class ReviewServiceImpl implements ReviewService{
	
	@Autowired
	public ReviewRepository reviewRepository;


	@Override
	public Review createReview(Review review) {
		return reviewRepository.save(review);
	}

	@Override
	public void removeReview(Long reviewId) throws ReviewDoesNotExistException {
		if(reviewRepository.exists(reviewId)){
			reviewRepository.delete(reviewId);
		}else{
			throw new ReviewDoesNotExistException("Review Does not exist");
		}		
	}

	@Override
	public void featureReview(Long reviewId) {
		Review review = findReview(reviewId);
		if (!review.isFeatured()) {
			review.setFeatured(true);
			reviewRepository.save(review);
		}
	}

	@Override
	public List<Review> findReviewsByApplicationId(String applicationId) {
		return reviewRepository.findReviewsByApplicationId(applicationId);
	}

	@Override
	public Review findReview(Long reviewId) {
		return reviewRepository.findOne(reviewId);
	}

	@Override
	public void unfeatureReview(Long reviewId) {
		Review review = findReview(reviewId);
		if (review.isFeatured()) {
			review.setFeatured(false);
			reviewRepository.save(review);
		}		
	}


}
