package io._29cu.usmserver.core.service;

import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.enumerations.AppListType;
import io._29cu.usmserver.core.service.utilities.ApplicationList;

@Component
public interface ApplicationListService {
	
	public ApplicationList getApplicationBrowsingList(AppListType appType);
}
