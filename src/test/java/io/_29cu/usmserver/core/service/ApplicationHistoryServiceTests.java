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
import io._29cu.usmserver.core.model.entities.ApplicationHistory;
import io._29cu.usmserver.core.model.entities.Authority;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.model.enumerations.AuthorityName;
import io._29cu.usmserver.core.repositories.ApplicationHistoryRepository;
import io._29cu.usmserver.core.repositories.ApplicationRepository;
import io._29cu.usmserver.core.repositories.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ApplicationHistoryServiceTests {

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private UserService userService;

    private User developer;
    private Application application;

    @Autowired
    private ApplicationHistoryService applicationHistoryService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationHistoryRepository applicationHistoryRepository;
    
    private ApplicationHistory applicationHistory;

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
        application.setName("applicationHistoryTest");
        application.setDeveloper(developer);
        application.setState(AppState.Active);
        application.setDescription("applicationHistoryTest description");
        application.setVersion("10.0");
        application.setWhatsNew("applicationHistoryTest");
        application = applicationService.createApplication(application);

        applicationHistory = new ApplicationHistory();
        applicationHistory.setApplication(application);
        applicationHistory.setName(application.getName());
        applicationHistory.setVersion(application.getVersion());
        applicationHistory.setWhatsNew(application.getWhatsNew());
        applicationHistory = applicationHistoryService.createApplicationHistory(applicationHistory);
    }

	@After
	public void tearDown() {
		applicationHistoryRepository.delete(applicationHistory.getId());
		applicationRepository.delete(application.getId());
		userRepository.delete(developer.getId());
	}

    @Test
    @Transactional
    public void testCreateApplicationHistory() {
        ApplicationHistory fromDb = applicationHistoryService.createApplicationHistory(applicationHistory);
        assertNotNull(fromDb);
        assertEquals("Application name does not match", applicationHistory.getId(), fromDb.getId());
        assertEquals("Application developer does not match", applicationHistory.getVersion(), fromDb.getVersion());
    }

}
