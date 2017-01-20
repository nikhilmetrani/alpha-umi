/**
 * Copyright 2016 - 29cu.io and the authors of alpha-umi open source project

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 *     http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package io._29cu.usmserver.core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.enumerations.AppState;
import java.util.Date;


@Component
public interface ApplicationRepository extends CrudRepository<Application, String> {
	@Query("select u from Application u where u.state = 1")
    List<Application> findAllActive();

	@Query("select u from Application u where u.developer.id = :developerId")
    List<Application> findApplicationsByDeveloper(@Param("developerId") long developerId);

    @Query("select u from Application u where u.category.name = :category")
    List<Application> findApplicationsByCategory(@Param("category") String category);

    @Query("select u from Application u where u.category.name = :category and u.state = :state")
    List<Application> findApplicationsByCategoryAndState(@Param("category") String category, @Param("state") AppState state);

    @Query("select a from Application a where LOWER(a.developer.username) = LOWER(:username) and a.name = :applicationName")
    Application findApplicationByUsernameAndAppName(@Param("username") String username, @Param("applicationName") String applicationName);

    @Query("select a from Application a where LOWER(a.developer.username) = LOWER(:username) and a.id = :applicationId")
    Application findApplicationByUsernameAndAppId(@Param("username") String username, @Param("applicationId") String applicationId);

    @Query("select a from Application a where LOWER(a.developer.id) = LOWER(:id) and a.name = :applicationName")
    Application findApplicationByDeveloperIdAndAppName(@Param("id") long id, @Param("applicationName") String applicationName);

    @Query("select a from Application a where a.developer.id = :id and a.id = :applicationId")
    Application findApplicationByDeveloperIdAndAppId(@Param("id") long id, @Param("applicationId") String applicationId);
    
    @Query("select u from Application u where u.state = 1")
    List<Application> findTrendingApplication();
    
    @Query("select a from Application a where a.state = 1 and ( " + 
    		" (fn_regexp_like(a.name, :keyword) = 1) " +
    		" or (fn_regexp_like(a.description, :keyword) = 1) " +
    		" or (fn_regexp_like(a.version, :keyword) = 1) " +
    		" or (fn_regexp_like(a.whatsNew, :keyword) = 1) " +
    		" or (fn_regexp_like(a.category.name, :keyword) = 1) " +
    		" or (fn_regexp_like(concat(a.developer.firstname, ' ', a.developer.lastname), :keyword) = 1) " +
    		")")
    List<Application> findApplicationsByKeyword(@Param("keyword") String keyword);

    @Query("select a from Application a where a.state = 1 and a.category.id = :categoryId and ( " + 
    		" (fn_regexp_like(a.name, :keyword) = 1) " +
    		" or (fn_regexp_like(a.description, :keyword) = 1) " +
    		" or (fn_regexp_like(a.version, :keyword) = 1) " +
    		" or (fn_regexp_like(a.whatsNew, :keyword) = 1) " +
    		" or (fn_regexp_like(concat(a.developer.firstname, ' ', a.developer.lastname), :keyword) = 1) " +
    		")")
    List<Application> findApplicationsByCategoryAndKeyword(@Param("categoryId") Long categoryId, @Param("keyword") String keyword);

    @Query("select u from Application u where LOWER(u.developer.username) = LOWER(:username) and u.state = 1 and u.applicationPublishDate >= :startDate and u.applicationPublishDate <= :endDate")
    List<Application> findApplicationsByUserNameAndState(@Param("username") String username, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("select u from Application u where u.developer.id = :developerId and u.state = 1")
    List<Application> findAllActiveApplicationsByDeveloper(@Param("developerId") long developerId);

}
