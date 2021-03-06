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

package io._29cu.usmserver.core.repositories;

import io._29cu.usmserver.core.model.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;

    private User account;

    @Before
    @Transactional
    @Rollback(false)
    public void setup() {
        account = new User();
        account.setEmail("name@email.com");
        account.setUsername("name");
        account.setEnabled(true);
        repo.save(account);
    }

    @Test
    @Transactional
    public void testFind() {
        User fromDb = repo.findOne(account.getId());
        assertNotNull(fromDb);
        assertEquals("User email does not match", account.getEmail(), fromDb.getEmail());
    }
}
