/**
 * Copyright 2016 - 29cu.io and the authors of alpha-umi open source project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
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

import io._29cu.usmserver.core.model.entities.Installer;
import io._29cu.usmserver.core.repositories.InstallerRepository;
import io._29cu.usmserver.core.repositories.UserRepository;
import io._29cu.usmserver.core.service.InstallerService;

@Component
public class InstallerServiceImpl implements InstallerService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	InstallerRepository installerRepository;

	@Override
	public Installer createInstaller(Installer installer) {
		return installerRepository.save(installer);
	}

	@Override
	public Installer updateInstaller(Installer installer) {
		return installerRepository.save(installer);
	}

	@Override
	public List<Installer> findAllInstallersByApplicationId(String applicationId) {
		return installerRepository.findAllInstallersByApplicationId(applicationId);
	}

	@Override
	public List<Installer> findAllInstallersByApplicationUpdateId(String applicationUpdateId) {
		return installerRepository.findAllInstallersByApplicationUpdateId(applicationUpdateId);
	}

	@Override
	public Installer findInstallerByApplicationId(long installerId, String applicationId) {
		return installerRepository.findInstallerByApplicationId(installerId, applicationId);
	}

	@Override
	public void deleteInstaller(Long installerId) {
		installerRepository.delete(installerId);
	}
}
