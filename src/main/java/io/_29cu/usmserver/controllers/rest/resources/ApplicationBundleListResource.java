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

import io._29cu.usmserver.core.service.utilities.ApplicationBundleList;

public class ApplicationBundleListResource extends EntityResourceBase<ApplicationBundleList> {

	private List<ApplicationBundleResource> applicationBundles = new ArrayList<>();

    public List<ApplicationBundleResource> getApplicationBundles() {
        return applicationBundles;
    }

    public void setApplicationBundles(List<ApplicationBundleResource> applicationBundles) {
        this.applicationBundles = applicationBundles;
    }

    @Override
    public ApplicationBundleList toEntity() {
        return null;
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((applicationBundles == null) ? 0 : applicationBundles.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationBundleListResource other = (ApplicationBundleListResource) obj;
		if (applicationBundles == null) {
			if (other.applicationBundles != null)
				return false;
		} else if (!applicationBundles.equals(other.applicationBundles))
			return false;
		return true;
	}
}
