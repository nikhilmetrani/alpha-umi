package io._29cu.usmserver.core.service;

import io._29cu.usmserver.core.model.entities.EmployeeProfile;

/**
 * Created by yniu on 10/12/2016.
 */

public interface EmployeeProfileService {
    public EmployeeProfile findProfileByUserId(Long id);                 // View Employee Profile
    public EmployeeProfile createProfile(EmployeeProfile profile);       // Create Employee Profile
    public EmployeeProfile modifyProfile(EmployeeProfile profile);       // Modify Employee Profile
}
