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

import io._29cu.usmserver.common.utilities.AppHelper;
import io._29cu.usmserver.common.utilities.ReportUtils;
import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.Subscription;
import io._29cu.usmserver.core.repositories.ApplicationRepository;
import io._29cu.usmserver.core.repositories.CategoryRepository;
import io._29cu.usmserver.core.repositories.SubscriptionRepository;
import io._29cu.usmserver.core.repositories.UserRepository;
import io._29cu.usmserver.core.service.ReportService;
import io._29cu.usmserver.core.service.utilities.ApplicationList;
import io._29cu.usmserver.core.service.utilities.DummyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ReportServiceImpl implements ReportService{

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ApplicationRepository applicationRepository;



    @Override
    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public ApplicationList findApplicationsByUserNameAndState(String username,Date toApplicationPublishDate) {
        List<Application> finalApplicationList = null;
        ApplicationList applicationList = null;
        List<Application> appList = applicationRepository.findApplicationsByUserNameAndState(username,toApplicationPublishDate);
        Date fromApplicationPublishDate = ReportUtils.findDateRange(toApplicationPublishDate);
        if(appList != null && !appList.isEmpty()){
            finalApplicationList = new ArrayList<Application>();
            for(Application application : appList){
                if(application.getApplicationPublishDate().getTime() >= fromApplicationPublishDate.getTime()){
                    finalApplicationList.add(application);
                }
            }
            applicationList = new ApplicationList();
            applicationList.setApplications(finalApplicationList);
        }
        return  applicationList;
    }

    @Override
    public int findSubscribedUsersPerApplication(String applicationName,Date toSubscriptionDate) {
        List<String> subscribedUserList = new ArrayList<String>();
        List<Subscription> subscriptionList = subscriptionRepository.findSubscribedUsersPerApplication(applicationName,toSubscriptionDate);
        Date fromApplicationPublishDate = ReportUtils.findDateRange(toSubscriptionDate);
        if(subscriptionList != null && !subscriptionList.isEmpty()){
            for(Subscription subscription : subscriptionList){
                if(subscription.getDateSubscribed().getTime() >= fromApplicationPublishDate.getTime()){
                    subscribedUserList.add(subscription.getUser().getUsername());
                }
            }
        }
        return  subscribedUserList.size();
    }

    @Override
    public int findSubscribedActiveUsersPerApplication(String applicationName,Date toSubscriptionDate) {
        List<String> subscribedActiveUserList = new ArrayList<String>();
        List<Subscription> subscriptionList = subscriptionRepository.findSubscribedActiveUsersPerApplication(applicationName,toSubscriptionDate);
        Date fromApplicationPublishDate = ReportUtils.findDateRange(toSubscriptionDate);
        if(subscriptionList != null && !subscriptionList.isEmpty()){
            for(Subscription subscription : subscriptionList){
                if(subscription.getDateSubscribed().getTime() >= fromApplicationPublishDate.getTime()){
                    subscribedActiveUserList.add(subscription.getUser().getUsername());
                }
            }
        }
        return  subscribedActiveUserList.size();
    }

    public int findTerminatedSubscriptionsPerApplication(String applicationName,Date toSubscriptionDate) {
        List<String> terminatedApplicationList = new ArrayList<String>();
        List<Subscription> subscriptionList = subscriptionRepository.findTerminatedSubscriptionsPerApplication(applicationName,toSubscriptionDate);
        Date fromApplicationPublishDate = ReportUtils.findDateRange(toSubscriptionDate);
        if(subscriptionList != null && !subscriptionList.isEmpty()){
            for(Subscription subscription : subscriptionList){
                if(subscription.getDateUnsubscribed().getTime() >= fromApplicationPublishDate.getTime()){
                    terminatedApplicationList.add(subscription.getApplication().getName());
                }
            }
        }
        return  terminatedApplicationList.size();
    }

}
