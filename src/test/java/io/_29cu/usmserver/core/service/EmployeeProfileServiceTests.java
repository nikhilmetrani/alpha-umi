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
import io._29cu.usmserver.core.model.entities.EmployeeProfile;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AuthorityName;
import io._29cu.usmserver.core.repositories.EmployeeProfileRepository;
import io._29cu.usmserver.core.repositories.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeProfileServiceTests {
    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeProfileService employeeService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmployeeProfileRepository employeeRepository;
    
    private User employee, employee2;
    private EmployeeProfile employeeProfile, employeeProfile2;

    @Before
    @Transactional
    @Rollback(false)
    public void setup() {
        employee = new User();
        employee.setUsername("employee");
        employee.setEmail("employee@email.com");
        Authority authority = new Authority();
        authority.setName(AuthorityName.ROLE_MAINTAINER);
        List<Authority> authList = new ArrayList<>();
        authList = new ArrayList<>();
        authList.add(authority);
        employee.setAuthorities(authList);
        employee.setEnabled(true);
        employee = userService.createUser(employee);
        
        employeeProfile = new EmployeeProfile();
        employeeProfile.setEmployee(employee);
        employeeProfile.setEmail(employee.getEmail());
        employeeProfile.setCountry("INDIA");
        employeeProfile = employeeService.createProfile(employeeProfile);

        employee2 = new User();
        employee2.setUsername("employee2");
        employee2.setEmail("employee2@email.com");
        authority = new Authority();
        authority.setName(AuthorityName.ROLE_CONSUMER);
        authList = new ArrayList<>();
        authList = new ArrayList<>();
        authList.add(authority);
        employee2.setAuthorities(authList);
        employee2.setEnabled(true);
        employee2 = userService.createUser(employee2);
        
        employeeProfile2 = new EmployeeProfile();
        employeeProfile2.setEmployee(employee2);
        employeeProfile2.setEmail(employee2.getEmail());
        employeeProfile2.setCountry("INDIA");
    }
	
    @After
	public void tearDown() {
    	employeeRepository.delete(employeeProfile.getId());
    	userRepository.delete(employee.getId());
    	if(employeeProfile2.getId() != null) {
    		employeeRepository.delete(employeeProfile2.getId());
    	}
   		userRepository.delete(employee2.getId());
    }

    @Test
    @Transactional
    public void testFindProfileByUserId() {
        EmployeeProfile fromDb = employeeService.findProfileByUserId(employee.getId());
        assertNotNull("Employee does not exist", fromDb);
        assertEquals("Incorrect Employee has been retrieved", employeeProfile.getEmail(), fromDb.getEmail());
    }

    @Test
    @Transactional
    public void testCreateProfile() {
    	EmployeeProfile fromDb = employeeService.createProfile(employeeProfile2);
    	assertNotNull("Employee not created", fromDb);
        assertEquals("Invalid Employee info has been inserted", employeeProfile2.getEmail(), fromDb.getEmail());
    }

    @Test
    @Transactional
    public void testModifyProfile() {
    	employeeProfile2 = employeeService.createProfile(employeeProfile2);
    	employeeProfile2.setCountry("SINGAPORE");
    	EmployeeProfile fromDb = employeeService.modifyProfile(employeeProfile2);
        assertNotNull("Employee does not exist", fromDb);
        assertEquals("Employee is not modified", employeeProfile2.getCountry(), fromDb.getCountry());
    }
}
