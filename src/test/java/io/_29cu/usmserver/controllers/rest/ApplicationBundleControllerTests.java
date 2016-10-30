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

import io._29cu.usmserver.core.model.entities.ApplicationBundle;
import io._29cu.usmserver.core.model.entities.AuUser;
import io._29cu.usmserver.core.model.entities.Category;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.service.ApplicationBundleService;
import io._29cu.usmserver.core.service.ApplicationUpdateService;
import io._29cu.usmserver.core.service.UserService;
import io._29cu.usmserver.core.service.utilities.ApplicationBundleList;
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

import java.util.ArrayList;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ApplicationBundleControllerTests {
    @InjectMocks
    private ApplicationBundleController developerApplicationBundleController;
    @Mock
    private ApplicationBundleService applicationBundleService;
    @Mock
    private ApplicationUpdateService applicationBundleUpdateService;
    @Mock
    private UserService userService;

    // @Mock not used here since they are mocked in setup()
    // Spring boot does not allow mocking with annotation.
    SecurityContext securityContextMocked;
    Authentication authenticationMocked;

    private MockMvc mockMvc;

    private AuUser developer;
    private ApplicationBundleList applicationBundleList;
    private ApplicationBundle applicationBundle;
    private ApplicationBundle existingApplicationBundle;
	private ApplicationBundle updatedApplicationBundle;
    private String uuid;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(developerApplicationBundleController).build();

        uuid = UUID.randomUUID().toString();

        developer = new AuUser();
        developer.setId(1L);
        developer.setEmail("owner@test.com");
        developer.setUsername("Test Owner");
        developer.setEnabled(true);

        applicationBundle = new ApplicationBundle();
        applicationBundle.setId(uuid);
        applicationBundle.setCategory(new Category("Productivity"));
        applicationBundle.setName("Dreamweaver");
        applicationBundle.setState(AppState.Staging);
        applicationBundle.setDeveloper(developer);
        applicationBundle.setDescription("test description");

        existingApplicationBundle = new ApplicationBundle();
        existingApplicationBundle.setId(uuid);
        existingApplicationBundle.setCategory(new Category("Productivity"));
        existingApplicationBundle.setName("Dreamweaver");
        existingApplicationBundle.setState(AppState.Staging);
        existingApplicationBundle.setDeveloper(developer);
        existingApplicationBundle.setDescription("test description");

	    updatedApplicationBundle = new ApplicationBundle();
	    updatedApplicationBundle.setId(uuid);
	    updatedApplicationBundle.setCategory(new Category("LifeStyle"));
	    updatedApplicationBundle.setName("PhotoShop");
	    updatedApplicationBundle.setState(AppState.Staging);
	    updatedApplicationBundle.setDeveloper(developer);
	    updatedApplicationBundle.setDescription("PhotoShop Description");
	    
        applicationBundleList = new ApplicationBundleList();
        ArrayList<ApplicationBundle> appList = new ArrayList<ApplicationBundle>();
        appList.add(existingApplicationBundle);
        applicationBundleList.setApplicationBundles(appList);
	    
        // Let's mock the security context
        authenticationMocked = Mockito.mock(Authentication.class);
        securityContextMocked = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContextMocked.getAuthentication()).thenReturn(authenticationMocked);
        SecurityContextHolder.setContext(securityContextMocked);
    }

    @Test
    public void  testGetApplicationBundles() throws Exception {
        when(userService.findUser()).thenReturn(null);
    	
        mockMvc.perform(get("/api/0/developer/applicationBundles"))
                .andExpect(status().isForbidden());

        when(userService.findUser()).thenReturn(developer);
        when(applicationBundleService.findApplicationBundlesByDeveloper(developer.getId())).thenReturn(null);

        mockMvc.perform(get("/api/0/developer/applicationBundles"))
                .andExpect(status().isBadRequest());

    	when(applicationBundleService.findApplicationBundlesByDeveloper(developer.getId())).thenReturn(applicationBundleList);

        mockMvc.perform(get("/api/0/developer/applicationBundles"))
                .andExpect(status().isOk());
    }

    @Test
    public void  testGetApplicationBundle() throws Exception {
        when(userService.findUser()).thenReturn(null);
    	
        mockMvc.perform(get("/api/0/developer/applicationBundles/" + uuid))
                .andExpect(status().isForbidden());


        when(userService.findUser()).thenReturn(developer);
        when(applicationBundleService.findApplicationBundleByDeveloperAndId(developer.getId(), uuid)).thenReturn(applicationBundle);

        mockMvc.perform(get("/api/0/developer/applicationBundles/" + uuid))
		        .andExpect(status().isOk());

        when(userService.findUser()).thenReturn(developer);
        when(applicationBundleService.findApplicationBundleByDeveloperAndId(developer.getId(), uuid)).thenReturn(null);

        mockMvc.perform(get("/api/0/developer/applicationBundles/22"))
		        .andExpect(status().isBadRequest());
    }

    @Test
    public void  testCreateApplicationBundle() throws Exception {
        when(userService.findUser()).thenReturn(null);
    	
        mockMvc.perform(post("/api/0/developer/applicationBundles/create")
                .content("{'name':'dreamweaver','downloadUrl':'https://test.com', 'version':'1.0', 'category': { 'name': 'Productivity'}, 'state': 'Staging', 'description':'test description'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        when(userService.findUser()).thenReturn(developer);
        when(applicationBundleService.createApplicationBundle(any(ApplicationBundle.class))).thenReturn(applicationBundle);

        mockMvc.perform(post("/api/0/developer/applicationBundles/create")
                .content("{'name':'dreamweaver','downloadUrl':'https://test.com', 'version':'1.0', 'category': { 'name': 'Productivity'}, 'state': 'Staging', 'description':'test description'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name",
                        equalTo(applicationBundle.getName())))
                .andExpect(jsonPath("$.rid",
                        equalTo(uuid)))
                .andExpect(jsonPath("$.state",
                        equalTo(applicationBundle.getState().name())))
                .andExpect(jsonPath("$.category.name",
                        equalTo(applicationBundle.getCategory().getName())))
                .andExpect(jsonPath("$.description",
                        equalTo(applicationBundle.getDescription())))
                .andExpect(status().isOk());
    }

    @Test
    public void  testCheckApplicationBundleNameExistsForDeveloper() throws Exception {
        when(userService.findUser()).thenReturn(null);
    	
        mockMvc.perform(get("/api/0/developer/applicationBundles/create")
                .param("name", "Dreamweaver"))
                .andExpect(status().isForbidden());

        when(userService.findUser()).thenReturn(developer);
        when(applicationBundleService.findApplicationBundleByDeveloperAndName(developer.getId(), "Dreamweaver")).thenReturn(applicationBundle);

        mockMvc.perform(get("/api/0/developer/applicationBundles/create")
        		.param("name", "Dreamweaver"))
		        .andExpect(status().isOk());
    }

    @Test
    public void  testCheckApplicationBundleNameNotExistsForDeveloper() throws Exception {
        when(userService.findUser()).thenReturn(developer);
        when(applicationBundleService.findApplicationBundleByDeveloperAndName(developer.getId(), "Dreams")).thenReturn(null);

        mockMvc.perform(get("/api/0/developer/applicationBundles/create")
        		.param("name", "Dreams"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateApplicationBundle() throws Exception {
        when(userService.findUser()).thenReturn(null);
    	
        mockMvc.perform(post("/api/0/developer/applicationBundles/"+ uuid +"/update")
                .content("{'name':'PhotoShop','downloadUrl':'https://test.com/photoshop', 'version':'1.1', 'category': { 'name': 'Lifestyle'}, 'state': 'Staging', 'description':'PhotoShop Description'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        when(userService.findUser()).thenReturn(developer);
    	when(applicationBundleService.findApplicationBundleByDeveloperAndId(developer.getId(), uuid)).thenReturn(null);
    	
        mockMvc.perform(post("/api/0/developer/applicationBundles/22/update")
                .content("{'name':'PhotoShop','downloadUrl':'https://test.com/photoshop', 'version':'1.1', 'category': { 'name': 'Lifestyle'}, 'state': 'Staging', 'description':'PhotoShop Description'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed());

        when(userService.findUser()).thenReturn(developer);
        when(applicationBundleService.findApplicationBundleByDeveloperAndId(developer.getId(), uuid)).thenReturn(applicationBundle);
	    when(applicationBundleService.updateApplicationBundle(any(ApplicationBundle.class))).thenReturn(updatedApplicationBundle);

	    mockMvc.perform(post("/api/0/developer/applicationBundles/" + uuid +"/update")
                .content("{'name':'PhotoShop','downloadUrl':'https://test.com/photoshop', 'version':'1.1', 'category': { 'name': 'Lifestyle'}, 'state': 'Staging', 'description':'PhotoShop Description'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$.name",
				        equalTo(updatedApplicationBundle.getName())))
		        .andExpect(jsonPath("$.rid",
				        equalTo(uuid)))
		        .andExpect(jsonPath("$.state",
				        equalTo(updatedApplicationBundle.getState().name())))
		        .andExpect(jsonPath("$.category.name",
				        equalTo(updatedApplicationBundle.getCategory().getName())))
		        .andExpect(jsonPath("$.description",
				        equalTo(updatedApplicationBundle.getDescription())))
		        .andExpect(status().isOk());
    }

    @Test
    public void  testPublishApplicationBundle() throws Exception {
        when(userService.findUser()).thenReturn(null);
    	
        mockMvc.perform(post("/api/0/developer/applicationBundles/" + uuid +"/publish")
                .content("{'name':'Dreamweaver v1.1', 'whatsNew':'What is new', 'version':'1.1'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        when(userService.findUser()).thenReturn(developer);
        when(applicationBundleService.findApplicationBundleByDeveloperAndId(developer.getId(), "22")).thenReturn(null);
        mockMvc.perform(post("/api/0/developer/applicationBundles/22/publish")
                .content("{'name':'Dreamweaver v1.1', 'whatsNew':'What is new', 'version':'1.1'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        
        when(applicationBundleService.findApplicationBundleByDeveloperAndId(developer.getId(), uuid)).thenReturn(applicationBundle);

        mockMvc.perform(post("/api/0/developer/applicationBundles/" + uuid +"/publish")
                .content("{'name':'Dreamweaver v1.1', 'whatsNew':'What is new', 'version':'1.1'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rid",
                        equalTo(uuid)))
                .andExpect(jsonPath("$.name",
                        equalTo(applicationBundle.getName())))
                .andExpect(jsonPath("$.state",
                        equalTo(AppState.Active.getState())))
                .andExpect(status().isOk());
        
        applicationBundle.setState(AppState.Blocked);
        mockMvc.perform(post("/api/0/developer/applicationBundles/" + uuid +"/publish")
                .content("{'name':'Dreamweaver v1.1', 'whatsNew':'What is new', 'version':'1.1'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed());
    }
}
