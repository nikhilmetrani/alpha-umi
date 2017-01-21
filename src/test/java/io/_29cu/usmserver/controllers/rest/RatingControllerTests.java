package io._29cu.usmserver.controllers.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.Rate;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.model.enumerations.Rating;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.RateService;
import io._29cu.usmserver.core.service.UserService;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RatingControllerTests {
	@InjectMocks
	private RatingController ratingController;
	@Mock
	private UserService userService;
	@Mock
	private RateService rateService;
	@Mock
	private ApplicationService applicationService;
	
	private MockMvc mockMvc;
	private String uuid;
	private Rate rate;
	private User consumer;
	private Application application;
	
	SecurityContext securityContextMocked;
	Authentication authenticationMocked;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();
		uuid = UUID.randomUUID().toString();

		consumer = new User();
		consumer.setId(1L);
		consumer.setEmail("owner@test.com");
		consumer.setUsername("Test Owner");
		consumer.setEnabled(true);

		application = new Application();
		application.setId("appId22");
		application.setName("application");
		application.setDeveloper(consumer);
		application.setState(AppState.Staging);
		application.setDescription("test description");
		application.setVersion("1.0");
		application.setWhatsNew("test");

		rate = new Rate();
		rate.setId(22l);
		rate.setApplication(application);
		rate.setConsumer(consumer);
		rate.setCreateBy(consumer.getUsername());
		rate.setRating(Rating.Like);
		
		authenticationMocked = Mockito.mock(Authentication.class);
		securityContextMocked = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContextMocked.getAuthentication()).thenReturn(authenticationMocked);
		SecurityContextHolder.setContext(securityContextMocked);
	}

	@Test
	public void testCreateRating() throws Exception {
		when(userService.findAuthenticatedUser()).thenReturn(consumer);
		when(applicationService.findApplication(any(String.class))).thenReturn(application);
		when(rateService.createRate(any(Rate.class), any(Application.class), any(User.class))).thenReturn(rate);
		String content = "{'applicationId':'appId22',"
				+ "'consumer':{'id':1,'username':'Test Owner','password':null,'firstname':null,'lastname':null,'email':'owner@test.com','enabled':true,'lastPasswordResetDate':null,'authorities':null},"
				+ "'rating':'Like'}";
		
	    mockMvc
				.perform(post("/api/0/consumer/22/rating/create").content(
						content.replaceAll("'", "\""))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.applicationId", equalTo((rate.getApplication().getId()))))
				.andExpect(jsonPath("$.createBy", equalTo((rate.getCreateBy()))))
				.andExpect(jsonPath("$.rid", equalTo((rate.getId().toString()))))
				.andExpect(jsonPath("$.rating", equalTo((rate.getRating().toString()))))
				.andExpect(status().isCreated())
				.andReturn();
	    
		
	}
	
	@Test
	public void testCreateRatingUnauthenticatedUser() throws Exception {
		when(userService.findAuthenticatedUser()).thenReturn(null);
		when(applicationService.findApplication(any(String.class))).thenReturn(application);
		when(rateService.createRate(any(Rate.class), any(Application.class), any(User.class))).thenReturn(rate);
		String content = "{'applicationId':'appId22',"
				+ "'consumer':{'id':1,'username':'Test Owner','password':null,'firstname':null,'lastname':null,'email':'owner@test.com','enabled':true,'lastPasswordResetDate':null,'authorities':null},"
				+ "'rating':'Like'}";
		
	    mockMvc
				.perform(post("/api/0/consumer/22/rating/create").content(
						content.replaceAll("'", "\""))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status()
				.isForbidden());
	}
	
	@Test
	public void testCreateRatingApplicationNotFound() throws Exception {
		when(userService.findAuthenticatedUser()).thenReturn(consumer);
		when(applicationService.findApplication(any(String.class))).thenReturn(null);
		when(rateService.createRate(any(Rate.class), any(Application.class), any(User.class))).thenReturn(rate);
		String content = "{'applicationId':'appId22',"
				+ "'consumer':{'id':1,'username':'Test Owner','password':null,'firstname':null,'lastname':null,'email':'owner@test.com','enabled':true,'lastPasswordResetDate':null,'authorities':null},"
				+ "'rating':'Like'}";
		
	    mockMvc
				.perform(post("/api/0/consumer/22/rating/create").content(
						content.replaceAll("'", "\""))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status()
				.isNotFound());
	}

	@Test
	public void testGetRateLikeNumber() throws Exception {
		when(userService.findAuthenticatedUser()).thenReturn(consumer);
		when(applicationService.findApplication(uuid)).thenReturn(application);
		when(rateService.countRatingsByApplicationId(uuid, Rating.Dislike)).thenReturn(1);

		mockMvc.perform(post("/api/0/consumer/"+uuid+"/rating/getRateLikeNum/0"))
				.andExpect(status().isOk());
	}

	@Test
	public void testCheckUserRate() throws Exception {
		when(userService.findAuthenticatedUser()).thenReturn(consumer);
		when(applicationService.findApplication(uuid)).thenReturn(application);
		when(rateService.checkUserRate(uuid, consumer.getId())).thenReturn(null);

		mockMvc.perform(post("/api/0/consumer/"+uuid+"/rating/checkUserRate"))
				.andExpect(status().isOk());
	}
}
