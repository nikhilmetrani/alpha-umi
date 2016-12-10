package io._29cu.usmserver.common.utilities;

import io._29cu.usmserver.core.model.entities.ApplicationBrowsingList;
import io._29cu.usmserver.core.model.entities.FeaturedApplication;
import io._29cu.usmserver.core.model.entities.TrendingApplication;
import io._29cu.usmserver.core.model.enumerations.AppListType;

public class ApplicationListFactory {

	public static ApplicationBrowsingList getApplicationBrowsingList(AppListType appType) {

		if (AppListType.Trending.equals(appType)) {
			return new TrendingApplication();
		} else if (AppListType.Featured.equals(appType)) {
			return new FeaturedApplication();
		}
		return null;
	}

}
