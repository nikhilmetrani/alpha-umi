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

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ApplicationServiceTests {
    @Autowired
    private ApplicationService service;
    @Autowired
    private UserService userService;

    private User developer;
    private Application application;

    @Before
    @Transactional
    @Rollback(false)
    public void setup() {
        developer = new User();
        developer.setName("developer");
        developer.setEmail("developer@email.com");
        userService.createUser(developer);

        application = new Application();
        application.setName("application");
        application.setDeveloper(developer);
        application.setState(AppState.Staging);
        application.setDescription("test description");
        application.setVersion("1.0");
        application.setWhatsNew("test");
        service.createApplication(application);
    }

    @Test
    @Transactional
    public void testFind() {
        Application fromDb = service.findApplication(application.getId());
        assertNotNull(fromDb);
        assertEquals("Application name does not match", application.getName(), fromDb.getName());
        assertEquals("Application developer does not match", application.getDeveloper(), fromDb.getDeveloper());
    }

    @Test
    @Transactional
    public void testFindApplicationByDeveloperAndName() {
        Application fromDb = service.findApplicationByDeveloperAndName(developer.getId(), application.getName());
        assertNotNull(fromDb);
        assertEquals("Application name does not match", application.getName(), fromDb.getName());
        assertEquals("Application developer does not match", application.getDeveloper(), fromDb.getDeveloper());
    }

    @Test
    @Transactional
    public void testFindApplicationByDeveloperAndId() {
        Application fromDb = service.findApplicationByDeveloperAndId(developer.getId(), application.getId());
        assertNotNull(fromDb);
        assertEquals("Application Id does not match", application.getId(), fromDb.getId());
        assertEquals("Application developer does not match", application.getDeveloper(), fromDb.getDeveloper());
    }

    @Test
    @Transactional
    public void testCreateApplication() {
        Application fromDb = service.createApplication(application);
        assertNotNull(fromDb);
        assertEquals("Application name does not match", application.getId(), fromDb.getId());
        assertEquals("Application developer does not match", application.getDeveloper(), fromDb.getDeveloper());
    }
}
