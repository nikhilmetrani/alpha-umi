package io._29cu.usmserver.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.ApplicationBrowsingList;
import io._29cu.usmserver.core.model.enumerations.AppListType;
import io._29cu.usmserver.core.repositories.ApplicationListRepository;
import io._29cu.usmserver.core.repositories.ApplicationRepository;
import io._29cu.usmserver.core.service.ApplicationListService;
import io._29cu.usmserver.core.service.utilities.ApplicationList;

@Component
public class ApplicationListServiceImpl implements ApplicationListService {
	@Autowired
	ApplicationListRepository applicationListRepository;

	@Autowired
	ApplicationRepository applicationRepository;

	ApplicationBrowsingList applicationBrowsingList;

	ApplicationList applicationList;

	@Override
	public ApplicationList getApplicationBrowsingList(AppListType appType) {
		applicationList = new ApplicationList();
		if (AppListType.Trending.equals(appType)) {
			List<Application> trendingApplications = applicationRepository.findTrendingApplication();
			applicationList.setApplications(trendingApplications);

		} else if (AppListType.Featured.equals(appType)) {
			applicationBrowsingList = applicationListRepository.findFeaturedApplications();
			applicationList.setApplications(applicationBrowsingList.getApplications());
		}
		return applicationList;
	}

}
