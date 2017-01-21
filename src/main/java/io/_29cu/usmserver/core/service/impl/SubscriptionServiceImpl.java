/**
 * Copyright 2016 - 29cu.io and the authors of alpha-umi open source project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package io._29cu.usmserver.core.service.impl;

import io._29cu.usmserver.common.utilities.AppHelper;
import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.Subscription;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.repositories.SubscriptionRepository;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.SubscriptionService;
import io._29cu.usmserver.core.service.UserService;
import io._29cu.usmserver.core.service.utilities.ApplicationList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private ApplicationService applicationService;

	@Override
	public Subscription subscribeApplication(Subscription subscription) {
		return subscriptionRepository.save(subscription);
	}

	@Override
	public Subscription subscribeApplication(String appId, User user) {
		Subscription receivedSubscription = new Subscription();
		Application application = applicationService.findApplication(appId);
		if (application != null && application.getState() == AppState.Active) {
			Subscription subscriptionInDB = subscriptionRepository.findSubscription(user.getId(), application.getId());
			if(subscriptionInDB != null) {
				receivedSubscription.setId(subscriptionInDB.getId());
			}
			receivedSubscription.setUser(user);
			receivedSubscription.setApplication(application);
			receivedSubscription.setActive(true);
			receivedSubscription.setDateSubscribed(new Date());
		} else {
			return null;
		}
		return subscriptionRepository.save(receivedSubscription);
	}

	@Override
	public Subscription findSubscriptionByUserIdAndApplicationId(Long userId, String applicationId) {
		return subscriptionRepository.findSubscription(userId, applicationId);
	}

	@Override
	public Subscription unsubscribeApplication(String appId, User user) {
		Subscription subscription = findSubscriptionByUserIdAndApplicationId(user.getId(), appId);
		if(subscription == null){
			return subscription;
		}
		subscription.setActive(false);
		subscription.setDateUnsubscribed(new Date());
		return subscriptionRepository.save(subscription);
	}

	@Override
	public ApplicationList getSubscribedApplications(long userId) {
		ApplicationList appList = new ApplicationList();
		appList.setApplications(AppHelper.getInstance().convertIterableToList(subscriptionRepository.getSubscribedApplications(userId)));
		return appList;
	}

	@Override
	public ApplicationList getApplicationsBySubscriptions(Date startDate, Date endDate) {
		ApplicationList appList = new ApplicationList();
		List<Application> apps = subscriptionRepository.getApplicationsBySubscriptions(startDate, endDate);
		if (apps != null && apps.size() > 10) {
			apps = apps.subList(0, 10);
		}
		appList.setApplications(AppHelper.getInstance().convertIterableToList(apps));
		return appList;
	}
}
