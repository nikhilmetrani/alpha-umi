package io._29cu.usmserver.controllers.rest.resources;

import java.util.Date;

import io._29cu.usmserver.core.model.entities.ReviewReply;
import io._29cu.usmserver.core.model.entities.User;

public class ReviewReplyResource extends EntityResourceBase<ReviewReply> {
	private String rid;
	private String reviewId;
	private User developer;
	private String description;
	private Date creationDate;	
	private String createBy;

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	public User getDeveloper() {
		return developer;
	}

	public void setDeveloper(User developer) {
		this.developer = developer;
	}

	@Override
	public ReviewReply toEntity() {
		ReviewReply reviewReply = new ReviewReply();
		reviewReply.setDescription(getDescription());
		return reviewReply;
	}

}
