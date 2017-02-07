package io._29cu.usmserver.core.model.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import io._29cu.usmserver.core.model.enumerations.FeatureAppState;

@Entity
public class FeaturedApplication extends BaseValueObject{
	
	@NotNull
	@OneToOne
	private Application application;
	
	@NotNull
	private FeatureAppState featureAppState;
	
	private Date unFeatureDate;	

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public FeatureAppState getFeatureAppState() {
		return featureAppState;
	}

	public void setFeatureAppState(FeatureAppState featureAppState) {
		this.featureAppState = featureAppState;
	}

	public Date getUnFeatureDate() {
		return unFeatureDate;
	}

	public void setUnFeatureDate(Date unFeatureDate) {
		this.unFeatureDate = unFeatureDate;
	} 
	
}
