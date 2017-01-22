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

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.Subscription;


@Component
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
    @Query("select s.application from Subscription s where s.user.id = :userId and s.active = 1")
    List<Application> getSubscribedApplications(@Param("userId") long userId);

    @Query("select s from Subscription s where s.application.id = :applicationId and s.dateSubscribed >= :startDate and s.dateSubscribed <= :endDate")
    List<Subscription> findSubscribedUsersPerApplication(@Param("applicationId") String applicationId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("select s from Subscription s where s.application.id = :applicationId and s.dateSubscribed >= :startDate and s.dateSubscribed <= :endDate and s.active = 1")
    List<Subscription> findSubscribedActiveUsersPerApplication(@Param("applicationId") String applicationId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("select s from Subscription s where s.application.id = :applicationId and s.dateUnsubscribed >= :startDate and s.dateUnsubscribed <= :endDate and s.active <> 1")
    List<Subscription> findTerminatedSubscriptionsPerApplication(@Param("applicationId") String applicationId,@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("select a from Subscription a where a.user.id = :userId and a.application.id = :applicationId and a.active = 1 order by a.id desc")
    Subscription findSubscription(@Param("userId") Long userId, @Param("applicationId") String applicationId);

	@Query("select s.application from Subscription s where s.active = 1 and s.dateSubscribed >= :startDate and s.dateSubscribed <= :endDate")
	List<Application> getTrendingApplications(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
