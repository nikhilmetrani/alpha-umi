package io._29cu.usmserver.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.Rate;
import io._29cu.usmserver.core.repositories.RateRepository;
import io._29cu.usmserver.core.service.RateService;

@Component
public class RateServiceImpl implements RateService{
	
	@Autowired
	private RateRepository rateRepository;

	@Override
	public Rate createRate(Rate rate) {
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
	public Rate updateRating(Rate rate) {
		return rateRepository.save(rate);
	}

	
	
	

}
