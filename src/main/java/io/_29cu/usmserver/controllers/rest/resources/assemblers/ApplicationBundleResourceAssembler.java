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

import io._29cu.usmserver.controllers.rest.ApplicationBundleController;
import io._29cu.usmserver.controllers.rest.resources.ApplicationBundleResource;
import io._29cu.usmserver.core.model.entities.ApplicationBundle;

public class ApplicationBundleResourceAssembler extends ResourceAssemblerSupport<ApplicationBundle, ApplicationBundleResource> {
    public ApplicationBundleResourceAssembler() {
        super(ApplicationBundleController.class, ApplicationBundleResource.class);
    }
    @Override
    public ApplicationBundleResource toResource(ApplicationBundle applicationBundle) {
        ApplicationBundleResource applicationBundleResource = new ApplicationBundleResource();
        applicationBundleResource.setRid(applicationBundle.getId());
        applicationBundleResource.setName(applicationBundle.getName());
        applicationBundleResource.setDeveloper(applicationBundle.getDeveloper());
        applicationBundleResource.setCategory(applicationBundle.getCategory());
        applicationBundleResource.setState(applicationBundle.getState());
        applicationBundleResource.setDescription(applicationBundle.getDescription());
        applicationBundleResource.add(linkTo(methodOn(ApplicationBundleController.class).getApplicationBundle(applicationBundle.getId())).withSelfRel());
        if(applicationBundle.getDeveloper() != null)
            applicationBundleResource.add(linkTo(ApplicationBundleController.class).slash("developer/" + applicationBundle.getDeveloper().getId()).withRel("developerApps"));//TODO update "developerApps"
        return applicationBundleResource;
    }
}
