package io._29cu.usmserver.core.service;

import io._29cu.usmserver.core.model.entities.User;

/**
 * Created by yniu on 10/12/2016.
 */
public interface ConsumerProfileService {
    public User findProfileByUserId(Long id);      // View Consumer Profile
    public User createProfile(User profile);       // Create Consumer Profile
    public User modifyProfile(User profile);       // Modify Consumer Profile
}
