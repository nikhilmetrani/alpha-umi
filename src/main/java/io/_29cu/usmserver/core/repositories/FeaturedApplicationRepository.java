package io._29cu.usmserver.core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.FeaturedApplication;
import io._29cu.usmserver.core.model.enumerations.FeatureAppState;

@Component
public interface FeaturedApplicationRepository extends CrudRepository<FeaturedApplication, Long> {

	@Query("select featuredApps.application from FeaturedApplication featuredApps where featuredApps.featureAppState = :state")
	public List<Application> findFeaturedApplications(@Param("state") FeatureAppState state);

	@Query("select featuredApps.application from FeaturedApplication featuredApps where featuredApps.featureAppState = :state and featuredApps.application.id=:applicationId")
	public Application findFeaturedApplicationByApplId(@Param("applicationId") String applicationId,@Param("state") FeatureAppState state);

}
