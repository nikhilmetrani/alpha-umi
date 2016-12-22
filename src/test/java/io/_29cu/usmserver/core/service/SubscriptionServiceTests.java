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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
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
import io._29cu.usmserver.core.model.entities.Authority;
import io._29cu.usmserver.core.model.entities.Category;
import io._29cu.usmserver.core.model.entities.Subscription;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.model.enumerations.AuthorityName;
import io._29cu.usmserver.core.repositories.ApplicationRepository;
import io._29cu.usmserver.core.repositories.CategoryRepository;
import io._29cu.usmserver.core.repositories.SubscriptionRepository;
import io._29cu.usmserver.core.repositories.UserRepository;
import io._29cu.usmserver.core.service.utilities.ApplicationList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SubscriptionServiceTests {
    @Autowired
    private ApplicationService service;
    @Autowired
    private UserService userService;
    @Autowired
    private SubscriptionService subscriptionService;

    private User developer, consumer;
    private Application application, application2;
    private Category cat1, cat2;
    private Subscription subscription;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private SubscriptionRepository subscriptionRepository;

    @Before
    @Transactional
    @Rollback(false)
    public void setup() {
        developer = new User();
        developer.setUsername("developer1");
        developer.setEmail("developer1@email.com");
        Authority authority = new Authority();
        authority.setName(AuthorityName.ROLE_CONSUMER);
        List<Authority> authList = new ArrayList<>();
        authList.add(authority);
        authority = new Authority();
        authority.setName(AuthorityName.ROLE_DEVELOPER);
        developer.setAuthorities(authList);
        developer.setEnabled(true);
        developer = userService.createUser(developer);

        consumer = new User();
        consumer.setUsername("consumer1");
        consumer.setEmail("consumer1@email.com");
        authority = new Authority();
        authority.setName(AuthorityName.ROLE_CONSUMER);
        authList = new ArrayList<>();
        authList.add(authority);
        consumer.setAuthorities(authList);
        consumer.setEnabled(true);
        consumer = userService.createUser(consumer);

        cat1 = categoryRepository.save(new Category("ImageEditor"));
        cat2 = categoryRepository.save(new Category("CreativeTools"));
        
        application = new Application();
        application.setName("application");
        application.setDeveloper(developer);
        application.setState(AppState.Staging);
        application.setDescription("test description");
        application.setVersion("1.0");
        application.setWhatsNew("test");
        application.setCategory(cat1);
        application = service.createApplication(application);

        application2 = new Application();
        application2.setName("Application2");
        application2.setDeveloper(developer);
        application2.setState(AppState.Active);
        application2.setDescription("test description2");
        application2.setVersion("2.0");
        application2.setWhatsNew("test2");
        application2.setCategory(cat2);
        application2 = service.createApplication(application2);

        subscription = new Subscription();
        subscription.setApplication(application2);
        subscription.setUser(consumer);
        subscription.setDateSubscribed(new Date(System.currentTimeMillis()));
        subscription.setActive(true);
        subscription = subscriptionService.subscribeApplication(subscription);
    }

	@After
	public void tearDown() {
		userRepository.delete(developer.getId());
		applicationRepository.delete(application.getId());
		applicationRepository.delete(application2.getId());
		categoryRepository.delete(cat1.getId());
		categoryRepository.delete(cat2.getId());
		subscriptionRepository.delete(subscription.getId());
	}

    @Test
    @Transactional
    public void testGetSubscribedApplications() {
        ApplicationList fromDb = subscriptionService.getSubscribedApplications(consumer.getId());
        assertNotNull("ApplicationList is Null", fromDb);
        assertNotNull("ApplicationList is Null", fromDb.getItems());
        assertFalse("ApplicationList is empty", fromDb.getItems().isEmpty());
        for(Application app: fromDb.getItems()) {
        	assertTrue("AppState is Not Active", app.getState().equals(AppState.Active));
        }
    }

 }
