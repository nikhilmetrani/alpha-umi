package io._29cu.usmserver.controllers.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import io._29cu.usmserver.core.model.entities.User;
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
    SecurityContext securityContextMocked;
    Authentication authenticationMocked;

    private MockMvc mockMvc;

    private User user;
    private String uuid;
    private Principal principal;
    
    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        uuid = UUID.randomUUID().toString();

        user = new User();
        user.setId(uuid);
        user.setEmail("owner@test.com");
        user.setName("Test Owner");
        
        principal = new Principal(){
			@Override
			public String getName() {
				return "Test Owner";
			}
        };
    }
    
    @Test
    public void  testUser() throws Exception {
    	when(userService.createUser(principal)).thenReturn(user);
    	when(userService.findUserByPrincipal(principal.getName())).thenReturn(null);
        mockMvc.perform(get("/user")
        		.principal(principal))
                .andExpect(jsonPath("$.name",
                        equalTo(principal.getName())))
                .andExpect(status().isOk());

    	when(userService.findUserByPrincipal(principal.getName())).thenReturn(user);
        mockMvc.perform(get("/user")
        		.principal(principal))
                .andExpect(jsonPath("$.name",
                        equalTo(user.getName())))
                .andExpect(status().isOk());
    }
    
    @Test
    public void  testGetUser() throws Exception {
    	when(userService.findUser("22")).thenReturn(null);
        mockMvc.perform(get("/user/22"))
                .andExpect(status().isNotFound());

        when(userService.findUser(uuid)).thenReturn(user);
        mockMvc.perform(get("/user/"+uuid))
                .andExpect(jsonPath("$.name",
                        equalTo(user.getName())))
                .andExpect(status().isOk());
    }
}
