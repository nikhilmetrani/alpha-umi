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

package io.cu.usmframework.rest.controllers;

import io.cu.usmframework.core.model.entities.User;
import io.cu.usmframework.rest.resources.UserResource;
import io.cu.usmframework.rest.resources.assemblers.UserResourceAssembler;
import io.cu.usmframework.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserResource> createUser(
            @RequestBody UserResource receivedUser
    ) {
        try {
            User createdUser = userService.createUser(receivedUser.toEntity());
            UserResource res = new UserResourceAssembler().toResource(createdUser);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(res.getLink("self").getHref()));
            return new ResponseEntity<UserResource>(res, headers, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<UserResource>(HttpStatus.BAD_REQUEST);
        }
    }
}