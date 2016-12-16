package io._29cu.usmserver.core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.FeaturedApplication;

@Component
public interface FeaturedApplicationRepository extends CrudRepository<FeaturedApplication, Long> {

	@Query("select featuredApps.application from FeaturedApplication featuredApps where featuredApps.featureAppState like 'Active'")
	public List<Application> findFeaturedApplications();

	@Query("select featuredApps.application from FeaturedApplication featuredApps where featuredApps.featureAppState like 'Active' and featuredApps.application.id=:applicationId")
	public Application findFeaturedApplicationByApplId(String applicationId);

}
