package io._29cu.usmserver.controllers.rest;

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

@Controller
@RequestMapping("/api/0/consumer/{applicationId}/rating")
public class RatingController {
	
	@Autowired
    private UserService userService;
	@Autowired
	private RateService rateService;
	@Autowired
    private ApplicationService applicationService;
	
	@RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseEntity<RatingResource> createReview(
    		@PathVariable String applicationId,
    		@RequestBody RatingResource rateResource
    ) {
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<RatingResource>(HttpStatus.FORBIDDEN);
		
        Rate rate = rateResource.toEntity();
        Application application = applicationService.findApplication(applicationId);
        if(application == null){
        	 return new ResponseEntity<RatingResource>(HttpStatus.NOT_FOUND);
        }       
        rate = rateService.createRate(rate,application,user);
        RatingResource createdRateResource = new RatingResourceAssembler().toResource(rate);
        return new ResponseEntity<RatingResource>(createdRateResource,HttpStatus.CREATED);
    }

}
