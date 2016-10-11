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
import io._29cu.usmserver.core.model.entities.Category;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.ApplicationService;
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

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ApplicationControllerTests {
    @InjectMocks
    private ApplicationController applicationController;

    @Mock
    private ApplicationService applicationService;

    private MockMvc mockMvc;

    private User appOwner;
    Application app;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationController).build();

        appOwner = new User();
        appOwner.setId(22L);
        appOwner.setEmail("owner@test.com");
        appOwner.setName("Test Owner");
        app = new Application();
        app.setDeveloper(appOwner);
        app.setName("Application A");
        app.setId(23L);
        app.setCategory(new Category("Productivity"));

    }

    @Test
    public void  testGetApplication() throws Exception {
        when(applicationService.findApplication(23L)).thenReturn(app);

        mockMvc.perform(get("/api/1/store/application/23"))
                .andExpect(jsonPath("$.name",
                        equalTo("Application A")))
                .andExpect(status().isOk());
    }

    @Test
    public void  testGetApplicationErrorHandling() throws Exception {
        when(applicationService.findApplication(23L)).thenReturn(null);

        mockMvc.perform(get("/api/1/store/application/23"))
                .andExpect(status().isNotFound());
    }
}