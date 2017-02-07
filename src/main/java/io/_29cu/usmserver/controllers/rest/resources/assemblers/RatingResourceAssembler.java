package io._29cu.usmserver.controllers.rest.resources.assemblers;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import io._29cu.usmserver.controllers.rest.RatingController;
import io._29cu.usmserver.controllers.rest.resources.RatingResource;
import io._29cu.usmserver.core.model.entities.Rate;

public class RatingResourceAssembler extends ResourceAssemblerSupport<Rate,RatingResource> {
	public RatingResourceAssembler(){
		super(RatingController.class, RatingResource.class);
	}

	@Override
	public RatingResource toResource(Rate rate) {
		RatingResource ratingResource = new RatingResource(); 
		ratingResource.setRid(rate.getId().toString());
		ratingResource.setApplicationId(rate.getApplication().getId());
		ratingResource.setConsumer(rate.getConsumer());
		ratingResource.setCreateBy(rate.getCreateBy());
		ratingResource.setCreationDate(rate.getCreationDate());
		ratingResource.setRating(rate.getRating());
		return ratingResource;
	}
}
