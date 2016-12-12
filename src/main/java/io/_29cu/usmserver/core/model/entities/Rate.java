package io._29cu.usmserver.core.model.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;

import io._29cu.usmserver.core.model.enumerations.Rating;

@Entity
public class Rate extends BaseValueObject{
	@ManyToOne
	Application application;

	@ManyToOne
	@NaturalId
	private User consumer;
	
	@NotNull
	private Rating rating;	

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public User getConsumer() {
		return consumer;
	}

	public void setConsumer(User consumer) {
		this.consumer = consumer;
	}
	
}
