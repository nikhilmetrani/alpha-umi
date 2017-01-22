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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io._29cu.usmserver.controllers.rest.resources.ApplicationListResource;
import io._29cu.usmserver.controllers.rest.resources.SubscriptionResource;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationListResourceAssembler;
import io._29cu.usmserver.controllers.rest.resources.assemblers.SubscriptionResourceAssembler;
import io._29cu.usmserver.core.model.entities.Subscription;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.SubscriptionService;
import io._29cu.usmserver.core.service.UserService;
import io._29cu.usmserver.core.service.utilities.ApplicationList;

@Controller
@RequestMapping("/api/0/store")
public class SubscriptionController {
    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;
    private final Log logger = LogFactory.getLog(this.getClass());

    /**
     * Subscribe application
     * @param appId The id of the application to be subscribed
     * @return
     * @see SubscriptionResource
     */
    @RequestMapping(path = "/applications/{appId}/subscribe", method = RequestMethod.POST)
    public ResponseEntity<SubscriptionResource> SubscribeApplication(
            @PathVariable String appId
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Subscription subscription = subscriptionService.subscribeApplication(appId,user);
        if(subscription != null){
            SubscriptionResource createdSubscriptionResource = new SubscriptionResourceAssembler().toResource(subscription);
            return new ResponseEntity<>(createdSubscriptionResource, HttpStatus.OK);
        }
        else{
        	logger.debug("Subscription is present");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Unsubscribe application
     * @param appId The id of the application to be unsubscribed
     * @return
     * @see SubscriptionResource
     */
    @RequestMapping(path = "/applications/{appId}/unsubscribe", method = RequestMethod.POST)
    public ResponseEntity<SubscriptionResource> UnsubscribeApplication(
            @PathVariable String appId
    ) {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Subscription updatedSubscription = subscriptionService.unsubscribeApplication(appId,user);
        SubscriptionResource updatedSubscriptionResource=null;
        if(updatedSubscription != null){
            updatedSubscriptionResource = new SubscriptionResourceAssembler().toResource(updatedSubscription);
            return new ResponseEntity<>(updatedSubscriptionResource, HttpStatus.OK);
        }
        else{
        	logger.debug("Updated Subscription is present");
            return new ResponseEntity<>(updatedSubscriptionResource, HttpStatus.NOT_FOUND);
        }
    }

	/**
	 * Get subscribed application
	 * @return
	 * @see ApplicationListResource
	 */
    @RequestMapping(path = "/subscriptions", method = RequestMethod.GET)
    public ResponseEntity<ApplicationListResource> getSubscribedApplications() {
        // Let's get the user from principal and validate the userId against it.
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        ApplicationList appList = subscriptionService.getSubscribedApplications(user.getId());
        // It's assumed that appList will never be null
        ApplicationListResource appListResource = new ApplicationListResourceAssembler().toResource(appList);
        return new ResponseEntity<>(appListResource, HttpStatus.OK);
    }

	/**
	 * Find subscription by user and application
	 * @param appId The id of the application
	 * @return
	 * @see SubscriptionResource
	 */
    @RequestMapping(path = "/applications/{appId}/checkAppIsSubscribed", method = RequestMethod.GET)
    public ResponseEntity<SubscriptionResource> FindSubscriptionByUserIdAndApplicationId(
            @PathVariable String appId
    ) {
        User user = userService.findAuthenticatedUser();
        if (user == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Subscription subscription = subscriptionService.findSubscriptionByUserIdAndApplicationId(user.getId(),appId);
        if (null == subscription) { 
        	logger.debug("We can't find the subscription in our database for the user.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
