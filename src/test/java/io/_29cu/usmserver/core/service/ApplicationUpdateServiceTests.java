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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

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

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.ApplicationUpdate;
import io._29cu.usmserver.core.model.entities.Authority;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.model.enumerations.AuthorityName;
import io._29cu.usmserver.core.repositories.ApplicationRepository;
import io._29cu.usmserver.core.repositories.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ApplicationUpdateServiceTests {
    @Autowired
    private ApplicationUpdateService appUpdateService;
    @Autowired
    private ApplicationService appService;
    @Autowired
    private UserService userService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;

    private User developer;
    private Application application;
    private Application application1;
    private Application application2;
    private ApplicationUpdate applicationUpdate;
    private ApplicationUpdate applicationUpdate1;
    private ApplicationUpdate applicationUpdate2;

    @Before
    @Transactional
    @Rollback(false)
    public void setup() {
        developer = new User();
        developer.setId(99l);
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
        application.setState(AppState.Staging);
        application.setDescription("test description");
        application.setVersion("1.0");
        application.setWhatsNew("test");
        application = appService.createApplication(application);

        applicationUpdate = new ApplicationUpdate();
        applicationUpdate.setName("application");
        applicationUpdate.setDescription("test description");
        applicationUpdate.setVersion("1.1");
        applicationUpdate.setWhatsNew("What is new");
        application.setState(AppState.Active);

        application1 = new Application();
        application1.setName("application1");
        application1.setDeveloper(developer);
        application1.setState(AppState.Active);
        application1.setDescription("test description");
        application1.setVersion("1.0");
        application1.setWhatsNew("test");
        application1 = appService.createApplication(application1);


        application2 = new Application();
        application2.setName("application2");
        application2.setDeveloper(developer);
        application2.setState(AppState.Staging);
        application2.setDescription("test description1");
        application2.setVersion("1.0");
        application2.setWhatsNew("test");
        application2 = appService.createApplication(application2);

        applicationUpdate1 = new ApplicationUpdate();
        applicationUpdate2 = new ApplicationUpdate();

        applicationUpdate1.setTarget(application1);
        applicationUpdate2.setTarget(application2);

        applicationUpdate1.setName("application1");
        applicationUpdate1.setDescription("test description");
        applicationUpdate1.setVersion("1.1");
        applicationUpdate1.setWhatsNew("What is new");

        applicationUpdate2.setName("application2");
        applicationUpdate2.setDescription("test description");
        applicationUpdate2.setVersion("2.0");
        applicationUpdate2.setWhatsNew("What is new");
    }

	@After
	public void tearDown() {
		applicationRepository.delete(application.getId());
		applicationRepository.delete(application1.getId());
		applicationRepository.delete(application2.getId());
		userRepository.delete(developer.getId());
	}

    /*@Test
    @Transactional
    public void testFindByApplication() {
        ApplicationUpdate applicationUpdate = appUpdateService.createApplicationUpdateByDeveloper(application1.getDeveloper().getId(), applicationUpdate1);
    	ApplicationUpdate fromDb = appUpdateService.findByApplication(application1.getId());
        assertNotNull(fromDb);
        assertEquals("Application Update Version does not match", applicationUpdate.getVersion(), fromDb.getVersion());
        assertEquals("Application name does not match", application1.getName(), fromDb.getTarget().getName());
    }*/

    @Test
    @Transactional
    public void testCreateApplicationUpdate() {
        applicationUpdate.setTarget(application);
        ApplicationUpdate fromDb = appUpdateService.createApplicationUpdate(applicationUpdate);
        assertNotNull(fromDb);
        assertEquals("Application Update Version does not match", applicationUpdate.getVersion(), fromDb.getVersion());
        assertEquals("Application name does not match", application.getName(), fromDb.getTarget().getName());
        assertEquals("Application state does not match", application.getState(), fromDb.getTarget().getState());
    }

    @Test
    @Transactional
    public void testCreateApplicationUpdateByDeveloper() {
        ApplicationUpdate applicationUpdate = appUpdateService.createApplicationUpdateByDeveloper(application1.getDeveloper().getId(), applicationUpdate1);
        assertNotNull("applicationUpdate should not be not null",applicationUpdate);
        assertEquals("Developer should be same",applicationUpdate1.getTarget().getDeveloper().getId(),application1.getDeveloper().getId());
        assertEquals("Returned Application state should be Active",applicationUpdate.getTarget().getState(),AppState.Active);
    }

    @Test
    @Transactional
    public void testModifyApplicationUpdateByDeveloper() {
        ApplicationUpdate applicationUpdate = appUpdateService.modifyApplicationUpdateByDeveloper(application2.getDeveloper().getId(), applicationUpdate2);
        assertNotNull("applicationUpdate should not be not null",applicationUpdate);
        assertEquals("Developer should be same",applicationUpdate2.getTarget().getDeveloper().getId(),application2.getDeveloper().getId());
        assertEquals("Returned Application state should be Staging",applicationUpdate.getTarget().getState(),AppState.Staging);
    }
}
