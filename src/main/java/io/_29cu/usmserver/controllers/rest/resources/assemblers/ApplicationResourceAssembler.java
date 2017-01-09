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

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import io._29cu.usmserver.controllers.rest.ApplicationController;
import io._29cu.usmserver.controllers.rest.resources.ApplicationResource;
import io._29cu.usmserver.controllers.rest.resources.InstallerResource;
import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.Installer;

public class ApplicationResourceAssembler extends ResourceAssemblerSupport<Application, ApplicationResource> {
    public ApplicationResourceAssembler() {
        super(ApplicationController.class, ApplicationResource.class);
    }
    @Override
    public ApplicationResource toResource(Application application) {
        ApplicationResource applicationResource = new ApplicationResource();
        applicationResource.setRid(application.getId());
        applicationResource.setName(application.getName());
        applicationResource.setDeveloper(application.getDeveloper());
        applicationResource.setCategory(application.getCategory());
        applicationResource.setVersion(application.getVersion());
        applicationResource.setState(application.getState());
        applicationResource.setDescription(application.getDescription());
        applicationResource.setWhatsNew(application.getWhatsNew());
        List<InstallerResource> installers = new ArrayList<InstallerResource>();
        if(application.getInstallers() != null) {
	        for(Installer installer : application.getInstallers()) {
	        	installers.add(new InstallerResourceAssembler().toResource(installer));
	        }
        }
        applicationResource.setInstallers(installers);
        applicationResource.add(linkTo(methodOn(ApplicationController.class).getApplication(application.getId())).withSelfRel());
        if(application.getDeveloper() != null)
            applicationResource.add(linkTo(ApplicationController.class).slash("developer/" + application.getDeveloper().getId()).withRel("developerApps"));
        return applicationResource;
    }
}
