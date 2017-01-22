package io._29cu.usmserver.controllers.rest;

import io._29cu.usmserver.core.model.enumerations.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io._29cu.usmserver.controllers.rest.resources.RatingResource;
import io._29cu.usmserver.controllers.rest.resources.assemblers.RatingResourceAssembler;
import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.Rate;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.RateService;
import io._29cu.usmserver.core.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Controller
@RequestMapping("")
public class RatingController {
	
	@Autowired
    private UserService userService;
	@Autowired
	private RateService rateService;
	@Autowired
    private ApplicationService applicationService;

    private final Log logger = LogFactory.getLog(this.getClass());

    /**
     * Create application review
     * @param applicationId The id of the application
     * @param rateResource The rate to be created
     * @return
     * @see RatingResource
     */
	@RequestMapping(path = "/api/0/consumer/{applicationId}/rating/create", method = RequestMethod.POST)
    public ResponseEntity<RatingResource> createReview(
    		@PathVariable String applicationId,
    		@RequestBody RatingResource rateResource
    ) {
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
        Rate rate = rateResource.toEntity();
        Application application = applicationService.findApplication(applicationId);
        if(application == null){
        	 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }       
        rate = rateService.createRate(rate,application,user);
        RatingResource createdRateResource = new RatingResourceAssembler().toResource(rate);
        return new ResponseEntity<>(createdRateResource,HttpStatus.CREATED);
    }

	/**
	 * Get number of likes
	 * @param applicationId The id of the application
	 * @param likeType Rating.Like or Rating.Dislike
	 * @return
	 */
	@RequestMapping(path = "/api/1/consumer/{applicationId}/rating/getRateLikeNum/{likeType}", method = RequestMethod.GET)
    public ResponseEntity<String> getRateLikeNumber(
            @PathVariable String applicationId,
            @PathVariable String likeType
    ) {      
        Application application = applicationService.findApplication(applicationId);
        if(application == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("likeType = " + likeType);

        Rating rating = Rating.Dislike;
        if("0".equals(likeType)){
            rating = Rating.Like;
        }
        logger.info("rating type = " + rating.toString());

        int likeNum = rateService.countRatingsByApplicationId(applicationId, rating);
        logger.info("likeNum = " + Integer.toString(likeNum));
        return new ResponseEntity<>(Integer.toString(likeNum),HttpStatus.OK);
    }

	/**
	 * Check user rate
	 * @param applicationId The id of the application
	 * @return
	 */
    @RequestMapping(path = "/api/0/consumer/{applicationId}/rating/checkUserRate", method = RequestMethod.GET)
    public ResponseEntity<String> checkUserRate(
            @PathVariable String applicationId
    ) {
        logger.info("Application ID = " + applicationId);
        String appRate = "2";
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Application application = applicationService.findApplication(applicationId);
        if(application == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Rate rate = rateService.checkUserRate(applicationId, user.getId());
        if(rate != null){
            appRate = Integer.toString(rate.getRating().ordinal());
            logger.info("appRate = " + appRate);
        }
        return new ResponseEntity<>(appRate,HttpStatus.OK);
    }
}
