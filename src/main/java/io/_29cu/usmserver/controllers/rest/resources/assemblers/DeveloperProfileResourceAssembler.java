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

import io._29cu.usmserver.controllers.rest.DeveloperProfileController;
import io._29cu.usmserver.controllers.rest.resources.DeveloperProfileResource;
import io._29cu.usmserver.core.model.entities.DeveloperProfile;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class DeveloperProfileResourceAssembler extends ResourceAssemblerSupport<DeveloperProfile, DeveloperProfileResource>{
    public DeveloperProfileResourceAssembler() {
        super(DeveloperProfileController.class, DeveloperProfileResource.class);
    }
    @Override
    public DeveloperProfileResource toResource(DeveloperProfile developerProfile) {
        DeveloperProfileResource developerProfileResource = new DeveloperProfileResource();
        developerProfileResource.setRid(developerProfile.getId());
        developerProfileResource.setWebsite(developerProfile.getWebsite());
        developerProfileResource.setEmail(developerProfile.getEmail());
        developerProfileResource.setDescription(developerProfile.getDescription());
        developerProfileResource.setCompany(developerProfile.getCompany());
        developerProfileResource.setAddress(developerProfile.getAddress());
        developerProfileResource.setJobTitle(developerProfile.getJobTitle());
        developerProfileResource.setCity(developerProfile.getCity());
        developerProfileResource.setCountry(developerProfile.getCountry());
        developerProfileResource.setZipCode(developerProfile.getZipCode());
        developerProfileResource.setState(developerProfile.getState());
        developerProfileResource.setGender(developerProfile.getGender());
        developerProfileResource.setHomePhone(developerProfile.getHomePhone());
        developerProfileResource.setJoinDate(developerProfile.getJoinDate());
        developerProfileResource.setDateOfBirth(developerProfile.getDateOfBirth());
        developerProfileResource.setLogo(developerProfile.getLogo());
        developerProfileResource.setWorkPhone(developerProfile.getWorkPhone());
        developerProfileResource.add(linkTo(methodOn(DeveloperProfileController.class).developerProfile()).withSelfRel());
        return developerProfileResource;
    }
}
