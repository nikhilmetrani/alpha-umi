package io._29cu.usmserver.controllers.rest.resources.assemblers;

import io._29cu.usmserver.controllers.rest.UserController;
import io._29cu.usmserver.controllers.rest.resources.UserResource;
import io._29cu.usmserver.core.model.entities.User;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

/**
 * Created by yniu on 1/23/2017.
 */
public class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {
    public UserResourceAssembler() {
        super(UserController.class, UserResource.class);
    }

    @Override
    public UserResource toResource(User user) {
        UserResource userResource = new UserResource();
        userResource.setRid(user.getId());
        userResource.setEmail(user.getEmail());
        userResource.setUsername(user.getUsername());
        userResource.setFirstname(user.getFirstname());
        userResource.setLastname(user.getLastname());
        return userResource;
    }
}
