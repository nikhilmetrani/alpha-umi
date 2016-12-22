package io._29cu.usmserver.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.Authority;
import io._29cu.usmserver.core.model.entities.Rate;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.model.enumerations.AuthorityName;
import io._29cu.usmserver.core.model.enumerations.Rating;
import io._29cu.usmserver.core.repositories.ApplicationRepository;
import io._29cu.usmserver.core.repositories.RateRepository;
import io._29cu.usmserver.core.repositories.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RateServiceTests {

	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private UserService userService;

	private Application application;
	private User developer;
	private User consumer;
	private Rate rate, rate1, rate2, rate3, rate4;

	@Autowired
	private RateService rateService;

	@Autowired
	private RateRepository rateRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;

	@Before
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

		rate = new Rate();
		rate.setApplication(application);
		rate.setConsumer(consumer);
		rate.setRating(Rating.Like);
		rate = rateService.createRate(rate);

		rate1 = new Rate();
		rate1.setApplication(application);
		rate1.setConsumer(consumer);
		rate1.setRating(Rating.Like);
		rate1 = rateService.createRate(rate1);

		rate2 = new Rate();
		rate2.setApplication(application);
		rate2.setConsumer(consumer);
		rate2.setRating(Rating.Like);
		rate2 = rateService.createRate(rate2);

		rate3 = new Rate();
		rate3.setApplication(application);
		rate3.setConsumer(consumer);
		rate3.setRating(Rating.Dislike);
		rate3 = rateService.createRate(rate3);

		rate4 = new Rate();
		rate4.setApplication(application);
		rate4.setConsumer(consumer);
		rate4.setRating(Rating.Dislike);
		rate4 = rateService.createRate(rate4);
	}

	@Test
	public void testFindRating() {
		Rate findRating = rateService.findRating(rate.getId());
		assertNotNull(findRating);
		assertEquals(findRating.getId(), rate.getId());
	}

	@Test
	public void testCreateRating() {
		rate = rateService.createRate(rate);
		assertEquals(application.getId(), rate.getApplication().getId());
		assertEquals(consumer.getId(), rate.getConsumer().getId());
		assertEquals(Rating.Like, rate.getRating());
	}
	
	@Test
	public void testCountLikeRating(){
		int count =  rateService.countRatingsByApplicationId(application.getId(),Rating.Like);
		assertEquals(3,count);
	}
	
	@Test
	public void testCountDisLikeRating(){
		int count =  rateService.countRatingsByApplicationId(application.getId(),Rating.Dislike);
		assertEquals(2,count);
	}
	
	@Test
	public void testFindRatingsByApplicationId(){
		List<Rate> ratings =  rateService.findRatingsByApplicationId(application.getId());
		assertNotNull(ratings);
		assertEquals(ratings.size(), 5);
	}

	@After
	public void tearDown() {
		rateRepository.delete(rate.getId());
		rateRepository.delete(rate1.getId());
		rateRepository.delete(rate2.getId());
		rateRepository.delete(rate3.getId());
		rateRepository.delete(rate4.getId());
		applicationRepository.delete(application.getId());
		userRepository.delete(consumer.getId());
		userRepository.delete(developer.getId());
	}

}
