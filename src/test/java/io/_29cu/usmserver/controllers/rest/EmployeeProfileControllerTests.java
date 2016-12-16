package io._29cu.usmserver.controllers.rest;

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

import io._29cu.usmserver.core.model.entities.EmployeeProfile;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.EmployeeProfileService;
import io._29cu.usmserver.core.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeProfileControllerTests {
    @InjectMocks
    private EmployeeProfileController employeeProfileController;
    @Mock
    private EmployeeProfileService profileService;
    @Mock
    private UserService userService;

    // @Mock not used here since they are mocked in setup()
    // Spring boot does not allow mocking with annotation.
    SecurityContext securityContextMocked;
    Authentication authenticationMocked;

    private MockMvc mockMvc;

    private User employee;
    EmployeeProfile profile;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeProfileController).build();

        employee = new User();
        employee.setId(1L);
        employee.setEmail("owner@test.com");
        employee.setUsername("Test Owner");
        employee.setEnabled(true);

        profile = new EmployeeProfile();
        profile.setEmployee(employee);
        profile.setId(23L);
        profile.setEmail("test@test.com");
        profile.setCompany("29cu");
        profile.setJobTitle("Site Manager");
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        profile.setJoinDate(dateFormat.parse("10/11/2016"));
        profile.setAddress("Punggol");
        profile.setCity("Singapore");
        profile.setState("Singapore");
        profile.setCountry("Singapore");
        profile.setZipCode(12345);
        profile.setHomePhone(123456);
        profile.setWorkPhone(567890);
        profile.setDateOfBirth(dateFormat.parse("01/01/2001"));
        profile.setGender("Male");

         // Let's mock the security context
        authenticationMocked = Mockito.mock(Authentication.class);
        securityContextMocked = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContextMocked.getAuthentication()).thenReturn(authenticationMocked);
        SecurityContextHolder.setContext(securityContextMocked);
    }

    @Test
    public void  testGetNonExistentEmployeeProfile() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(employee);
        when(profileService.findProfileByUserId(1L)).thenReturn(null);
        mockMvc.perform(get("/api/0/employee/profile"))
                .andExpect(jsonPath("$.rid",
                        equalTo(null)))
                .andExpect(jsonPath("$.email",
                        equalTo(null)))
                .andExpect(jsonPath("$.company",
                        equalTo(null)))
                .andExpect(jsonPath("$.jobTitle",
                        equalTo(null)))
                .andExpect(jsonPath("$.joinDate",
                        equalTo(null)))
                .andExpect(jsonPath("$.address",
                        equalTo(null)))
                .andExpect(jsonPath("$.city",
                        equalTo(null)))
                .andExpect(jsonPath("$.state",
                        equalTo(null)))
                .andExpect(jsonPath("$.country",
                        equalTo(null)))
                .andExpect(jsonPath("$.zipCode",
                        equalTo(null)))
                .andExpect(jsonPath("workPhone",
                        equalTo(null)))
                .andExpect(jsonPath("$.homePhone",
                        equalTo(null)))
                .andExpect(jsonPath("$.dateOfBirth",
                        equalTo(null)))
                .andExpect(jsonPath("$.gender",
                        equalTo(null)))
                .andExpect(status().isOk());
    }

    @Test
    public void  testGetExistingEmployeeProfile() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(employee);
        when(profileService.findProfileByUserId(1L)).thenReturn(profile);
        mockMvc.perform(get("/api/0/employee/profile"))
                .andExpect(jsonPath("$.rid",
                        equalTo(23)))
                .andExpect(jsonPath("$.email",
                        equalTo(profile.getEmail())))
                .andExpect(jsonPath("$.company",
                        equalTo(profile.getCompany())))
                .andExpect(jsonPath("$.jobTitle",
                        equalTo(profile.getJobTitle())))
                .andExpect(jsonPath("$.joinDate",
                        equalTo(profile.getJoinDate().getTime())))
                .andExpect(jsonPath("$.address",
                        equalTo(profile.getAddress())))
                .andExpect(jsonPath("$.city",
                        equalTo(profile.getCity())))
                .andExpect(jsonPath("$.state",
                        equalTo(profile.getState())))
                .andExpect(jsonPath("$.country",
                        equalTo(profile.getCountry())))
                .andExpect(jsonPath("$.zipCode",
                        equalTo(profile.getZipCode())))
                .andExpect(jsonPath("workPhone",
                        equalTo(profile.getWorkPhone())))
                .andExpect(jsonPath("$.homePhone",
                        equalTo(profile.getHomePhone())))
                .andExpect(jsonPath("$.dateOfBirth",
                        equalTo(profile.getDateOfBirth().getTime())))
                .andExpect(jsonPath("$.gender",
                        equalTo(profile.getGender())))
                .andExpect(status().isOk());
    }

    @Test
    public void  testCreateEmployeeProfile() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(employee);
        when(profileService.createProfile(any(EmployeeProfile.class))).thenReturn(profile);

        mockMvc.perform(post("/api/0/employee/profile")
                .content("{\"rid\":23,\"email\":\"test@test.com\",\"company\":\"29cu\",\"jobTitle\":\"Site Manager\",\"joinDate\":1478707200000,\"address\":\"Punggol\",\"city\":\"Singapore\",\"state\":\"Singapore\",\"country\":\"Singapore\",\"zipCode\":12345,\"workPhone\":567890,\"homePhone\":123456,\"dateOfBirth\":978278400000,\"gender\":\"Male\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email",
                        equalTo(profile.getEmail())))
                .andExpect(jsonPath("$.rid",
                        equalTo(23)))
                .andExpect(jsonPath("$.company",
                        equalTo(profile.getCompany())))
                .andExpect(jsonPath("$.jobTitle",
                        equalTo(profile.getJobTitle())))
                .andExpect(jsonPath("$.joinDate",
                        equalTo(profile.getJoinDate().getTime())))
                .andExpect(jsonPath("$.address",
                        equalTo(profile.getAddress())))
                .andExpect(jsonPath("$.city",
                        equalTo(profile.getCity())))
                .andExpect(jsonPath("$.state",
                        equalTo(profile.getState())))
                .andExpect(jsonPath("$.country",
                        equalTo(profile.getCountry())))
                .andExpect(jsonPath("$.zipCode",
                        equalTo(profile.getZipCode())))
                .andExpect(jsonPath("workPhone",
                        equalTo(profile.getWorkPhone())))
                .andExpect(jsonPath("$.homePhone",
                        equalTo(profile.getHomePhone())))
                .andExpect(jsonPath("$.dateOfBirth",
                        equalTo(profile.getDateOfBirth().getTime())))
                .andExpect(jsonPath("$.gender",
                        equalTo(profile.getGender())))
                .andExpect(header().string("Location", endsWith("/api/0/employee/profile")))
                .andExpect(status().isOk());
    }

    @Test
    public void  testEmployeeProfileErrorHandling() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(null);
        mockMvc.perform(get("/api/0/employee/profile"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void  testEmployeeProfileErrorHandlingDifferentUrl() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(null);
        mockMvc.perform(get("/api/0/employee/profile"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void  testCreateEmployeeProfileErrorHandling() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(null);
        mockMvc.perform(post("/api/0/employee/profile")
                .content("{\"rid\":23,\"email\":\"test@test.com\",\"company\":\"29cu\",\"jobTitle\":\"Site Manager\",\"joinDate\":1478707200000,\"address\":\"Punggol\",\"city\":\"Singapore\",\"state\":\"Singapore\",\"country\":\"Singapore\",\"zipCode\":12345,\"workPhone\":567890,\"homePhone\":123456,\"dateOfBirth\":978278400000,\"gender\":\"Male\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void  testCreateEmployeeProfileWhenCreateProfileFailed() throws Exception {
        when(userService.findAuthenticatedUser()).thenReturn(employee);
        when(profileService.createProfile(any(EmployeeProfile.class))).thenReturn(null);

        mockMvc.perform(post("/api/0/employee/profile")
                .content("{\"rid\":23,\"email\":\"test@test.com\",\"company\":\"29cu\",\"jobTitle\":\"Site Manager\",\"joinDate\":1478707200000,\"address\":\"Punggol\",\"city\":\"Singapore\",\"state\":\"Singapore\",\"country\":\"Singapore\",\"zipCode\":12345,\"workPhone\":567890,\"homePhone\":123456,\"dateOfBirth\":978278400000,\"gender\":\"Male\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}