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

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import io._29cu.usmserver.controllers.rest.StoreController;
import io._29cu.usmserver.controllers.rest.resources.ApplicationBundleListResource;
import io._29cu.usmserver.controllers.rest.resources.ApplicationBundleResource;
import io._29cu.usmserver.core.service.utilities.ApplicationBundleList;

public class ApplicationBundleListResourceAssembler extends ResourceAssemblerSupport<ApplicationBundleList, ApplicationBundleListResource> {

    public ApplicationBundleListResourceAssembler() {
        super(StoreController.class, ApplicationBundleListResource.class);
    }
    @Override
    public ApplicationBundleListResource toResource(ApplicationBundleList applicationBundleList) {
        List<ApplicationBundleResource> appBundleResourceList = new ApplicationBundleResourceAssembler().toResources(applicationBundleList.getApplicationBundles());
        ApplicationBundleListResource finalRes = new ApplicationBundleListResource();
        finalRes.setApplicationBundles(appBundleResourceList);
        finalRes.add(linkTo(methodOn(StoreController.class).store()).withSelfRel());
        return finalRes;
    }
}
