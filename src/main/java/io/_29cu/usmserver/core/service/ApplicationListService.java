package io._29cu.usmserver.core.service;

import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.FeaturedApplication;
import io._29cu.usmserver.core.model.enumerations.AppListType;
import io._29cu.usmserver.core.service.exception.ApplicationAlreadyFeaturedException;
import io._29cu.usmserver.core.service.exception.ApplicationDoesNotExistException;
import io._29cu.usmserver.core.service.exception.FeatureApplicationException;
import io._29cu.usmserver.core.service.utilities.ApplicationList;

@Component
public interface ApplicationListService {
	
	public ApplicationList getApplicationBrowsingList(AppListType appType);
	
	public FeaturedApplication createFeaturedApplication(String applicationId,String maintainerName) throws ApplicationDoesNotExistException,ApplicationAlreadyFeaturedException;
	
	public FeaturedApplication unFeatureApplication(Long featureApplicationId,String maintainerName) throws FeatureApplicationException;
}
