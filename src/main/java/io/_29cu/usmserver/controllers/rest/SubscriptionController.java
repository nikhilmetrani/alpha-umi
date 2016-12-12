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

import io._29cu.usmserver.core.model.entities.Subscription;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.SubscriptionService;
import io._29cu.usmserver.core.service.UserService;
import io._29cu.usmserver.controllers.rest.resources.SubscriptionResource;
import io._29cu.usmserver.controllers.rest.resources.assemblers.SubscriptionResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api/0/store/subscription")
public class SubscriptionController {
    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    // Create Subscription
    @RequestMapping(path = "/subscribe", method = RequestMethod.POST)
    public ResponseEntity<SubscriptionResource> SubscribeApplication(
            @RequestBody SubscriptionResource subscriptionResource
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<SubscriptionResource>(HttpStatus.FORBIDDEN);
        Subscription receivedSubscription = subscriptionResource.toEntity();
        receivedSubscription.setUser(user);
        Subscription subscription = subscriptionService.subscribeApplication(receivedSubscription);
        SubscriptionResource createdSubscriptionResource = new SubscriptionResourceAssembler().toResource(subscription);
        return new ResponseEntity<SubscriptionResource>(createdSubscriptionResource, HttpStatus.OK);
    }

    @RequestMapping(path = "/unsubscribe", method = RequestMethod.POST)
    public ResponseEntity<SubscriptionResource> UnsubscribeApplication(
            @RequestBody SubscriptionResource subscriptionResource
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<SubscriptionResource>(HttpStatus.FORBIDDEN);
        Subscription receivedSubscription = subscriptionResource.toEntity();
        receivedSubscription.setUser(user);
        Subscription subscription = subscriptionService.unsubscribeApplication(receivedSubscription);
        SubscriptionResource createdSubscriptionResource = new SubscriptionResourceAssembler().toResource(subscription);
        return new ResponseEntity<SubscriptionResource>(createdSubscriptionResource, HttpStatus.OK);
    }
}
