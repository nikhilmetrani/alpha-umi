package io._29cu.usmserver.controllers.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
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
import io._29cu.usmserver.core.model.entities.Review;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.ReviewService;
import io._29cu.usmserver.core.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ReviewControllerTests {

	@InjectMocks
	private ReviewController reviewController;
	@Mock
	private UserService userService;
	@Mock
	private ReviewService reviewService;
	@Mock
	private ApplicationService applicationService;

	private MockMvc mockMvc;
	private User consumer;
	private Application application;
	private Review review;
	// @Mock not used here since they are mocked in setup()
	// Spring boot does not allow mocking with annotation.
	SecurityContext securityContextMocked;
	Authentication authenticationMocked;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
		
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

		review = new Review();
		review.setId(22l);
		review.setTitle("testTitle");
		review.setDescription("testDescription");
		review.setApplication(application);
		review.setConsumer(consumer);
		review.setCreateBy(consumer.getUsername());

		authenticationMocked = Mockito.mock(Authentication.class);
		securityContextMocked = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContextMocked.getAuthentication()).thenReturn(authenticationMocked);
		SecurityContextHolder.setContext(securityContextMocked);
	}

	@Test
	public void testCreateReview() throws Exception {
		when(userService.findAuthenticatedUser()).thenReturn(consumer);
		when(applicationService.findApplication(any(String.class))).thenReturn(application);
		when(reviewService.createReview(any(Review.class), any(Application.class), any(User.class))).thenReturn(review);
		String content = "{'applicationId':'appId22',"
				+ "'consumer':{'id':1,'username':'Test Owner','password':null,'firstname':null,'lastname':null,'email':'owner@test.com','enabled':true,'lastPasswordResetDate':null,'authorities':null},"
				+ "'title':'testTitle','description':'testDescription'}";
		
	    mockMvc
				.perform(post("/api/0/consumer/22/review/create").content(
						content.replaceAll("'", "\""))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title", equalTo((review.getTitle()))))
				.andExpect(jsonPath("$.description", equalTo((review.getDescription()))))
				.andExpect(jsonPath("$.applicationId", equalTo((review.getApplication().getId()))))
				.andExpect(jsonPath("$.createBy", equalTo((review.getCreateBy()))))
				.andExpect(jsonPath("$.rid", equalTo((review.getId().toString()))))
				.andExpect(jsonPath("$.featured", equalTo((review.isFeatured()))))
				.andExpect(status().isCreated()).andReturn();
	}
	
	
	@Test
	public void testCreateReviewWhenUserNotAuthenticated() throws Exception {
		when(userService.findAuthenticatedUser()).thenReturn(null);
		when(applicationService.findApplication(any(String.class))).thenReturn(application);
		when(reviewService.createReview(any(Review.class), any(Application.class), any(User.class))).thenReturn(review);
		String content = "{'applicationId':'appId22',"
				+ "'consumer':{'id':1,'username':'Test Owner','password':null,'firstname':null,'lastname':null,'email':'owner@test.com','enabled':true,'lastPasswordResetDate':null,'authorities':null},"
				+ "'title':'testTitle','description':'testDescription'}";
		
		mockMvc
				.perform(post("/api/0/consumer/22/review/create").content(
						content
								.replaceAll("'", "\""))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden()).andReturn();
	}
	
	@Test
	public void testCreateReviewWhenApplicationNotFound() throws Exception {
		when(userService.findAuthenticatedUser()).thenReturn(consumer);
		when(applicationService.findApplication(any(String.class))).thenReturn(null);
		when(reviewService.createReview(any(Review.class), any(Application.class), any(User.class))).thenReturn(review);
		String content = "{'applicationId':'appId22',"
				+ "'consumer':{'id':1,'username':'Test Owner','password':null,'firstname':null,'lastname':null,'email':'owner@test.com','enabled':true,'lastPasswordResetDate':null,'authorities':null},"
				+ "'title':'testTitle','description':'testDescription'}";
		
		mockMvc
				.perform(post("/api/0/consumer/22/review/create").content(
						content
								.replaceAll("'", "\""))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();
	}

}
