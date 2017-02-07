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

import io._29cu.usmserver.core.model.entities.Code;
import io._29cu.usmserver.core.service.CategoryService;
import io._29cu.usmserver.core.service.utilities.Codes;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CodeDefinitionControllerTests {
    @InjectMocks
    private CodeDefinitionController codeDefinitionController;

    @Mock
    private CategoryService categoryService;

    private MockMvc mockMvc;

    private Code code1;
    private Code code2;

    private Codes codes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(codeDefinitionController).build();

        code1 = new Code("1", "Productivity");
        code2 = new Code("2", "Others");
        
        codes = new Codes();
        codes.add(code1);
        codes.add(code2);
    }

    @Test
    public void  testCategory() throws Exception {
        when(categoryService.getCategories()).thenReturn(codes);

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
