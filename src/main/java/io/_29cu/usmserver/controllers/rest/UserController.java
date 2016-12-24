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

import io._29cu.usmserver.configurations.security.ChangePasswordRequest;
import io._29cu.usmserver.configurations.security.JwtUser;
import io._29cu.usmserver.configurations.security.JwtUserFactory;
import io._29cu.usmserver.controllers.rest.resources.ApplicationResource;
import io._29cu.usmserver.controllers.rest.resources.UserResource;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
//@EnableResourceServer
public class UserController {
	@Autowired
	private UserService userService;

//    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
//    public ResponseEntity<UserResource> getUser(
//            @PathVariable String userId
//    ) {
//        User user = userService.findUser(userId);
//        if (null == user)
//            return new ResponseEntity<UserResource>(HttpStatus.NOT_FOUND);
//
//        UserResource userResource = new UserResourceAssembler().toResource(user);
//        return new ResponseEntity<UserResource>(userResource, HttpStatus.OK);
//    }

//    @RequestMapping(value = "{id:\\d+}")
//    public UserDTO show(@PathVariable("id") Long id) {
//        return userService.findOne(id).orElseThrow(UserNotFoundException::new);
//    }

	@RequestMapping(value = "/0/user", method = RequestMethod.GET)
	public JwtUser getUser() {
		return JwtUserFactory.create(userService.findAuthenticatedUser()); //.orElseThrow(UserNotFoundException::new);
	}

	@RequestMapping(value = "/1/signup", method = RequestMethod.POST)
	public JwtUser createUser(
			@RequestBody UserResource userResource
	) {
		// set enabled = true by default
		userResource.setEnabled(true);
		User createdUser = userService.createUser(userResource.toEntity());
		return JwtUserFactory.create(createdUser);
	}

	@RequestMapping(value = "/0/users/changepwd", method = RequestMethod.POST)
	public ResponseEntity<Boolean> changePassword(
			@RequestBody ChangePasswordRequest changePasswordRequest
	) {
		// Let's get the user from principal and validate the userId against it.
		User user = userService.findAuthenticatedUser();
		if (user == null) {
			return new ResponseEntity<Boolean>(HttpStatus.FORBIDDEN);
		}

		// Existing pwd not match
		if (!user.getPassword().equals(changePasswordRequest.getCurrentPwd())) {
			return new ResponseEntity<Boolean>(HttpStatus.FORBIDDEN);
		}

		// TODO check and ensure new pwd follow password policy

		user.setPassword(changePasswordRequest.getNewPwd());
		Boolean result = userService.updateUser(user);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/0/admin/users/{userId}/block", method = RequestMethod.POST)
	public ResponseEntity<Boolean> blockUser(
			@PathVariable("userId") Long userId
	) {
		// Let's get the user from principal and validate the userId against it.
		User user = userService.findAuthenticatedUser();
		if (user == null) {
			return new ResponseEntity<Boolean>(HttpStatus.FORBIDDEN);
		}

		// TODO check and ensure user is Moderator role

		User target = userService.findUser(userId);
		if (target == null) {
			return new ResponseEntity<Boolean>(HttpStatus.PRECONDITION_FAILED);
		}
		// TODO what if Application was already blocked?

		Boolean result = userService.blockUser(target);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No user")
	private class UserNotFoundException extends RuntimeException {

		/**
		 *
		 */
		private static final long serialVersionUID = -6137974317485247126L;
	}
}