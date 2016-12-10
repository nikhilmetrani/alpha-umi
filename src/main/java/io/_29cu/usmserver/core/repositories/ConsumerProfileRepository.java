package io._29cu.usmserver.core.repositories;

import io._29cu.usmserver.core.model.entities.ConsumerProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

/**
 * Created by yniu on 10/12/2016.
 */

@Component
public interface ConsumerProfileRepository extends CrudRepository<ConsumerProfile, Long> {
    @Query("select p from ConsumerProfile p where p.owner.id = :id")
    ConsumerProfile findConsumerProfileByUserId(@Param("id") Long id);
}