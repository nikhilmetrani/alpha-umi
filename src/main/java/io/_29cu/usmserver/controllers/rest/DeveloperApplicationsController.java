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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io._29cu.usmserver.controllers.rest.resources.ApplicationListResource;
import io._29cu.usmserver.controllers.rest.resources.ApplicationResource;
import io._29cu.usmserver.controllers.rest.resources.ApplicationUpdateResource;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationListResourceAssembler;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationResourceAssembler;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationUpdateResourceAssembler;
import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.ApplicationUpdate;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.ApplicationUpdateService;
import io._29cu.usmserver.core.service.DeveloperProfileService;
import io._29cu.usmserver.core.service.UserService;
import io._29cu.usmserver.core.service.utilities.ApplicationList;

@Controller
@RequestMapping("/api/0/developer")
@EnableResourceServer
public class DeveloperApplicationsController {
    @Autowired
    private UserService userService;
    @Autowired
    private DeveloperProfileService developerProfileService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private ApplicationUpdateService applicationUpdateService;

    // Skeleton methods
    // Add similar methods for create, modify and publish updates
    // And publish application

    // Get all applications
    @RequestMapping(path = "/{userId}/applications", method = RequestMethod.GET)
    public ResponseEntity<ApplicationListResource> getApplications(
            @PathVariable Long userId
    ){
        // Let's get the user from principal and validate the userId against it.
        User user = userService.validateUserIdWithPrincipal(userId);
        if (user == null)
            return new ResponseEntity<ApplicationListResource>(HttpStatus.FORBIDDEN);
        try {
            ApplicationList appList = applicationService.findApplicationsByDeveloper(userId);
            ApplicationListResource appListResource = new ApplicationListResourceAssembler().toResource(appList);
            return new ResponseEntity<ApplicationListResource>(appListResource, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<ApplicationListResource>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get all applications
    @RequestMapping(path = "/{userId}/application/{appId}", method = RequestMethod.GET)
    public ResponseEntity<ApplicationResource> getApplication(
            @PathVariable Long userId, 
            @PathVariable String appId
    ){
        // Let's get the user from principal and validate the userId against it.
        User user = userService.validateUserIdWithPrincipal(userId);
        if (user == null)
            return new ResponseEntity<ApplicationResource>(HttpStatus.FORBIDDEN);
        try {
            Application application = applicationService.findApplicationByDeveloperAndId(userId, appId);
            ApplicationResource applicationResource = new ApplicationResourceAssembler().toResource(application);
            return new ResponseEntity<ApplicationResource>(applicationResource, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<ApplicationResource>(HttpStatus.BAD_REQUEST);
        }
    }

    // Create Application
    @RequestMapping(path = "/{userId}/application/create", method = RequestMethod.POST)
    public ResponseEntity<ApplicationResource> createDeveloperApplication(
            @PathVariable Long userId,
            @RequestBody ApplicationResource applicationResource
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.validateUserIdWithPrincipal(userId);
        if (user == null)
            return new ResponseEntity<ApplicationResource>(HttpStatus.FORBIDDEN);
        Application receivedApplication = applicationResource.toEntity();
        receivedApplication.setDeveloper(user); 
        Application application = applicationService.createApplication(receivedApplication); 
        ApplicationResource createdApplicationResource = new ApplicationResourceAssembler().toResource(application); 
        return new ResponseEntity<ApplicationResource>(createdApplicationResource, HttpStatus.OK);
    }

    // Create Application
    @RequestMapping(path = "/{userId}/application/create", method = RequestMethod.GET)
    public ResponseEntity<ApplicationResource> checkApplicationNameExistsForDeveloper(
            @PathVariable Long userId,
            @RequestParam String name
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.validateUserIdWithPrincipal(userId);
        if (user == null)
            return new ResponseEntity<ApplicationResource>(HttpStatus.FORBIDDEN);

        //Let's check whether the application is already registered.
        Application existingApp = applicationService.findApplicationByDeveloperAndName(userId, name);
        if (null == existingApp) { //We can't find the application in our database for the developer.
            return new ResponseEntity<ApplicationResource>(HttpStatus.NO_CONTENT);
        } else {
            // Application with same name already exists 
        	return new ResponseEntity<ApplicationResource>(HttpStatus.OK);
        }
    }

    // Create Application
    @RequestMapping(path = "/{userId}/application/{appId}/publish", method = RequestMethod.POST)
    public ResponseEntity<ApplicationUpdateResource> publishDeveloperApplication(
            @PathVariable Long userId,
            @PathVariable String appId,
            @RequestBody ApplicationUpdateResource applicationUpdateResource
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.validateUserIdWithPrincipal(userId);
        if (user == null)
            return new ResponseEntity<ApplicationUpdateResource>(HttpStatus.FORBIDDEN);

        try{
	        ApplicationUpdate applicationUpdate = applicationUpdateResource.toEntity();
	        Application application = applicationService.findApplicationByDeveloperAndId(userId, appId);
	        // If the application state is not 'blocked' AND there is no existing Application Update, then proceed for Publish
	        if(!application.getState().equals(AppState.Blocked) && applicationUpdateService.findByApplication(appId)==null) {
	        	application.setState(AppState.Active);
	        	application.setVersion(applicationUpdate.getVersion());
		        applicationUpdate.setApplication(application);
		        ApplicationUpdate newApplicationUpdate = applicationUpdateService.createApplicationUpdate(applicationUpdate); 
		        ApplicationUpdateResource newapplicationUpdateResource = new ApplicationUpdateResourceAssembler().toResource(newApplicationUpdate); 
		        return new ResponseEntity<ApplicationUpdateResource>(newapplicationUpdateResource, HttpStatus.OK);
	        }else{
	        	return new ResponseEntity<ApplicationUpdateResource>(HttpStatus.PRECONDITION_FAILED);
	        }
        }catch(Exception ex){
        	ex.printStackTrace();
        	return new ResponseEntity<ApplicationUpdateResource>(HttpStatus.BAD_REQUEST);
        }
    }

//
    // Get Application
//    @RequestMapping(path = "/{userId}/application/{appId}/updates", method = RequestMethod.GET)
//    public ResponseEntity<ApplicationResource> modifyDeveloperApplication(
//            @PathVariable Long userId,
//            @PathVariable Long appId,
//    ) {
//
//    }

    // Modify Application
//    @RequestMapping(path = "/{userId}/application/{appId}", method = RequestMethod.POST)
//    public ResponseEntity<ApplicationResource> modifyDeveloperApplication(
//            @PathVariable Long userId,
//            @PathVariable Long appId,
//            @RequestBody ApplicationResource applicationResource
//    ) {
//
//    }
}
