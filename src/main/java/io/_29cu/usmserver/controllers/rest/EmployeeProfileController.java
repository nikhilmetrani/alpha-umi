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

import io._29cu.usmserver.controllers.rest.resources.EmployeeProfileResource;
import io._29cu.usmserver.controllers.rest.resources.assemblers.EmployeeProfileResourceAssembler;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.entities.EmployeeProfile;
import io._29cu.usmserver.core.service.EmployeeProfileService;
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
@RequestMapping("/api/0/employee")
public class EmployeeProfileController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeProfileService employeeProfileService;

    // userId path variable imposes a security risk. Need to remove it.
    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public ResponseEntity<EmployeeProfileResource> employeeProfile() {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<EmployeeProfileResource>(HttpStatus.FORBIDDEN);
        try {
            EmployeeProfile employeeProfile = employeeProfileService.findProfileByUserId(user.getId());
            if (null == employeeProfile) {
                employeeProfile = new EmployeeProfile(); //Create an empty profile object
                employeeProfile.setEmployee(user);
            }
            EmployeeProfileResource employeeProfileResource = new EmployeeProfileResourceAssembler().toResource(employeeProfile);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(employeeProfileResource.getLink("self").getHref()));
            return new ResponseEntity<EmployeeProfileResource>(employeeProfileResource, headers, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<EmployeeProfileResource>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/profile", method = RequestMethod.POST)
    public ResponseEntity<EmployeeProfileResource> createEmployeeProfile(
            @RequestBody EmployeeProfileResource employeeProfileResource
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<EmployeeProfileResource>(HttpStatus.FORBIDDEN);
        try {
            EmployeeProfile receivedProfile = employeeProfileResource.toEntity();
            receivedProfile.setEmployee(user);
            EmployeeProfile createdProfile = employeeProfileService.createProfile(receivedProfile);
            EmployeeProfileResource createdProfileResource = new EmployeeProfileResourceAssembler().toResource(createdProfile);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(createdProfileResource.getLink("self").getHref()));
            return new ResponseEntity<EmployeeProfileResource>(createdProfileResource, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<EmployeeProfileResource>(HttpStatus.BAD_REQUEST);
        }
    }
}
