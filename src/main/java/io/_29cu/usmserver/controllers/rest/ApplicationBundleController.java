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

import io._29cu.usmserver.common.utilities.AppConstants;
import io._29cu.usmserver.controllers.rest.resources.*;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationBundleListResourceAssembler;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationBundleResourceAssembler;
import io._29cu.usmserver.core.model.entities.*;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.service.*;
import io._29cu.usmserver.core.service.utilities.ApplicationBundleList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/0/developer")
public class ApplicationBundleController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationBundleService applicationBundleService;
    private final Log logger = LogFactory.getLog(this.getClass());
    /**
     * Get all applications
     * @return The ApplicationBundleListResource found
     * @see ApplicationBundleListResource
     */
    @RequestMapping(path = "/applicationBundles", method = RequestMethod.GET)
    public ResponseEntity<ApplicationBundleListResource> getApplicationBundles(){
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        try {
            ApplicationBundleList appBundleList = applicationBundleService.findApplicationBundlesByDeveloper(user.getId());
            ApplicationBundleListResource appBundleListResource = new ApplicationBundleListResourceAssembler().toResource(appBundleList);
            return new ResponseEntity<>(appBundleListResource, HttpStatus.OK);
        } catch (Exception ex) {
        	logger.error(AppConstants.REQUEST_PROCCESS_ERROR,ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all ApplicationBundle by appBundleId
     * @param appBundleId The id of the ApplicationBundle to be search by
     * @return The ApplicationBundleResource found
     * @see ApplicationBundleResource
     */
    @RequestMapping(path = "/applicationBundles/{appBundleId}", method = RequestMethod.GET)
    public ResponseEntity<ApplicationBundleResource> getApplicationBundle(
            @PathVariable String appBundleId
    ){
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        try {
            ApplicationBundle applicationBundle = applicationBundleService.findApplicationBundleByDeveloperAndId(user.getId(), appBundleId);
            ApplicationBundleResource applicationBundleResource = new ApplicationBundleResourceAssembler().toResource(applicationBundle);
            return new ResponseEntity<>(applicationBundleResource, HttpStatus.OK);
        } catch (Exception ex) {
        	logger.error(AppConstants.REQUEST_PROCCESS_ERROR,ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Create ApplicationBundle
     * @param applicationBundleResource The ApplicationBundle to be created
     * @return The ApplicationBundleResource created
     * @see ApplicationBundleResource
     */
    @RequestMapping(path = "/applicationBundles/create", method = RequestMethod.POST)
    public ResponseEntity<ApplicationBundleResource> createDeveloperApplicationBundle(
            @RequestBody ApplicationBundleResource applicationBundleResource
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        ApplicationBundle receivedApplicationBundle = applicationBundleResource.toEntity();
        receivedApplicationBundle.setDeveloper(user);
        ApplicationBundle applicationBundle = applicationBundleService.createApplicationBundle(receivedApplicationBundle);
        ApplicationBundleResource createdApplicationBundleResource = new ApplicationBundleResourceAssembler().toResource(applicationBundle);
        return new ResponseEntity<>(createdApplicationBundleResource, HttpStatus.OK);
    }

    /**
     * Check whether the bundle name already exists under the same developer
     * @param name The bundle name to be checked
     * @return The ApplicationBundleResource with the bundle name under the same developer
     * @see ApplicationBundleResource
     */
    @RequestMapping(path = "/applicationBundles/create", method = RequestMethod.GET)
    public ResponseEntity<ApplicationBundleResource> checkApplicationBundleNameExistsForDeveloper(
            @RequestParam String name
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        ApplicationBundle existingAppBdl = applicationBundleService.findApplicationBundleByDeveloperAndName(user.getId(), name);
        if (existingAppBdl == null) {           //non-existing
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {                                //existing
        	return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    /**
     * Update ApplicationBundle
     * @param appBundleId The id of the ApplicationBundle to be updated
     * @param applicationBundleResource The updated ApplicationBundle
     * @return The updated ApplicationBundleResource
     * @see ApplicationBundleResource
     */
    @RequestMapping(path = "/applicationBundles/{appBundleId}/update", method = RequestMethod.POST)
    public ResponseEntity<ApplicationBundleResource> UpdateDeveloperApplicationBundle(
            @PathVariable String appBundleId,
            @RequestBody ApplicationBundleResource applicationBundleResource
    ) {
	    // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

	    ApplicationBundle applicationBundle = applicationBundleService.findApplicationBundleByDeveloperAndId(user.getId(), appBundleId);
	    if (applicationBundle == null)
		    return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);

	    ApplicationBundle receivedApplicationBundle = applicationBundleResource.toEntity();
	    receivedApplicationBundle.setDeveloper(user);
	    receivedApplicationBundle.setId(appBundleId);
	    applicationBundle = applicationBundleService.updateApplicationBundle(receivedApplicationBundle);
	    ApplicationBundleResource updatedApplicationBundleResource = new ApplicationBundleResourceAssembler().toResource(applicationBundle);
	    return new ResponseEntity<>(updatedApplicationBundleResource, HttpStatus.OK);
    }

    /**
     * Publish ApplicationBundle
     * @param appBundleId The id of the ApplicationBundle to be published
     * @return The ApplicationBundleResource that has been published
     * @see ApplicationBundleResource
     */
    @RequestMapping(path = "/applicationBundles/{appBundleId}/publish", method = RequestMethod.POST)
    public ResponseEntity<ApplicationBundleResource> publishDeveloperApplicationBundle(
            @PathVariable String appBundleId
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        try{
	        ApplicationBundle appBdl = applicationBundleService.findApplicationBundleByDeveloperAndId(user.getId(), appBundleId);

            // To traverse the list of applications and check if all are in 'active' status
            List<Application> appsInBundle = appBdl.getApplications();
            boolean allActive = true;
            if(!appBdl.getState().equals(AppState.Active)){
            	allActive = false;
            }else {
                for (Application app : appsInBundle) {
                    if (app.getState() != AppState.Active) {
                        allActive = false;
                    }
                }
            }

            // Publish bundle only if all applications within the bundle are 'active'
            if(allActive) {
                appBdl.setState(AppState.Active);                 //to set the bundle's status to 'Active'
                appBdl.setName(appBdl.getName());                 //to set the name of the bundle
                appBdl.setDescription(appBdl.getDescription());   //to add a description for the bundle
                ApplicationBundleResource newApplicationBundleResource = new ApplicationBundleResourceAssembler().toResource(appBdl);
                return new ResponseEntity<>(newApplicationBundleResource, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
            }
        }catch(Exception ex){
        	logger.error(AppConstants.REQUEST_PROCCESS_ERROR,ex);
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Recall ApplicationBundle
     * @param appBundleId The id of the ApplicationBundle to be recalled
     * @return The ApplicationBundleResource that has been successfully recalled
     * @see ApplicationBundleResource
     */
    @RequestMapping(path = "/applicationBundles/{appBundleId}/recall", method = RequestMethod.POST)
    public ResponseEntity<ApplicationBundleResource> recallDeveloperApplicationBundle(
            @PathVariable String appBundleId
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        try{
            ApplicationBundle applicationBundle = applicationBundleService.findApplicationBundleByDeveloperAndId(user.getId(), appBundleId);
            // If the applicationBundle state is 'Active'
            if(applicationBundle.getState().equals(AppState.Active)) {
                //get top 1 applicationBundle from history based on date (desc).

                //set the history applicationBundle value to applicationBundle

                //if there is no history app, then set the state to "Recalled"
                applicationBundle.setState(AppState.Recalled);

                //create new history app

                //return the applicationBundle
                ApplicationBundleResource createdApplicationBundleResource = new ApplicationBundleResourceAssembler().toResource(applicationBundle);
                return new ResponseEntity<>(createdApplicationBundleResource, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
            }
        }catch(Exception ex){
        	logger.error(AppConstants.REQUEST_PROCCESS_ERROR,ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
