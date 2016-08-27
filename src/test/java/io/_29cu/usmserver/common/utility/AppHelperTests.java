package io._29cu.usmserver.common.utility;

import io._29cu.usmserver.core.model.entity.Application;
import io._29cu.usmserver.core.repository.ApplicationRepository;
import io._29cu.usmserver.core.repository.UserRepository;
import io._29cu.usmserver.core.service.utility.DummyData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AppHelperTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    public void testInstanciation() {
        AppHelper appHelper = null;
        appHelper = AppHelper.getInstance();
        assertNotNull(appHelper);
    }

    @Test
    public void testConvertIterableToList() {
        DummyData.createDummyData(userRepository, applicationRepository);
        Iterable<Application> appIterable = applicationRepository.findAll();
        List<Application> appList =  AppHelper.getInstance().convertIterableToList(appIterable);

        for (Application app: appIterable) {
            assertTrue(appList.contains(app));
        }
    }
}
