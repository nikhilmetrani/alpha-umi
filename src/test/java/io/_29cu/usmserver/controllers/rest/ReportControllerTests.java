package io._29cu.usmserver.controllers.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.Category;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.ReportService;
import io._29cu.usmserver.core.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ReportControllerTests {

	@InjectMocks
	private ReportController reportController;
	@Mock
	private UserService userService;
	@Mock
	private ReportService reportService;
	@Mock
	private ApplicationService applicationService;

	private MockMvc mockMvc;
	private User developer;
	private Category category;
	private Application application;

	private String uuid;
	private DateFormat df;
	private Date startDate, endDate;

	SecurityContext securityContextMocked;
	Authentication authenticationMocked;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
		uuid = UUID.randomUUID().toString();

		startDate = new Date();
		endDate = new Date();

		df = new SimpleDateFormat("yyyyMMdd");

		developer = new User();
		developer.setId(1L);
		developer.setEmail("owner@test.com");
		developer.setUsername("Test Owner");
		developer.setEnabled(true);

		category = new Category("Productivity");
		category.setId(1l);

		application = new Application();
		application.setId(uuid);
		application.setCategory(category);
		application.setName("Dreamweaver");
		application.setState(AppState.Staging);
		application.setVersion("1.0");
		application.setDeveloper(developer);
		application.setDescription("test description");

		// Let's mock the security context
		authenticationMocked = Mockito.mock(Authentication.class);
		securityContextMocked = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContextMocked.getAuthentication()).thenReturn(authenticationMocked);
		SecurityContextHolder.setContext(securityContextMocked);
	}

	@Test
	public void testFindSubscribedUsersPerApplication() throws Exception {
		when(userService.findAuthenticatedUser()).thenReturn(developer);
		when(applicationService.findApplication(uuid)).thenReturn(application);
		when(reportService.findSubscriptionsPerApplication(uuid, startDate, endDate)).thenReturn(1);

		mockMvc.perform(get("/api/0/developer/report/subscriptions/" + uuid + "/"
				+ df.format(startDate) + "/" + df.format(endDate)))
				.andExpect(status().isOk());
	}

	@Test
	public void testFindActiveSubscribedUsersPerApplication() throws Exception {
		when(userService.findAuthenticatedUser()).thenReturn(developer);
		when(applicationService.findApplication(uuid)).thenReturn(application);
		when(reportService.findActiveSubscriptionsPerApplication(uuid, startDate, endDate)).thenReturn(1);

		mockMvc.perform(get("/api/0/developer/report/activesubscriptions/" + uuid + "/"
				+ df.format(startDate) + "/" + df.format(endDate)))
				.andExpect(status().isOk());
	}

	@Test
	public void testFindTerminatedSubscribedUsersPerApplication() throws Exception {
		when(userService.findAuthenticatedUser()).thenReturn(developer);
		when(applicationService.findApplication(uuid)).thenReturn(application);
		when(reportService.findTerminatedSubscriptionsPerApplication(uuid, startDate, endDate)).thenReturn(1);

		mockMvc.perform(get("/api/0/developer/report/terminatedsubscriptions/" + uuid + "/"
				+ df.format(startDate) + "/" + df.format(endDate)))
				.andExpect(status().isOk());
	}
}
