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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.Category;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.UserService;
import io._29cu.usmserver.core.service.utilities.ApplicationList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ApplicationControllerTests {
    @InjectMocks
    private ApplicationController applicationController;

    @Mock
    private ApplicationService applicationService;

    @Mock 
    private UserService userService;

    private MockMvc mockMvc;

    private User appOwner;
    private ApplicationList applicationList;
    Application app;
    private String uuid;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationController).build();

        uuid = UUID.randomUUID().toString();

        appOwner = new User();
        appOwner.setId(1L);
        appOwner.setEmail("owner@test.com");
        appOwner.setUsername("Test Owner");
        app = new Application();
        app.setDeveloper(appOwner);
        app.setName("Application A");
        app.setId(uuid);
        app.setCategory(new Category("Productivity"));
        
        applicationList = new ApplicationList();
        ArrayList<Application> appList = new ArrayList<Application>();
        appList.add(app);
        applicationList.setApplications(appList);

    }

    @Test
    public void  testGetApplication() throws Exception {
        when(applicationService.findApplication(uuid)).thenReturn(app);

        mockMvc.perform(get("/api/1/store/application/" + uuid))
                .andExpect(jsonPath("$.name",
                        equalTo("Application A")))
                .andExpect(status().isOk());
    }

    @Test
    public void  testGetApplicationErrorHandling() throws Exception {
        when(applicationService.findApplication(uuid)).thenReturn(null);

        mockMvc.perform(get("/api/1/store/application/" + uuid))
                .andExpect(status().isNotFound());
    }

    @Test
    public void  testGetApplicationsByDeveloperForNotFound() throws Exception {
        when(applicationService.findApplicationsByDeveloper(1l)).thenReturn(null);

        mockMvc.perform(get("/api/1/store/application/developer/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void  testGetApplicationsByDeveloper() throws Exception {
    	when(applicationService.findApplicationsByDeveloper(1l)).thenReturn(applicationList);

        mockMvc.perform(get("/api/1/store/application/developer/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void  testUpdateDeveloperApplicationForAuthenticationFailed() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(null);

        mockMvc.perform(post("/api/0/admin/applications/" + uuid + "/block/"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void  testUpdateDeveloperApplicationForPreConditionFailed() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(appOwner);
        when(applicationService.findApplication(uuid)).thenReturn(null);

        mockMvc.perform(post("/api/0/admin/applications/" + uuid + "/block/"))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void  testUpdateDeveloperApplication() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(appOwner);
        when(applicationService.findApplication(uuid)).thenReturn(app);
        app.setState(AppState.Blocked);
        when(applicationService.blockApplication(app)).thenReturn(true);

        mockMvc.perform(post("/api/0/admin/applications/" + app.getId() + "/block/"))
                .andExpect(jsonPath("$",
                        equalTo(true)))
                .andExpect(status().isOk());
    }
}
