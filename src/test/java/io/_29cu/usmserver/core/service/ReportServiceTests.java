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
import io._29cu.usmserver.core.model.entities.Authority;
import io._29cu.usmserver.core.model.entities.Subscription;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.model.enumerations.AuthorityName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import io._29cu.usmserver.core.service.utilities.ApplicationList;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ReportServiceTests {
    @Autowired
    private ReportService service;
    @Autowired
    private UserService userService;
    @Autowired
    private SubscriptionService subscriptionService;


    private User developer;
    private Application application,application1,application2,application3;
    private Subscription subscription,subscription1,subscription2,subscription3;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

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

        User developer1 = new User();
        developer1.setUsername("developer1");
        developer1.setEmail("developer1@email.com");
        Authority authority1 = new Authority();
        authority1.setName(AuthorityName.ROLE_CONSUMER);
        authority1 = new Authority();
        authority1.setName(AuthorityName.ROLE_DEVELOPER);
        developer1.setAuthorities(authList);
        developer1.setEnabled(true);
        developer1 = userService.createUser(developer1);

        User developer2 = new User();
        developer2.setUsername("developer2");
        developer2.setEmail("developer2@email.com");
        Authority authority2 = new Authority();
        authority2.setName(AuthorityName.ROLE_CONSUMER);
        authority2 = new Authority();
        authority2.setName(AuthorityName.ROLE_DEVELOPER);
        developer2.setAuthorities(authList);
        developer2.setEnabled(true);
        developer2 = userService.createUser(developer2);

        User developer3 = new User();
        developer3.setUsername("developer3");
        developer3.setEmail("developer3@email.com");
        Authority authority3 = new Authority();
        authority3.setName(AuthorityName.ROLE_CONSUMER);
        authority3 = new Authority();
        authority3.setName(AuthorityName.ROLE_DEVELOPER);
        developer3.setAuthorities(authList);
        developer3.setEnabled(true);
        developer3 = userService.createUser(developer3);

        application = new Application();
        application.setName("application");
        application.setDeveloper(developer);
        application.setState(AppState.Active);
        application.setDescription("test description");
        application.setVersion("1.0");
        application.setWhatsNew("test");


        try {
            application.setApplicationPublishDate(dateFormatter.parse("2016-12-12"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        service.createApplication(application);

        application1 = new Application();
        application1.setName("application1");
        application1.setDeveloper(developer);
        application1.setState(AppState.Active);
        application1.setDescription("test description");
        application1.setVersion("1.0");
        application1.setWhatsNew("test");
        try {
            application1.setApplicationPublishDate(dateFormatter.parse("2016-12-8"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        service.createApplication(application1);

        application2 = new Application();
        application2.setName("application2");
        application2.setDeveloper(developer);
        application2.setState(AppState.Active);
        application2.setDescription("test description");
        application2.setVersion("1.0");
        application2.setWhatsNew("test");
        try {
            application2.setApplicationPublishDate(dateFormatter.parse("2016-12-15"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        service.createApplication(application2);

        application3 = new Application();
        application3.setName("application3");
        application3.setDeveloper(developer);
        application3.setState(AppState.Active);
        application3.setDescription("test description");
        application3.setVersion("1.0");
        application3.setWhatsNew("test");
        try {
            application3.setApplicationPublishDate(dateFormatter.parse("2016-12-15"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        service.createApplication(application3);


        subscription = new Subscription();
        subscription.setActive(Boolean.TRUE);
        subscription.setApplication(application);

        try {
            subscription.setDateSubscribed(dateFormatter.parse("2016-12-15"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        subscription.setId(1L);
        subscription.setUser(developer);
        subscriptionService.subscribeApplication(subscription);

        subscription1 = new Subscription();
        subscription1.setActive(Boolean.TRUE);
        subscription1.setApplication(application);

        try {
            subscription1.setDateSubscribed(dateFormatter.parse("2016-12-24"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        subscription1.setId(11L);
        subscription1.setUser(developer1);
        subscriptionService.subscribeApplication(subscription1);

        subscription2 = new Subscription();
        subscription2.setActive(Boolean.FALSE);
        subscription2.setApplication(application);

        try {
            subscription2.setDateSubscribed(dateFormatter.parse("2016-12-12"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        subscription2.setId(111L);
        subscription2.setUser(developer2);
        subscriptionService.subscribeApplication(subscription2);


        subscription3 = new Subscription();
        subscription3.setActive(Boolean.FALSE);
        subscription3.setApplication(application);

        try {
            subscription3.setDateUnsubscribed(dateFormatter.parse("2016-12-16"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        subscription3.setId(1111L);
        subscription3.setUser(developer3);
        subscriptionService.subscribeApplication(subscription3);

    }


    @Test
    @Transactional
    public void testFindApplicationsByUserNameAndState() {
        //Date toDate1 = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date toDate1 = null;
        try {
            toDate1 = formatter.parse("2016-12-16");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ApplicationList applicationListfromDb = service.findApplicationsByUserNameAndState(developer.getUsername(),toDate1);
        assertNotNull(applicationListfromDb);
        assertEquals("Size of the list should be 3", applicationListfromDb.getApplications().size(), 3);
        assertEquals("First Application state should be active", applicationListfromDb.getApplications().get(0).getState(),AppState.Active);
        assertNotEquals("First Application should not be application 2", applicationListfromDb.getApplications().get(0),application1);
        assertEquals("Third Application state should be active", applicationListfromDb.getApplications().get(1).getState(),AppState.Active);
        assertNotEquals("Second Application should not be application 2", applicationListfromDb.getApplications().get(1),application1);
    }

    @Test
    @Transactional
    public void testfindSubscribedUsersPerApplication() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date toDate1 = null;
        try {
            toDate1 = formatter.parse("2016-12-16");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int count = service.findSubscribedUsersPerApplication(subscription.getApplication().getName(),toDate1);
        assertEquals("count should be 2", count,2);
    }

    @Test
    @Transactional
    public void testfindActiveSubscribedUsersPerApplication() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date toDate1 = null;
        try {
            toDate1 = formatter.parse("2016-12-16");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int activeCount = service.findSubscribedActiveUsersPerApplication(subscription.getApplication().getName(),toDate1);
        assertEquals("count should be 1", activeCount,1);

    }

    @Test
    @Transactional
    public void findTerminatedSubscriptionsPerApplication() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date toDate1 = null;
        try {
            toDate1 = formatter.parse("2016-12-16");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int activeCount = service.findTerminatedSubscriptionsPerApplication(subscription.getApplication().getName(),toDate1);
        assertEquals("count should be 1", activeCount,1);

    }
}
