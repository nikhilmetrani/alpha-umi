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

import java.util.Date;

import io._29cu.usmserver.core.model.entities.DeveloperProfile;

public class DeveloperProfileResource extends EntityResourceBase<DeveloperProfile>  {

    private Long rid;
    private String email;
    private String website;
    private String description;
    private String company;
    private String jobTitle;
    private String address;
    private String city;
    private String state;
    private Integer zipCode;
    private String country;
    private Integer workPhone;
    private Integer homePhone;
    private Date dateOfBirth;
    private String gender;
    private Date joinDate;
    private String logo;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    
    public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(Integer workPhone) {
		this.workPhone = workPhone;
	}

	public Integer getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(Integer homePhone) {
		this.homePhone = homePhone;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Override
    public DeveloperProfile toEntity() {

		DeveloperProfile developerProfile = new DeveloperProfile();
        developerProfile.setId(getRid());
        developerProfile.setCompany(getCompany());
        developerProfile.setDescription(getDescription());
        developerProfile.setEmail(getEmail());
        developerProfile.setWebsite(getWebsite());
        developerProfile.setAddress(getAddress());
        developerProfile.setJobTitle(getJobTitle());
        developerProfile.setCity(getCity());
        developerProfile.setCountry(getCountry());
        developerProfile.setZipCode(getZipCode());
        developerProfile.setState(getState());
        developerProfile.setGender(getGender());
        developerProfile.setHomePhone(getHomePhone());
        developerProfile.setDateOfBirth(getDateOfBirth());
        developerProfile.setLogo(getLogo());
        developerProfile.setWorkPhone(getWorkPhone());
        return developerProfile;
    }
}
