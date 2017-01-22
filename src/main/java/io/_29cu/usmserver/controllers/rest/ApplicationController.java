/**
 * Copyright 2016 - 29cu.io and the authors of alpha-umi open source project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package io._29cu.usmserver.controllers.rest;

import io._29cu.usmserver.controllers.rest.resources.ApplicationListResource;
import io._29cu.usmserver.controllers.rest.resources.ApplicationResource;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationListResourceAssembler;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationResourceAssembler;
import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.UserService;
import io._29cu.usmserver.core.service.utilities.ApplicationList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api")
public class ApplicationController {
	@Autowired
	private UserService userService;
	@Autowired
	private ApplicationService applicationService;

	/**
	 * Get application by application id
	 * @param appId The Application id to be search by
	 * @return The ApplicationResource found
	 * @see ApplicationResource
	 */
	@RequestMapping(value = "/1/store/application/{appId}", method = RequestMethod.GET)
	public ResponseEntity<ApplicationResource> getApplication(
			@PathVariable String appId
	) {
		Application application = applicationService.findApplication(appId);
		if (null == application)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		ApplicationResource applicationResource = new ApplicationResourceAssembler().toResource(application);
		return new ResponseEntity<>(applicationResource, HttpStatus.OK);
	}

	@RequestMapping(value = "/1/store/application/developer/{developerId}", method = RequestMethod.GET)
	public ResponseEntity<ApplicationListResource> getApplicationsByDeveloper(
			@PathVariable long developerId
	) {
		ApplicationList applicationList = applicationService.findApplicationsByDeveloper(developerId);
		if (null == applicationList)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		ApplicationListResource applicationListResource = new ApplicationListResourceAssembler().toResource(applicationList);
		return new ResponseEntity<>(applicationListResource, HttpStatus.OK);
	}

	/**
	 * Block developer application
	 * @param appId The id of the Application to be blocked
	 * @return A boolean value that indicates whether the update is successful
	 */
	@RequestMapping(path = "/0/admin/applications/{appId}/block", method = RequestMethod.POST)
	public ResponseEntity<Boolean> updateDeveloperApplication(
			@PathVariable String appId
	) {
		// Let's get the user from principal and validate the userId against it.
		User user = userService.findAuthenticatedUser();
		if (user == null)
			return new ResponseEntity<Boolean>(HttpStatus.FORBIDDEN);

		// TODO check and ensure user is Moderator role

		Application application = applicationService.findApplication(appId);
		if (application == null)
			return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);

		// TODO what if Application was already blocked?

		Boolean result = applicationService.blockApplication(application);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
