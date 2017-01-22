package io._29cu.usmserver.core.model.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ReviewReply extends BaseValueObject {

	@ManyToOne
	private Review review;

	@ManyToOne
	private User developer;

	private String description;

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public User getDeveloper() {
		return developer;
	}

	public void setDeveloper(User developer) {
		this.developer = developer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
