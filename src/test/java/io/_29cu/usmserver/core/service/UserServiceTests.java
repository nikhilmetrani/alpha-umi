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
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import io._29cu.usmserver.core.model.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTests {

    @Autowired
    private UserService service;

    private User account;

    @Before
    @Transactional
    @Rollback(false)
    public void setup() {
        account = new User();
        account.setEmail("email");
        account.setUsername("username");
        account.setEnabled(true);
        service.createUser(account);
    }

    @Test
    @Transactional
    public void testCreateUser() {
        User fromDb = service.createUser(account);
        assertNotNull(fromDb);
        assertEquals("Account was retrieved", account.getEmail(), fromDb.getEmail());
    }

    @Test
    @Transactional
    public void testUpdateUser() {
        account.setEmail("email2");
        Boolean result = service.updateUser(account);
        assertTrue(result);
    }

    @Test
    @Transactional
    public void testFindUser() {
        User fromDb = service.findUser(account.getId());
        assertNotNull(fromDb);
        assertEquals("Account was retrieved", account.getEmail(), fromDb.getEmail());
    }

    @Test
    @Transactional
    public void testBlockUser() {
        User fromDb = service.findUser(account.getId());
        assertNotNull(fromDb);
        boolean blocked = service.blockUser(fromDb);
        assertTrue("Account was blocked", blocked);
    }
}
