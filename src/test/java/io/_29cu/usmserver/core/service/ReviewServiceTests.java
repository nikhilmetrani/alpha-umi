package io._29cu.usmserver.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.model.enumerations.AuthorityName;
import io._29cu.usmserver.core.service.exception.ReviewDoesNotExistException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ReviewServiceTests {

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
		application = applicationService.createApplication(application);

		review = new Review();
		review.setApplication(application);
		review.setConsumer(consumer);
		review.setTitle("testTitle");
		review.setDescription("testDescription");
		review = reviewService.createReview(review);
	}

	@After
	public void tearDown() {
		Exception exception = null;
		if (review != null) {
			try {
				reviewService.removeReview(review.getId());
			} catch (ReviewDoesNotExistException e) {
				exception = e;
			} finally {
				assertNull("ReviewDoesNotExistException is thrown", exception);
			}
		}
	}
	
	@Test
	public void testCreateReview(){
		review = reviewService.createReview(review);
		assertEquals(application.getId(),review.getApplication().getId());
		assertEquals(consumer.getId(),review.getConsumer().getId());
		assertEquals("testTitle",review.getTitle());
		assertEquals("testDescription",review.getDescription());
	}

	@Test
	public void testFindReview() {
		Review findReview = reviewService.findReview(review.getId());
		assertNotNull(findReview);
		assertEquals(findReview.getId(), review.getId());
	}

	@Test
	public void testRemoveReview() {
		Exception exception = null;
		try {
			reviewService.removeReview(review.getId());
		} catch (ReviewDoesNotExistException e) {
			exception = e;
		} finally {
			assertNull("ReviewDoesNotExistException is thrown", exception);
		}
		Review findReview = reviewService.findReview(review.getId());
		review = null;
		assertNull(findReview);
	}

	@Test
	public void testRemoveReviewByWrongId() {
		Exception exception = null;
		try {
			reviewService.removeReview(2222l);
		} catch (ReviewDoesNotExistException e) {
			exception = e;
		} finally {
			assertNotNull("ReviewDoesNotExistException is Not thrown", exception);
		}
		Review findReview = reviewService.findReview(review.getId());
		assertNotNull(findReview);
	}

	@Test
	public void testFindReviewByApplicationId() {
		List<Review> reviews = reviewService.findReviewsByApplicationId(application.getId());
		assertNotNull(reviews);
		assertEquals(reviews.size(), 1);
		assertEquals(reviews.get(0).getId(), review.getId());
	}

	@Test
	public void testDefaultFeaturReview() {
		Review findReview = reviewService.findReview(review.getId());
		assertNotNull(findReview);
		assertFalse(findReview.isFeatured());
	}

	@Test
	public void testFeatureReview() {
		assertNotNull(review);
		assertFalse(review.isFeatured());
		Long id = review.getId();
		reviewService.featureReview(id);
		Review featureReview = reviewService.findReview(id);
		assertTrue(featureReview.isFeatured());
	}

	@Test
	public void testUnFeatureReview() {
		Long id = review.getId();
		reviewService.featureReview(id);
		Review featureReview = reviewService.findReview(id);
		assertTrue(featureReview.isFeatured());
		reviewService.unfeatureReview(id);
		Review unfeatureReview = reviewService.findReview(id);
		assertFalse(unfeatureReview.isFeatured());
	}

}
