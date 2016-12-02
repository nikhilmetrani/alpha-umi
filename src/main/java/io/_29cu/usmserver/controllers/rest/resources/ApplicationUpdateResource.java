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

import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.ApplicationUpdate;

public class ApplicationUpdateResource extends EntityResourceBase<ApplicationUpdate> {

    private String rid;
    private String name;
    private String version;
    private String description;
    private String whatsNew;
    private Application target;

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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

    public Application getTarget() { return target; }

    public void setTarget(Application target) { this.target = target; }


    @Override
    public ApplicationUpdate toEntity() {
        ApplicationUpdate applicationUpdate = new ApplicationUpdate();
        applicationUpdate.setId(this.getRid());
        applicationUpdate.setName(this.getName());
        applicationUpdate.setVersion(this.getVersion());
        applicationUpdate.setDescription(this.getDescription());
        applicationUpdate.setWhatsNew(this.getWhatsNew());
        return applicationUpdate;
    }
}
