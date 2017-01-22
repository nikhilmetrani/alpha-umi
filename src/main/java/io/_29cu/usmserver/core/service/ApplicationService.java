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

import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.service.utilities.ApplicationList;

@Component
public interface ApplicationService {
    public ApplicationList getAllApplications();
    public Application createApplication(Application application);
    public Application updateApplication(Application application);
    public Application recallApplication(Application application);
    public Application findApplicationByDeveloperIdAndAppName(long developerId, String applicationName);
    public Application findApplicationByDeveloperIdAndAppId(long developerId, String applicationId);
    public Application findApplicationByUsernameAndAppName(String username, String applicationName);
    public Application findApplicationByUsernameAndAppId(String username, String applicationId);
    public Application findApplication(String id);
    public ApplicationList findApplicationsByDeveloper(long developerId);
    public ApplicationList findApplicationsByCategory(String category);
    public ApplicationList findApplicationsByCategoryAndState(String category, AppState state);
    public ApplicationList getAllActiveApplications();
    public Boolean blockApplication(Application application);
    public ApplicationList findApplicationsByKeyword(String keyword);
    public ApplicationList findApplicationsByCategoryAndKeyword(Long categoryId, String keyword);
    public ApplicationList findAllActiveApplicationsByDeveloper(long developerId);

}
