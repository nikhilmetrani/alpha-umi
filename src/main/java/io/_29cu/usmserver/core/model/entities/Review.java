package io._29cu.usmserver.core.model.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Review extends BaseValueObject {
	
	@ManyToOne
	Application application;

	@ManyToOne
	private User consumer;
	
	@OneToMany(mappedBy = "review")
	private List<ReviewReply> replies;

	@NotNull
	private String title;

	@NotNull
	private String description;
	
	private boolean featured;	
		
	public List<ReviewReply> getReplies() {
		return replies;
	}

	public void setReplies(List<ReviewReply> replies) {
		this.replies = replies;
	}

	public boolean isFeatured() {
		return featured;
	}

	public void setFeatured(boolean featured) {
		this.featured = featured;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
