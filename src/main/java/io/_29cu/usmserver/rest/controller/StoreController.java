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

package io._29cu.usmserver.rest.controller;

import io._29cu.usmserver.core.model.entity.Application;
import io._29cu.usmserver.core.model.entity.User;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.UserService;
import io._29cu.usmserver.core.service.utility.ApplicationList;
import io._29cu.usmserver.rest.resource.ApplicationListResource;
import io._29cu.usmserver.rest.resource.UserResource;
import io._29cu.usmserver.rest.resource.assembler.ApplicationListResourceAssembler;
import io._29cu.usmserver.rest.resource.assembler.UserResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private ApplicationService applicationService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ApplicationListResource> store() {
        try {
            ApplicationList appList = applicationService.getAllApplications();
            ApplicationListResource resource = new ApplicationListResourceAssembler().toResource(appList);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(resource.getLink("self").getHref()));
            return new ResponseEntity<ApplicationListResource>(resource, headers, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<ApplicationListResource>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{category}", method = RequestMethod.GET)
    public ResponseEntity<ApplicationListResource> getApplication(
            @PathVariable String category
    ) {
        try {
            ApplicationList appList = applicationService.findApplicationsByCategory(category);
            ApplicationListResource resource = new ApplicationListResourceAssembler().toResource(appList);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(resource.getLink("self").getHref()));
            return new ResponseEntity<ApplicationListResource>(resource, headers, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<ApplicationListResource>(HttpStatus.BAD_REQUEST);
        }
    }


}
