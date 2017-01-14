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

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.ApplicationUpdate;
import io._29cu.usmserver.core.model.entities.Installer;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.repositories.ApplicationRepository;
import io._29cu.usmserver.core.repositories.ApplicationUpdateRepository;
import io._29cu.usmserver.core.service.ApplicationUpdateService;
import io._29cu.usmserver.core.service.InstallerService;

@Component
public class ApplicationUpdateServiceImpl implements ApplicationUpdateService{
    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    ApplicationUpdateRepository applicationUpdateRepository;

    @Autowired
    InstallerService installerService;

    @Override
    public ApplicationUpdate findByApplication(String applicationId) {
    	return applicationUpdateRepository.findByApplication(applicationId);
    }

    @Override
    public ApplicationUpdate createApplicationUpdate(ApplicationUpdate applicationUpdate) {
        //Application application = applicationRepository.save(applicationUpdate.getTarget());
    	ApplicationUpdate savedApplicationUpdate = applicationUpdateRepository.save(applicationUpdate);
    	List<Installer> oldInstallers = installerService.findAllInstallersByApplicationUpdateId(applicationUpdate.getId());
    	if(oldInstallers != null) {
			for(Installer installer : oldInstallers) {
				installerService.deleteInstaller(installer.getId());
			}
    	}
    	List<Installer> installers = applicationUpdate.getInstallers();
		if(installers != null) {
			for(Installer installer : installers) {
				installer.setApplicationUpdate(savedApplicationUpdate);
				installerService.updateInstaller(installer);
			}
		}
    	return savedApplicationUpdate;
    }

    @Override
    public ApplicationUpdate createApplicationUpdateByDeveloper(long developerId, ApplicationUpdate newAppUpdate) {
        if(newAppUpdate != null){
            // fetches the existing Active/Recalled application by the developer. If application state is something else, then throws execption
            Application targetApp = applicationRepository.findOne(newAppUpdate.getTarget().getId());
            if (null == targetApp || targetApp.getState() == AppState.Active || targetApp.getState() == AppState.Recalled)
                newAppUpdate.setTarget(targetApp);
            else
                return null;
            return createApplicationUpdate(newAppUpdate);
        }
        return null;
    }


    @Override
    public ApplicationUpdate modifyApplicationUpdateByDeveloper(long developerId, ApplicationUpdate appUpdate) {
        if(appUpdate != null){
            // fetches the existing Active/Recalled application by the developer. If application state is something else, then throws execption
            Application targetApp = applicationRepository.findOne(appUpdate.getTarget().getId());
            if (null == targetApp || targetApp.getState() != AppState.Staging)
                return null; // Throw exception instead.
            appUpdate.setTarget(targetApp);

            return createApplicationUpdate(appUpdate);

        }
        return null;
    }
}
