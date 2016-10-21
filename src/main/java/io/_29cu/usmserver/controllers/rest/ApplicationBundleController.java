/**
 * Copyright 2016 - 29cu.io and the authors of alpha-umi open source project

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 *     http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package io._29cu.usmserver.controllers.rest;

import io._29cu.usmserver.controllers.rest.resources.*;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationBundleListResourceAssembler;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationBundleResourceAssembler;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationListResourceAssembler;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationResourceAssembler;
import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.ApplicationBundle;
import io._29cu.usmserver.core.model.entities.ApplicationUpdate;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.service.*;
import io._29cu.usmserver.core.service.utilities.ApplicationBundleList;
import io._29cu.usmserver.core.service.utilities.ApplicationList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/0/developer")
@EnableResourceServer
public class ApplicationBundleController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationBundleService applicationBundleService;
    @Autowired
    private ApplicationUpdateService applicationUpdateService;

    // Skeleton methods
    // Add similar methods for create, update and publish updates
    // And publish application

    // Get all applications
    @RequestMapping(path = "/{userId}/applicationBundles", method = RequestMethod.GET)
    public ResponseEntity<ApplicationBundleListResource> getApplicationBundles(
            @PathVariable String userId
    ){
        // Let's get the user from principal and validate the userId against it.
        User user = userService.validateUserIdWithPrincipal(userId);
        if (user == null)
            return new ResponseEntity<ApplicationBundleListResource>(HttpStatus.FORBIDDEN);
        try {
            ApplicationBundleList appBundleList = applicationBundleService.findApplicationBundlesByDeveloper(userId);
            ApplicationBundleListResource appBundleListResource = new ApplicationBundleListResourceAssembler().toResource(appBundleList);
            return new ResponseEntity<ApplicationBundleListResource>(appBundleListResource, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<ApplicationBundleListResource>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get all applications
    @RequestMapping(path = "/{userId}/applicationBundles/{appBundleId}", method = RequestMethod.GET)
    public ResponseEntity<ApplicationBundleResource> getApplicationBundle(
            @PathVariable String userId,
            @PathVariable String appBundleId
    ){
        // Let's get the user from principal and validate the userId against it.
        User user = userService.validateUserIdWithPrincipal(userId);
        if (user == null)
            return new ResponseEntity<ApplicationBundleResource>(HttpStatus.FORBIDDEN);
        try {
            ApplicationBundle applicationBundle = applicationBundleService.findApplicationBundleByDeveloperAndId(userId, appBundleId);
            ApplicationBundleResource applicationBundleResource = new ApplicationBundleResourceAssembler().toResource(applicationBundle);
            return new ResponseEntity<ApplicationBundleResource>(applicationBundleResource, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<ApplicationBundleResource>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get all applications
    @RequestMapping(value = "/{appBundleId}", method = RequestMethod.GET)
    public ResponseEntity<ApplicationBundleResource> getApplicationBundle(
            @PathVariable String appBundleId
    ) {
        ApplicationBundle applicationBundle = applicationBundleService.findApplicationBundle(appBundleId);
        if (null == applicationBundle)
            return new ResponseEntity<ApplicationBundleResource>(HttpStatus.NOT_FOUND);

        ApplicationBundleResource applicationBundleResource = new ApplicationBundleResourceAssembler().toResource(applicationBundle);
        return new ResponseEntity<ApplicationBundleResource>(applicationBundleResource, HttpStatus.OK);
    }

    // Create Application
    @RequestMapping(path = "/{userId}/applicationBundles/create", method = RequestMethod.POST)
    public ResponseEntity<ApplicationBundleResource> createDeveloperApplicationBundle(
            @PathVariable String userId,
            @RequestBody ApplicationBundleResource applicationBundleResource
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.validateUserIdWithPrincipal(userId);
        if (user == null)
            return new ResponseEntity<ApplicationBundleResource>(HttpStatus.FORBIDDEN);
        ApplicationBundle receivedApplicationBundle = applicationBundleResource.toEntity();
        receivedApplicationBundle.setDeveloper(user);
        ApplicationBundle applicationBundle = applicationBundleService.createApplicationBundle(receivedApplicationBundle);
        ApplicationBundleResource createdApplicationBundleResource = new ApplicationBundleResourceAssembler().toResource(applicationBundle);
        return new ResponseEntity<ApplicationBundleResource>(createdApplicationBundleResource, HttpStatus.OK);
    }

    // Create Application
    @RequestMapping(path = "/{userId}/applicationBundles/create", method = RequestMethod.GET)
    public ResponseEntity<ApplicationBundleResource> checkApplicationBundleNameExistsForDeveloper(
            @PathVariable String userId,
            @RequestParam String name
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.validateUserIdWithPrincipal(userId);
        if (user == null)
            return new ResponseEntity<ApplicationBundleResource>(HttpStatus.FORBIDDEN);

        //Let's check whether the applicationBundle is already registered.
        ApplicationBundle existingApp = applicationBundleService.findApplicationBundleByDeveloperAndName(userId, name);
        if (null == existingApp) { //We can't find the applicationBundle in our database for the developer.
            return new ResponseEntity<ApplicationBundleResource>(HttpStatus.NO_CONTENT);
        } else {
            // ApplicationBundle with same name already exists 
        	return new ResponseEntity<ApplicationBundleResource>(HttpStatus.OK);
        }
    }

    // Update ApplicationBundle
    @RequestMapping(path = "/{userId}/applicationBundles/{appBundleId}/update", method = RequestMethod.POST)
    public ResponseEntity<ApplicationBundleResource> UpdateDeveloperApplicationBundle(
            @PathVariable String userId,
            @PathVariable String appBundleId,
            @RequestBody ApplicationBundleResource applicationBundleResource
    ) {
	    // Let's get the user from principal and validate the userId against it.
	    User user = userService.validateUserIdWithPrincipal(userId);
	    if (user == null)
		    return new ResponseEntity<ApplicationBundleResource>(HttpStatus.FORBIDDEN);

	    ApplicationBundle applicationBundle = applicationBundleService.findApplicationBundleByDeveloperAndId(userId, appBundleId);
	    if (applicationBundle == null)
		    return new ResponseEntity<ApplicationBundleResource>(HttpStatus.PRECONDITION_FAILED);

	    ApplicationBundle receivedApplicationBundle = applicationBundleResource.toEntity();
	    receivedApplicationBundle.setDeveloper(user);
	    receivedApplicationBundle.setId(appBundleId);
	    applicationBundle = applicationBundleService.updateApplicationBundle(receivedApplicationBundle);
	    ApplicationBundleResource createdApplicationBundleResource = new ApplicationBundleResourceAssembler().toResource(applicationBundle);
	    return new ResponseEntity<ApplicationBundleResource>(createdApplicationBundleResource, HttpStatus.OK);
    }

    // Publish ApplicationBundle
    @RequestMapping(path = "/{userId}/applicationBundles/{appBundleId}/publish", method = RequestMethod.POST)
    public ResponseEntity<ApplicationBundleResource> publishDeveloperApplicationBundle(
            @PathVariable String userId,
            @PathVariable String appBundleId
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.validateUserIdWithPrincipal(userId);
        if (user == null)
            return new ResponseEntity<ApplicationBundleResource>(HttpStatus.FORBIDDEN);

        try{
	        ApplicationBundle applicationBundle = applicationBundleService.findApplicationBundleByDeveloperAndId(userId, appBundleId);
	        // If the applicationBundle state is not 'blocked' AND there is no existing ApplicationBundle Update, then proceed for Publish
	        if(!applicationBundle.getState().equals(AppState.Blocked)) {
	        	applicationBundle.setState(AppState.Active);
                applicationBundle.setDescription(applicationBundle.getDescription());
                applicationBundle.setName(applicationBundle.getName());
		        ApplicationBundleResource newApplicationBundleResource = new ApplicationBundleResourceAssembler().toResource(applicationBundle);
		        return new ResponseEntity<ApplicationBundleResource>(newApplicationBundleResource, HttpStatus.OK);
	        }else{
	        	return new ResponseEntity<ApplicationBundleResource>(HttpStatus.PRECONDITION_FAILED);
	        }
        }catch(Exception ex){
        	ex.printStackTrace();
        	return new ResponseEntity<ApplicationBundleResource>(HttpStatus.BAD_REQUEST);
        }
    }

    // Recall ApplicationBundle
    @RequestMapping(path = "/{userId}/applicationBundle/{appBundleId}/recall", method = RequestMethod.POST)
    public ResponseEntity<ApplicationBundleResource> recallDeveloperApplicationBundle(
            @PathVariable String userId,
            @PathVariable String appBundleId
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.validateUserIdWithPrincipal(userId);
        if (user == null)
            return new ResponseEntity<ApplicationBundleResource>(HttpStatus.FORBIDDEN);

        try{
            //ApplicationBundleUpdate applicationBundleUpdate = ApplicationBundleResource.toEntity();
            ApplicationBundle applicationBundle = applicationBundleService.findApplicationBundleByDeveloperAndId(userId, appBundleId);
            // If the applicationBundle state is 'Active'
            if(applicationBundle.getState().equals(AppState.Active)) {
                //get top 1 applicationBundle from history based on date (desc).

                //set the history applicationBundle value to applicationBundle

                //if there is no history app, then set the state to "Recalled"
                applicationBundle.setState(AppState.Recalled);

                //create new history app

                //return the applicationBundle
                ApplicationBundleResource createdApplicationBundleResource = new ApplicationBundleResourceAssembler().toResource(applicationBundle);
                return new ResponseEntity<ApplicationBundleResource>(createdApplicationBundleResource, HttpStatus.OK);
            }else{
                return new ResponseEntity<ApplicationBundleResource>(HttpStatus.PRECONDITION_FAILED);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<ApplicationBundleResource>(HttpStatus.BAD_REQUEST);
        }
    }
}