package io._29cu.usmserver.controllers.rest.resources.assemblers;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import io._29cu.usmserver.controllers.rest.ReviewReplyController;
import io._29cu.usmserver.controllers.rest.resources.ReviewReplyResource;
import io._29cu.usmserver.core.model.entities.ReviewReply;

public class ReviewReplyResourceAssembler extends ResourceAssemblerSupport<ReviewReply, ReviewReplyResource>{
	public ReviewReplyResourceAssembler(){
		super(ReviewReplyController.class, ReviewReplyResource.class);
	}
	
	@Override
	public ReviewReplyResource toResource(ReviewReply reviewReply) {
		ReviewReplyResource reviewReplyResource = new ReviewReplyResource();
		reviewReplyResource.setRid(reviewReply.getId().toString());
		reviewReplyResource.setDeveloper(reviewReply.getDeveloper());
		reviewReplyResource.setReviewId(reviewReply.getReview().getId().toString());
		reviewReplyResource.setDescription(reviewReply.getDescription());
		reviewReplyResource.setCreationDate(reviewReply.getCreationDate());
		reviewReplyResource.setCreateBy(reviewReply.getCreateBy());
		return reviewReplyResource;
	}

}
