package io._29cu.usmserver.controllers.rest.resources.assemblers;

/**
 * Created by yniu on 10/12/2016.
 */
import io._29cu.usmserver.controllers.rest.ConsumerProfileController;
import io._29cu.usmserver.controllers.rest.resources.ConsumerProfileResource;
import io._29cu.usmserver.core.model.entities.User;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ConsumerProfileResourceAssembler extends ResourceAssemblerSupport<User, ConsumerProfileResource>{
    public ConsumerProfileResourceAssembler() {
        super(ConsumerProfileController.class, ConsumerProfileResource.class);
    }
    @Override
    public ConsumerProfileResource toResource(User consumerProfile) {
        ConsumerProfileResource consumerProfileResource = new ConsumerProfileResource();
        consumerProfileResource.setRid(consumerProfile.getId());
        consumerProfileResource.setEmail(consumerProfile.getEmail());
        consumerProfileResource.add(linkTo(methodOn(ConsumerProfileController.class).consumerProfile()).withSelfRel());
        return consumerProfileResource;
    }
}