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

import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationUpdateResourceAssembler;
import io._29cu.usmserver.core.model.entities.*;
import io._29cu.usmserver.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.ArrayList;

import io._29cu.usmserver.controllers.rest.resources.ApplicationListResource;
import io._29cu.usmserver.controllers.rest.resources.ApplicationResource;
import io._29cu.usmserver.controllers.rest.resources.ApplicationUpdateResource;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationListResourceAssembler;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationResourceAssembler;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.service.utilities.ApplicationList;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/api/0/developer")
//@EnableResourceServer
public class DeveloperApplicationsController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private ApplicationHistoryService applicationHistoryService;
    @Autowired
    private ApplicationUpdateService applicationUpdateService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private StorageService storageService;

    // Skeleton methods
    // Add similar methods for create, update and publish updates
    // And publish application

    // Get all applications
    @RequestMapping(path = "/applications", method = RequestMethod.GET)
    public ResponseEntity<ApplicationListResource> getApplications(){
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<ApplicationListResource>(HttpStatus.FORBIDDEN);
        try {
            ApplicationList appList = applicationService.findApplicationsByDeveloper(user.getId());
            ApplicationListResource appListResource = new ApplicationListResourceAssembler().toResource(appList);
            return new ResponseEntity<ApplicationListResource>(appListResource, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<ApplicationListResource>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get all applications
    @RequestMapping(path = "/applications/{appId}", method = RequestMethod.GET)
    public ResponseEntity<ApplicationResource> getApplication(
            @PathVariable String appId
    ){
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<ApplicationResource>(HttpStatus.FORBIDDEN);
        try {
            Application application = applicationService.findApplicationByDeveloperIdAndAppId(user.getId(), appId);
            ApplicationResource applicationResource = new ApplicationResourceAssembler().toResource(application);
            return new ResponseEntity<ApplicationResource>(applicationResource, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<ApplicationResource>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/applications/{appId}/image", method = RequestMethod.POST)
    public ResponseEntity<String> uploadApplicationLogo(
            @PathVariable String appId,
            @RequestParam("file") MultipartFile file) {
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        try {
            Path generatedFile = storageService.storeApplicationLogo(file, user.getId(), appId);
            String response = "{'originalName': '" + file.getOriginalFilename() + "', 'generatedName': '" + generatedFile.getFileName() + "'}";
            response = response.replace("'", "\"");
            ResponseEntity<String> responseEntity = new ResponseEntity<String>(response, HttpStatus.OK);
            return responseEntity;
        } catch (Exception ex) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/applications/{appId}/image", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> getApplicationLogo(
            @PathVariable String appId) {
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<Resource>(HttpStatus.FORBIDDEN);

        try {
        Resource file = storageService.loadApplicationLogoAsResource(user.getId(), appId);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .header(HttpHeaders.CONTENT_TYPE, "image/png")
                .body(file);
        } catch (Exception ex) {
            // Maybe the image is not yet uploaded, so let's just return ok.
            return new ResponseEntity<Resource>(HttpStatus.OK);
        }
    }

    // Create Application
    @RequestMapping(path = "/applications/create", method = RequestMethod.POST)
    public ResponseEntity<ApplicationResource> createDeveloperApplication(
            @RequestBody ApplicationResource applicationResource
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<ApplicationResource>(HttpStatus.FORBIDDEN);
        Application receivedApplication = applicationResource.toEntity();
        receivedApplication.setDeveloper(user);
        receivedApplication.setCategory(categoryService.findCategory(Long.valueOf(receivedApplication.getCategory().getName())));
        receivedApplication.setState(AppState.Staging);
        Application application = applicationService.createApplication(receivedApplication);
        ApplicationResource createdApplicationResource = new ApplicationResourceAssembler().toResource(application); 
        return new ResponseEntity<ApplicationResource>(createdApplicationResource, HttpStatus.OK);
    }

    // Create Application
    @RequestMapping(path = "/applications/check", method = RequestMethod.GET)
    public ResponseEntity<ApplicationResource> checkApplicationNameExistsForDeveloper(
            @RequestParam String name
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<ApplicationResource>(HttpStatus.FORBIDDEN);

        //Let's check whether the application is already registered.
        Application existingApp = applicationService.findApplicationByDeveloperIdAndAppName(user.getId(), name);
        if (null == existingApp) { //We can't find the application in our database for the developer.
            return new ResponseEntity<ApplicationResource>(HttpStatus.NO_CONTENT);
        } else {
            // Application with same name already exists 
        	return new ResponseEntity<ApplicationResource>(HttpStatus.OK);
        }
    }

    // Create Application
    @RequestMapping(path = "/applications/publish", method = RequestMethod.POST)
    public ResponseEntity<ApplicationResource> createAndPublishDeveloperApplication(
            @RequestBody ApplicationResource applicationResource
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<ApplicationResource>(HttpStatus.FORBIDDEN);
        Application receivedApplication = applicationResource.toEntity();
        receivedApplication.setDeveloper(user);
        receivedApplication.setCategory(categoryService.findCategory(Long.valueOf(receivedApplication.getCategory().getName())));
        receivedApplication.setState(AppState.Active);
        receivedApplication.setApplicationPublishDate(Calendar.getInstance().getTime());
        Application application = applicationService.createApplication(receivedApplication);
        ApplicationResource createdApplicationResource = new ApplicationResourceAssembler().toResource(application); 
        return new ResponseEntity<ApplicationResource>(createdApplicationResource, HttpStatus.OK);
    }

    // Update Application
    @RequestMapping(path = "/applications/{appId}/update", method = RequestMethod.POST)
    public ResponseEntity<ApplicationResource> updateDeveloperApplication(
            @PathVariable String appId,
            @RequestBody ApplicationResource applicationResource
    ) {
	    // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<ApplicationResource>(HttpStatus.FORBIDDEN);
	    Application application = applicationService.findApplicationByDeveloperIdAndAppId(user.getId(), appId);
	    if (application == null)
		    return new ResponseEntity<ApplicationResource>(HttpStatus.PRECONDITION_FAILED);

	    Application receivedApplication = applicationResource.toEntity();
	    receivedApplication.setDeveloper(user);
	    receivedApplication.setId(appId);
        String  categoryName = receivedApplication.getCategory().getName();
        receivedApplication.setCategory(categoryService.findCategoryByName(categoryName));
	    application = applicationService.updateApplication(receivedApplication);
	    ApplicationResource createdApplicationResource = new ApplicationResourceAssembler().toResource(application);
	    return new ResponseEntity<ApplicationResource>(createdApplicationResource, HttpStatus.OK);
    }

    // Update and Publish Application
    @RequestMapping(path = "/applications/{appId}/updateAndPublish", method = RequestMethod.POST)
    public ResponseEntity<ApplicationResource> updateAndPublishDeveloperApplication(
            @PathVariable String appId,
            @RequestBody ApplicationResource applicationResource
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<ApplicationResource>(HttpStatus.FORBIDDEN);
        Application application = applicationService.findApplicationByDeveloperIdAndAppId(user.getId(), appId);
        if (application == null)
            return new ResponseEntity<ApplicationResource>(HttpStatus.PRECONDITION_FAILED);

        Application receivedApplication = applicationResource.toEntity();
        receivedApplication.setDeveloper(user);
        receivedApplication.setId(appId);
        receivedApplication.setState(AppState.Active);
        String  categoryName = receivedApplication.getCategory().getName();
        receivedApplication.setCategory(categoryService.findCategoryByName(categoryName));
        application = applicationService.updateApplication(receivedApplication);
        ApplicationResource createdApplicationResource = new ApplicationResourceAssembler().toResource(application);
        return new ResponseEntity<ApplicationResource>(createdApplicationResource, HttpStatus.OK);
    }

    // Publish Application
    @RequestMapping(path = "/applications/{appId}/publish", method = RequestMethod.POST)
    public ResponseEntity<ApplicationResource> publishDeveloperApplication(
            @PathVariable String appId
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<ApplicationResource>(HttpStatus.FORBIDDEN);
        try{
	        Application application = applicationService.findApplicationByDeveloperIdAndAppId(user.getId(), appId);
	        // If the application state is not 'blocked' 
	        if(application!=null && !application.getState().equals(AppState.Blocked)) {
                application.setState(AppState.Active);
                application.setApplicationPublishDate(Calendar.getInstance().getTime());
                //push the app to history
                ApplicationHistory applicationHistory = new ApplicationHistory();
                applicationHistory.setApplication(application);
                applicationHistory.setName(application.getName());
                applicationHistory.setVersion(application.getVersion());
                applicationHistory.setWhatsNew(application.getWhatsNew());
                applicationHistoryService.createApplicationHistory(applicationHistory);
                application = applicationService.updateApplication(application);
                ApplicationResource newApplicationResource = new ApplicationResourceAssembler().toResource(application);
                return new ResponseEntity<ApplicationResource>(newApplicationResource, HttpStatus.OK);
	        }else{
	        	return new ResponseEntity<ApplicationResource>(HttpStatus.PRECONDITION_FAILED);
	        }
        }catch(Exception ex){
        	//ex.printStackTrace();
        	return new ResponseEntity<ApplicationResource>(HttpStatus.BAD_REQUEST);
        }
    }

    // Recall Application
    @RequestMapping(path = "/applications/{appId}/recall", method = RequestMethod.POST)
    public ResponseEntity<ApplicationResource> recallDeveloperApplication(
            @PathVariable String appId
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<ApplicationResource>(HttpStatus.FORBIDDEN);
        try{
            //ApplicationUpdate applicationUpdate = ApplicationResource.toEntity();
            Application application = applicationService.findApplicationByDeveloperIdAndAppId(user.getId(), appId);
            if (application == null)
                return new ResponseEntity<ApplicationResource>(HttpStatus.PRECONDITION_FAILED);
            // If the application state is 'Active'
            if(application.getState().equals(AppState.Active)) {
                //get top 1 application from history based on date (desc).

                //set the history application value to application

                //create new history app

                //return the application

                //if there is no history app, then set the state to "Recalled"
                application.setState(AppState.Recalled);
                application = applicationService.updateApplication(application);
                ApplicationResource updateApplicationResource = new ApplicationResourceAssembler().toResource(application);
                return new ResponseEntity<ApplicationResource>(updateApplicationResource, HttpStatus.OK);
            }else{
                return new ResponseEntity<ApplicationResource>(HttpStatus.PRECONDITION_FAILED);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<ApplicationResource>(HttpStatus.BAD_REQUEST);
        }
    }

    // Create Update Application
    @RequestMapping(path = "/{userId}/applications/{appId}/createUpdate", method = RequestMethod.POST)
    public ResponseEntity<ApplicationResource> createUpdateDeveloperApplication(
            @PathVariable String userId,
            @PathVariable String appId,
            @RequestBody ApplicationUpdateResource applicationUpdateResource
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<ApplicationResource>(HttpStatus.FORBIDDEN);

        try{
            ApplicationUpdate applicationUpdate = applicationUpdateResource.toEntity();
            Application application = applicationService.findApplicationByDeveloperIdAndAppId(user.getId(), appId);
            // If the application state is not 'blocked' and should be active/RECALLED for the update to take place
            if(null != application && !application.getState().equals(AppState.Blocked) && (application.getState().equals(AppState.Active)) || application.getState().equals(AppState.Recalled)) {
                applicationUpdate.setTarget(application);
                ApplicationUpdate newApplicationUpdate = applicationUpdateService.createApplicationUpdateByDeveloper(user.getId(),applicationUpdate);
                application.setId(applicationUpdate.getTarget().getId());
                application.setVersion(applicationUpdate.getVersion());
                application.setWhatsNew(applicationUpdate.getWhatsNew());
                application.setDescription(applicationUpdate.getDescription());
                application.setName(applicationUpdate.getName());
                ApplicationResource newApplicationUpdateResource = new ApplicationResourceAssembler().toResource(application);
                return new ResponseEntity<ApplicationResource>(newApplicationUpdateResource, HttpStatus.OK);
            }else{
                return new ResponseEntity<ApplicationResource>(HttpStatus.PRECONDITION_FAILED);
            }
        }catch(Exception ex){
            return new ResponseEntity<ApplicationResource>(HttpStatus.BAD_REQUEST);
        }
    }

    // Publish Application Update
    @RequestMapping(path = "/{userId}/applications/{appId}/publishUpdate", method = RequestMethod.POST)
    public ResponseEntity<ApplicationUpdateResource> PublishDeveloperApplicationUpdate(
            @PathVariable String userId,
            @PathVariable ApplicationUpdate appUpdate,
            @RequestBody ApplicationUpdateResource applicationUpdateResource) {
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<ApplicationUpdateResource>(HttpStatus.FORBIDDEN);

        try{
            //ApplicationUpdate dbApplicationUpdate = applicationUpdateService.findByApplication(appId);
            //ApplicationUpdate dbApplicationUpdate = applicationUpdateService.findByApplication(appId);
            //Application app = appUpdate.getTarget();    //The application whose update is for publishing
            //ApplicationUpdate newAppUpdate = new ApplicationUpdateResourceAssembler().toResource(appUpdate);

            ApplicationUpdateResource newAppUpdateResource = new ApplicationUpdateResourceAssembler().toResource(appUpdate);
            return new ResponseEntity<ApplicationUpdateResource>(newAppUpdateResource, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<ApplicationUpdateResource>(HttpStatus.BAD_REQUEST);
        }
    }
}
