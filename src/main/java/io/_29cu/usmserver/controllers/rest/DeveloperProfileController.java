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

import java.net.URI;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io._29cu.usmserver.common.exceptions.StorageFileNotFoundException;
import io._29cu.usmserver.controllers.rest.resources.DeveloperProfileResource;
import io._29cu.usmserver.controllers.rest.resources.assemblers.DeveloperProfileResourceAssembler;
import io._29cu.usmserver.core.model.entities.DeveloperProfile;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.DeveloperProfileService;
import io._29cu.usmserver.core.service.StorageService;
import io._29cu.usmserver.core.service.UserService;

@Controller
@RequestMapping("/api/0/developer")
public class DeveloperProfileController {
    @Autowired
    private UserService userService;
    @Autowired
    private DeveloperProfileService developerProfileService;
    @Autowired
    private StorageService storageService;

    // userId path variable imposes a security risk. Need to remove it.
	/**
	 * Get developer profile
	 * @return
	 * @see DeveloperProfileResource
	 */
	@RequestMapping(path = "/profile", method = RequestMethod.GET)
    public ResponseEntity<DeveloperProfileResource> developerProfile() {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        try {
            DeveloperProfile developerProfile = developerProfileService.findProfileByUserId(user.getId());
            if (null == developerProfile) {
                developerProfile = new DeveloperProfile(); //Create an empty profile object
                developerProfile.setOwner(user);
            }
            DeveloperProfileResource developerProfileResource = new DeveloperProfileResourceAssembler().toResource(developerProfile);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(developerProfileResource.getLink("self").getHref()));
            return new ResponseEntity<>(developerProfileResource, headers, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

	/**
	 * Create developer profile
	 * @param developerProfileResource The profile to be created
	 * @return
	 * @see DeveloperProfileResource
	 */
    @RequestMapping(path = "/profile", method = RequestMethod.POST)
    public ResponseEntity<DeveloperProfileResource> createDeveloperProfile(
            @RequestBody DeveloperProfileResource developerProfileResource
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        try {
            DeveloperProfile receivedProfile = developerProfileResource.toEntity();
            receivedProfile.setOwner(user);
            DeveloperProfile createdProfile = developerProfileService.createProfile(receivedProfile);
            DeveloperProfileResource createdProfileResource = new DeveloperProfileResourceAssembler().toResource(createdProfile);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(createdProfileResource.getLink("self").getHref()));
            return new ResponseEntity<>(createdProfileResource, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

	/**
	 * Upload profile image
	 * @param file The image file for the profile
	 * @return
	 */
    @RequestMapping(path = "/profile/image", method = RequestMethod.POST)
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   RedirectAttributes redirectAttributes) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Path generatedFile = storageService.storeProfileImage(file, user.getId());
        String response = "{'originalName': '" + file.getOriginalFilename() + "', 'generatedName': '" + generatedFile.getFileName() + "'}";
        response = response.replace("'", "\"");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

	/**
	 * Handle storage file not found
	 * @param exc The exception for storage file not found
	 * @return
	 * @see StorageFileNotFoundException
	 */
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
