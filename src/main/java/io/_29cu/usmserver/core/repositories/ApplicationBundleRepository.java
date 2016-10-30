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
import io._29cu.usmserver.core.model.entities.ApplicationBundle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ApplicationBundleRepository extends CrudRepository<ApplicationBundle, String> {
    @Query("select u from ApplicationBundle u where u.developer.id = :id")
    List<ApplicationBundle> findApplicationBundlesByDeveloper(@Param("id") Long id);

    //TODO may not needed
    @Query("select u from ApplicationBundle u where u.category.name = :category")
    List<ApplicationBundle> findApplicationBundlesByCategory(@Param("category") String category);

    @Query("select a from ApplicationBundle a where a.developer.id = :id and a.name = :applicationBundleName")
    ApplicationBundle findApplicationBundleByDeveloperAndName(@Param("id") Long id, @Param("applicationBundleName") String applicationBundleName);

    @Query("select a from ApplicationBundle a where a.developer.id = :id and a.id = :applicationBundleId")
    ApplicationBundle findApplicationBundleByDeveloperAndId(@Param("id") Long id, @Param("applicationBundleId") String applicationBundleId);
}
