package io._29cu.usmserver.core.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FeaturedApplication extends ApplicationBrowsingList{
	@Id
    @GeneratedValue
    private Long id;

}
