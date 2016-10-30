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
import io._29cu.usmserver.controllers.rest.resources.assemblers.DeveloperProfileResourceAssembler;
import io._29cu.usmserver.core.model.entities.AuUser;
import io._29cu.usmserver.core.model.entities.DeveloperProfile;
import io._29cu.usmserver.core.service.DeveloperProfileService;
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
@RequestMapping("/api/0/developer")
public class DeveloperProfileController {
    @Autowired
    private UserService userService;
    @Autowired
    private DeveloperProfileService developerProfileService;

    // userId path variable imposes a security risk. Need to remove it.
    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public ResponseEntity<DeveloperProfileResource> developerProfile() {
        // Let's get the user from principal and validate the userId against it.
        AuUser user = userService.findUser();
        if (user == null)
            return new ResponseEntity<DeveloperProfileResource>(HttpStatus.FORBIDDEN);
        try {
            DeveloperProfile developerProfile = developerProfileService.findProfileByUserId(user.getId());
            if (null == developerProfile) {
                developerProfile = new DeveloperProfile(); //Create an empty profile object
                developerProfile.setOwner(user);
            }
            DeveloperProfileResource developerProfileResource = new DeveloperProfileResourceAssembler().toResource(developerProfile);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(developerProfileResource.getLink("self").getHref()));
            return new ResponseEntity<DeveloperProfileResource>(developerProfileResource, headers, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<DeveloperProfileResource>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/profile", method = RequestMethod.POST)
    public ResponseEntity<DeveloperProfileResource> createDeveloperProfile(
            @RequestBody DeveloperProfileResource developerProfileResource
    ) {
        // Let's get the user from principal and validate the userId against it.
        AuUser user = userService.findUser();
        if (user == null)
            return new ResponseEntity<DeveloperProfileResource>(HttpStatus.FORBIDDEN);
        try {
            DeveloperProfile receivedProfile = developerProfileResource.toEntity();
            receivedProfile.setOwner(user);
            DeveloperProfile createdProfile = developerProfileService.createProfile(receivedProfile);
            DeveloperProfileResource createdProfileResource = new DeveloperProfileResourceAssembler().toResource(createdProfile);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(createdProfileResource.getLink("self").getHref()));
            return new ResponseEntity<DeveloperProfileResource>(createdProfileResource, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<DeveloperProfileResource>(HttpStatus.BAD_REQUEST);
        }
    }
}
