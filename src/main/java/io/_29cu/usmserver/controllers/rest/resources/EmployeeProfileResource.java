package io._29cu.usmserver.controllers.rest.resources;

import io._29cu.usmserver.core.model.entities.EmployeeProfile;

import java.util.Date;

/**
 * Created by yniu on 10/12/2016.
 */

public class EmployeeProfileResource extends EntityResourceBase<EmployeeProfile>  {

    private Long rid;
    private String email;
    private String company;
    private String jobTitle;    //Site Manager or Site Maintainer
    private Date joinDate;
    private String address;
    private String city;
    private String state;
    private String country;
    private Integer zipCode;
    private Integer workPhone;
    private Integer homePhone;
    private Date dateOfBirth;
    private String gender;

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

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
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

    @Override
    public EmployeeProfile toEntity() {
        EmployeeProfile employeeProfile = new EmployeeProfile();
        employeeProfile.setId(getRid());
        employeeProfile.setEmail(getEmail());
        employeeProfile.setCompany(getCompany());
        employeeProfile.setJobTitle(getJobTitle());
        employeeProfile.setJoinDate(getJoinDate());
        employeeProfile.setAddress(getAddress());
        employeeProfile.setCity(getCity());
        employeeProfile.setState(getState());
        employeeProfile.setCountry(getCountry());
        employeeProfile.setZipCode(getZipCode());
        employeeProfile.setWorkPhone(getWorkPhone());
        employeeProfile.setHomePhone(getHomePhone());
        employeeProfile.setDateOfBirth(getDateOfBirth());
        employeeProfile.setGender(getGender());
        return employeeProfile;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((rid == null) ? 0 : rid.hashCode());
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
		EmployeeProfileResource other = (EmployeeProfileResource) obj;
		if (rid == null) {
			if (other.rid != null)
				return false;
		} else if (!rid.equals(other.rid))
			return false;
		return true;
	}

}
