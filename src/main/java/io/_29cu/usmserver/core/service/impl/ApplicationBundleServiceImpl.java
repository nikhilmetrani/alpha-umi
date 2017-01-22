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
import io._29cu.usmserver.core.model.entities.ApplicationBundle;
import io._29cu.usmserver.core.repositories.ApplicationBundleRepository;
import io._29cu.usmserver.core.repositories.CategoryRepository;
import io._29cu.usmserver.core.service.ApplicationBundleService;
import io._29cu.usmserver.core.service.utilities.ApplicationBundleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationBundleServiceImpl implements ApplicationBundleService{

    @Autowired
    ApplicationBundleRepository applicationBundleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ApplicationBundleList getAllApplicationBundles() {
        ApplicationBundleList appBundleList = new ApplicationBundleList();
        appBundleList.setApplicationBundles(AppHelper.getInstance().convertIterableToList(applicationBundleRepository.findAll()));
        return appBundleList;
    }

    @Override
    public ApplicationBundle createApplicationBundle(ApplicationBundle applicationBundle) {
        return applicationBundleRepository.save(applicationBundle);
    }

    @Override
    public ApplicationBundle updateApplicationBundle(ApplicationBundle applicationBundle) {
        return applicationBundleRepository.save(applicationBundle);
    }

    @Override
    public ApplicationBundle findApplicationBundleByDeveloperAndName(Long developerId, String applicationBundleName) {
        return applicationBundleRepository.findApplicationBundleByDeveloperAndName(developerId, applicationBundleName);
    }

    @Override
    public ApplicationBundle findApplicationBundleByDeveloperAndId(Long developerId, String applicationBundleId) {
        return applicationBundleRepository.findApplicationBundleByDeveloperAndId(developerId, applicationBundleId);
    }

    @Override
    public ApplicationBundle findApplicationBundle(String id) {
        return applicationBundleRepository.findOne(id);
    }

    @Override
    public ApplicationBundleList findApplicationBundlesByDeveloper(Long developerId) {
        List<ApplicationBundle> appBundleList = applicationBundleRepository.findApplicationBundlesByDeveloper(developerId);
        ApplicationBundleList applicationBundleList = new ApplicationBundleList();
        applicationBundleList.setApplicationBundles(appBundleList);
        return  applicationBundleList;
    }

    @Override
    public ApplicationBundleList findApplicationBundlesByCategory(String category) {
        List<ApplicationBundle> appBundleList = applicationBundleRepository.findApplicationBundlesByCategory(category);
        ApplicationBundleList applicationBundleList = new ApplicationBundleList();
        applicationBundleList.setApplicationBundles(appBundleList);
        return  applicationBundleList;
    }
}
