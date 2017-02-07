package io._29cu.usmserver.controllers.rest.resources;

import java.util.Date;

import io._29cu.usmserver.core.model.entities.Rate;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.Rating;

public class RatingResource extends EntityResourceBase<Rate> {

	private String rid;
	private String applicationId;
	private User consumer;
	private Rating rating;
	private Date creationDate;
	private String createBy;

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public User getConsumer() {
		return consumer;
	}

	public void setConsumer(User consumer) {
		this.consumer = consumer;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Override
	public Rate toEntity() {
		Rate rate = new Rate();
		rate.setConsumer(getConsumer());
		rate.setRating(getRating());
		return rate;
	}
}
