package io._29cu.usmserver.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.Rate;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.Rating;
import io._29cu.usmserver.core.repositories.RateRepository;
import io._29cu.usmserver.core.service.RateService;

@Component
public class RateServiceImpl implements RateService{
	
	@Autowired
	private RateRepository rateRepository;

	@Override
	public Rate createRate(Rate rate, Application application,User user) {
		Rate rateInDB = checkUserRate(application.getId(),user.getId());
		if(rateInDB != null) {
			rate.setId(rateInDB.getId());
		}
		rate.setApplication(application);
		rate.setConsumer(user);
		return rateRepository.save(rate);
	}

	@Override
	public List<Rate> findRatingsByApplicationId(String applicationId) {
		return rateRepository.findRatingsByApplicationId(applicationId);
	}

	@Override
	public Rate findRating(Long rateId) {
		return rateRepository.findOne(rateId);
	}

	@Override
	public int countRatingsByApplicationId(String applicationId, Rating rating) {
		return rateRepository.countRatingsByApplicationId(applicationId, rating);
	}

	@Override
	public Rate checkUserRate(String applicationId, Long userId) {
		return rateRepository.checkUserRate(applicationId, userId);
	}
}
