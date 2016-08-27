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

package io._29cu.usmserver.core.service.utility;

import io._29cu.usmserver.common.utility.AppHelper;
import io._29cu.usmserver.core.model.entity.Application;
import io._29cu.usmserver.core.repository.ApplicationRepository;
import io._29cu.usmserver.core.repository.UserRepository;
import io._29cu.usmserver.core.service.ApplicationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ApplicationListTests {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void testApplicationListClass() {
        DummyData.createDummyData(userRepository, applicationRepository);
        List<Application> appList =  AppHelper.getInstance().convertIterableToList(applicationRepository.findAll());
        ApplicationList applicationList = new ApplicationList();
        applicationList.setApplications(appList);
        List<Application> appList2 = applicationList.getApplications();
        for (Application app: appList) {
            assertTrue(appList2.contains(app));
        }
    }
}
