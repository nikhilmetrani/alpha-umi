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

package io._29cu.usmserver.controllers.rest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.Category;
import io._29cu.usmserver.core.model.entities.FeaturedApplication;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppListType;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.service.ApplicationListService;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.CategoryService;
import io._29cu.usmserver.core.service.utilities.ApplicationList;
import io._29cu.usmserver.core.service.utilities.CategoryList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class StoreControllerTests {
    @InjectMocks
    private StoreController storeController;

    @Mock
    private ApplicationService applicationService;

    @Mock
    private ApplicationListService applicationListService;

    @Mock 
    private CategoryService categoryService;

    private MockMvc mockMvc;

    private User appOwner;
    private ApplicationList appList;
    private ApplicationList activeAppList;

    private String uuid;
    private String appUUID2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(storeController).build();

        uuid = UUID.randomUUID().toString();
        appUUID2 = UUID.randomUUID().toString();

        appOwner = new User();
        appOwner.setId(1L);
        appOwner.setEmail("owner@test.com");
        appOwner.setUsername("Test Owner");

        List<Application> list = new ArrayList<>();

        appList = new ApplicationList();
        activeAppList = new ApplicationList();

        Application appA = new Application();
        appA.setDeveloper(appOwner);
        appA.setName("Application A");
        appA.setId(uuid);
        appA.setCategory(new Category("Productivity"));
        appA.setState(AppState.Active);
        list.add(appA);

        Application appB = new Application();
        appB.setDeveloper(appOwner);
        appB.setName("Application B");
        appB.setId(appUUID2);
        appB.setCategory(new Category("Development"));
        appB.setState(AppState.Active);
        list.add(appB);
        
        activeAppList.setApplications(list);

        Application appC = new Application();
        appC.setDeveloper(appOwner);
        appC.setName("Application C");
        appC.setId(appUUID2);
        appC.setCategory(new Category("Development"));
        appC.setState(AppState.Staging);
        list.add(appC);

        appList.setApplications(list);
    }

    @Test
    public void  testStore() throws Exception {
        when(applicationService.getAllApplications()).thenReturn(appList);
        when(applicationService.getAllActiveApplications()).thenReturn(activeAppList);

        mockMvc.perform(get("/api/1/store"))
                .andExpect(jsonPath("$.applications[*].name",
                        hasItems(endsWith("Application A"), endsWith("Application B"))))
                .andExpect(jsonPath("$.applications[*].name",
                        not(endsWith("Application C"))))
                .andExpect(status().isOk());
    }

    @Test
    public void  testStoreErrorHandling() throws Exception {
        when(applicationService.getAllApplications()).thenReturn(null);

        mockMvc.perform(get("/api/1/store"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void  testGetApplicationByCategory() throws Exception {
        List<Application> list = new ArrayList<>();
        Application appB = new Application();
        appB.setDeveloper(appOwner);
        appB.setName("Application B");
        appB.setId(appUUID2);
        appB.setCategory(new Category("Development"));
        appB.setState(AppState.Active);
        list.add(appB);
        appList.setApplications(list);

        when(applicationService.findApplicationsByCategory("Development")).thenReturn(appList);
        when(applicationService.findApplicationsByCategoryAndState("Development", AppState.Active.ordinal())).thenReturn(appList);

        mockMvc.perform(get("/api/1/store/Development"))
                .andExpect(jsonPath("$.applications[*].category.name",
                        hasItems(endsWith(appB.getCategory().getName()))))
                .andExpect(jsonPath("$.applications[*].state",
                		hasItems("Active")))
                .andExpect(status().isOk());
    }

    @Test
    public void  testGetApplicationByCategoryErrorHandling() throws Exception {
        when(applicationService.findApplicationsByCategory("Development")).thenReturn(null);

        mockMvc.perform(get("/api/1/store/Development"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSearchApplication() throws Exception {
        List<Application> list = new ArrayList<>();
        Application appA = new Application();
        appA.setId(appUUID2);
        appA.setName("Dreamweaver 1.0");
        appA.setDescription("Dreamweaver description. Powerful tool to build web applications");
        appA.setVersion("1.0");
        appA.setWhatsNew("This is the 1st version.");
        appA.setCategory(new Category("Development"));
        appA.setState(AppState.Active);
        appA.setDeveloper(appOwner);
        list.add(appA);

        Application appB = new Application();
        appB.setId(appUUID2);
        appB.setName("Acrobat Reader 2.0");
        appB.setDescription("Acrobat Reader. Application to create/edit pdf");
        appB.setVersion("2.0");
        appB.setWhatsNew("This is the version 2.0.");
        appB.setCategory(new Category("Productivity"));
        appB.setState(AppState.Active);
        appB.setDeveloper(appOwner);
        list.add(appB);

        appOwner.setFirstname("tool de guru");
        Application appC = new Application();
        appC.setId(appUUID2);
        appC.setName("Adobe photoshop 2.0");
        appC.setDescription("Adobe photoshop. Tool to create/edit images");
        appC.setVersion("2.0");
        appC.setWhatsNew("This is the version 2.0.");
        appC.setCategory(new Category("Productivity"));
        appC.setState(AppState.Active);
        appC.setDeveloper(appOwner);
        list.add(appC);

        appList.setApplications(list);
        
        when(applicationService.findApplicationsByKeyword("tool reader")).thenReturn(appList);

        mockMvc.perform(get("/api/1/store/search?keyword=tool reader"))
                .andExpect(jsonPath("$.applications[*].name",
                        hasItems(containsString("Reader"))))
                .andExpect(jsonPath("$.applications[*].description",
                        hasItems(containsString("tool"))))
                .andExpect(jsonPath("$.applications[*].developer.firstname",
                        hasItems(containsString("tool"))))
                .andExpect(jsonPath("$.applications[*].state",
        		        hasItems("Active")))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchApplicationForBadRequest() throws Exception {
        when(applicationService.findApplicationsByKeyword("tool")).thenReturn(null);

        mockMvc.perform(get("/api/1/store/search?keyword=tool"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/1/store/search?keyword="))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSearchApplicationForNotFound() throws Exception {
    	appList = new ApplicationList();

        when(applicationService.findApplicationsByKeyword("tool")).thenReturn(appList);

        mockMvc.perform(get("/api/1/store/search?keyword=tool"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSearchApplicationByCateogry() throws Exception {
        List<Application> list = new ArrayList<>();
        Application appA = new Application();
        appA.setId(appUUID2);
        appA.setName("Dreamweaver 1.0");
        appA.setDescription("Dreamweaver description. Powerful tool to build web applications");
        appA.setVersion("1.0");
        appA.setWhatsNew("This is the 1st version.");
        appA.setCategory(new Category("Development"));
        appA.setState(AppState.Active);
        appA.setDeveloper(appOwner);
        // list.add(appA);

        Application appB = new Application();
        appB.setId(appUUID2);
        appB.setName("Acrobat Reader 2.0");
        appB.setDescription("Acrobat Reader. Application to create/edit pdf");
        appB.setVersion("2.0");
        appB.setWhatsNew("This is the version 2.0.");
        appB.setCategory(new Category("Productivity"));
        appB.setState(AppState.Active);
        appB.setDeveloper(appOwner);
        list.add(appB);

        appOwner.setFirstname("tool de guru");
        Application appC = new Application();
        appC.setId(appUUID2);
        appC.setName("Adobe photoshop 2.0");
        appC.setDescription("Adobe photoshop. Tool to create/edit images");
        appC.setVersion("2.0");
        appC.setWhatsNew("This is the version 2.0.");
        appC.setCategory(new Category("Productivity"));
        appC.setState(AppState.Active);
        appC.setDeveloper(appOwner);
        list.add(appC);

        appList.setApplications(list);
        
        when(applicationService.findApplicationsByCategoryAndKeyword(1l, "tool reader")).thenReturn(appList);

        mockMvc.perform(get("/api/1/store/search/category/1?keyword=tool reader"))
                .andExpect(jsonPath("$.applications[*].name",
                        hasItems(containsString("Reader"))))
                .andExpect(jsonPath("$.applications[*].description",
                        hasItems(either(containsString("tool")).or(containsString("Reader")))))
                .andExpect(jsonPath("$.applications[*].developer.firstname",
                        hasItems(containsString("tool"))))
                .andExpect(jsonPath("$.applications[*].category.name",
                        hasItems(containsString("Productivity"))))
                .andExpect(jsonPath("$.applications[*].state",
        		        hasItems("Active")))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchApplicationByCateogryForBadRequest() throws Exception {
        when(applicationService.findApplicationsByCategoryAndKeyword(1l, "tool")).thenReturn(null);

        mockMvc.perform(get("/api/1/store/search?keyword="))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/1/store/search/category/1?keyword=tool"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSearchApplicationByCateogryForNotFound() throws Exception {
    	appList = new ApplicationList();

        when(applicationService.findApplicationsByCategoryAndKeyword(1l, "tool")).thenReturn(appList);

        mockMvc.perform(get("/api/1/store/search/category/1?keyword=tool"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetApplicationsByBrowseType() throws Exception {
        List<Application> list = new ArrayList<>();
        Application appA = new Application();
        appA.setId(appUUID2);
        appA.setName("Dreamweaver 1.0");
        appA.setDescription("Dreamweaver description. Powerful tool to build web applications");
        appA.setVersion("1.0");
        appA.setWhatsNew("This is the 1st version.");
        appA.setCategory(new Category("Development"));
        appA.setState(AppState.Active);
        appA.setDeveloper(appOwner);
        // list.add(appA);

        Application appB = new Application();
        appB.setId(appUUID2);
        appB.setName("Acrobat Reader 2.0");
        appB.setDescription("Acrobat Reader. Application to create/edit pdf");
        appB.setVersion("2.0");
        appB.setWhatsNew("This is the version 2.0.");
        appB.setCategory(new Category("Productivity"));
        appB.setState(AppState.Active);
        appB.setDeveloper(appOwner);
        list.add(appB);

        appList.setApplications(list);
        
        FeaturedApplication featuredApp1 = new FeaturedApplication(); 
        featuredApp1.setApplication(appA);
        FeaturedApplication featuredApp2 = new FeaturedApplication(); 
        featuredApp2.setApplication(appB);

        when(applicationListService.getApplicationBrowsingList(AppListType.Featured)).thenReturn(appList);

        mockMvc.perform(get("/api/1/store/browse/Featured"))
                .andExpect(jsonPath("$.applications[*].name",
                        hasItems(either(containsString("Reader")).or(containsString("Dreamweaver")))))
                .andExpect(jsonPath("$.applications[*].description",
                        hasItems(either(containsString("tool")).or(containsString("Reader")))))
                .andExpect(jsonPath("$.applications[*].state",
        		        hasItems("Active")))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetApplicationsByBrowseTypeForBadRequest() throws Exception {
    	when(applicationListService.getApplicationBrowsingList(AppListType.Featured)).thenReturn(null);

        mockMvc.perform(get("/api/1/store/browse/Featured"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetCategory() throws Exception {
        List<Category> list = new ArrayList<>();
        Category cat1 = new Category("Development");
        cat1.setId(1L);
        Category cat2 = new Category("Productivity");
        cat2.setId(2L);
        
        list.add(cat1);
        list.add(cat2);
        
        CategoryList catList = new CategoryList(list);

        when(categoryService.findCategories()).thenReturn(catList);

        mockMvc.perform(get("/api/1/store/category"))
                .andExpect(jsonPath("$.categories[*].name",
                        hasItems(either(containsString("Development")).or(containsString("Productivity")))))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCategoryForBadRequest() throws Exception {
    	when(categoryService.findCategories()).thenReturn(null);

        mockMvc.perform(get("/api/1/store/category"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetCategoryById() throws Exception {
        List<Category> list = new ArrayList<>();
        Category cat1 = new Category("Development");
        cat1.setId(1L);
        Category cat2 = new Category("Productivity");
        cat2.setId(2L);
        
        list.add(cat1);
        list.add(cat2);
        
        CategoryList catList = new CategoryList(list);

        when(categoryService.findCategory(1L)).thenReturn(cat1);

        mockMvc.perform(get("/api/1/store/category/1"))
                .andExpect(jsonPath("$.name",
                        equalTo(("Development"))))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCategoryByIdForNotFound() throws Exception {
    	when(categoryService.findCategory(1L)).thenReturn(null);

        mockMvc.perform(get("/api/1/store/category/1"))
                .andExpect(status().isNotFound());
    }

}
