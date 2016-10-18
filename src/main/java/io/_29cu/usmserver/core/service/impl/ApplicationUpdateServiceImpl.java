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

import io._29cu.usmserver.core.model.entities.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.ApplicationUpdate;
import io._29cu.usmserver.core.repositories.ApplicationRepository;
import io._29cu.usmserver.core.repositories.ApplicationUpdateRepository;
import io._29cu.usmserver.core.service.ApplicationUpdateService;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApplicationUpdateServiceImpl implements ApplicationUpdateService{
    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    ApplicationUpdateRepository applicationUpdateRepository;

    @Override
    public ApplicationUpdate findByApplication(String applicationId) {
    	return applicationUpdateRepository.findByApplication(applicationId);
    }

    @Override
    public ApplicationUpdate createApplicationUpdate(ApplicationUpdate applicationUpdate) {
        Application application = applicationRepository.save(applicationUpdate.getTarget());
    	applicationUpdate = applicationUpdateRepository.save(applicationUpdate);
    	return applicationUpdate;
    }

    /**
     * appListToBePublished - List of applications to be Published
     **/

    @Override
    public List<ApplicationUpdate> createApplicationUpdateByDeveloper(String developerId, List<ApplicationUpdate> appListToBePublished) {
        if(appListToBePublished != null && !appListToBePublished.isEmpty()){
            // updatedAppList - List of applications for which update to be Published, this excludes all the staging applications
            List<ApplicationUpdate> updatedAppList = new ArrayList<ApplicationUpdate>();
            // fetches all the existing applications(existingAppList) by the developer
            List<Application> existingAppList = applicationRepository.findApplicationsByDeveloper(developerId);
            if(existingAppList != null && !existingAppList.isEmpty()){
                for(ApplicationUpdate appToBePublished : appListToBePublished){
                    for(Application updatedApp : existingAppList){
                        if(updatedApp.getName().equals(appToBePublished.getName()) && updatedApp.getDeveloper().getName().equals(appToBePublished.getTarget().getDeveloper().getName())){
                            updatedAppList.add(appToBePublished);
                        }
                    }
                }
            }
            if(!updatedAppList.isEmpty()){
                for(ApplicationUpdate applicationUpdate : updatedAppList ){
                    applicationUpdate = createApplicationUpdate(applicationUpdate);
                }
                return updatedAppList;
            }
        }
        return appListToBePublished;
    }
}
