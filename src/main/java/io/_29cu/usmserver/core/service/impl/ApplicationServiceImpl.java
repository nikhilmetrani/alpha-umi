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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io._29cu.usmserver.common.utilities.AppHelper;
import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.repositories.ApplicationRepository;
import io._29cu.usmserver.core.repositories.CategoryRepository;
import io._29cu.usmserver.core.repositories.UserRepository;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.utilities.ApplicationList;
import io._29cu.usmserver.core.service.utilities.DummyData;

@Component
public class ApplicationServiceImpl implements ApplicationService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ApplicationList getAllApplications() {
        DummyData.createDummyData(userRepository, applicationRepository, categoryRepository);
        ApplicationList appList = new ApplicationList();
        appList.setApplications(AppHelper.getInstance().convertIterableToList(applicationRepository.findAll()));
        return appList;
    }

    @Override
    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public Application modifyApplication(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public Application findApplicationByDeveloperAndName(String developerId, String applicationName) {
        return applicationRepository.findApplicationByDeveloperAndName(developerId, applicationName);
    }

    @Override
    public Application findApplicationByDeveloperAndId(String developerId, String applicationId) {
        return applicationRepository.findApplicationByDeveloperAndId(developerId, applicationId);
    }

    @Override
    public Application findApplication(String id) {
        return applicationRepository.findOne(id);
    }

    @Override
    public ApplicationList findApplicationsByDeveloper(String developerId) {
        List<Application> appList = applicationRepository.findApplicationsByDeveloper(developerId);
        ApplicationList applicationList = new ApplicationList();
        applicationList.setApplications(appList);
        return  applicationList;
    }

    @Override
    public ApplicationList findApplicationsByCategory(String category) {
        List<Application> appList = applicationRepository.findApplicationsByCategory(category);
        ApplicationList applicationList = new ApplicationList();
        applicationList.setApplications(appList);
        return  applicationList;
    }

    @Override
    public Application updateApplication(Application application) {
        application = applicationRepository.save(application);
        return application;
    }
}
