/**
 * Copyright 2016 - 29cu.io and the authors of alpha-umi open source project

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 *     http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package io._29cu.usmserver.controllers.rest.resources;

import java.util.ArrayList;
import java.util.List;

import io._29cu.usmserver.core.model.entities.*;
import io._29cu.usmserver.core.model.enumerations.AppState;

public class ApplicationResource extends EntityResourceBase<Application> {

    private String rid;
    private String name;
    private User developer;
    private Category category;
    private AppState state;
    private String downloadUrl;
    private String version;
    private String description;
    private String whatsNew;
    private List<InstallerResource> installers;
    private List<ReviewResource> reviews;
   
	public AppState getState() {
        return state;
    }

    public void setState(AppState state) {
        this.state = state;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getWhatsNew() {
        return whatsNew;
    }

    public void setWhatsNew(String whatsNew) {
        this.whatsNew = whatsNew;
    }

	public List<InstallerResource> getInstallers() {
		return installers;
	}

	public void setInstallers(List<InstallerResource> installers) {
		this.installers = installers;
	}

    public List<ReviewResource> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewResource> reviews) {
        this.reviews = reviews;
    }

    @Override
    public Application toEntity() {
        Application application = new Application();
        application.setId(rid);
        application.setName(name);
        application.setDeveloper(developer);
        application.setState(state);
        application.setVersion(version);
        application.setCategory(category);
        application.setDescription(description);
        application.setWhatsNew(whatsNew);
        List<Installer> installerObjects = new ArrayList<Installer>();
        List<Review> reviewObjects = new ArrayList<Review>();

        if(installers != null) {
	        for(InstallerResource installerResource : installers) {
	        	installerObjects.add(installerResource.toEntity());
	        }
        }
        application.setInstallers(installerObjects);
        if(reviews != null) {
            for(ReviewResource reviewResource : reviews) {
                reviewObjects.add(reviewResource.toEntity());
            }
        }
        application.setReviews(reviewObjects);
        return application;
    }
}
