package io._29cu.usmserver.controllers.rest.resources;

/**
 * Created by yniu on 17/10/2016.
 */

import io._29cu.usmserver.core.service.utilities.ApplicationBundle;

import java.util.ArrayList;
import java.util.List;

public class ApplicationBundleResource extends EntityResourceBase<ApplicationBundle> {

    private List<ApplicationResource> applications = new ArrayList<>();

    public List<ApplicationResource> getApplications() {
        return applications;
    }

    public void setApplications(List<ApplicationResource> applications) {
        this.applications = applications;
    }

    @Override
    public ApplicationBundle toEntity() {
        return null;
    }
}
