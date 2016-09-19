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

import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.UserService;
import io._29cu.usmserver.controllers.rest.resources.UserResource;
import io._29cu.usmserver.controllers.rest.resources.assemblers.UserResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<UserResource> user(
            Principal principal /**/
    ) {
        try {
            //Let's check whether the user is already registered.
            User user = userService.findUserByPrincipal(principal.getName());
            if (null == user) { //We can't find the principal in our database, let's create it.
                user = userService.createUser(principal);
            }

            UserResource userResource = new UserResourceAssembler().toResource(user);
            return new ResponseEntity<UserResource>(userResource, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<UserResource>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserResource> getUser(
            @PathVariable Long userId
    ) {
        User user = userService.findUser(userId);
        if (null == user)
            return new ResponseEntity<UserResource>(HttpStatus.NOT_FOUND);

        UserResource userResource = new UserResourceAssembler().toResource(user);
        return new ResponseEntity<UserResource>(userResource, HttpStatus.OK);
    }
}