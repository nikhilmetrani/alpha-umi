package io._29cu.usmserver.core.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.Rate;

@Component
public interface RateRepository extends CrudRepository<Rate, Long>{

}
