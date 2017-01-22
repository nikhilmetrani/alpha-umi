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

}