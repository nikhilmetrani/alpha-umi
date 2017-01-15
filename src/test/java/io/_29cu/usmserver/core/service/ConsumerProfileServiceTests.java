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
import io._29cu.usmserver.core.model.entities.ConsumerProfile;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AuthorityName;
import io._29cu.usmserver.core.repositories.ConsumerProfileRepository;
import io._29cu.usmserver.core.repositories.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ConsumerProfileServiceTests {
    @Autowired
    private UserService userService;

    @Autowired
    private ConsumerProfileService consumerService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ConsumerProfileRepository consumerRepository;
    
    private User consumer, consumer2;
    private ConsumerProfile consumerProfile, consumerProfile2;

    @Before
    @Transactional
    @Rollback(false)
    public void setup() {
        consumer = new User();
        consumer.setUsername("consumer");
        consumer.setEmail("consumer@email.com");
        Authority authority = new Authority();
        authority.setName(AuthorityName.ROLE_CONSUMER);
        List<Authority> authList = new ArrayList<>();
        authList = new ArrayList<>();
        authList.add(authority);
        consumer.setAuthorities(authList);
        consumer.setEnabled(true);
        consumer = userService.createUser(consumer);
        
        consumerProfile = new ConsumerProfile();
        consumerProfile.setConsumer(consumer);
        consumerProfile.setEmail(consumer.getEmail());
        consumerProfile.setWebsite("http://www.consumer.com");
        consumerProfile = consumerService.createProfile(consumerProfile);

        consumer2 = new User();
        consumer2.setUsername("consumer2");
        consumer2.setEmail("consumer2@email.com");
        authority = new Authority();
        authority.setName(AuthorityName.ROLE_CONSUMER);
        authList = new ArrayList<>();
        authList = new ArrayList<>();
        authList.add(authority);
        consumer2.setAuthorities(authList);
        consumer2.setEnabled(true);
        consumer2 = userService.createUser(consumer2);
        
        consumerProfile2 = new ConsumerProfile();
        consumerProfile2.setConsumer(consumer2);
        consumerProfile2.setEmail(consumer2.getEmail());
        consumerProfile2.setWebsite("http://www.consumer.com");
    }
	
    @After
	public void tearDown() {
    	consumerRepository.delete(consumerProfile.getId());
    	userRepository.delete(consumer.getId());
    	if(consumerProfile2.getId() != null) {
    		consumerRepository.delete(consumerProfile2.getId());
    	}
   		userRepository.delete(consumer2.getId());
    }

    @Test
    @Transactional
    public void testFindProfileByUserId() {
        ConsumerProfile fromDb = consumerService.findProfileByUserId(consumer.getId());
        assertNotNull("Consumer does not exist", fromDb);
        assertEquals("Incorrect Consumer has been retrieved", consumerProfile.getEmail(), fromDb.getEmail());
    }

    @Test
    @Transactional
    public void testCreateProfile() {
    	ConsumerProfile fromDb = consumerService.createProfile(consumerProfile2);
    	assertNotNull("Consumer not created", fromDb);
        assertEquals("Invalid Consumer info has been inserted", consumerProfile2.getEmail(), fromDb.getEmail());
    }

    @Test
    @Transactional
    public void testModifyProfile() {
    	consumerProfile2 = consumerService.createProfile(consumerProfile2);
    	consumerProfile2.setWebsite("http://www.consumer2.com");
    	ConsumerProfile fromDb = consumerService.modifyProfile(consumerProfile2);
        assertNotNull("Consumer does not exist", fromDb);
        assertEquals("Consumer is not modified", consumerProfile2.getWebsite(), fromDb.getWebsite());
    }
}
