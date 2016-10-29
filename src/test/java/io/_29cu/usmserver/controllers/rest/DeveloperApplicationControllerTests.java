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
import java.util.UUID;

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.ApplicationUpdate;
import io._29cu.usmserver.core.model.entities.AuUser;
import io._29cu.usmserver.core.model.entities.Category;
import io._29cu.usmserver.core.service.*;
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

import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.service.utilities.ApplicationList;

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
    @Mock
    private CategoryService categoryService;

    // @Mock not used here since they are mocked in setup()
    // Spring boot does not allow mocking with annotation.
    SecurityContext securityContextMocked;
    Authentication authenticationMocked;

    private MockMvc mockMvc;

    private AuUser developer;
    private ApplicationList applicationList;
    private Application application;
    private ApplicationUpdate applicationUpdate;
    private Application existingApplication;
	private Application updatedApplication;
    private Application recalledApplication;
    private Application activeApplication;
    private String uuid;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(developerApplicationController).build();

        uuid = UUID.randomUUID().toString();

        developer = new AuUser();
        developer.setId(1L);
        developer.setEmail("owner@test.com");
        developer.setUsername("Test Owner");
        developer.setEnabled(true);

        application = new Application();
        application.setId(uuid);
        application.setCategory(new Category("Productivity"));
        application.setName("Dreamweaver");
        application.setState(AppState.Staging);
        application.setVersion("1.0");
        application.setDeveloper(developer);
        application.setDescription("test description");
        
        applicationUpdate = new ApplicationUpdate();
        applicationUpdate.setId(uuid);
        applicationUpdate.setName("Dreamweaver v1.1");
        applicationUpdate.setVersion("1.1");
        applicationUpdate.setDescription("test description v1.1");
        applicationUpdate.setWhatsNew("What is new");

        existingApplication = new Application();
        existingApplication.setId(uuid);
        existingApplication.setCategory(new Category("Productivity"));
        existingApplication.setName("Dreamweaver");
        existingApplication.setState(AppState.Staging);
        existingApplication.setVersion("1.0");
        existingApplication.setDeveloper(developer);
        existingApplication.setDescription("test description");

	    updatedApplication = new Application();
	    updatedApplication.setId(uuid);
	    updatedApplication.setCategory(new Category("LifeStyle"));
	    updatedApplication.setName("PhotoShop");
	    updatedApplication.setState(AppState.Staging);
	    updatedApplication.setVersion("1.1");
	    updatedApplication.setDeveloper(developer);
	    updatedApplication.setDescription("PhotoShop Description");

        recalledApplication = new Application();
        recalledApplication.setId(uuid);
        recalledApplication.setCategory(new Category("LifeStyle"));
        recalledApplication.setName("PhotoShop");
        recalledApplication.setState(AppState.Recalled);
        recalledApplication.setVersion("1.1");
        recalledApplication.setDeveloper(developer);
        recalledApplication.setDescription("PhotoShop Description");

        activeApplication = new Application();
        activeApplication.setId(uuid);
        activeApplication.setCategory(new Category("LifeStyle"));
        activeApplication.setName("PhotoShop");
        activeApplication.setState(AppState.Active);
        activeApplication.setVersion("1.1");
        activeApplication.setDeveloper(developer);
        activeApplication.setDescription("PhotoShop Description");


        applicationList = new ApplicationList();
        ArrayList<Application> appList = new ArrayList<Application>();
        appList.add(existingApplication);
        applicationList.setApplications(appList);
	    
        // Let's mock the security context
        authenticationMocked = Mockito.mock(Authentication.class);
        securityContextMocked = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContextMocked.getAuthentication()).thenReturn(authenticationMocked);
        SecurityContextHolder.setContext(securityContextMocked);
    }

    @Test
    public void  testGetApplicationsIsForbidden() throws Exception {
        when(userService.findUser()).thenReturn(null);
    	when(userService.findUser()).thenReturn(null);
    	
        mockMvc.perform(get("/api/0/developer/applications"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetApplicationsIsBadRequest() throws Exception {
        when(userService.findUser()).thenReturn(developer);
        when(applicationService.findApplicationsByDeveloper(developer.getUsername())).thenReturn(null);

        mockMvc.perform(get("/api/0/developer/applications"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetApplicationsIsOk() throws Exception {
        when(userService.findUser()).thenReturn(developer);
        when(applicationService.findApplicationsByDeveloper(developer.getUsername())).thenReturn(applicationList);

        mockMvc.perform(get("/api/0/developer/applications"))
                .andExpect(status().isOk());
    }

    @Test
    public void  testGetApplication() throws Exception {
        when(userService.findUser()).thenReturn(null);
    	
        mockMvc.perform(get("/api/0/developer/applications/" + uuid))
                .andExpect(status().isForbidden());

        when(userService.findUser()).thenReturn(developer);
        when(userService.findUser()).thenReturn(developer);
        when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), uuid)).thenReturn(application);

        mockMvc.perform(get("/api/0/developer/applications/" + uuid))
		        .andExpect(status().isOk());

        when(userService.findUser()).thenReturn(developer);
        when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), uuid)).thenReturn(null);

        mockMvc.perform(get("/api/0/developer/applications/22"))
		        .andExpect(status().isBadRequest());
    }

    @Test
    public void  testCreateDeveloperApplication() throws Exception {
        when(userService.findUser()).thenReturn(null);
    	
        mockMvc.perform(post("/api/0/developer/applications/create")
                .content("{'name':'dreamweaver','downloadUrl':'https://test.com', 'version':'1.0', 'category': { 'name': 'Productivity'}, 'state': 'Staging', 'description':'test description'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        when(userService.findUser()).thenReturn(developer);
        when(categoryService.findCategoryByName("Productivity")).thenReturn(new Category("Productivity"));
        when(applicationService.createApplication(any(Application.class))).thenReturn(application);

        mockMvc.perform(post("/api/0/developer/applications/create")
                .content("{'name':'dreamweaver','downloadUrl':'https://test.com', 'version':'1.0', 'category': { 'name': 'Productivity'}, 'state': 'Staging', 'description':'test description'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name",
                        equalTo(application.getName())))
                .andExpect(jsonPath("$.rid",
                        equalTo(uuid)))
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
        when(userService.findUser()).thenReturn(null);
    	
        mockMvc.perform(get("/api/0/developer/applications/create")
                .param("name", "Dreamweaver"))
                .andExpect(status().isForbidden());

        when(userService.findUser()).thenReturn(developer);
        when(applicationService.findApplicationByDeveloperIdAndAppName(developer.getId(), "Dreamweaver")).thenReturn(application);

        mockMvc.perform(get("/api/0/developer/applications/create")
        		.param("name", "Dreamweaver"))
		        .andExpect(status().isOk());
    }

    @Test
    public void  testCheckApplicationNameNotExistsForDeveloper() throws Exception {
        when(userService.findUser()).thenReturn(developer);
        when(applicationService.findApplicationByDeveloperIdAndAppName(developer.getId(), "Dreams")).thenReturn(null);

        mockMvc.perform(get("/api/0/developer/applications/create")
        		.param("name", "Dreams"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateDeveloperApplication() throws Exception {
    	when(userService.findUser()).thenReturn(null);
    	
        mockMvc.perform(post("/api/0/developer/applications/"+ uuid +"/update")
                .content("{'name':'PhotoShop','downloadUrl':'https://test.com/photoshop', 'version':'1.1', 'category': { 'name': 'Lifestyle'}, 'state': 'Staging', 'description':'PhotoShop Description'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        when(userService.findUser()).thenReturn(developer);
    	when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), uuid)).thenReturn(null);
    	
        mockMvc.perform(post("/api/0/developer" +"/applications/22/update")
                .content("{'name':'PhotoShop','downloadUrl':'https://test.com/photoshop', 'version':'1.1', 'category': { 'name': 'Lifestyle'}, 'state': 'Staging', 'description':'PhotoShop Description'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed());

        when(userService.findUser()).thenReturn(developer);
        when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), uuid)).thenReturn(application);
	    when(applicationService.updateApplication(any(Application.class))).thenReturn(updatedApplication);

	    mockMvc.perform(post("/api/0/developer/applications/" + uuid +"/update")
                .content("{'name':'PhotoShop','downloadUrl':'https://test.com/photoshop', 'version':'1.1', 'category': { 'name': 'Lifestyle'}, 'state': 'Staging', 'description':'PhotoShop Description'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$.name",
				        equalTo(updatedApplication.getName())))
		        .andExpect(jsonPath("$.rid",
				        equalTo(uuid)))
		        .andExpect(jsonPath("$.version",
				        equalTo(updatedApplication.getVersion())))
		        .andExpect(jsonPath("$.state",
				        equalTo(updatedApplication.getState().name())))
		        .andExpect(jsonPath("$.category.name",
				        equalTo(updatedApplication.getCategory().getName())))
		        .andExpect(jsonPath("$.description",
				        equalTo(updatedApplication.getDescription())))
		        .andExpect(status().isOk());
    }


    @Test
    public void  testPublishDeveloperApplication() throws Exception {
    	when(userService.findUser()).thenReturn(null);
    	
        mockMvc.perform(post("/api/0/developer/applications/" + uuid +"/publish")
                .content("{'name':'Dreamweaver v1.1', 'whatsNew':'What is new', 'version':'1.1'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        when(userService.findUser()).thenReturn(developer);
        
        when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), "22")).thenReturn(null);
        mockMvc.perform(post("/api/0/developer/applications/22/publish")
                .content("{'name':'Dreamweaver v1.1', 'whatsNew':'What is new', 'version':'1.1'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        
        when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), uuid)).thenReturn(application);
        applicationUpdate.setTarget(application);
        when(applicationUpdateService.createApplicationUpdate(any(ApplicationUpdate.class))).thenReturn(applicationUpdate);

        mockMvc.perform(post("/api/0/developer/applications/" + uuid +"/publish")
                .content("{'name':'Dreamweaver v1.1', 'whatsNew':'What is new', 'version':'1.1'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.whatsNew",
                        equalTo(applicationUpdate.getWhatsNew())))
                .andExpect(jsonPath("$.rid",
                        equalTo(uuid)))
                .andExpect(jsonPath("$.version",
                        equalTo(applicationUpdate.getVersion())))
                .andExpect(jsonPath("$.name",
                        equalTo(applicationUpdate.getName())))
                .andExpect(jsonPath("$.state",
                        equalTo(AppState.Active.getState())))
                .andExpect(status().isOk());
        
        application.setState(AppState.Blocked);
        mockMvc.perform(post("/api/0/developer/applications/" + uuid +"/publish")
                .content("{'name':'Dreamweaver v1.1', 'whatsNew':'What is new', 'version':'1.1'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void testRecallDeveloperApplication() throws Exception {
        when(userService.findUser()).thenReturn(null);

        mockMvc.perform(post("/api/0/developer/applications/"+ uuid +"/recall")
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        when(userService.findUser()).thenReturn(developer);
        when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), uuid)).thenReturn(null);

        mockMvc.perform(post("/api/0/developer/applications/22/recall")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed());

        when(userService.findUser()).thenReturn(developer);
        when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), uuid)).thenReturn(application);
        mockMvc.perform(post("/api/0/developer/applications/" + uuid +"/recall")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed());

        when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), uuid)).thenReturn(activeApplication);
        when(applicationService.updateApplication(any(Application.class))).thenReturn(recalledApplication);

        mockMvc.perform(post("/api/0/developer/applications/" + uuid +"/recall")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.state",
                        equalTo(AppState.Recalled.getState())))
                .andExpect(jsonPath("$.rid",
                        equalTo(uuid)))
                .andExpect(status().isOk());
    }
}
