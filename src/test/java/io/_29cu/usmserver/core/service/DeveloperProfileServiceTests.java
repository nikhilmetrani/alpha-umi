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
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

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

import io._29cu.usmserver.core.model.entities.Authority;
import io._29cu.usmserver.core.model.entities.DeveloperProfile;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AuthorityName;
import io._29cu.usmserver.core.repositories.DeveloperProfileRepository;
import io._29cu.usmserver.core.repositories.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DeveloperProfileServiceTests {
    @Autowired
    private UserService userService;

    @Autowired
    private DeveloperProfileService developerService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DeveloperProfileRepository developerRepository;
    
    private User developer, developer2;
    private DeveloperProfile developerProfile, developerProfile2;

    @Before
    @Transactional
    @Rollback(false)
    public void setup() {
        developer = new User();
        developer.setUsername("developer");
        developer.setEmail("developer@email.com");
        Authority authority = new Authority();
        authority.setName(AuthorityName.ROLE_DEVELOPER);
        List<Authority> authList = new ArrayList<>();
        authList = new ArrayList<>();
        authList.add(authority);
        developer.setAuthorities(authList);
        developer.setEnabled(true);
        developer = userService.createUser(developer);
        
        developerProfile = new DeveloperProfile();
        developerProfile.setOwner(developer);
        developerProfile.setEmail(developer.getEmail());
        developerProfile.setWebsite("http://www.developer.com");
        developerProfile = developerService.createProfile(developerProfile);

        developer2 = new User();
        developer2.setUsername("developer2");
        developer2.setEmail("developer2@email.com");
        authority = new Authority();
        authority.setName(AuthorityName.ROLE_CONSUMER);
        authList = new ArrayList<>();
        authList = new ArrayList<>();
        authList.add(authority);
        developer2.setAuthorities(authList);
        developer2.setEnabled(true);
        developer2 = userService.createUser(developer2);
        
        developerProfile2 = new DeveloperProfile();
        developerProfile2.setOwner(developer2);
        developerProfile2.setEmail(developer2.getEmail());
        developerProfile2.setWebsite("http://www.developer.com");
    }
	
    @After
	public void tearDown() {
    	developerRepository.delete(developerProfile.getId());
    	userRepository.delete(developer.getId());
    	if(developerProfile2.getId() != null) {
    		developerRepository.delete(developerProfile2.getId());
    	}
   		userRepository.delete(developer2.getId());
    }

    @Test
    @Transactional
    public void testFindProfileByUserId() {
        DeveloperProfile fromDb = developerService.findProfileByUserId(developer.getId());
        assertNotNull("Developer does not exist", fromDb);
        assertEquals("Incorrect Developer has been retrieved", developerProfile.getEmail(), fromDb.getEmail());
    }

    @Test
    @Transactional
    public void testCreateProfile() {
    	DeveloperProfile fromDb = developerService.createProfile(developerProfile2);
    	assertNotNull("Developer not created", fromDb);
        assertEquals("Invalid Developer info has been inserted", developerProfile2.getEmail(), fromDb.getEmail());
    }

    @Test
    @Transactional
    public void testModifyProfile() {
    	developerProfile2 = developerService.createProfile(developerProfile2);
    	developerProfile2.setWebsite("http://www.developer2.com");
    	DeveloperProfile fromDb = developerService.modifyProfile(developerProfile2);
        assertNotNull("Developer does not exist", fromDb);
        assertEquals("Developer is not modified", developerProfile2.getWebsite(), fromDb.getWebsite());
    }
}
