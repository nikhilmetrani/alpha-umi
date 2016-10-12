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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.ApplicationUpdate;
import io._29cu.usmserver.core.model.entities.Category;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.ApplicationUpdateService;
import io._29cu.usmserver.core.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DeveloperApplicationControllerTests {
    @InjectMocks
    private DeveloperApplicationsController developerApplicationController;
    @Mock
    private ApplicationService applicationService;
    @Mock
    private ApplicationUpdateService applicationUpdateService;
    @Mock
    private UserService userService;

    // @Mock not used here since they are mocked in setup()
    // Spring boot does not allow mocking with annotation.
    SecurityContext securityContextMocked;
    Authentication authenticationMocked;

    private MockMvc mockMvc;

    private User developer;
    private Application application;
    private ApplicationUpdate applicationUpdate;
    private Application existingApplication;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(developerApplicationController).build();

        developer = new User();
        developer.setId(22L);
        developer.setEmail("owner@test.com");
        developer.setName("Test Owner");

        application = new Application();
        application.setId(22L);
        application.setCategory(new Category("Productivity"));
        application.setName("Dreamweaver");
        application.setState(AppState.Staging);
        application.setVersion("1.0");
        application.setDeveloper(developer);
        application.setDescription("test description");
        
        applicationUpdate = new ApplicationUpdate();
        applicationUpdate.setId(22L);
        applicationUpdate.setVersion("1.0");
        applicationUpdate.setWhatsNew("What is new");
        applicationUpdate.setApplication(application);

        existingApplication = new Application();
        existingApplication.setId(22L);
        existingApplication.setCategory(new Category("Productivity"));
        existingApplication.setName("Dreamweaver");
        existingApplication.setState(AppState.Staging);
        existingApplication.setVersion("1.0");
        existingApplication.setDeveloper(developer);

        // Let's mock the security context
        authenticationMocked = Mockito.mock(Authentication.class);
        securityContextMocked = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContextMocked.getAuthentication()).thenReturn(authenticationMocked);
        SecurityContextHolder.setContext(securityContextMocked);
    }

    @Test
    public void  testGetApplication() throws Exception {
        when(userService.findUserByPrincipal("22")).thenReturn(developer);
        when(authenticationMocked.getPrincipal()).thenReturn("22");
        when(applicationService.findApplicationByDeveloperAndId(22L, 22L)).thenReturn(application);

        mockMvc.perform(get("/api/0/developer/22/application/22"))
		        .andExpect(status().isOk());
    }

    @Test
    public void  testCreateDeveloperApplication() throws Exception {
        when(userService.findUserByPrincipal("22")).thenReturn(developer);
        when(userService.validateUserIdWithPrincipal(22L)).thenReturn(developer);
        when(authenticationMocked.getPrincipal()).thenReturn("22");
        when(userService.findUser(22L)).thenReturn(developer);
        when(applicationService.createApplication(any(Application.class))).thenReturn(application);

        mockMvc.perform(post("/api/0/developer/22/application/create")
                .content("{'name':'dreamweaver','downloadUrl':'https://test.com', 'version':'1.0', 'category': { 'name': 'Productivity'}, 'state': 'Staging', 'description':'test description'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
               // .andDo(print())
                .andExpect(jsonPath("$.name",
                        equalTo(application.getName())))
                .andExpect(jsonPath("$.rid",
                        equalTo(22)))
                .andExpect(jsonPath("$.version",
                        equalTo(application.getVersion())))
                .andExpect(jsonPath("$.state",
                        equalTo(application.getState().name())))
                .andExpect(jsonPath("$.category.name",
                        equalTo(application.getCategory().getName())))
                .andExpect(jsonPath("$.description",
                        equalTo(application.getDescription())))
                .andExpect(status().isOk());
    }

    @Test
    public void  testCheckApplicationNameExistsForDeveloper() throws Exception {
        when(userService.findUserByPrincipal("22")).thenReturn(developer);
        when(userService.validateUserIdWithPrincipal(22L)).thenReturn(developer);
        when(authenticationMocked.getPrincipal()).thenReturn("22");
        when(applicationService.findApplicationByDeveloperAndName(22L, "Dreamweaver")).thenReturn(application);

        mockMvc.perform(get("/api/0/developer/22/application/create")
        		.param("name", "Dreamweaver"))
		        .andExpect(status().isOk());
    }

    @Test
    public void  testCheckApplicationNameNotExistsForDeveloper() throws Exception {
        when(userService.findUserByPrincipal("22")).thenReturn(developer);
        when(userService.validateUserIdWithPrincipal(22L)).thenReturn(developer);
        when(authenticationMocked.getPrincipal()).thenReturn("22");
        when(applicationService.findApplicationByDeveloperAndName(22L, "Dreams")).thenReturn(null);

        mockMvc.perform(get("/api/0/developer/22/application/create")
        		.param("name", "Dreams"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void  testPublishDeveloperApplication() throws Exception {
        when(userService.findUserByPrincipal("22")).thenReturn(developer);
        when(authenticationMocked.getPrincipal()).thenReturn("22");
        when(applicationService.findApplicationByDeveloperAndId(22L, 22L)).thenReturn(application);
        when(applicationUpdateService.findByApplication(22L)).thenReturn(null);
        when(applicationUpdateService.createApplicationUpdate(any(ApplicationUpdate.class))).thenReturn(applicationUpdate);

        mockMvc.perform(post("/api/0/developer/22/application/22/publish")
                .content("{'whatsNew':'What is new', 'version':'1.0'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.whatsNew",
                        equalTo(applicationUpdate.getWhatsNew())))
                .andExpect(jsonPath("$.rid",
                        equalTo(22)))
                .andExpect(jsonPath("$.version",
                        equalTo(applicationUpdate.getVersion())))
                .andExpect(jsonPath("$.application.name",
                        equalTo(applicationUpdate.getApplication().getName())))
                .andExpect(jsonPath("$.application.state",
                        equalTo(applicationUpdate.getApplication().getState().getState())))
                .andExpect(status().isOk());
    }
}
