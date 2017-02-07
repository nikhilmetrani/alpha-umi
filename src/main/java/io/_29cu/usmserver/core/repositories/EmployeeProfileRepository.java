package io._29cu.usmserver.core.repositories;

/**
 * Created by yniu on 10/12/2016.
 */

import io._29cu.usmserver.core.model.entities.EmployeeProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;


@Component
public interface EmployeeProfileRepository extends CrudRepository<EmployeeProfile, Long>{
    @Query("select p from EmployeeProfile p where p.employee.id = :id")
    EmployeeProfile findProfileByUserId(@Param("id") Long id);
}