package io._29cu.usmserver.core.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import io._29cu.usmserver.core.model.entities.ApplicationBrowsingList;
import io._29cu.usmserver.core.model.entities.FeaturedApplication;

public interface ApplicationListRepository extends CrudRepository<ApplicationBrowsingList, String> {

	@Query("select featuredApps from FeaturedApplication featuredApps")
	FeaturedApplication findFeaturedApplications();
	
}
