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

import io._29cu.usmserver.core.model.entity.Application;
import io._29cu.usmserver.core.model.entity.User;
import io._29cu.usmserver.core.repository.ApplicationRepository;
import io._29cu.usmserver.core.repository.UserRepository;


public class DummyData {

    private static boolean DATA_CREATED = false;

    public static void createDummyData(UserRepository userRepository,
                                        ApplicationRepository applicationRepository) {
        if (DATA_CREATED) return;

        createDevelopersAndApplications(userRepository, applicationRepository);

        DATA_CREATED = true;
    }

    private static void createDevelopersAndApplications(UserRepository userRepository,
                                                        ApplicationRepository applicationRepository) {

        User developer = createDeveloper(1L, "Microsoft", "support@microsoft.com");
        userRepository.save(developer);
        Application app = createApplication(1L, "Visual Studio Code", developer);
        applicationRepository.save(app);
        app = createApplication(2L, "Microsoft Office", developer);
        applicationRepository.save(app);
        app = createApplication(3L, "Dot Net Framewok", developer);
        applicationRepository.save(app);



        developer = createDeveloper(2L, "GitHub", "support@github.com");
        userRepository.save(developer);
        app = createApplication(4L, "GitHub Desktop App", developer);
        applicationRepository.save(app);
        app = createApplication(5L, "Atom Editor", developer);
        applicationRepository.save(app);


        developer = createDeveloper(3L, "Google", "support@google.com");
        userRepository.save(developer);
        app = createApplication(6L, "Google Chrome", developer);
        applicationRepository.save(app);
        app = createApplication(7L, "Google Drive Sync", developer);
        applicationRepository.save(app);
        app = createApplication(8L, "Google Web Developer", developer);
        applicationRepository.save(app);


        developer = createDeveloper(4L, "Adobe", "support@adobe.com");
        userRepository.save(developer);
        app = createApplication(9L, "Photoshop CC", developer);
        applicationRepository.save(app);
        app = createApplication(10L, "Lightroom CC", developer);
        applicationRepository.save(app);
        app = createApplication(11L, "Acrobat DC", developer);
        applicationRepository.save(app);

    }

    private static User createDeveloper(Long id, String name, String email) {
        User developer = new User();
//        developer.setId(id);
        developer.setName(name);
        developer.setEmail(email);
        return developer;
    }

    private static Application createApplication(Long id, String name, User developer) {
        Application app = new Application();
//        app.setId(id);
        app.setDeveloper(developer);
        app.setName(name);
        return app;
    }
}
