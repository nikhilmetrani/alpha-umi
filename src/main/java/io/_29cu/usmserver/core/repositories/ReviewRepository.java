package io._29cu.usmserver.core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import io._29cu.usmserver.core.model.entities.Review;

public interface ReviewRepository extends CrudRepository<Review, Long> {
	
  @Query("select r from Review r where r.application.id=:applicationId and r.consumer.id=:consumerId") 
  public Review findReviewByApplicationIdAndConsumerId(String applicationId,Long consumerId); 
	
  @Query("select r from Review r where r.application.id=:applicationId")
  public List<Review> findReviewsByApplicationId(String applicationId);
  
  @Query("select r from Review r where r.consumer.id=:consumerId")
  public List<Review> findReviewsByConsumerId(Long consumerId);
    

}
