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

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.utilities.ApplicationList;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class StoreControllerTests {
    @InjectMocks
    private StoreController storeController;

    @Mock
    private ApplicationService applicationService;

    private MockMvc mockMvc;

    private User appOwner;
    private ApplicationList appList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(storeController).build();

        appOwner = new User();
        appOwner.setId(22L);
        appOwner.setEmail("owner@test.com");
        appOwner.setName("Test Owner");

        List<Application> list = new ArrayList<>();

        appList = new ApplicationList();

        Application appA = new Application();
        appA.setDeveloper(appOwner);
        appA.setName("Application A");
        appA.setId(23L);
        appA.setCategory("Productivity");
        list.add(appA);

        Application appB = new Application();
        appB.setDeveloper(appOwner);
        appB.setName("Application B");
        appB.setId(24L);
        appB.setCategory("Development");
        list.add(appB);

        appList.setApplications(list);
    }

    @Test
    public void  testStore() throws Exception {
        when(applicationService.getAllApplications()).thenReturn(appList);

        mockMvc.perform(get("/api/1/store"))
                .andExpect(jsonPath("$.applications[*].name",
                        hasItems(endsWith("Application A"), endsWith("Application B"))))
                .andExpect(status().isOk());
    }

    @Test
    public void  testStoreErrorHandling() throws Exception {
        when(applicationService.getAllApplications()).thenReturn(null);

        mockMvc.perform(get("/api/1/store"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void  testGetApplicationByCategory() throws Exception {
        List<Application> list = new ArrayList<>();
        Application appB = new Application();
        appB.setDeveloper(appOwner);
        appB.setName("Application B");
        appB.setId(24L);
        appB.setCategory("Development");
        list.add(appB);
        appList.setApplications(list);

        when(applicationService.findApplicationsByCategory("Development")).thenReturn(appList);

        mockMvc.perform(get("/api/1/store/Development"))
                .andExpect(jsonPath("$.applications[*].category",
                        hasItems(endsWith("Development"))))
                .andExpect(status().isOk());
    }

    @Test
    public void  testGetApplicationByCategoryErrorHandling() throws Exception {
        when(applicationService.findApplicationsByCategory("Development")).thenReturn(null);

        mockMvc.perform(get("/api/1/store/Development"))
                .andExpect(status().isBadRequest());
    }
}
