package io._29cu.usmserver.core.service;

import java.util.List;

import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.Rate;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.Rating;

@Component
public interface RateService {
	
	public List<Rate> findRatingsByApplicationId(String applicationId);
	
	public Rate createRate(Rate rate, Application application, User user);
	
	public Rate findRating(Long rateId);

	public int countRatingsByApplicationId(String applicationId, Rating rating);

	public Rate checkUserRate(String applicationId, Long userId);
	
}
