package io._29cu.usmserver.core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.Review;

@Component
public interface ReviewRepository extends CrudRepository<Review, Long> {
	
  @Query("select r from Review r where r.application.id=:applicationId")
  public List<Review> findReviewsByApplicationId(String applicationId);
  
  @Query("select r from Review r where r.consumer.id=:consumerId")
  public List<Review> findReviewsByConsumerId(Long consumerId);
    
  @Query("select r.consumer.username from Review r where r.id = :reviewId ")
  public String getUserNameFromReview(Long reviewId);
}
