package io._29cu.usmserver.controllers.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import io._29cu.usmserver.core.model.entities.ReviewReply;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.service.ReviewReplyService;
import io._29cu.usmserver.core.service.ReviewService;
import io._29cu.usmserver.core.service.UserService;
import io._29cu.usmserver.core.service.exception.ReviewReplyDoesNotExistException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ReviewReplyControllerTests {

	@InjectMocks
	private ReviewReplyController reviewReplyController;
	@Mock
	private UserService userService;
	@Mock
	private ReviewReplyService reviewReplyService;
	@Mock
	private ReviewService reviewService;

	private MockMvc mockMvc;
	private User consumer;
	private Application application;
	private ReviewReply reviewReply;
	private Review review;
	
	SecurityContext securityContextMocked;
	Authentication authenticationMocked;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(reviewReplyController).build();
		
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
		review.setTitle("Review Title");
		review.setDescription("Review Description");
		review.setApplication(application);
		review.setConsumer(consumer);
		review.setCreateBy(consumer.getUsername());

		reviewReply = new ReviewReply();
		reviewReply.setId(222l);
		reviewReply.setDescription("Review Reply Description");
		reviewReply.setReview(review);
		reviewReply.setCreateBy(consumer.getUsername());

		authenticationMocked = Mockito.mock(Authentication.class);
		securityContextMocked = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContextMocked.getAuthentication()).thenReturn(authenticationMocked);
		SecurityContextHolder.setContext(securityContextMocked);
	}

	@Test
	public void testCreateReviewReply() throws Exception {
		when(userService.findAuthenticatedUser()).thenReturn(consumer);
		when(reviewService.findReview(any(Long.class))).thenReturn(review);
		when(reviewReplyService.createReviewReply(any(ReviewReply.class), any(Review.class), any(User.class))).thenReturn(reviewReply);
		String content = " {'rid':'222','reviewId':'22','developer':null,'description':'Review Reply Description','creationDate':1482925588809,'createBy':'Test Owner'}";
		
	    mockMvc
				.perform(post("/api/0/consumer/review/22/reviewReply/create").content(
						content.replaceAll("'", "\""))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.description", equalTo((reviewReply.getDescription()))))
				.andExpect(jsonPath("$.reviewId", equalTo((reviewReply.getReview().getId().toString()))))
				.andExpect(jsonPath("$.createBy", equalTo((reviewReply.getCreateBy()))))
				.andExpect(jsonPath("$.rid", equalTo((reviewReply.getId().toString()))))
				.andExpect(status().isCreated())
				.andReturn();
	}
	
	@Test
	public void testCreateReviewReplyWhenUserNotAuthenticated() throws Exception {
		when(userService.findAuthenticatedUser()).thenReturn(null);
		when(reviewService.findReview(any(Long.class))).thenReturn(review);
		when(reviewReplyService.createReviewReply(any(ReviewReply.class), any(Review.class), any(User.class))).thenReturn(reviewReply);
		String content = " {'rid':'222','reviewId':'22','developer':null,'description':'Review Reply Description','creationDate':1482925588809,'createBy':'Test Owner'}";
		
	    mockMvc
				.perform(post("/api/0/consumer/review/22/reviewReply/create").content(
						content.replaceAll("'", "\""))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();
	}
	
	@Test
	public void testCreateReviewReplyWhenReviewNotFound() throws Exception {
		when(userService.findAuthenticatedUser()).thenReturn(consumer);
		when(reviewService.findReview(any(Long.class))).thenReturn(null);
		when(reviewReplyService.createReviewReply(any(ReviewReply.class), any(Review.class), any(User.class))).thenReturn(reviewReply);
		String content = " {'rid':'222','reviewId':'22','developer':null,'description':'Review Reply Description','creationDate':1482925588809,'createBy':'Test Owner'}";
		
	    mockMvc
				.perform(post("/api/0/consumer/review/22/reviewReply/create").content(
						content.replaceAll("'", "\""))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();
	}
	
	@Test
	public void testRemoveReviewReply() throws Exception {
		when(userService.findAuthenticatedUser()).thenReturn(consumer);
        mockMvc.perform(delete("/api/0/consumer/review/22/reviewReply/remove/222")
                .content("{'rid':'222'}".replaceAll("'", "\""))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());		
	}
	
	@Test
	public void testRemoveReviewReplyWhenUserNotAuthenticated() throws Exception {
		when(userService.findAuthenticatedUser()).thenReturn(null);
        mockMvc.perform(delete("/api/0/consumer/review/22/reviewReply/remove/222")
                .content("{'rid':'222'}".replaceAll("'", "\""))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());		
	}
	
	
	@Test
	public void testRemoveReviewReplyWithReviewReplyDoesNotExistException() throws Exception {
		when(userService.findAuthenticatedUser()).thenReturn(consumer);
		Mockito.doThrow(new ReviewReplyDoesNotExistException("Review Reply does not exist")).when(reviewReplyService).removeReviewReply(any(Long.class));
        mockMvc.perform(delete("/api/0/consumer/review/22/reviewReply/remove/222")
                .content("{'rid':'222'}".replaceAll("'", "\""))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());		
	}
}
