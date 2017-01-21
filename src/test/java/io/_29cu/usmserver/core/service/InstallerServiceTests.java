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

import io._29cu.usmserver.core.model.entities.*;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.model.enumerations.AuthorityName;
import io._29cu.usmserver.core.model.enumerations.OperatingSystem;
import io._29cu.usmserver.core.model.enumerations.Platform;
import io._29cu.usmserver.core.repositories.ApplicationRepository;
import io._29cu.usmserver.core.repositories.InstallerRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class InstallerServiceTests {
    @Autowired
    private InstallerService service;
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private InstallerRepository installerRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    private User developer;
    private Installer installer;
    private Application application;

    @Before
    @Transactional
    @Rollback(false)
    public void setup() {

	    developer = new User();
        developer.setUsername("developer");
        developer.setEmail("developer@email.com");
        Authority authority = new Authority();
        authority.setName(AuthorityName.ROLE_CONSUMER);
        List<Authority> authList = new ArrayList<>();
        authList.add(authority);
        authority = new Authority();
        authority.setName(AuthorityName.ROLE_DEVELOPER);
        developer.setAuthorities(authList);
        developer.setEnabled(true);
        developer = userService.createUser(developer);

        application = new Application();
        application.setName("application");
        application.setDeveloper(developer);
        application.setState(AppState.Active);
        application.setDescription("test description");
        application.setVersion("1.0");
        application.setWhatsNew("test");
        application = applicationService.createApplication(application);

        installer = new Installer();
        installer.setApplication(application);
        installer.setDownloadUrl("http://www.abc.com");
        installer.setExpressInstallCommand("command");
        installer.setOs(OperatingSystem.Windows);
        installer.setPlatform(Platform.x64);
        installer = service.createInstaller(installer);
    }

    @After
    public void tearDown() {
	    installerRepository.delete(installer);
        applicationRepository.delete(application.getId());
    }

    @Test
    @Transactional
    public void testCreateInstaller() {
        Installer fromDb = service.createInstaller(installer);
	    assertNotNull(fromDb);
        assertEquals("Installer id does not match", installer.getId(), fromDb.getId());
    }

	@Test
	@Transactional
	public void testUpdateInstaller() {
    	installer.setExpressInstallCommand("new command");
		Installer fromDb = service.updateInstaller(installer);
		assertNotNull(fromDb);
		assertEquals("Installer command does not updated", installer.getExpressInstallCommand(), fromDb.getExpressInstallCommand());
	}

	@Test
	@Transactional
	public void testFindAllInstallersByApplicationId() {
		List<Installer> fromDb = service.findAllInstallersByApplicationId(application.getId());
		assertNotNull(fromDb);
		assertEquals("Installer list size should be 1", fromDb.size(), 1);
	}

	@Test
	@Transactional
	public void testFindInstallerByApplicationId() {
		Installer fromDb = service.findInstallerByApplicationId(installer.getId(), application.getId());
		assertNotNull(fromDb);
		assertEquals("Installer id does not match", installer.getId(), fromDb.getId());
	}

	@Test
	@Transactional
	public void testDeleteInstaller() {
		service.deleteInstaller(installer.getId());
		Installer fromDb = service.findInstallerByApplicationId(installer.getId(), application.getId());
		assertEquals("Installer should be null", fromDb, null);
		installer = service.createInstaller(installer);
	}
}