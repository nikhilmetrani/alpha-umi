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

package io._29cu.usmserver.controllers.rest.resources.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import io._29cu.usmserver.controllers.rest.ApplicationController;
import io._29cu.usmserver.controllers.rest.DeveloperApplicationsController;
import io._29cu.usmserver.controllers.rest.resources.ApplicationUpdateResource;
import io._29cu.usmserver.core.model.entities.ApplicationUpdate;

public class ApplicationUpdateResourceAssembler extends ResourceAssemblerSupport<ApplicationUpdate, ApplicationUpdateResource> {
    public ApplicationUpdateResourceAssembler() {
        super(DeveloperApplicationsController.class, ApplicationUpdateResource.class);
    }
    @Override
    public ApplicationUpdateResource toResource(ApplicationUpdate applicationUpdate) {
        ApplicationUpdateResource applicationUpdateResource = new ApplicationUpdateResource();
        applicationUpdateResource.setRid(applicationUpdate.getId());
        applicationUpdateResource.setVersion(applicationUpdate.getVersion());
        applicationUpdateResource.setApplication(applicationUpdate.getApplication());
        applicationUpdateResource.setWhatsNew(applicationUpdate.getWhatsNew());
        applicationUpdateResource.add(linkTo(methodOn(DeveloperApplicationsController.class).getApplication(applicationUpdate.getApplication().getDeveloper().getId(), applicationUpdate.getApplication().getId())).withSelfRel());
        if(applicationUpdate.getApplication() != null && applicationUpdate.getApplication().getDeveloper() != null)
            applicationUpdateResource.add(linkTo(ApplicationController.class).slash("developer/" + applicationUpdate.getApplication().getDeveloper().getId()).withRel("developerApps"));
        return applicationUpdateResource;
    }
}
