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

import io._29cu.usmserver.core.model.entities.*;
import io._29cu.usmserver.core.model.enumerations.AppState;

public class ApplicationBundleResource extends EntityResourceBase<ApplicationBundle> {

    private String rid;
    private String name;
    private AuUser developer;
    private Category category;
    private AppState state;
    private String description;

    public AppState getState() {
        return state;
    }

    public void setState(AppState state) {
        this.state = state;
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

    public AuUser getDeveloper() {
        return developer;
    }

    public void setDeveloper(AuUser developer) {
        this.developer = developer;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    @Override
    public ApplicationBundle toEntity() {
        ApplicationBundle applicationBundle = new ApplicationBundle();
        applicationBundle.setId(rid);
        applicationBundle.setName(name);
        applicationBundle.setDeveloper(developer);
        applicationBundle.setState(state);
        applicationBundle.setCategory(category);
        applicationBundle.setDescription(description);
        return applicationBundle;
    }
}
