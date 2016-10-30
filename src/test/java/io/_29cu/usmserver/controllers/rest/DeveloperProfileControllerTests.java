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
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;

import io._29cu.usmserver.core.model.entities.AuUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import io._29cu.usmserver.core.model.entities.DeveloperProfile;
import io._29cu.usmserver.core.service.DeveloperProfileService;
import io._29cu.usmserver.core.service.UserService;

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

    private AuUser developer;
    DeveloperProfile profile;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(developerProfileController).build();

        developer = new AuUser();
        developer.setId(1L);
        developer.setEmail("owner@test.com");
        developer.setUsername("Test Owner");
        developer.setEnabled(true);

        profile = new DeveloperProfile();
        profile.setOwner(developer);
        profile.setEmail("test@test.com");
        profile.setId(23L);
        profile.setWebsite("https://test.com");
        profile.setCountry("country");
        profile.setAddress("address");
        profile.setCity("city");
        profile.setState("state");
        profile.setZipCode(12345);
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        profile.setDateOfBirth(dateFormat.parse("01/01/2001"));
        profile.setGender("gender");
        profile.setHomePhone(123456);
        profile.setWorkPhone(567890);
        profile.setJobTitle("jobTitle");
        profile.setJoinDate(dateFormat.parse("10/11/2016"));
        profile.setLogo("logo_path_detail");
        

        // Let's mock the security context
        authenticationMocked = Mockito.mock(Authentication.class);
        securityContextMocked = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContextMocked.getAuthentication()).thenReturn(authenticationMocked);
        SecurityContextHolder.setContext(securityContextMocked);
    }

    @Test
    public void  testGetNonExistentDeveloperProfile() throws Exception {
        when(userService.findUser()).thenReturn(developer);
        when(profileService.findProfileByUserId(1L)).thenReturn(null);
        mockMvc.perform(get("/api/0/developer/profile"))
                .andExpect(jsonPath("$.email",
                        equalTo(null))) 
                .andExpect(jsonPath("$.rid",
                        equalTo(null)))
                .andExpect(jsonPath("$.website",
                		equalTo(null)))
                .andExpect(jsonPath("$.address",
                		equalTo(null)))
                .andExpect(jsonPath("$.city",
                		equalTo(null)))
                .andExpect(jsonPath("$.state",
                		equalTo(null)))
                .andExpect(jsonPath("$.zipCode",
                		equalTo(null)))
                .andExpect(jsonPath("$.dateOfBirth",
                		equalTo(null)))
                .andExpect(jsonPath("$.gender",
                		equalTo(null)))
                .andExpect(jsonPath("$.homePhone",
                		equalTo(null)))
                .andExpect(jsonPath("workPhone",
                		equalTo(null)))
                .andExpect(jsonPath("$.jobTitle",
                		equalTo(null)))
                .andExpect(jsonPath("$.joinDate",
                		equalTo(null)))
                .andExpect(jsonPath("$.logo",
                		equalTo(null)))
                .andExpect(status().isOk());
    }

    @Test
    public void  testGetExistingDeveloperProfile() throws Exception {
        when(userService.findUser()).thenReturn(developer);
        when(profileService.findProfileByUserId(1L)).thenReturn(profile);
        mockMvc.perform(get("/api/0/developer/profile"))
                .andExpect(jsonPath("$.email",
                        equalTo(profile.getEmail())))
                .andExpect(jsonPath("$.rid",
                        equalTo(23)))
                .andExpect(jsonPath("$.website",
                        equalTo(profile.getWebsite())))
                .andExpect(jsonPath("$.address",
                        equalTo(profile.getAddress())))
                .andExpect(jsonPath("$.city",
                        equalTo(profile.getCity())))
                .andExpect(jsonPath("$.state",
                        equalTo(profile.getState())))
                .andExpect(jsonPath("$.zipCode",
                        equalTo(profile.getZipCode())))
                .andExpect(jsonPath("$.dateOfBirth",
                        equalTo(profile.getDateOfBirth().getTime())))
                .andExpect(jsonPath("$.gender",
                        equalTo(profile.getGender())))
                .andExpect(jsonPath("$.homePhone",
                        equalTo(profile.getHomePhone())))
                .andExpect(jsonPath("workPhone",
                        equalTo(profile.getWorkPhone())))
                .andExpect(jsonPath("$.jobTitle",
                        equalTo(profile.getJobTitle())))
                .andExpect(jsonPath("$.joinDate",
                        equalTo(profile.getJoinDate().getTime())))
                .andExpect(jsonPath("$.logo",
                        equalTo(profile.getLogo())))
                .andExpect(status().isOk());
    }

    @Test
    public void  testCreateDeveloperProfile() throws Exception {
        when(userService.findUser()).thenReturn(developer);
        when(profileService.createProfile(any(DeveloperProfile.class))).thenReturn(profile);

        mockMvc.perform(post("/api/0/developer/profile")
                .content("{\"email\":\"test@test.com\",\"website\":\"https://test.com\",\"jobTitle\":\"jobTitle\",\"address\":\"address\",\"city\":\"city\",\"state\":\"state\",\"zipCode\":12345,\"country\":\"country\",\"workPhone\":567890,\"homePhone\":123456,\"dateOfBirth\":978278400000,\"gender\":\"gender\",\"joinDate\":1478707200000,\"logo\":\"logo_path_detail\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email",
                        equalTo(profile.getEmail())))
                .andExpect(jsonPath("$.rid",
                        equalTo(23)))
                .andExpect(jsonPath("$.website",
                        equalTo(profile.getWebsite())))
                .andExpect(jsonPath("$.country",
                        equalTo(profile.getCountry())))
                .andExpect(jsonPath("$.address",
                        equalTo(profile.getAddress())))
                .andExpect(jsonPath("$.city",
                        equalTo(profile.getCity())))
                .andExpect(jsonPath("$.state",
                        equalTo(profile.getState())))
                .andExpect(jsonPath("$.zipCode",
                        equalTo(profile.getZipCode())))
                .andExpect(jsonPath("$.dateOfBirth",
                        equalTo(profile.getDateOfBirth().getTime())))
                .andExpect(jsonPath("$.gender",
                        equalTo(profile.getGender())))
                .andExpect(jsonPath("$.homePhone",
                        equalTo(profile.getHomePhone())))
                .andExpect(jsonPath("workPhone",
                        equalTo(profile.getWorkPhone())))
                .andExpect(jsonPath("$.jobTitle",
                        equalTo(profile.getJobTitle())))
                .andExpect(jsonPath("$.joinDate",
                        equalTo(profile.getJoinDate().getTime())))
                .andExpect(jsonPath("$.logo",
                        equalTo(profile.getLogo())))
                .andExpect(header().string("Location", endsWith("/api/0/developer/profile")))
                .andExpect(status().isOk());
    }

    @Test
    public void  testDeveloperProfileErrorHandling() throws Exception {
        when(userService.findUser()).thenReturn(null);
        mockMvc.perform(get("/api/0/developer/profile"))
                .andExpect(status().isForbidden());
    }
    
    
    @Test
    public void  testDeveloperProfileErrorHandlingDifferentUrl() throws Exception {
        when(userService.findUser()).thenReturn(null);
        mockMvc.perform(get("/api/0/developer/profile"))
                .andExpect(status().isForbidden());
    }
}
