package io._29cu.usmserver.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.Authority;
import io._29cu.usmserver.core.model.entities.Review;
import io._29cu.usmserver.core.model.entities.ReviewReply;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.model.enumerations.AuthorityName;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ReviewReplyServiceTests {
	
	@Autowired
	ReviewReplyService reviewReplyService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private UserService userService;
	@Autowired
	ReviewService reviewService;
	
	private User developer;
	private User consumer;
	private Application application;
	private Review review;
	private ReviewReply reviewReply1,reviewReply2,reviewReply3,reviewReply4,reviewReply5;
	
	@Before
	@Rollback(false)
	public void setup() {
		Authority authorityConsumer = new Authority();
		authorityConsumer.setName(AuthorityName.ROLE_CONSUMER);
		Authority authorityDeveloper = new Authority();
		authorityDeveloper.setName(AuthorityName.ROLE_DEVELOPER);

		developer = new User();
		developer.setUsername("developer");
		developer.setEmail("developer@email.com");
		List<Authority> authListDev = new ArrayList<>();
		authListDev.add(authorityConsumer);
		authListDev.add(authorityDeveloper);
		developer.setAuthorities(authListDev);
		developer.setEnabled(true);
		developer = userService.createUser(developer);

		consumer = new User();
		consumer.setUsername("consumer");
		consumer.setEmail("consumer@email.com");
		List<Authority> authListConsumer = new ArrayList<>();
		authListConsumer.add(authorityConsumer);
		consumer.setAuthorities(authListConsumer);
		consumer.setEnabled(true);
		consumer = userService.createUser(consumer);

		application = new Application();
		application.setName("application");
		application.setDeveloper(developer);
		application.setState(AppState.Staging);
		application.setDescription("test description");
		application.setVersion("1.0");
		application.setWhatsNew("test");
		applicationService.createApplication(application);

		review = new Review();
		review.setApplication(application);
		review.setConsumer(consumer);
		review.setTitle("testTitle");
		review.setDescription("testDescription");
		review = reviewService.createReview(review);
		
		reviewReply1 = new ReviewReply();
		reviewReply1.setDescription("reply1");
		reviewReply1.setDeveloper(developer);
		reviewReply1.setReview(review);
		reviewReply1 = reviewReplyService.createReviewReply(reviewReply1);
		
		reviewReply2 = new ReviewReply();
		reviewReply2.setDescription("reply2");
		reviewReply2.setDeveloper(developer);
		reviewReply2.setReview(review);
		reviewReply2 = reviewReplyService.createReviewReply(reviewReply2);
		
		reviewReply3 = new ReviewReply();
		reviewReply3.setDescription("reply3");
		reviewReply3.setDeveloper(developer);
		reviewReply3.setReview(review);
		reviewReply3 = reviewReplyService.createReviewReply(reviewReply3);
		
		reviewReply4 = new ReviewReply();
		reviewReply4.setDescription("reply4");
		reviewReply4.setDeveloper(consumer);
		reviewReply4.setReview(review);
		reviewReply4 = reviewReplyService.createReviewReply(reviewReply4);
		
		reviewReply5 = new ReviewReply();
		reviewReply5.setDescription("reply5");
		reviewReply5.setDeveloper(consumer);
		reviewReply5.setReview(review);
		reviewReply5 = reviewReplyService.createReviewReply(reviewReply5);
	}
	
	@Test
	public void testFindReviewRepliesByReviewId() {
		List<ReviewReply> reviewReplies = reviewReplyService.findReviewRepliesByReviewId(review.getId());
		assertNotNull(reviewReplies);
		assertEquals(reviewReplies.size(),5);
	}
	
	@Test
	public void testCreateReviewReply(){
		reviewReply1 = reviewReplyService.createReviewReply(reviewReply1);
		assertEquals(review.getId(),reviewReply1.getReview().getId());
		assertEquals(developer.getId(),reviewReply1.getDeveloper().getId());
		assertEquals("reply1",reviewReply1.getDescription());
	}
	
	@Test
	public void testRemoveReviewReply(){
		reviewReplyService.removeReviewReply(reviewReply1.getId());
		ReviewReply findReviewReply = reviewReplyService.findReviewReply(reviewReply1.getId());
		assertNull(findReviewReply);
		reviewReply1 = reviewReplyService.createReviewReply(reviewReply1);
	}
	
	
	@After
	public void tearDown() {
		reviewReplyService.removeReviewReply(reviewReply1.getId());
		reviewReplyService.removeReviewReply(reviewReply2.getId());
		reviewReplyService.removeReviewReply(reviewReply3.getId());
		reviewReplyService.removeReviewReply(reviewReply4.getId());
		reviewReplyService.removeReviewReply(reviewReply5.getId());
	}
}
