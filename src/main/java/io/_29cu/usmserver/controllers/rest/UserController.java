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

import io._29cu.usmserver.configurations.security.JwtUser;
import io._29cu.usmserver.configurations.security.JwtUserFactory;
import io._29cu.usmserver.controllers.rest.resources.UserResource;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        User createdUser = userService.createUser(userResource.toEntity());
        return JwtUserFactory.create(createdUser);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No user")
    private class UserNotFoundException extends RuntimeException {
    }
}