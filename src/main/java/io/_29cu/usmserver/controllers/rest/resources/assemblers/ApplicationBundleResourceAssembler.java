package io._29cu.usmserver.controllers.rest.resources.assemblers;

/**
 * Created by yniu on 17/10/2016.
 */

import io._29cu.usmserver.controllers.rest.resources.ApplicationBundleResource;
import io._29cu.usmserver.core.service.utilities.ApplicationBundle;
import io._29cu.usmserver.controllers.rest.StoreController;
import io._29cu.usmserver.controllers.rest.resources.ApplicationResource;
import io._29cu.usmserver.controllers.rest.resources.ApplicationBundleResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

public class ApplicationBundleResourceAssembler extends ResourceAssemblerSupport<ApplicationBundle, ApplicationBundleResource> {

    public ApplicationBundleResourceAssembler() {
        super(StoreController.class, ApplicationBundleResource.class);
    }
    @Override
    public ApplicationBundleResource toResource(ApplicationBundle applicationBundle) {
        List<ApplicationResource> appResourceBundle = new ApplicationResourceAssembler().toResources(applicationBundle.getApplications());
        ApplicationBundleResource appBundleRes = new ApplicationBundleResource();
        appBundleRes.setApplications(appResourceBundle);
        appBundleRes.add(linkTo(methodOn(StoreController.class).store()).withSelfRel());
        return appBundleRes;
    }
}
