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

package io._29cu.usmserver.core.service.utilities;

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.AuUser;
import io._29cu.usmserver.core.model.entities.Category;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.repositories.ApplicationRepository;
import io._29cu.usmserver.core.repositories.AuUserRepository;
import io._29cu.usmserver.core.repositories.CategoryRepository;


public class DummyData {

    private static boolean DATA_CREATED = false;

    public static void createDummyData(AuUserRepository userRepository,
                                       ApplicationRepository applicationRepository,
                                       CategoryRepository categoryRepository) {
        if (DATA_CREATED) return;

        createDevelopersAndApplications(userRepository, applicationRepository, categoryRepository);

        DATA_CREATED = true;
    }

    private static void createDevelopersAndApplications(AuUserRepository userRepository,
                                                        ApplicationRepository applicationRepository,
                                                        CategoryRepository categoryRepository) {
        Category catDev = categoryRepository.save(new Category("Development"));
        Category catProd = categoryRepository.save(new Category("Productivity"));
        Category catPhoto = categoryRepository.save(new Category("Photography"));

        AuUser developer = createDeveloper("Microsoft", "support@microsoft.com");
        userRepository.save(developer);
        Application app = createApplication(1L, "Visual Studio Code", developer, catDev, "1.0", AppState.Active, "http:\\test.con", "IDE for scripts");
        applicationRepository.save(app);
        app = createApplication(2L, "Microsoft Office", developer, catProd, "2.0", AppState.Active, "http:\\test.con", "Office tools");
        applicationRepository.save(app);
        app = createApplication(3L, "Dot Net Framewok", developer, catDev, "3.1", AppState.Staging, "http:\\test.con", "Programming framework");
        applicationRepository.save(app);



        developer = createDeveloper("GitHub", "support@github.com");
        userRepository.save(developer);
        app = createApplication(4L, "GitHub Desktop App", developer, catDev, "1.0", AppState.Active, "http:\\test.con", "Github deskop application");
        applicationRepository.save(app);
        app = createApplication(5L, "Atom Editor", developer, catDev, "2.6", AppState.Staging, "http:\\test.con", "IDE");
        applicationRepository.save(app);


        developer = createDeveloper("Google", "support@google.com");
        userRepository.save(developer);
        app = createApplication(6L, "Google Chrome", developer, catProd, "1.0", AppState.Active, "http:\\test.con", "Powerful Browser");
        applicationRepository.save(app);
        app = createApplication(7L, "Google Drive Sync", developer, catProd, "2.0", AppState.Active, "http:\\test.con", "Application for Google drive");
        applicationRepository.save(app);
        app = createApplication(8L, "Google Web Developer", developer, catDev, "3.0", AppState.Staging, "http:\\test.con", "Web application development tool");
        applicationRepository.save(app);


        developer = createDeveloper("Adobe", "support@adobe.com");
        userRepository.save(developer);
        app = createApplication(9L, "Photoshop CC", developer, catPhoto, "1.0", AppState.Active, "http:\\test.con", "Photo Editing tool");
        applicationRepository.save(app);
        app = createApplication(10L, "Lightroom CC", developer, catPhoto, "2.2", AppState.Active, "http:\\test.con", "Photo Editing tool");
        applicationRepository.save(app);
        app = createApplication(11L, "Acrobat DC", developer, catProd, "3.0", AppState.Staging, "http:\\test.con", "PDF editing tool");
        applicationRepository.save(app);

    }

    private static AuUser createDeveloper(String username, String email) {
        AuUser developer = new AuUser();
//        developer.setId(id);
        developer.setUsername(username);
        developer.setEnabled(true);
        developer.setPassword("password");
        developer.setEmail(email);
        return developer;
    }

    private static Application createApplication(Long id, String name, AuUser developer, Category category, String version, AppState state, String downloadUrl, String description) {
        Application app = new Application();
//        app.setId(id);
        app.setDeveloper(developer);
        app.setName(name);
        app.setCategory(category);
        app.setVersion(version);
        app.setState(state);
        app.setDescription(description);
        app.setWhatsNew("test");
        return app;
    }
}
