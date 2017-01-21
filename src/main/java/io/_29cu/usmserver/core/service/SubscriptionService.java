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

package io._29cu.usmserver.core.service;

import io._29cu.usmserver.core.model.entities.Subscription;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.utilities.ApplicationList;
import io._29cu.usmserver.core.model.entities.Application;

import java.util.Date;

public interface SubscriptionService {
    Subscription subscribeApplication(String applicationId, User user);
    Subscription unsubscribeApplication(String applicationId, User user);
    ApplicationList getSubscribedApplications(long userId);
    Subscription findSubscriptionByUserIdAndApplicationId(Long userId, String applicationId);
    Subscription subscribeApplication(Subscription subscription);
    ApplicationList getTrendingApplications();
}
