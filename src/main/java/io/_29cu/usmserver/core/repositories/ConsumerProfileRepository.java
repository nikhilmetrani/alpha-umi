package io._29cu.usmserver.core.repositories;

import io._29cu.usmserver.core.model.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

/**
 * Created by yniu on 10/12/2016.
 */

@Component
public interface ConsumerProfileRepository extends CrudRepository<User, Long> {
    @Query("select u from User u where u.id = :id")
    User findUser(@Param("id") Long id);
}