package io._29cu.usmserver.controllers.rest.resources.assemblers;

import io._29cu.usmserver.controllers.rest.EmployeeProfileController;
import io._29cu.usmserver.controllers.rest.resources.EmployeeProfileResource;
import io._29cu.usmserver.core.model.entities.EmployeeProfile;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by yniu on 10/12/2016.
 */

public class EmployeeProfileResourceAssembler extends ResourceAssemblerSupport<EmployeeProfile, EmployeeProfileResource> {
    public EmployeeProfileResourceAssembler() {
        super(EmployeeProfileController.class, EmployeeProfileResource.class);
    }
    @Override
    public EmployeeProfileResource toResource(EmployeeProfile employeeProfile) {
        EmployeeProfileResource employeeProfileResource = new EmployeeProfileResource();
        employeeProfileResource.setRid(employeeProfile.getId());
        employeeProfileResource.setEmail(employeeProfile.getEmail());
        employeeProfileResource.setCompany(employeeProfile.getCompany());
        employeeProfileResource.setJobTitle(employeeProfile.getJobTitle());
        employeeProfileResource.setJoinDate(employeeProfile.getJoinDate());
        employeeProfileResource.setAddress(employeeProfile.getAddress());
        employeeProfileResource.setCity(employeeProfile.getCity());
        employeeProfileResource.setState(employeeProfile.getState());
        employeeProfileResource.setCountry(employeeProfile.getCountry());
        employeeProfileResource.setZipCode(employeeProfile.getZipCode());
        employeeProfileResource.setWorkPhone(employeeProfile.getWorkPhone());
        employeeProfileResource.setHomePhone(employeeProfile.getHomePhone());
        employeeProfileResource.setDateOfBirth(employeeProfile.getDateOfBirth());
        employeeProfileResource.setGender(employeeProfile.getGender());
        employeeProfileResource.add(linkTo(methodOn(EmployeeProfileController.class).employeeProfile()).withSelfRel());
        return employeeProfileResource;
    }
}
