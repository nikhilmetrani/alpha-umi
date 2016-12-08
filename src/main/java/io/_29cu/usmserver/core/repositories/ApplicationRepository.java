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

import io._29cu.usmserver.core.model.entities.Application;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ApplicationRepository extends CrudRepository<Application, String> {
    @Query("select u from Application u where u.state = 1")
    List<Application> findAllActive();

	@Query("select u from Application u where LOWER(u.developer.username) = lower(:username)")
    List<Application> findApplicationsByDeveloper(@Param("username") String username);

    @Query("select u from Application u where u.category.name = :category")
    List<Application> findApplicationsByCategory(@Param("category") String category);

    @Query("select u from Application u where u.category.name = :category and u.state = :state")
    List<Application> findApplicationsByCategoryAndState(@Param("category") String category, @Param("state") int state);

    @Query("select a from Application a where LOWER(a.developer.username) = LOWER(:username) and a.name = :applicationName")
    Application findApplicationByUsernameAndAppName(@Param("username") String username, @Param("applicationName") String applicationName);

    @Query("select a from Application a where LOWER(a.developer.username) = LOWER(:username) and a.id = :applicationId")
    Application findApplicationByUsernameAndAppId(@Param("username") String username, @Param("applicationId") String applicationId);

    @Query("select a from Application a where LOWER(a.developer.id) = LOWER(:id) and a.name = :applicationName")
    Application findApplicationByDeveloperIdAndAppName(@Param("id") Long id, @Param("applicationName") String applicationName);

    @Query("select a from Application a where LOWER(a.developer.id) = LOWER(:id) and a.id = :applicationId")
    Application findApplicationByDeveloperIdAndAppId(@Param("id") Long id, @Param("applicationId") String applicationId);
}
