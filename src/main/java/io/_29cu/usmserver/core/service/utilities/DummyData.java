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
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.entities.Category;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.repositories.ApplicationRepository;
import io._29cu.usmserver.core.repositories.UserRepository;
import io._29cu.usmserver.core.repositories.CategoryRepository;


public class DummyData {

    private static final String DOWNLOAD_URL = "http:\\test.con";
	private static boolean DATA_CREATED = false;

    public static void createDummyData(UserRepository userRepository,
                                       ApplicationRepository applicationRepository,
                                       CategoryRepository categoryRepository) {
        if (DATA_CREATED) 
        	return;

        createDevelopersAndApplications(userRepository, applicationRepository, categoryRepository);

        DATA_CREATED = true;
    }

    private static void createDevelopersAndApplications(UserRepository userRepository,
                                                        ApplicationRepository applicationRepository,
                                                        CategoryRepository categoryRepository) {
        Category catDev = categoryRepository.save(new Category("Development"));
        Category catProd = categoryRepository.save(new Category("Productivity"));
        Category catPhoto = categoryRepository.save(new Category("Photography"));

        User developer = createDeveloper("Microsoft", "support@microsoft.com");
        userRepository.save(developer);
        Application app = createApplication("Visual Studio Code", developer, catDev, "1.0", AppState.Active, DOWNLOAD_URL, "IDE for scripts");
        applicationRepository.save(app);
        app = createApplication("Microsoft Office", developer, catProd, "2.0", AppState.Active, DOWNLOAD_URL, "Office tools");
        applicationRepository.save(app);
        app = createApplication("Dot Net Framewok", developer, catDev, "3.1", AppState.Staging, DOWNLOAD_URL, "Programming framework");
        applicationRepository.save(app);



        developer = createDeveloper("GitHub", "support@github.com");
        userRepository.save(developer);
        app = createApplication("GitHub Desktop App", developer, catDev, "1.0", AppState.Active, DOWNLOAD_URL, "Github deskop application");
        applicationRepository.save(app);
        app = createApplication("Atom Editor", developer, catDev, "2.6", AppState.Staging, DOWNLOAD_URL, "IDE");
        applicationRepository.save(app);


        developer = createDeveloper("Google", "support@google.com");
        userRepository.save(developer);
        app = createApplication("Google Chrome", developer, catProd, "1.0", AppState.Active, DOWNLOAD_URL, "Powerful Browser");
        applicationRepository.save(app);
        app = createApplication("Google Drive Sync", developer, catProd, "2.0", AppState.Active, DOWNLOAD_URL, "Application for Google drive");
        applicationRepository.save(app);
        app = createApplication("Google Web Developer", developer, catDev, "3.0", AppState.Staging, DOWNLOAD_URL, "Web application development tool");
        applicationRepository.save(app);


        developer = createDeveloper("Adobe", "support@adobe.com");
        userRepository.save(developer);
        app = createApplication("Photoshop CC", developer, catPhoto, "1.0", AppState.Active, DOWNLOAD_URL, "Photo Editing tool");
        applicationRepository.save(app);
        app = createApplication("Lightroom CC", developer, catPhoto, "2.2", AppState.Active, DOWNLOAD_URL, "Photo Editing tool");
        applicationRepository.save(app);
        app = createApplication("Acrobat DC", developer, catProd, "3.0", AppState.Staging, DOWNLOAD_URL, "PDF editing tool");
        applicationRepository.save(app);

    }

    private static User createDeveloper(String username, String email) {
        User developer = new User();
        developer.setUsername(username);
        developer.setEnabled(true);
        developer.setPassword("password");
        developer.setEmail(email);
        return developer;
    }

    private static Application createApplication(String name, User developer, Category category, String version, AppState state, String downloadUrl, String description) {
        Application app = new Application();
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
