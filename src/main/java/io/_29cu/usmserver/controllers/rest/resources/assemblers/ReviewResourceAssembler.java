package io._29cu.usmserver.controllers.rest.resources.assemblers;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import io._29cu.usmserver.controllers.rest.ReviewController;
import io._29cu.usmserver.controllers.rest.resources.ReviewResource;
import io._29cu.usmserver.core.model.entities.Review;

public class ReviewResourceAssembler extends ResourceAssemblerSupport<Review, ReviewResource>{
	public ReviewResourceAssembler(){
		super(ReviewController.class, ReviewResource.class);
	}
	
	@Override
	public ReviewResource toResource(Review review) {
		ReviewResource reviewResource = new ReviewResource();
		reviewResource.setRid(review.getId().toString());
		reviewResource.setConsumer(review.getConsumer());
		reviewResource.setApplicationId(review.getApplication().getId());
		reviewResource.setTitle(review.getTitle());
		reviewResource.setDescription(review.getDescription());
		reviewResource.setFeatured(review.isFeatured());
		reviewResource.setCreationDate(review.getCreationDate());
		reviewResource.setCreateBy(review.getCreateBy());
		return reviewResource;
	}

}
