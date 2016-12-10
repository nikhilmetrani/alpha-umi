package io._29cu.usmserver.core.service.impl;

/**
 * Created by yniu on 10/12/2016.
 */
import io._29cu.usmserver.core.model.entities.EmployeeProfile;
import io._29cu.usmserver.core.repositories.EmployeeProfileRepository;
import io._29cu.usmserver.core.service.EmployeeProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeProfileServiceImpl implements EmployeeProfileService{
    @Autowired
    private EmployeeProfileRepository employeeProfileRepository;

    @Override
    public EmployeeProfile findProfileByUserId(Long id) {
        return employeeProfileRepository.findProfileByUserId(id);
    }

    @Override
    public EmployeeProfile createProfile(EmployeeProfile profile) {
        return employeeProfileRepository.save(profile);
    }

    @Override
    public EmployeeProfile modifyProfile(EmployeeProfile profile) {
        return employeeProfileRepository.save(profile);
    }
}