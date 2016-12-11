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
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
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
import io._29cu.usmserver.core.model.entities.ApplicationHistory;
import io._29cu.usmserver.core.model.entities.ApplicationUpdate;
import io._29cu.usmserver.core.model.entities.Category;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.service.ApplicationHistoryService;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.ApplicationUpdateService;
import io._29cu.usmserver.core.service.CategoryService;
import io._29cu.usmserver.core.service.UserService;
import io._29cu.usmserver.core.service.utilities.ApplicationList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DeveloperApplicationsControllerTests {
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
    @Mock
    private ApplicationHistoryService applicationHistoryService;

    // @Mock not used here since they are mocked in setup()
    // Spring boot does not allow mocking with annotation.
    SecurityContext securityContextMocked;
    Authentication authenticationMocked;

    private MockMvc mockMvc;

    private User developer;
    private ApplicationList applicationList;
    private Application application;
    private ApplicationUpdate applicationUpdate;
    private Application existingApplication;
	private Application updatedApplication;
    private Application recalledApplication;
    private Application activeApplication;
    private ApplicationHistory applicationHistory;
    private Category cat1, cat2;
    private String uuid;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(developerApplicationController).build();

        uuid = UUID.randomUUID().toString();

        developer = new User();
        developer.setId(1L);
        developer.setEmail("owner@test.com");
        developer.setUsername("Test Owner");
        developer.setEnabled(true);

        cat1 = new Category("Productivity");
        cat1.setId(1l);
        cat2 = new Category("LifeStyle");
        cat2.setId(3l);

        application = new Application();
        application.setId(uuid);
        application.setCategory(cat1);
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
        existingApplication.setCategory(cat1);
        existingApplication.setName("Dreamweaver");
        existingApplication.setState(AppState.Staging);
        existingApplication.setVersion("1.0");
        existingApplication.setDeveloper(developer);
        existingApplication.setDescription("test description");

	    updatedApplication = new Application();
	    updatedApplication.setId(uuid);
	    updatedApplication.setCategory(cat2);
	    updatedApplication.setName("PhotoShop");
	    updatedApplication.setState(AppState.Staging);
	    updatedApplication.setVersion("1.1");
	    updatedApplication.setDeveloper(developer);
	    updatedApplication.setDescription("PhotoShop Description");

        recalledApplication = new Application();
        recalledApplication.setId(uuid);
        recalledApplication.setCategory(cat2);
        recalledApplication.setName("PhotoShop");
        recalledApplication.setState(AppState.Recalled);
        recalledApplication.setVersion("1.1");
        recalledApplication.setDeveloper(developer);
        recalledApplication.setDescription("PhotoShop Description");

        activeApplication = new Application();
        activeApplication.setId(uuid);
        activeApplication.setCategory(cat2);
        activeApplication.setName("PhotoShop");
        activeApplication.setState(AppState.Active);
        activeApplication.setVersion("1.1");
        activeApplication.setDeveloper(developer);
        activeApplication.setDescription("PhotoShop Description");

        ApplicationHistory applicationHistory = new ApplicationHistory();
        applicationHistory.setId(1L);
        applicationHistory.setApplication(application);
        applicationHistory.setName(application.getName());
        applicationHistory.setVersion(application.getVersion());
        applicationHistory.setWhatsNew(application.getWhatsNew());

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
    	when(userService.findAuthenticatedUser()).thenReturn(null);
    	
        mockMvc.perform(get("/api/0/developer/applications"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetApplicationsIsBadRequest() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(developer);
        when(applicationService.findApplicationsByDeveloper(developer.getUsername())).thenReturn(null);

        mockMvc.perform(get("/api/0/developer/applications"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetApplicationsIsOk() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(developer);
        when(applicationService.findApplicationsByDeveloper(developer.getUsername())).thenReturn(applicationList);

        mockMvc.perform(get("/api/0/developer/applications"))
                .andExpect(status().isOk());
    }

    @Test
    public void  testGetApplication() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(null);
    	
        mockMvc.perform(get("/api/0/developer/applications/" + uuid))
                .andExpect(status().isForbidden());

        when(userService.findAuthenticatedUser()).thenReturn(developer);
        when(userService.findAuthenticatedUser()).thenReturn(developer);
        when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), uuid)).thenReturn(application);

        mockMvc.perform(get("/api/0/developer/applications/" + uuid))
		        .andExpect(status().isOk());

        when(userService.findAuthenticatedUser()).thenReturn(developer);
        when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), uuid)).thenReturn(null);

        mockMvc.perform(get("/api/0/developer/applications/22"))
		        .andExpect(status().isBadRequest());
    }

    @Test
    public void  testCreateDeveloperApplication() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(null);
    	
        mockMvc.perform(post("/api/0/developer/applications/create")
                .content("{'name':'dreamweaver','downloadUrl':'https://test.com', 'version':'1.0', 'category': { 'name': '1'}, 'description':'test description'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        when(userService.findAuthenticatedUser()).thenReturn(developer);
        when(categoryService.findCategoryByName("Productivity")).thenReturn(cat1);
        when(applicationService.createApplication(any(Application.class))).thenReturn(application);
        assertTrue("AppState is not in Staging", application.getState()==AppState.Staging);

        mockMvc.perform(post("/api/0/developer/applications/create")
                .content("{'name':'dreamweaver','downloadUrl':'https://test.com', 'version':'1.0', 'category': { 'name': '1'}, 'description':'test description'}".replaceAll("'",  "\""))
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
                .andExpect(jsonPath("$.state",
                        equalTo(application.getState().name())))
                .andExpect(status().isOk());
    }

    @Test
    public void  testCreateAndPublishDeveloperApplication() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(null);
    	
        mockMvc.perform(post("/api/0/developer/applications/create")
                .content("{'name':'dreamweaver','downloadUrl':'https://test.com', 'version':'1.0', 'category': { 'name': '1'}, 'description':'test description'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        when(userService.findAuthenticatedUser()).thenReturn(developer);
        when(categoryService.findCategoryByName("Productivity")).thenReturn(cat1);
        application.setState(AppState.Active);
        when(applicationService.createApplication(any(Application.class))).thenReturn(application);
        assertTrue("AppState is not in Staging", application.getState()==AppState.Active);

        mockMvc.perform(post("/api/0/developer/applications/create")
                .content("{'name':'dreamweaver','downloadUrl':'https://test.com', 'version':'1.0', 'category': { 'name': '1'}, 'description':'test description'}".replaceAll("'",  "\""))
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
        when(userService.findAuthenticatedUser()).thenReturn(null);
    	
        mockMvc.perform(get("/api/0/developer/applications/create")
                .param("name", "Dreamweaver"))
                .andExpect(status().isForbidden());

        when(userService.findAuthenticatedUser()).thenReturn(developer);
        when(applicationService.findApplicationByDeveloperIdAndAppName(developer.getId(), "Dreamweaver")).thenReturn(application);

        mockMvc.perform(get("/api/0/developer/applications/create")
        		.param("name", "Dreamweaver"))
		        .andExpect(status().isOk());
    }

    @Test
    public void  testCheckApplicationNameNotExistsForDeveloper() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(developer);
        when(applicationService.findApplicationByDeveloperIdAndAppName(developer.getId(), "Dreams")).thenReturn(null);

        mockMvc.perform(get("/api/0/developer/applications/create")
        		.param("name", "Dreams"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateDeveloperApplication() throws Exception {
    	when(userService.findAuthenticatedUser()).thenReturn(null);
    	
        mockMvc.perform(post("/api/0/developer/applications/"+ uuid +"/update")
                .content("{'name':'PhotoShop','downloadUrl':'https://test.com/photoshop', 'version':'1.1', 'category': { 'name': '3'}, 'description':'PhotoShop Description'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        when(userService.findAuthenticatedUser()).thenReturn(developer);
    	when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), uuid)).thenReturn(null);
    	
        mockMvc.perform(post("/api/0/developer" +"/applications/22/update")
                .content("{'name':'PhotoShop','downloadUrl':'https://test.com/photoshop', 'version':'1.1', 'category': { 'name': '3'}, 'description':'PhotoShop Description'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed());

        when(userService.findAuthenticatedUser()).thenReturn(developer);
        when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), uuid)).thenReturn(application);
	    when(applicationService.updateApplication(any(Application.class))).thenReturn(updatedApplication);

	    mockMvc.perform(post("/api/0/developer/applications/" + uuid +"/update")
                .content("{'name':'PhotoShop','downloadUrl':'https://test.com/photoshop', 'version':'1.1', 'category': { 'name': '3'}, 'description':'PhotoShop Description'}".replaceAll("'",  "\""))
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
    	when(userService.findAuthenticatedUser()).thenReturn(null);
    	
        mockMvc.perform(post("/api/0/developer/applications/" + uuid +"/publish"))
                .andExpect(status().isForbidden());

        when(userService.findAuthenticatedUser()).thenReturn(developer);
        
        when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), "22")).thenReturn(null);
        mockMvc.perform(post("/api/0/developer/applications/22/publish"))
                .andExpect(status().isPreconditionFailed());
        
        when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), uuid)).thenReturn(application);

        when(applicationHistoryService.createApplicationHistory(any(ApplicationHistory.class))).thenReturn(applicationHistory);

        when(applicationService.updateApplication(any(Application.class))).thenReturn(activeApplication);

        mockMvc.perform(post("/api/0/developer/applications/" + uuid +"/publish"))
                .andExpect(jsonPath("$.state",
                        equalTo(AppState.Active.getState())))
                .andExpect(status().isOk());
        
        application.setState(AppState.Blocked);
        mockMvc.perform(post("/api/0/developer/applications/" + uuid +"/publish"))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void  testPublishDeveloperApplicationWhenAppStateIsInvalid() throws Exception {
    	when(userService.findAuthenticatedUser()).thenReturn(null);
        when(userService.findAuthenticatedUser()).thenReturn(developer);
        application.setState(null);
        when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), uuid)).thenReturn(application);
        mockMvc.perform(post("/api/0/developer/applications/" + uuid +"/publish"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRecallDeveloperApplication() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(null);

        mockMvc.perform(post("/api/0/developer/applications/"+ uuid +"/recall")
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        when(userService.findAuthenticatedUser()).thenReturn(developer);
        when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), uuid)).thenReturn(null);

        mockMvc.perform(post("/api/0/developer/applications/22/recall")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed());

        when(userService.findAuthenticatedUser()).thenReturn(developer);
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

    @Test
    public void  testRecallDeveloperApplicationWhenAppStateIsInvalid() throws Exception {
    	when(userService.findAuthenticatedUser()).thenReturn(null);
        when(userService.findAuthenticatedUser()).thenReturn(developer);
        application.setState(null);
        when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), uuid)).thenReturn(application);
        mockMvc.perform(post("/api/0/developer/applications/" + uuid +"/recall"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void  testCreateUpdateDeveloperApplicationWhenUserIsInvalid() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(null);

        mockMvc.perform(post("/api/0/developer/22/applications/"+ uuid +"/createUpdate")
        		.content("{'name':'dreamweaver','downloadUrl':'https://test.com', 'version':'1.1', 'category': { 'name': '1'}, 'description':'test description for Application Update'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testCreateUpdateDeveloperApplicationWhenAppIdIsInvalid() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(developer);
        when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), uuid)).thenReturn(null);

        mockMvc.perform(post("/api/0/developer/"+ developer.getId() +"/applications/22/createUpdate")
        		.content("{'name':'dreamweaver','downloadUrl':'https://test.com', 'version':'1.1', 'category': { 'name': '1'}, 'description':'test description for Application Update'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateUpdateDeveloperApplicationWhenAppStateIsBlocked() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(developer);
        application.setState(AppState.Blocked);
        when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), uuid)).thenReturn(application);
        mockMvc.perform(post("/api/0/developer/"+ developer.getId() +"/applications/" + uuid +"/createUpdate")
        		.content("{'name':'dreamweaver','downloadUrl':'https://test.com', 'version':'1.1', 'category': { 'name': '1'}, 'description':'test description for Application Update'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void testCreateUpdateDeveloperApplication() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(developer);
        application.setState(AppState.Active);
        when(applicationService.findApplicationByDeveloperIdAndAppId(developer.getId(), uuid)).thenReturn(application);
        when(applicationUpdateService.findByApplication(uuid)).thenReturn(null);

        mockMvc.perform(post("/api/0/developer/"+ developer.getId() +"/applications/" + uuid +"/createUpdate")
                .content("{'name':'dreamweaver','downloadUrl':'https://test.com', 'version':'1.1', 'category': { 'name': '1'}, 'description':'test description for Application Update'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name",
                        equalTo(application.getName())))
                .andExpect(jsonPath("$.rid",
                        equalTo(uuid)))
                .andExpect(jsonPath("$.version",
                        equalTo("1.1")))
                .andExpect(jsonPath("$.state",
                        equalTo(application.getState().name())))
                .andExpect(jsonPath("$.category.name",
                        equalTo(application.getCategory().getName())))
                .andExpect(jsonPath("$.description",
                        equalTo("test description for Application Update")))
                .andExpect(status().isOk());
    }

}
