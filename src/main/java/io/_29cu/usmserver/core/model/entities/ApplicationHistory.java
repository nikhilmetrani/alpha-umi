package io._29cu.usmserver.core.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import io._29cu.usmserver.core.model.enumerations.AppState;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class ApplicationHistory extends BaseValueObject{
	@NotNull
	@ManyToOne
	private Application application;
	@NotNull
	private String name;
	@NotNull
	private String version;
	@NotNull
	private String whatsNew;

    public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
}
