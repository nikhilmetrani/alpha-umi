package io._29cu.usmserver.core.model.entities;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class ApplicationUpdateHistory extends BaseValueObject {

	@NotNull
	private String appUpdateId;
	@NotNull
	private String targetAppId;
	@NotNull
	private String appUpdateName;
	@NotNull
	private String appUpdateDescription;
	@NotNull
	private String appUpdateVersion;
	@NotNull
	private String appUpdateWhatsNew;
	
	public String getAppUpdateId() {
		return appUpdateId;
	}
	public void setAppUpdateId(String appUpdateId) {
		this.appUpdateId = appUpdateId;
	}
	public String getTargetAppId() {
		return targetAppId;
	}
	public void setTargetAppId(String targetAppId) {
		this.targetAppId = targetAppId;
	}
	public String getAppUpdateName() {
		return appUpdateName;
	}
	public void setAppUpdateName(String appUpdateName) {
		this.appUpdateName = appUpdateName;
	}
	public String getAppUpdateDescription() {
		return appUpdateDescription;
	}
	public void setAppUpdateDescription(String appUpdateDescription) {
		this.appUpdateDescription = appUpdateDescription;
	}
	public String getAppUpdateVersion() {
		return appUpdateVersion;
	}
	public void setAppUpdateVersion(String appUpdateVersion) {
		this.appUpdateVersion = appUpdateVersion;
	}
	public String getAppUpdateWhatsNew() {
		return appUpdateWhatsNew;
	}
	public void setAppUpdateWhatsNew(String appUpdateWhatsNew) {
		this.appUpdateWhatsNew = appUpdateWhatsNew;
	}
	
}
