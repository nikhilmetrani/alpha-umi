package io._29cu.usmserver.controllers.rest;

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.ReportService;
import io._29cu.usmserver.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/api/0/developer/report")
public class ReportController {
	
	@Autowired
    private UserService userService;
	@Autowired
	private ReportService reportService;
	@Autowired
    private ApplicationService applicationService;

	/**
	 * Find subscribed users for application
	 * @param appId The id of the application
	 * @param start The start date
	 * @param end The end date
	 * @return
	 */
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
			int count = reportService.findSubscriptionsPerApplication(appId, df.parse(start), df.parse(end));
			return new ResponseEntity<Integer>(count, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
    }

	/**
	 * Find active subscribed users for application
	 * @param appId The id of the application
	 * @param start The start date
	 * @param end The end date
	 * @return
	 */
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
			int count = reportService.findActiveSubscriptionsPerApplication(appId, df.parse(start), df.parse(end));
			return new ResponseEntity<Integer>(count, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Find terminated subscribed users for application
	 * @param appId The id of the application
	 * @param start The start date
	 * @param end The end date
	 * @return
	 */
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
