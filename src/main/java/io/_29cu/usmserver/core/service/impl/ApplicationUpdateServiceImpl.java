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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.ApplicationUpdate;
import io._29cu.usmserver.core.repositories.ApplicationRepository;
import io._29cu.usmserver.core.repositories.ApplicationUpdateRepository;
import io._29cu.usmserver.core.service.ApplicationUpdateService;

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
    	applicationUpdate = applicationUpdateRepository.save(applicationUpdate);
    	applicationRepository.save(applicationUpdate.getApplication());
    	return applicationUpdate;
    }

}
