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

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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

import io._29cu.usmserver.core.model.entities.Category;
import io._29cu.usmserver.core.service.CategoryService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CodeDefinitionControllerTests {
    @InjectMocks
    private CodeDefinitionController codeDefinitionController;

    @Mock
    private CategoryService categoryService;

    private MockMvc mockMvc;

    private List<Category> catlist;
    private Category cat1;
    private Category cat2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(codeDefinitionController).build();

        catlist = new ArrayList<>();

        cat1 = new Category();
        cat1.setId(1l);
        cat1.setName("Productivity");
        catlist.add(cat1);

        cat2 = new Category();
        cat2.setId(2l);
        cat2.setName("Others");
        catlist.add(cat2);
    }

    @Test
    public void  testCategory() throws Exception {
        when(categoryService.getCategories()).thenReturn(catlist);

        mockMvc.perform(get("/api/1/codes/category"))
                .andExpect(jsonPath("$.codes[*].value",
                        hasItems(endsWith("Productivity"), endsWith("Others"))))
                .andExpect(status().isOk());
    }

    @Test
    public void  testCategoryBadRequest() throws Exception {
        when(categoryService.getCategories()).thenReturn(null);

        mockMvc.perform(get("/api/1/codes/category"))
                .andExpect(status().isBadRequest());
    }

}
