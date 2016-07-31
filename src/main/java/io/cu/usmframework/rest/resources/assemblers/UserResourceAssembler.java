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

package io.cu.usmframework.rest.resources.assemblers;

import io.cu.usmframework.core.model.entities.User;
import io.cu.usmframework.rest.controllers.UserController;
import io.cu.usmframework.rest.resources.UserResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {
    public UserResourceAssembler() {
        super(UserController.class, UserResource.class);
    }
    @Override
    public UserResource toResource(User user) {
        UserResource userResource = new UserResource();
        userResource.setRid(user.getId());
        userResource.setEmail(user.getEmail());
        userResource.add(linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel());
        return userResource;
    }
}
