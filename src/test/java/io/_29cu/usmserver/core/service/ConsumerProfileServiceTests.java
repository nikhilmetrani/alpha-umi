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
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AuthorityName;
import io._29cu.usmserver.core.repositories.ConsumerProfileRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ConsumerProfileServiceTests {
    @Autowired
    private ConsumerProfileService consumerService;

    @Autowired
    private ConsumerProfileRepository consumerRepository;

    private User profile1, profile2;

    @Before
    @Transactional
    @Rollback(false)
    public void setup() {
        // First user
        profile1 = new User();
        profile1.setUsername("consumer1");
        profile1.setEmail("consumer1@usm.com");
        Authority authority = new Authority();
        authority.setName(AuthorityName.ROLE_CONSUMER);
        List<Authority> authList = new ArrayList<>();
        authList = new ArrayList<>();
        authList.add(authority);
        profile1.setAuthorities(authList);
        profile1.setEnabled(true);
        profile1 = consumerService.createProfile(profile1);

        // Second user
        profile2 = new User();
        profile2.setUsername("consumer2");
        profile2.setEmail("consumer2@usm.com");
        authority = new Authority();
        authority.setName(AuthorityName.ROLE_CONSUMER);
        authList = new ArrayList<>();
        authList.add(authority);
        profile2.setAuthorities(authList);
        profile2.setEnabled(true);
        profile2 = consumerService.createProfile(profile2);
    }
	
    @After
	public void tearDown() {
    	consumerRepository.delete(profile1.getId());
    	if(profile2.getId() != null) {
    		consumerRepository.delete(profile2.getId());
    	}
    }

    @Test
    @Transactional
    public void testFindProfileByUserId() {
        User fromDb = consumerService.findProfileByUserId(profile1.getId());
        assertNotNull("Consumer does not exist", fromDb);
        assertEquals("Incorrect Consumer has been retrieved", profile1.getEmail(), fromDb.getEmail());
    }

    @Test
    @Transactional
    public void testCreateProfile() {
        User fromDb = consumerService.createProfile(profile2);
    	assertNotNull("Consumer not created", fromDb);
        assertEquals("Invalid Consumer info has been inserted", profile2.getEmail(), fromDb.getEmail());
    }

    @Test
    @Transactional
    public void testModifyProfile() {
        profile2 = consumerService.createProfile(profile1);
    	User fromDb = consumerService.modifyProfile(profile2);
        assertNotNull("Consumer does not exist", fromDb);
    }
}
