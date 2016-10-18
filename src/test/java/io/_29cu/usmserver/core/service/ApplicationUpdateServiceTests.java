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
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;

import java.util.ArrayList;
import java.util.List;

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
        developer.setName("developer");
        developer.setEmail("developer@email.com");
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
        application2.setState(AppState.Active);
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

    @Test
    @Transactional
    public void testFindByApplication() {
        applicationUpdate.setTarget(application);
    	applicationUpdate = appUpdateService.createApplicationUpdate(applicationUpdate);
        ApplicationUpdate fromDb = appUpdateService.findByApplication(application.getId());
        assertNotNull(fromDb);
        assertEquals("Application Update Version does not match", applicationUpdate.getVersion(), fromDb.getVersion());
        assertEquals("Application name does not match", application.getName(), fromDb.getTarget().getName());
    }

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
        List<ApplicationUpdate> appListToBePublished = new ArrayList<ApplicationUpdate>();
        appListToBePublished.add(applicationUpdate1);
        appListToBePublished.add(applicationUpdate2);
        List<ApplicationUpdate> applicationUpdateList = appUpdateService.createApplicationUpdateByDeveloper(application1.getDeveloper().getId(), appListToBePublished);
        assertNotNull("List should not be not null",applicationUpdateList);
        assertEquals("Size should be 2", applicationUpdateList.size(),2);
        assertEquals("Developer should be same",applicationUpdateList.get(0).getTarget().getDeveloper().getId(),applicationUpdateList.get(1).getTarget().getDeveloper().getId());
    }
}
