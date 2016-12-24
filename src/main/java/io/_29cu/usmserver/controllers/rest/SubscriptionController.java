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

import java.util.Date;
import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.controllers.rest.resources.SubscriptionResource;
import io._29cu.usmserver.controllers.rest.resources.assemblers.SubscriptionResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io._29cu.usmserver.controllers.rest.resources.ApplicationListResource;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationListResourceAssembler;
import io._29cu.usmserver.core.model.entities.Subscription;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.SubscriptionService;
import io._29cu.usmserver.core.service.UserService;
import io._29cu.usmserver.core.service.utilities.ApplicationList;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/api/0/store")
public class SubscriptionController {
    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private SubscriptionService subscriptionService;

    // Create Subscription
    @RequestMapping(path = "/applications/{appId}/subscribe", method = RequestMethod.POST)
    public ResponseEntity<SubscriptionResource> SubscribeApplication(
            @PathVariable String appId
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<SubscriptionResource>(HttpStatus.FORBIDDEN);
        Subscription subscription = subscriptionService.subscribeApplication(appId,user);
        if(subscription != null){
            SubscriptionResource createdSubscriptionResource = new SubscriptionResourceAssembler().toResource(subscription);
            return new ResponseEntity<SubscriptionResource>(createdSubscriptionResource, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<SubscriptionResource>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/applications/{appId}/unsubscribe", method = RequestMethod.POST)
    public ResponseEntity<SubscriptionResource> UnsubscribeApplication(
            @PathVariable String appId
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<SubscriptionResource>(HttpStatus.FORBIDDEN);
        Subscription updatedSubscription = subscriptionService.unsubscribeApplication(appId,user);
        SubscriptionResource updatedSubscriptionResource = new SubscriptionResourceAssembler().toResource(updatedSubscription);
        return new ResponseEntity<SubscriptionResource>(updatedSubscriptionResource, HttpStatus.OK);
    }

    @RequestMapping(path = "/subscription/myapps", method = RequestMethod.GET)
    public ResponseEntity<ApplicationListResource> getSubscribedApplications() {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<ApplicationListResource>(HttpStatus.FORBIDDEN);

        ApplicationList appList = subscriptionService.getSubscribedApplications(user.getId());
        if(appList == null || appList.getItems().isEmpty()) {
        	return new ResponseEntity<ApplicationListResource>(HttpStatus.NOT_FOUND);
        }
        ApplicationListResource appListResource = new ApplicationListResourceAssembler().toResource(appList);
        return new ResponseEntity<ApplicationListResource>(appListResource, HttpStatus.OK);
    }
}
