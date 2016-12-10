package io._29cu.usmserver.core.model.entities;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@MappedSuperclass
public class ApplicationBrowsingList {

	@OneToMany
	List<Application> applications;

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

}
