package io._29cu.usmserver.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io._29cu.usmserver.controllers.rest.resources.ReviewResource;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ReviewResourceAssembler;
import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.Review;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.ReviewService;
import io._29cu.usmserver.core.service.UserService;

@Controller
@RequestMapping("/api/0/consumer/{applicationId}/review")
public class ReviewController {
	@Autowired
    private UserService userService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
    private ApplicationService applicationService;
	
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseEntity<ReviewResource> createReview(
    		@PathVariable String applicationId,
    		@RequestBody ReviewResource reviewResource
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<ReviewResource>(HttpStatus.FORBIDDEN);
		
        Review review = reviewResource.toEntity();
        Application application = applicationService.findApplication(applicationId);
        if(application == null){
        	 return new ResponseEntity<ReviewResource>(HttpStatus.NOT_FOUND);
        }       
        review = reviewService.createReview(review,application,user);
        ReviewResource createdReviewResource = new ReviewResourceAssembler().toResource(review);
        return new ResponseEntity<ReviewResource>(createdReviewResource,HttpStatus.CREATED);
    }

}
