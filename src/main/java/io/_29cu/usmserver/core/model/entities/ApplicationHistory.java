package io._29cu.usmserver.core.model.entities;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import io._29cu.usmserver.core.model.enumerations.AppState;

@Entity
public class ApplicationHistory extends BaseValueObject{
	
	@NotNull
	private String applicationId;
	@NotNull
	private AppState state;
	@NotNull
	private String name;
	@NotNull
	private String description;
	@NotNull
	private String version;
	@NotNull
	private String whatsNew;
	@NotNull
	private String developerId;
	@NotNull
	private Long categoryId;
	
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public AppState getState() {
		return state;
	}
	public void setState(AppState state) {
		this.state = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getWhatsNew() {
		return whatsNew;
	}
	public void setWhatsNew(String whatsNew) {
		this.whatsNew = whatsNew;
	}
	public String getDeveloperId() {
		return developerId;
	}
	public void setDeveloperId(String developerId) {
		this.developerId = developerId;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	

}
