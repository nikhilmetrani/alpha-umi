package io._29cu.usmserver.core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.ReviewReply;

@Component
public interface ReviewReplyRepository extends CrudRepository<ReviewReply, Long>{
	
	 @Query("select rr From ReviewReply rr where rr.review.id = :reviewId")
	 public List<ReviewReply> findReviewRepliesByReviewId(Long reviewId);
	 
	 @Query("select rr.developer.username from ReviewReply rr where rr.id = :reviewReplyId ")
	 public String getUserNameFromReviewReply(Long reviewReplyId);

}
