package io._29cu.usmserver.controllers.rest;

/**
 * Created by yniu on 16/12/2016.
 */


import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;

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

import io._29cu.usmserver.core.model.entities.ConsumerProfile;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.ConsumerProfileService;
import io._29cu.usmserver.core.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ConsumerProfileControllerTests {
    @InjectMocks
    private ConsumerProfileController consumerProfileController;
    @Mock
    private ConsumerProfileService profileService;
    @Mock
    private UserService userService;

    // @Mock not used here since they are mocked in setup()
    // Spring boot does not allow mocking with annotation.
    SecurityContext securityContextMocked;
    Authentication authenticationMocked;

    private MockMvc mockMvc;

    private User profile;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(consumerProfileController).build();

        profile = new User();
        profile.setId(1L);
        profile.setEmail("owner@test.com");
        profile.setUsername("Test Owner");
        profile.setEnabled(true);

        String pattern = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        // Let's mock the security context
        authenticationMocked = Mockito.mock(Authentication.class);
        securityContextMocked = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContextMocked.getAuthentication()).thenReturn(authenticationMocked);
        SecurityContextHolder.setContext(securityContextMocked);
    }

    @Test
    public void  testGetNonExistentConsumerProfile() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(profile);
        when(profileService.findProfileByUserId(1L)).thenReturn(null);
        mockMvc.perform(get("/api/0/consumer/profile"))
                .andExpect(jsonPath("$.rid",
                        equalTo(null)))
                .andExpect(jsonPath("$.email",
                        equalTo(null)))
                .andExpect(status().isOk());
    }

    @Test
    public void  testGetExistingConsumerProfile() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(profile);
        when(profileService.findProfileByUserId(1L)).thenReturn(profile);
        mockMvc.perform(get("/api/0/consumer/profile"))
                .andExpect(jsonPath("$.rid",
                        equalTo(23)))
                .andExpect(jsonPath("$.email",
                        equalTo(profile.getEmail())))
                .andExpect(status().isOk());
    }

    @Test
    public void  testCreateConsumerProfile() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(profile);
        when(profileService.createProfile(any(User.class))).thenReturn(profile);

        mockMvc.perform(post("/api/0/consumer/profile")
                .content("{'email':'test@test.com'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rid",
                        equalTo(23)))
                .andExpect(jsonPath("$.email",
                        equalTo(profile.getEmail())))
                .andExpect(header().string("Location", endsWith("/api/0/consumer/profile")))
                .andExpect(status().isOk());
    }

    @Test
    public void  testConsumerProfileErrorHandling() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(null);
        mockMvc.perform(get("/api/0/consumer/profile"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void  testConsumerProfileErrorHandlingDifferentUrl() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(null);
        mockMvc.perform(get("/api/0/consumer/profile"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void  testCreateConsumerProfileErrorHandling() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(null);
        mockMvc.perform(post("/api/0/consumer/profile")
                .content("{'email':'test@test.com'}".replaceAll("'",  "\""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void  testCreateConsumerProfileWhenCreateProfileFailed() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(profile);
        when(profileService.createProfile(any(User.class))).thenReturn(null);
        mockMvc.perform(post("/api/0/consumer/profile")
                .content("{\"rid\":\"23L\",\"email\":\"test@test.com\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
