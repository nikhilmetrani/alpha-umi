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

import io._29cu.usmserver.core.model.entities.DeveloperProfile;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.DeveloperProfileService;
import io._29cu.usmserver.core.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DeveloperProfileControllerTests {
    @InjectMocks
    private DeveloperProfileController developerProfileController;
    @Mock
    private DeveloperProfileService profileService;
    @Mock
    private UserService userService;

    // @Mock not used here since they are mocked in setup()
    // Spring boot does not allow mocking with annotation.
    SecurityContext securityContextMocked;
    Authentication authenticationMocked;

    private MockMvc mockMvc;

    private User developer;
    DeveloperProfile profile;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(developerProfileController).build();

        developer = new User();
        developer.setId(22L);
        developer.setEmail("owner@test.com");
        developer.setName("Test Owner");
        profile = new DeveloperProfile();
        profile.setOwner(developer);
        profile.setEmail("test@test.com");
        profile.setId(23L);
        profile.setWebsite("https://test.com");

        // Let's mock the security context
        authenticationMocked = Mockito.mock(Authentication.class);
        securityContextMocked = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContextMocked.getAuthentication()).thenReturn(authenticationMocked);
        SecurityContextHolder.setContext(securityContextMocked);
    }

    @Test
    public void  testGetNonExistentDeveloperProfile() throws Exception {
        when(userService.findUserByPrincipal("22")).thenReturn(developer);
        when(profileService.findProfileByUserId(22L)).thenReturn(null);
        when(authenticationMocked.getPrincipal()).thenReturn("22");
        mockMvc.perform(get("/api/0/developer/22/profile"))
                .andExpect(jsonPath("$.email",
                        equalTo(null)))
                .andExpect(status().isOk());
    }

    @Test
    public void  testGetExistingDeveloperProfile() throws Exception {
        when(userService.findUserByPrincipal("22")).thenReturn(developer);
        when(profileService.findProfileByUserId(22L)).thenReturn(profile);
        when(authenticationMocked.getPrincipal()).thenReturn("22");
        mockMvc.perform(get("/api/0/developer/22/profile"))
                .andExpect(jsonPath("$.email",
                        equalTo(profile.getEmail())))
                .andExpect(jsonPath("$.rid",
                        equalTo(23)))
                .andExpect(jsonPath("$.website",
                        equalTo(profile.getWebsite())))
                .andExpect(status().isOk());
    }

    @Test
    public void  testCreateDeveloperProfile() throws Exception {
        when(userService.findUserByPrincipal("22")).thenReturn(developer);
        when(profileService.createProfile(any(DeveloperProfile.class))).thenReturn(profile);
        when(authenticationMocked.getPrincipal()).thenReturn("22");

        mockMvc.perform(post("/api/0/developer/22/profile")
                .content("{\"email\":\"test@test.com\",\"website\":\"https://test.com\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.email",
                        equalTo(profile.getEmail())))
                .andExpect(jsonPath("$.rid",
                        equalTo(23)))
                .andExpect(jsonPath("$.website",
                        equalTo(profile.getWebsite())))
                .andExpect(header().string("Location", endsWith("/api/0/developer/22/profile")))
                .andExpect(status().isOk());
    }

}
