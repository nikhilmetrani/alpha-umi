package io._29cu.usmserver.controllers.rest.resources;

/**
 * Created by yniu on 10/12/2016.
 */
import java.util.Date;

//import io._29cu.usmserver.core.model.entities.ConsumerProfile;
import io._29cu.usmserver.core.model.entities.User;

public class ConsumerProfileResource extends EntityResourceBase<User>  {

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
    public User toEntity() {
        User consumerProfile = new User();
        consumerProfile.setId(getRid());
        consumerProfile.setEmail(getEmail());
        return consumerProfile;
    }
}
