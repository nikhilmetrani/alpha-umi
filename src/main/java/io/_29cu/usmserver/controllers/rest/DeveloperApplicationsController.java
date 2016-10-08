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

import io._29cu.usmserver.controllers.rest.resources.DeveloperProfileResource;
import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.DeveloperProfileService;
import io._29cu.usmserver.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    // Skeleton methods
    // Add similar methods for create, modify and publish updates
    // And publish application

    // Get all applications
//    @RequestMapping(path = "/{userId}/applications", method = RequestMethod.GET)
//    public ResponseEntity<ApplicationListResource> getApplications(
//            @PathVariable Long userId
//    ){
//
//    }
//
    // Create Application
//    @RequestMapping(path = "/{userId}/application", method = RequestMethod.POST)
//    public ResponseEntity<ApplicationResource> createDeveloperApplication(
//            @PathVariable Long userId,
//            @RequestBody ApplicationResource applicationResource
//    ) {
//
//    }
//
    // Get Application
//    @RequestMapping(path = "/{userId}/application/{appId}", method = RequestMethod.GET)
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
