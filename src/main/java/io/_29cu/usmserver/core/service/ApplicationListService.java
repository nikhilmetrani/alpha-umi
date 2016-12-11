package io._29cu.usmserver.core.service;

import java.util.List;

import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.enumerations.AppListType;
import io._29cu.usmserver.core.service.utilities.ApplicationList;

@Component
public interface ApplicationListService {
	
	public ApplicationList getApplicationBrowsingList(AppListType appType);
	
	public ApplicationList saveFeaturedApplications(List<Application> applications);
}
