package io._29cu.usmserver.controllers.rest;

import io._29cu.usmserver.controllers.rest.resources.RatingResource;
import io._29cu.usmserver.controllers.rest.resources.assemblers.RatingResourceAssembler;
import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.Rate;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.RateService;
import io._29cu.usmserver.core.service.ReportService;
import io._29cu.usmserver.core.service.UserService;
import io._29cu.usmserver.core.service.utilities.ApplicationList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/api/0/developer/report")
public class ReportController {
	
	@Autowired
    private UserService userService;
	@Autowired
	private ReportService reportService;
	@Autowired
    private ApplicationService applicationService;
	
	@RequestMapping(path = "/subscriptions/{appId}/{start}/{end}", method = RequestMethod.GET)
    public ResponseEntity<Integer> findSubscribedUsersPerApplication(
			@PathVariable String appId,
    		@PathVariable String start,
			@PathVariable String end
    ) {
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<Integer>(HttpStatus.FORBIDDEN);
		
        Application application = applicationService.findApplication(appId);
        if(application == null){
        	 return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
        }
		try {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			int count = reportService.findSubscribedUsersPerApplication(appId, df.parse(start), df.parse(end));
			return new ResponseEntity<Integer>(count, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
    }

	@RequestMapping(path = "/activesubscriptions/{appId}/{start}/{end}", method = RequestMethod.GET)
	public ResponseEntity<Integer> findActiveSubscribedUsersPerApplication(
			@PathVariable String appId,
			@PathVariable String start,
			@PathVariable String end
	) {
		User user = userService.findAuthenticatedUser();
		if (user == null)
			return new ResponseEntity<Integer>(HttpStatus.FORBIDDEN);

		Application application = applicationService.findApplication(appId);
		if(application == null){
			return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		}
		try {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			int count = reportService.findSubscribedActiveUsersPerApplication(appId, df.parse(start), df.parse(end));
			return new ResponseEntity<Integer>(count, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(path = "/terminatedsubscriptions/{appId}/{start}/{end}", method = RequestMethod.GET)
	public ResponseEntity<Integer> findTerminatedSubscribedUsersPerApplication(
			@PathVariable String appId,
			@PathVariable String start,
			@PathVariable String end
	) {
		User user = userService.findAuthenticatedUser();
		if (user == null)
			return new ResponseEntity<Integer>(HttpStatus.FORBIDDEN);

		Application application = applicationService.findApplication(appId);
		if(application == null){
			return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		}
		try {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			int count = reportService.findTerminatedSubscriptionsPerApplication(appId, df.parse(start), df.parse(end));
			return new ResponseEntity<Integer>(count, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
	}
}
