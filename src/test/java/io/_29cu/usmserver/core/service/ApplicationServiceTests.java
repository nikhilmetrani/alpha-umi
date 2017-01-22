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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
import io._29cu.usmserver.core.model.entities.Category;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.model.enumerations.AuthorityName;
import io._29cu.usmserver.core.repositories.ApplicationRepository;
import io._29cu.usmserver.core.repositories.CategoryRepository;
import io._29cu.usmserver.core.repositories.UserRepository;
import io._29cu.usmserver.core.service.utilities.ApplicationList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ApplicationServiceTests {
    @Autowired
    private ApplicationService service;
    @Autowired
    private UserService userService;

    private User developer;
    private Application application, application2;
    private Category cat1, cat2;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private CategoryRepository categoryRepository;

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

        cat1 = categoryRepository.save(new Category("ImageEditor"));
        cat2 = categoryRepository.save(new Category("CreativeTools"));
        
        application = new Application();
        application.setName("application");
        application.setDeveloper(developer);
        application.setState(AppState.Staging);
        application.setDescription("test description");
        application.setVersion("1.0");
        application.setWhatsNew("test");
        application.setCategory(cat1);
        application = service.createApplication(application);

        application2 = new Application();
        application2.setName("Application2");
        application2.setDeveloper(developer);
        application2.setState(AppState.Active);
        application2.setDescription("test description2");
        application2.setVersion("2.0");
        application2.setWhatsNew("test2");
        application2.setCategory(cat2);
        application2 = service.createApplication(application2);
    }

	@After
	public void tearDown() {
		applicationRepository.delete(application.getId());
		applicationRepository.delete(application2.getId());
		categoryRepository.delete(cat1.getId());
		categoryRepository.delete(cat2.getId());
		userRepository.delete(developer.getId());
	}

    @Test
    @Transactional
    public void testFind() {
        Application fromDb = service.findApplication(application.getId());
        assertNotNull(fromDb);
        assertEquals("Application name does not match", application.getName(), fromDb.getName());
        assertEquals("Application developer does not match", application.getDeveloper(), fromDb.getDeveloper());
    }

    @Test
    @Transactional
    public void testFindApplicationByUsernameAndAppName() {
        Application fromDb = service.findApplicationByUsernameAndAppName(developer.getUsername(), application.getName());
        assertNotNull(fromDb);
        assertEquals("Application name does not match", application.getName(), fromDb.getName());
        assertEquals("Application developer does not match", application.getDeveloper(), fromDb.getDeveloper());
    }

    @Test
    @Transactional
    public void testFindApplicationByUsernameNameAndAppId() {
        Application fromDb = service.findApplicationByUsernameAndAppId(developer.getUsername(), application.getId());
        assertNotNull(fromDb);
        assertEquals("Application Id does not match", application.getId(), fromDb.getId());
        assertEquals("Application developer does not match", application.getDeveloper(), fromDb.getDeveloper());
    }

    @Test
    @Transactional
    public void testFindApplicationByDeveloperIdAndAppId() {
        Application fromDb = service.findApplicationByDeveloperIdAndAppId(developer.getId(), application.getId());
        assertNotNull(fromDb);
        assertEquals("Application Id does not match", application.getId(), fromDb.getId());
        assertEquals("Application developer does not match", application.getDeveloper(), fromDb.getDeveloper());
    }

    @Test
    @Transactional
    public void testFindApplicationByDeveloperIdAndAppName() {
        Application fromDb = service.findApplicationByDeveloperIdAndAppName(developer.getId(), application.getName());
        assertNotNull(fromDb);
        assertEquals("Application Id does not match", application.getId(), fromDb.getId());
        assertEquals("Application developer does not match", application.getDeveloper(), fromDb.getDeveloper());
    }

    @Test
    @Transactional
    public void testCreateApplication() {
        Application fromDb = service.createApplication(application);
        assertNotNull(fromDb);
        assertEquals("Application name does not match", application.getId(), fromDb.getId());
        assertEquals("Application developer does not match", application.getDeveloper(), fromDb.getDeveloper());
    }

    @Test
    @Transactional
    public void testGetAllApplications() {
        ApplicationList fromDb = service.getAllApplications();
        assertNotNull("ApplicationList is Null", fromDb);
        assertNotNull("ApplicationList is Null", fromDb.getItems());
        assertFalse("ApplicationList is empty", fromDb.getItems().isEmpty());
        boolean appInStaging = false;
        for(Application app: fromDb.getItems()) {
        	if(app.getState().equals(AppState.Staging)) {
        		appInStaging = true;
        		break;
        	}
        }
        assertTrue("No AppState in Staing", appInStaging);
    }

    @Test
    @Transactional
    public void testGetAllActiveApplications() {
        ApplicationList fromDb = service.getAllActiveApplications();
        assertNotNull("ApplicationList is Null", fromDb);
        assertNotNull("ApplicationList is Null", fromDb.getItems());
        assertFalse("ApplicationList is empty", fromDb.getItems().isEmpty());
        for(Application app: fromDb.getItems()) {
        	assertTrue("AppState is Not Active", app.getState().equals(AppState.Active));
        }
    }

    @Test
    @Transactional
    public void testUpdateApplication() {
    	application.setDescription("test description1");
        Application fromDb = service.updateApplication(application);
        assertTrue("ApplicationList is empty", fromDb.getDescription().equals("test description1"));
    }

    @Test
    @Transactional
    public void testFindApplicationsByDeveloper() {
        ApplicationList fromDb = service.findApplicationsByDeveloper(developer.getId());
        assertNotNull("ApplicationList is Null", fromDb);
        assertNotNull("ApplicationList is Null", fromDb.getItems());
        assertFalse("ApplicationList is empty", fromDb.getItems().isEmpty());
        for(Application app: fromDb.getItems()) {
        	assertTrue("Invalid Application Found", app.getDeveloper().getId().equals(developer.getId()));
        }
    }

    @Test
    @Transactional
    public void testFindApplicationsByCategory() {
        ApplicationList fromDb = service.findApplicationsByCategory(cat1.getName());
        assertNotNull("ApplicationList is Null", fromDb);
        assertNotNull("ApplicationList is Null", fromDb.getItems());
        assertFalse("ApplicationList is empty", fromDb.getItems().isEmpty());
        for(Application app: fromDb.getItems()) {
        	assertTrue("Invalid Application Found", app.getCategory().getId().equals(cat1.getId()));
        }
    }

    @Test
    @Transactional
    public void testFindApplicationsByCategoryAndState() {
        ApplicationList fromDb = service.findApplicationsByCategoryAndState(cat2.getName(), AppState.Active);
        assertNotNull("ApplicationList is Null", fromDb);
        assertNotNull("ApplicationList is Null", fromDb.getItems());
        assertFalse("ApplicationList is empty", fromDb.getItems().isEmpty());
        for(Application app: fromDb.getItems()) {
        	assertTrue("Invalid Application Found", (app.getCategory().getId().equals(cat2.getId()) && app.getState().equals(AppState.Active)));
        }
    }

    @Test
    @Transactional
    public void testBlockApplication() {
        boolean fromDb = service.blockApplication(application);
      	assertTrue("Application Not Blocked", fromDb);
    }

    @Test
    @Transactional
    public void testFindApplicationsByKeyword() {
    	ApplicationList fromDb = service.findApplicationsByKeyword("application description2");
        for(Application app: fromDb.getItems()) {
        	assertTrue("Application does not match", (Pattern.matches("(.*(application).*)|(.*(description2).*)", app.getName().toLowerCase()) || Pattern.matches("(.*(application).*)|(.*(description2).*)", app.getDescription().toLowerCase())));
        }
    }

    @Test
    @Transactional
    public void testFindApplicationsByCategoryAndKeyword() {
    	ApplicationList fromDb = service.findApplicationsByCategoryAndKeyword(cat1.getId(), "application description2");
        for(Application app: fromDb.getItems()) {
        	assertTrue("Application does not match", (Pattern.matches("(.*(application).*)|(.*(description2).*)", app.getName().toLowerCase()) || Pattern.matches("(.*(application).*)|(.*(description2).*)", app.getDescription().toLowerCase())) 
        			&& app.getCategory().getName().equals(cat1.getName()));
        }
    }
}
