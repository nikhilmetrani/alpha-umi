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

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.utilities.ApplicationList;
import io._29cu.usmserver.controllers.rest.resources.ApplicationListResource;
import io._29cu.usmserver.controllers.rest.resources.ApplicationResource;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationListResourceAssembler;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api/1/store/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @RequestMapping(value = "/{appId}", method = RequestMethod.GET)
    public ResponseEntity<ApplicationResource> getApplication(
            @PathVariable Long appId
    ) {
        Application application = applicationService.findApplication(appId);
        if (null == application)
            return new ResponseEntity<ApplicationResource>(HttpStatus.NOT_FOUND);

        ApplicationResource applicationResource = new ApplicationResourceAssembler().toResource(application);
        return new ResponseEntity<ApplicationResource>(applicationResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/developer/{developerId}", method = RequestMethod.GET)
    public ResponseEntity<ApplicationListResource> getApplicationByDeveloper(
            @PathVariable Long developerId
    ) {
        ApplicationList applicationList = applicationService.findApplicationsByDeveloper(developerId);
        if (null == applicationList)
            return new ResponseEntity<ApplicationListResource>(HttpStatus.NOT_FOUND);

        ApplicationListResource applicationListResource = new ApplicationListResourceAssembler().toResource(applicationList);
        return new ResponseEntity<ApplicationListResource>(applicationListResource, HttpStatus.OK);
    }

}
