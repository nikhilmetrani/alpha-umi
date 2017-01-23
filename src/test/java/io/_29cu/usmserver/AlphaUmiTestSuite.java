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

package io._29cu.usmserver;

import io._29cu.usmserver.configurations.security.JwtTokenUtilTest;
import io._29cu.usmserver.controllers.rest.*;
import io._29cu.usmserver.core.service.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.ActiveProfiles;

import io._29cu.usmserver.common.utilities.AppHelperTests;
import io._29cu.usmserver.core.repositories.UserRepositoryTests;
import io._29cu.usmserver.core.service.utilities.ApplicationListTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    AlphaUmiApplicationTests.class,
    AppHelperTests.class,
    ApplicationBundleControllerTests.class,
    ApplicationControllerTests.class,
    CodeDefinitionControllerTests.class,
    ConsumerProfileControllerTests.class,
    DeveloperApplicationsControllerTests.class,
    DeveloperProfileControllerTests.class,
    EmployeeProfileControllerTests.class,
    StoreControllerTests.class,
    SubscriptionControllerTests.class,
    UserControllerTests.class,
    UserRepositoryTests.class,
    ApplicationBundleServiceTests.class,
    ApplicationHistoryServiceTests.class,
    ApplicationServiceTests.class,
    ApplicationUpdateServiceTests.class,
    CategoryServiceTests.class,
    ConsumerProfileServiceTests.class,
    DeveloperProfileServiceTests.class,
    EmployeeProfileServiceTests.class,
    FeatureApplicationServiceTests.class,
    RateServiceTests.class,
    ReportServiceTests.class,
    ReviewReplyServiceTests.class,
    ReviewServiceTests.class,
    SubscriptionServiceTests.class,
    UserServiceTests.class,
    ApplicationListTests.class,
    ReviewControllerTests.class,
    RatingControllerTests.class,
    ReviewReplyControllerTests.class,
    JwtTokenUtilTest.class,
    ReportControllerTests.class,
    InstallerServiceTests.class
})
@ActiveProfiles("test")
public class AlphaUmiTestSuite {
}
