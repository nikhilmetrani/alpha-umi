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

import io._29cu.usmserver.core.service.utilities.ApplicationList;
import io._29cu.usmserver.controllers.rest.StoreController;
import io._29cu.usmserver.controllers.rest.resources.ApplicationListResource;
import io._29cu.usmserver.controllers.rest.resources.ApplicationResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

public class ApplicationListResourceAssembler extends ResourceAssemblerSupport<ApplicationList, ApplicationListResource> {

    public ApplicationListResourceAssembler() {
        super(StoreController.class, ApplicationListResource.class);
    }
    @Override
    public ApplicationListResource toResource(ApplicationList applicationList) {
        List<ApplicationResource> appResourceList = new ApplicationResourceAssembler().toResources(applicationList.getApplications());
        ApplicationListResource finalRes = new ApplicationListResource();
        finalRes.setApplications(appResourceList);
        finalRes.add(linkTo(methodOn(StoreController.class).store()).withSelfRel());
        return finalRes;
    }
}
