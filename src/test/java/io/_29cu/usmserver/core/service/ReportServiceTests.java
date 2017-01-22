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

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import io._29cu.usmserver.core.model.entities.Subscription;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.model.enumerations.AuthorityName;
import io._29cu.usmserver.core.repositories.ApplicationRepository;
import io._29cu.usmserver.core.repositories.SubscriptionRepository;
import io._29cu.usmserver.core.repositories.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ReportServiceTests {
    @Autowired
    private ReportService service;
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    private Date date1, date2;
    private User developer;
    private Application application;
    private Subscription subscription0,subscription1;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");

    @Before
    @Transactional
    @Rollback(false)
    public void setup() {

    	try {
		    date1 = dateFormatter.parse("20161212");
		    date2 = dateFormatter.parse("20161215");
	    } catch (ParseException e) {
		    date1 = new Date();
		    date2 = new Date();
	    }

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
	    application.setApplicationPublishDate(date1);
        application = applicationService.createApplication(application);

        subscription0 = new Subscription();
        subscription0.setActive(Boolean.TRUE);
        subscription0.setApplication(application);
	    subscription0.setDateSubscribed(date1);
        //subscription0.setId(1L);
        subscription0.setUser(developer);
        subscription0 = subscriptionService.subscribeApplication(subscription0);

        subscription1 = new Subscription();
        subscription1.setActive(Boolean.TRUE);
        subscription1.setApplication(application);
	    subscription1.setDateSubscribed(date2);
        //subscription1.setId(11L);
        subscription1.setUser(developer);
        subscription1 = subscriptionService.subscribeApplication(subscription1);
    }

    @After
    public void tearDown() {
        subscriptionRepository.delete(subscription0.getId());
        subscriptionRepository.delete(subscription1.getId());
        applicationRepository.delete(application.getId());
        userRepository.delete(developer.getId());
    }

    @Test
    @Transactional
    public void testFindSubscribedUsersPerApplication() {
        int count = service.findSubscriptionsPerApplication(application.getId(), date1, date2);
        assertEquals("count should be 2", count,2);
    }

	@Test
	@Transactional
	public void testFindActiveSubscribedUsersPerApplication() {
		int count = service.findActiveSubscriptionsPerApplication(application.getId(), date1, date2);
		assertEquals("count should be 2", count,2);
	}

	@Test
	@Transactional
	public void testFindTerminatedSubscribedUsersPerApplication() {
		int count = service.findTerminatedSubscriptionsPerApplication(application.getId(), date1, date2);
		assertEquals("count should be 0", count,0);
	}
}