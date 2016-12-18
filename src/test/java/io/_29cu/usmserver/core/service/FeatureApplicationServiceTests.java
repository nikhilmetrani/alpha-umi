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
import io._29cu.usmserver.core.model.entities.FeaturedApplication;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppListType;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.model.enumerations.AuthorityName;
import io._29cu.usmserver.core.model.enumerations.FeatureAppState;
import io._29cu.usmserver.core.repositories.ApplicationRepository;
import io._29cu.usmserver.core.repositories.FeaturedApplicationRepository;
import io._29cu.usmserver.core.service.exception.ApplicationAlreadyFeaturedException;
import io._29cu.usmserver.core.service.exception.ApplicationDoesNotExistException;
import io._29cu.usmserver.core.service.exception.FeatureApplicationException;
import io._29cu.usmserver.core.service.utilities.ApplicationList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FeatureApplicationServiceTests {
	@Autowired
	private ApplicationListService applicationListService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private UserService userService;
	@Autowired
	FeaturedApplicationRepository featuredApplicationRepository;
	
	private User developer;
	private User maintainer;
	private Application application1,application2,application3,application4,application5;
	private FeaturedApplication featuredApplication1,featuredApplication2,featuredApplication3,featuredApplication4,featuredApplication5;
	
	@Autowired
	ApplicationRepository applicationRepository;
	
	@Before
	@Rollback(false)
	public void setup() {
		Authority authorityMaintainer = new Authority();
		authorityMaintainer.setName(AuthorityName.ROLE_MAINTAINER);
		Authority authorityDeveloper = new Authority();
		authorityDeveloper.setName(AuthorityName.ROLE_DEVELOPER);

		developer = new User();
		developer.setUsername("developer");
		developer.setEmail("developer@email.com");
		List<Authority> authListDev = new ArrayList<>();
		authListDev.add(authorityDeveloper);
		developer.setAuthorities(authListDev);
		developer.setEnabled(true);
		developer = userService.createUser(developer);

		maintainer = new User();
		maintainer.setUsername("maintainer");
		maintainer.setEmail("maintainer@email.com");
		List<Authority> authListMaintainer = new ArrayList<>();
		authListMaintainer.add(authorityMaintainer);
		maintainer.setAuthorities(authListMaintainer);
		maintainer.setEnabled(true);
		maintainer = userService.createUser(maintainer);

		application1 = new Application();
		application1.setName("application1");
		application1.setDeveloper(developer);
		application1.setState(AppState.Staging);
		application1.setDescription("test description");
		application1.setVersion("1.0");
		application1.setWhatsNew("test");
		application1 = applicationService.createApplication(application1);
		
		application2 = new Application();
		application2.setName("application2");
		application2.setDeveloper(developer);
		application2.setState(AppState.Staging);
		application2.setDescription("test description");
		application2.setVersion("1.0");
		application2.setWhatsNew("test");
		application2 = applicationService.createApplication(application2);
		
		application3 = new Application();
		application3.setName("application3");
		application3.setDeveloper(developer);
		application3.setState(AppState.Staging);
		application3.setDescription("test description");
		application3.setVersion("1.0");
		application3.setWhatsNew("test");
		application3 = applicationService.createApplication(application3);
		
		application4 = new Application();
		application4.setName("application4");
		application4.setDeveloper(developer);
		application4.setState(AppState.Staging);
		application4.setDescription("test description");
		application4.setVersion("1.0");
		application4.setWhatsNew("test");
		application4 = applicationService.createApplication(application4);
		
		application5 = new Application();
		application5.setName("application5");
		application5.setDeveloper(developer);
		application5.setState(AppState.Staging);
		application5.setDescription("test description");
		application5.setVersion("1.0");
		application5.setWhatsNew("test");
		application5 = applicationService.createApplication(application5);
		
		Exception e1=null,e2=null;
		try {
			featuredApplication1=applicationListService.createFeaturedApplication(application1.getId(),"maintainer");
			featuredApplication2=applicationListService.createFeaturedApplication(application2.getId(),"maintainer");
			featuredApplication3=applicationListService.createFeaturedApplication(application3.getId(),"maintainer");
			featuredApplication4=applicationListService.createFeaturedApplication(application4.getId(),"maintainer");
			featuredApplication5=applicationListService.createFeaturedApplication(application5.getId(),"maintainer");

		} catch (ApplicationDoesNotExistException e) {
			e1=e;
		} catch (ApplicationAlreadyFeaturedException e) {
			e2=e;
		}finally{
			assertNull(e1);
			assertNull(e2);
		}
	}
	@Test
	public void testFindFeaturedApplications(){
		AppListType appType = AppListType.Featured;
		ApplicationList appList = applicationListService.getApplicationBrowsingList(appType);
		List<Application> applications = appList.getApplications();
		assertNotNull(applications);
		assertEquals(5,applications.size());
	}
	
	@Test
	public void testCreateFeaturedApplication(){
		assertNotNull(featuredApplication1);
		assertNull(featuredApplication1.getUnFeatureDate());
		assertEquals(FeatureAppState.Active,featuredApplication1.getFeatureAppState());
		assertEquals(maintainer.getUsername(),featuredApplication1.getCreateBy());
		assertEquals(maintainer.getUsername(),featuredApplication1.getLastUpdateBy());
	}
	
	@Test
	public void testCreateFeaturedApplicationDoesNotExist(){
		Exception e1=null,e2=null;
		try {
			 applicationListService.createFeaturedApplication("2224","maintainer");
		} catch (ApplicationDoesNotExistException e) {
			e1=e;
		} catch (ApplicationAlreadyFeaturedException e) {
			e2=e;
		}finally{
			assertNotNull(e1);
			assertNull(e2);
		}
	}
	
	@Test
	public void testCreateFeaturedApplicationAlreadyFeatured(){
		Exception e1=null,e2=null;
		try {
			 applicationListService.createFeaturedApplication(application1.getId(),"maintainer");
		} catch (ApplicationDoesNotExistException e) {
			e1=e;
		} catch (ApplicationAlreadyFeaturedException e) {
			e2=e;
		}finally{
			assertNull(e1);
			assertNotNull(e2);
		}
	}
	
	@Test
	public void testUnfeatureApplication(){
		Exception ex = null;
		FeaturedApplication unFeaturedApp = null;
		try {
			 unFeaturedApp = applicationListService.unFeatureApplication(featuredApplication1.getId(),"maintainer");
		} catch (FeatureApplicationException e) {
			ex=e;
		}
		assertNull(ex);
		assertNotNull(unFeaturedApp);
		assertEquals(FeatureAppState.Inactive,unFeaturedApp.getFeatureAppState());
		
		AppListType appType = AppListType.Featured;
		ApplicationList appList = applicationListService.getApplicationBrowsingList(appType);
		List<Application> applications = appList.getApplications();
		assertNotNull(applications);
		assertEquals(4,applications.size());
		
	}
	
	@Test
	public void testUnfeatureApplicationDoesNotExist(){
		Exception ex = null;
		try {
			  applicationListService.unFeatureApplication(2222l,"maintainer");
		} catch (FeatureApplicationException e) {
			ex=e;
		}
		assertNotNull(ex);
		assertEquals("Featured application does not exist",ex.getMessage());
	}
	
	@Test
	public void testUnfeatureApplicationAlreadyUnFeatured(){
		Exception ex = null;
		try {
			applicationListService.unFeatureApplication(featuredApplication1.getId(),"maintainer");
			applicationListService.unFeatureApplication(featuredApplication1.getId(),"maintainer");
		} catch (FeatureApplicationException e) {
			ex=e;
		}
		assertNotNull(ex);
		assertEquals("Application is not featured",ex.getMessage());
	}
	
	@After
	public void tearDown() {
		featuredApplicationRepository.delete(featuredApplication1);
		featuredApplicationRepository.delete(featuredApplication2);
		featuredApplicationRepository.delete(featuredApplication3);
		featuredApplicationRepository.delete(featuredApplication4);
		featuredApplicationRepository.delete(featuredApplication5);
		applicationRepository.delete(application1);
		applicationRepository.delete(application2);
		applicationRepository.delete(application3);
		applicationRepository.delete(application4);
		applicationRepository.delete(application5);
		
	}
}
