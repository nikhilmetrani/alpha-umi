package io._29cu.usmserver.core.service.utilities;

/**
 * Created by yniu on 17/10/2016.
 */

import io._29cu.usmserver.common.utilities.AppHelper;
import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.ApplicationBundle;
import io._29cu.usmserver.core.repositories.ApplicationRepository;
import io._29cu.usmserver.core.repositories.CategoryRepository;
import io._29cu.usmserver.core.repositories.UserRepository;
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
public class ApplicationBundleTests {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Transactional
    public void testApplicationBundleClass() {
        DummyData.createDummyData(userRepository, applicationRepository, categoryRepository);
        List<Application> appRepoApps =  AppHelper.getInstance().convertIterableToList(applicationRepository.findAll());
        ApplicationBundle appBundle = new ApplicationBundle();
        appBundle.setApplications(appRepoApps);
        List<Application> appBundleApps = appBundle.getApplications();
        for (Application app: appRepoApps) {
            assertTrue(appBundleApps.contains(app));
        }
    }
}