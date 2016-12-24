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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.ActiveProfiles;

import io._29cu.usmserver.common.utilities.AppHelperTests;
import io._29cu.usmserver.controllers.rest.ApplicationBundleControllerTests;
import io._29cu.usmserver.controllers.rest.ApplicationControllerTests;
import io._29cu.usmserver.controllers.rest.CodeDefinitionControllerTests;
import io._29cu.usmserver.controllers.rest.ConsumerProfileControllerTests;
import io._29cu.usmserver.controllers.rest.DeveloperApplicationsControllerTests;
import io._29cu.usmserver.controllers.rest.DeveloperProfileControllerTests;
import io._29cu.usmserver.controllers.rest.EmployeeProfileControllerTests;
import io._29cu.usmserver.controllers.rest.ReviewControllerTests;
import io._29cu.usmserver.controllers.rest.StoreControllerTests;
import io._29cu.usmserver.controllers.rest.SubscriptionControllerTests;
import io._29cu.usmserver.controllers.rest.UserControllerTests;
import io._29cu.usmserver.core.repositories.UserRepositoryTests;
import io._29cu.usmserver.core.service.ApplicationBundleServiceTests;
import io._29cu.usmserver.core.service.ApplicationHistoryServiceTests;
import io._29cu.usmserver.core.service.ApplicationServiceTests;
import io._29cu.usmserver.core.service.ApplicationUpdateServiceTests;
import io._29cu.usmserver.core.service.CategoryServiceTests;
import io._29cu.usmserver.core.service.ConsumerProfileServiceTests;
import io._29cu.usmserver.core.service.DeveloperProfileServiceTests;
import io._29cu.usmserver.core.service.EmployeeProfileServiceTests;
import io._29cu.usmserver.core.service.FeatureApplicationServiceTests;
import io._29cu.usmserver.core.service.RateServiceTests;
import io._29cu.usmserver.core.service.ReportServiceTests;
import io._29cu.usmserver.core.service.ReviewReplyServiceTests;
import io._29cu.usmserver.core.service.ReviewServiceTests;
import io._29cu.usmserver.core.service.SubscriptionServiceTests;
import io._29cu.usmserver.core.service.UserServiceTests;
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
    ReviewControllerTests.class
})
@ActiveProfiles("test")
public class AlphaUmiTestSuite {
}
