package io._29cu.usmserver.core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.Rate;
import io._29cu.usmserver.core.model.enumerations.Rating;

@Component
public interface RateRepository extends CrudRepository<Rate, Long>{
	
	  @Query("select r from Rate r where r.application.id=:applicationId")
	  public List<Rate> findRatingsByApplicationId(@Param("applicationId") String applicationId);
	  
	  @Query("select r from Rate r where r.consumer.id=:consumerId")
	  public List<Rate> findRatingsByConsumerId(@Param("consumerId") Long consumerId);
	  
	  @Query("select count(r) from Rate r where r.application.id=:applicationId and r.rating = :rating")
	  public int countRatingsByApplicationId(@Param("applicationId") String applicationId,@Param("rating") Rating rating);

}
