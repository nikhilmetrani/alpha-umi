package io._29cu.usmserver.core.service;

import io._29cu.usmserver.core.model.entities.ConsumerProfile;

/**
 * Created by yniu on 10/12/2016.
 */
public interface ConsumerProfileService {
    public ConsumerProfile findProfileByUserId(Long id);                 // View Consumer Profile
    public ConsumerProfile createProfile(ConsumerProfile profile);       // Create Consumer Profile
    public ConsumerProfile modifyProfile(ConsumerProfile profile);       // Modify Consumer Profile
}
