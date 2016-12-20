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

package io._29cu.usmserver.controllers.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io._29cu.usmserver.core.model.entities.*;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.service.*;
import io._29cu.usmserver.core.service.utilities.ApplicationList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SubscriptionControllerTests {
    @InjectMocks
    private SubscriptionController subscriptionController;
    @Mock
    private SubscriptionService subscriptionService;
    @Mock
    private UserService userService;


    // @Mock not used here since they are mocked in setup()
    // Spring boot does not allow mocking with annotation.
    SecurityContext securityContextMocked;
    Authentication authenticationMocked;

    private MockMvc mockMvc;

    private User user;
    private Application application;
    private String uuid;

    private ApplicationList appList;
    private String appUUID2;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(subscriptionController).build();

        uuid = UUID.randomUUID().toString();
        appUUID2 = UUID.randomUUID().toString();

        user = new User();
        user.setId(1L);
        user.setEmail("owner@test.com");
        user.setUsername("Test Owner");
        user.setEnabled(true);

        application = new Application();
        application.setId(uuid);
        application.setCategory(new Category("Productivity"));
        application.setName("Dreamweaver");
        application.setState(AppState.Active);
        application.setVersion("1.0");
        application.setDeveloper(user);
        application.setDescription("test description");

        List<Application> list = new ArrayList<>();
        appList = new ApplicationList();

        Application appA = new Application();
        appA.setName("Application A");
        appA.setId(uuid);
        appA.setCategory(new Category("Productivity"));
        appA.setState(AppState.Active);
        list.add(appA);

        Application appB = new Application();
        appB.setName("Application B");
        appB.setId(appUUID2);
        appB.setCategory(new Category("Development"));
        appB.setState(AppState.Active);
        list.add(appB);
        
        appList.setApplications(list);

        // Let's mock the security context
        authenticationMocked = Mockito.mock(Authentication.class);
        securityContextMocked = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContextMocked.getAuthentication()).thenReturn(authenticationMocked);
        SecurityContextHolder.setContext(securityContextMocked);
    }

    @Test
    public void  testSubscribeApplicationIsForbidden() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(null);

        mockMvc.perform(post("/api/0/store/subscription/subscribe")
                .content("{'application':{'name':'PhotoShop','downloadUrl':'https://test.com/photoshop', 'version':'1.1', 'category': { 'name': 'Lifestyle'}, 'state': 'Staging', 'description':'PhotoShop Description'}, 'user':{'username': 'Test Owner', 'email': 'owner@test.com', 'password': 'password'},'dateSubscribed':'2016/11/11', 'active':'True'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void  testGetSubscribedApplications() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(user);
        when(subscriptionService.getSubscribedApplications(user.getId())).thenReturn(appList);

        mockMvc.perform(get("/api/0/store/subscription/myapps"))
                .andExpect(status().isOk());
    }

    @Test
    public void  testGetSubscribedApplicationsForForbidden() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(null);

        mockMvc.perform(get("/api/0/store/subscription/myapps"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void  testGetSubscribedApplicationsForNotFound() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(user);
        when(subscriptionService.getSubscribedApplications(user.getId())).thenReturn(null);

        mockMvc.perform(get("/api/0/store/subscription/myapps"))
                .andExpect(status().isNotFound());
    }
}
