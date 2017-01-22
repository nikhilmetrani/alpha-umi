package io._29cu.usmserver.controllers.rest.resources;

import io._29cu.usmserver.core.model.entities.ConsumerProfile;

public class ConsumerProfileResource extends EntityResourceBase<ConsumerProfile>  {

    private Long rid;
    private String email;
    private String website;

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

    @Override
    public ConsumerProfile toEntity() {
        ConsumerProfile consumerProfile = new ConsumerProfile();
        consumerProfile.setId(getRid());
        consumerProfile.setEmail(getEmail());
        consumerProfile.setWebsite(getWebsite());
        return consumerProfile;
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
		ConsumerProfileResource other = (ConsumerProfileResource) obj;
		if (rid == null) {
			if (other.rid != null)
				return false;
		} else if (!rid.equals(other.rid))
			return false;
		return true;
	}

}