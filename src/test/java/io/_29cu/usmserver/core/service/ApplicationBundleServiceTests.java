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

import io._29cu.usmserver.core.model.entities.ApplicationBundle;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.repositories.ApplicationBundleRepository;
import io._29cu.usmserver.core.repositories.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ApplicationBundleServiceTests {
	@Autowired
	private ApplicationBundleService service;
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ApplicationBundleRepository applicationBundleRepository;

	private User developer;
	private ApplicationBundle applicationBundle;

	@Before
	@Transactional
	@Rollback(false)
	public void setup() {
		developer = new User();
		developer.setUsername("developer");
		developer.setEmail("developer@email.com");
		developer.setEnabled(true);
		developer = userService.createUser(developer);

		applicationBundle = new ApplicationBundle();
		applicationBundle.setName("applicationBundle");
		applicationBundle.setDeveloper(developer);
		applicationBundle.setState(AppState.Staging);
		applicationBundle.setDescription("test description");
		applicationBundle = service.createApplicationBundle(applicationBundle);
	}

	@After
	public void tearDown() {
		applicationBundleRepository.delete(applicationBundle.getId());
		userRepository.delete(developer.getId());
	}

	@Test
	@Transactional
	public void testFind() {
		ApplicationBundle fromDb = service.findApplicationBundle(applicationBundle.getId());
		assertNotNull(fromDb);
		assertEquals("ApplicationBundle name does not match", applicationBundle.getName(), fromDb.getName());
		assertEquals("ApplicationBundle developer does not match", applicationBundle.getDeveloper(), fromDb.getDeveloper());
	}

	@Test
	@Transactional
	public void testFindApplicationBundleByDeveloperAndName() {
		ApplicationBundle fromDb = service.findApplicationBundleByDeveloperAndName(developer.getId(), applicationBundle.getName());
		assertNotNull(fromDb);
		assertEquals("ApplicationBundle name does not match", applicationBundle.getName(), fromDb.getName());
		assertEquals("ApplicationBundle developer does not match", applicationBundle.getDeveloper(), fromDb.getDeveloper());
	}

	@Test
	@Transactional
	public void testFindApplicationBundleByDeveloperAndId() {
		ApplicationBundle fromDb = service.findApplicationBundleByDeveloperAndId(developer.getId(), applicationBundle.getId());
		assertNotNull(fromDb);
		assertEquals("ApplicationBundle Id does not match", applicationBundle.getId(), fromDb.getId());
		assertEquals("ApplicationBundle developer does not match", applicationBundle.getDeveloper(), fromDb.getDeveloper());
	}

	@Test
	@Transactional
	public void testCreateApplicationBundle() {
		ApplicationBundle fromDb = service.createApplicationBundle(applicationBundle);
		assertNotNull(fromDb);
		assertEquals("ApplicationBundle name does not match", applicationBundle.getId(), fromDb.getId());
		assertEquals("ApplicationBundle developer does not match", applicationBundle.getDeveloper(), fromDb.getDeveloper());
	}
}
