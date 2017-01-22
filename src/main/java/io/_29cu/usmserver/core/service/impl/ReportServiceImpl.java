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

package io._29cu.usmserver.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io._29cu.usmserver.common.utilities.ReportUtils;
import io._29cu.usmserver.core.model.entities.Subscription;
import io._29cu.usmserver.core.repositories.ApplicationRepository;
import io._29cu.usmserver.core.repositories.SubscriptionRepository;
import io._29cu.usmserver.core.repositories.UserRepository;
import io._29cu.usmserver.core.service.ReportService;

@Component
public class ReportServiceImpl implements ReportService{

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public int findSubscriptionsPerApplication(String applicationId, Date startDate, Date endDt) {
        List<String> subscribedUserList = new ArrayList<>();
        Date endDate = ReportUtils.findDateRange(endDt);
        List<Subscription> subscriptionList = subscriptionRepository.findSubscribedUsersPerApplication(applicationId, startDate, endDate);
        if(subscriptionList != null && !subscriptionList.isEmpty()){
	        long subscribeDate;
            for(Subscription subscription : subscriptionList){
	            subscribeDate = subscription.getDateSubscribed().getTime();
                if(subscribeDate >= startDate.getTime() && subscribeDate <= endDate.getTime()){
                    subscribedUserList.add(subscription.getUser().getUsername());
                }
            }
        }
        return  subscribedUserList.size();
    }

    @Override
    public int findActiveSubscriptionsPerApplication(String applicationId, Date startDate, Date endDt) {
        List<String> subscribedActiveUserList = new ArrayList<>();
        Date endDate = ReportUtils.findDateRange(endDt);
        List<Subscription> subscriptionList = subscriptionRepository.findSubscribedActiveUsersPerApplication(applicationId, startDate, endDate);
        if(subscriptionList != null && !subscriptionList.isEmpty()){
	        long subscribeDate;
            for(Subscription subscription : subscriptionList){
	            subscribeDate = subscription.getDateSubscribed().getTime();
                if(subscribeDate >= startDate.getTime() && subscribeDate <= endDate.getTime()){
                    subscribedActiveUserList.add(subscription.getUser().getUsername());
                }
            }
        }
        return  subscribedActiveUserList.size();
    }

    @Override
    public int findTerminatedSubscriptionsPerApplication(String applicationId, Date startDate, Date endDt) {
        List<String> terminatedApplicationList = new ArrayList<>();
        Date endDate = ReportUtils.findDateRange(endDt);
        List<Subscription> subscriptionList = subscriptionRepository.findTerminatedSubscriptionsPerApplication(applicationId, startDate, endDate);
	    if(subscriptionList != null && !subscriptionList.isEmpty()){
		    long subscribeDate;
		    for(Subscription subscription : subscriptionList){
			    subscribeDate = subscription.getDateSubscribed().getTime();
			    if(subscribeDate >= startDate.getTime() && subscribeDate <= endDate.getTime()){
				    terminatedApplicationList.add(subscription.getUser().getUsername());
			    }
		    }
	    }
        return  terminatedApplicationList.size();
    }

}
