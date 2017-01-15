package io._29cu.usmserver.controllers.rest;

/**
 * Created by yniu on 10/12/2016.
 */
import io._29cu.usmserver.controllers.rest.resources.ConsumerProfileResource;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ConsumerProfileResourceAssembler;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.ConsumerProfileService;
import io._29cu.usmserver.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URI;

@Controller
@RequestMapping("/api/0/consumer")
public class ConsumerProfileController {
    @Autowired
    private UserService userService;
    @Autowired
    private ConsumerProfileService consumerProfileService;

    // userId path variable imposes a security risk. Need to remove it.
    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public ResponseEntity<ConsumerProfileResource> consumerProfile() {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<ConsumerProfileResource>(HttpStatus.FORBIDDEN);
        try {
            User consumerProfile = consumerProfileService.findProfileByUserId(user.getId());
            if (null == consumerProfile) {
                consumerProfile = new User();           //Create an empty profile object
            }
            ConsumerProfileResource consumerProfileResource = new ConsumerProfileResourceAssembler().toResource(consumerProfile);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(consumerProfileResource.getLink("self").getHref()));
            return new ResponseEntity<ConsumerProfileResource>(consumerProfileResource, headers, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<ConsumerProfileResource>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/profile", method = RequestMethod.POST)
    public ResponseEntity<ConsumerProfileResource> createConsumerProfile(
            @RequestBody ConsumerProfileResource consumerProfileResource
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<ConsumerProfileResource>(HttpStatus.FORBIDDEN);
        try {
            User receivedProfile = consumerProfileResource.toEntity();
            User createdProfile = consumerProfileService.createProfile(receivedProfile);
            ConsumerProfileResource createdProfileResource = new ConsumerProfileResourceAssembler().toResource(createdProfile);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(createdProfileResource.getLink("self").getHref()));
            return new ResponseEntity<ConsumerProfileResource>(createdProfileResource, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ConsumerProfileResource>(HttpStatus.BAD_REQUEST);
        }
    }
}
