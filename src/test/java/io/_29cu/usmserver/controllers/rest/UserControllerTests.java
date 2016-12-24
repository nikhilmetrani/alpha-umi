package io._29cu.usmserver.controllers.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import io._29cu.usmserver.core.model.entities.Authority;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AuthorityName;
import io._29cu.usmserver.core.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTests {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;
    
    // @Mock not used here since they are mocked in setup()
    // Spring boot does not allow mocking with annotation.

    private MockMvc mockMvc;

    private User user;
    private User moderator;
    
    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user = new User();
        user.setEmail("owner@test.com");
        user.setUsername("Test Owner");
        user.setEnabled(true);
        Authority authUser = new Authority();
        authUser.setName(AuthorityName.ROLE_CONSUMER);
        List<Authority> authUserList = new ArrayList<>();
        authUserList.add(authUser);
        user.setAuthorities(authUserList);

        moderator = new User();
        moderator.setEmail("moderator@test.com");
        moderator.setUsername("Test Moderator");
        moderator.setEnabled(true);
        Authority authModerator = new Authority();
        authModerator.setName(AuthorityName.ROLE_ADMIN);
        List<Authority> authModeratorList = new ArrayList<>();
        authModeratorList.add(authModerator);
        moderator.setAuthorities(authModeratorList);

    }
    
    @Test
    public void  testGetUser() throws Exception {
    	when(userService.findAuthenticatedUser()).thenReturn(user);
        mockMvc.perform(get("/api/0/user"))
                .andExpect(jsonPath("$.username",
                        equalTo(user.getUsername())))
                .andExpect(status().isOk());
    }
    
    @Test
    public void  testCreateUser() throws Exception {
    	when(userService.createUser(any(User.class))).thenReturn(null);
        mockMvc.perform(post("/api/1/signup"))
                .andExpect(status().isBadRequest());

        when(userService.createUser(any(User.class))).thenReturn(user);
        mockMvc.perform(post("/api/1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{'username': 'Test Owner', 'email': 'owner@test.com', 'password': 'password'}".replace("'", "\"")))
                .andExpect(jsonPath("$.username",
                        equalTo(user.getUsername())))
                .andExpect(status().isOk());
    }

    @Test
    public void testBlockUser() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(moderator);
        when(userService.findUser(1l)).thenReturn(user);
        when(userService.blockUser(any(User.class))).thenReturn(true);

        mockMvc.perform(post("/api/0/admin/users/" + 1l + "/block/"))
                .andExpect(jsonPath("$", equalTo(true)))
                .andExpect(status().isOk());
    }

}
