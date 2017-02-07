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

import io._29cu.usmserver.core.model.entities.DeveloperProfile;
import io._29cu.usmserver.core.repositories.DeveloperProfileRepository;
import io._29cu.usmserver.core.service.DeveloperProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DeveloperProfileServiceImpl implements DeveloperProfileService{
    @Autowired
    private DeveloperProfileRepository developerProfileRepository;

    @Override
    public DeveloperProfile findProfileByUserId(Long id) {
        return developerProfileRepository.findDeveloperProfileByUserId(id);
    }

    @Override
    public DeveloperProfile createProfile(DeveloperProfile profile) {
        return developerProfileRepository.save(profile);
    }

    @Override
    public DeveloperProfile modifyProfile(DeveloperProfile profile) {
        //Save the updated developer profile to repository
        return developerProfileRepository.save(profile);
    }
}
