package io._29cu.usmserver.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.FeaturedApplication;
import io._29cu.usmserver.core.model.enumerations.AppListType;
import io._29cu.usmserver.core.model.enumerations.FeatureAppState;
import io._29cu.usmserver.core.repositories.FeaturedApplicationRepository;
import io._29cu.usmserver.core.repositories.ApplicationRepository;
import io._29cu.usmserver.core.service.ApplicationListService;
import io._29cu.usmserver.core.service.exception.ApplicationAlreadyFeaturedException;
import io._29cu.usmserver.core.service.exception.ApplicationDoesNotExistException;
import io._29cu.usmserver.core.service.exception.ApplicationNotFeaturedException;
import io._29cu.usmserver.core.service.exception.FeatureApplicationDoesNotExistException;
import io._29cu.usmserver.core.service.exception.FeatureApplicationException;
import io._29cu.usmserver.core.service.utilities.ApplicationList;

@Component
public class ApplicationListServiceImpl implements ApplicationListService {
	@Autowired
	FeaturedApplicationRepository featuredApplicationRepository;

	@Autowired
	ApplicationRepository applicationRepository;

	@Override
	public ApplicationList getApplicationBrowsingList(AppListType appType) {
		ApplicationList applicationList = new ApplicationList();
		List<Application> applications = null;
		if (AppListType.Trending.equals(appType)) {
			applications = applicationRepository.findTrendingApplication();
		} else if (AppListType.Featured.equals(appType)) {
			applications = featuredApplicationRepository.findFeaturedApplications();			
		}
		applicationList.setApplications(applications);
		return applicationList;
	}

	@Override
	public FeaturedApplication createFeaturedApplication(String applicationId,String maintainerName) throws  ApplicationDoesNotExistException,ApplicationAlreadyFeaturedException{
		FeaturedApplication featuredApplication = new FeaturedApplication();
		Application application = featuredApplicationRepository.findFeaturedApplicationByApplId(applicationId);
		if(application==null){
			application = applicationRepository.findOne(applicationId);
			if(application==null){
				throw new ApplicationDoesNotExistException("Application does not exist");
			}
			featuredApplication.setApplication(application);
			featuredApplication.setFeatureAppState(FeatureAppState.Active);
			featuredApplication.setCreateBy(maintainerName);
			featuredApplication.setLastUpdateBy(maintainerName);
			featuredApplication = featuredApplicationRepository.save(featuredApplication);
		} else{
			throw new ApplicationAlreadyFeaturedException("Application is already featured");
		}		
		return featuredApplication;
	}

	@Override
	public FeaturedApplication unFeatureApplication(Long featureApplicationId, String maintainerName)
			throws FeatureApplicationException {
		FeaturedApplication featuredApplication = featuredApplicationRepository.findOne(featureApplicationId);
		if(featuredApplication==null){
			throw new FeatureApplicationDoesNotExistException("Featured application does not exist");
		}
		if(FeatureAppState.Inactive.equals(featuredApplication.getFeatureAppState())){
			throw new ApplicationNotFeaturedException("Application is not featured");
		}
		featuredApplication.setFeatureAppState(FeatureAppState.Inactive);
		featuredApplication.setUnFeatureDate(new Date());
		featuredApplication.setLastUpdateBy(maintainerName);
		featuredApplication.setLastUpdateDate(new Date());
		return featuredApplication;
	}

}
