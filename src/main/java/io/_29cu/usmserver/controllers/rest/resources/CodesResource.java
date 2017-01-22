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

import io._29cu.usmserver.core.service.utilities.Codes;

public class CodesResource extends EntityResourceBase<Codes> {

    private List<CodeResource> codes = new ArrayList<>();

    public List<CodeResource> getCodes() {
        return codes;
    }

    public void setCodes(List<CodeResource> codes) {
        this.codes = codes;
    }

    @Override
    public Codes toEntity() {
        return null;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codes == null) ? 0 : codes.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CodesResource other = (CodesResource) obj;
		if (codes == null) {
			if (other.codes != null)
				return false;
		} else if (!codes.equals(other.codes))
			return false;
		return true;
	}
}
