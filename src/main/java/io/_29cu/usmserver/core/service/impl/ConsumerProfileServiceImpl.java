package io._29cu.usmserver.core.service.impl;

/**
 * Created by yniu on 10/12/2016.
 */

import io._29cu.usmserver.core.model.entities.ConsumerProfile;
import io._29cu.usmserver.core.repositories.ConsumerProfileRepository;
import io._29cu.usmserver.core.service.ConsumerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerProfileServiceImpl implements ConsumerProfileService{
    @Autowired
    private ConsumerProfileRepository consumerProfileRepository;

    @Override
    public ConsumerProfile findProfileByUserId(Long id) {
        return consumerProfileRepository.findProfileByUserId(id);
    }

    @Override
    public ConsumerProfile createProfile(ConsumerProfile profile) {
        return consumerProfileRepository.save(profile);
    }

    @Override
    public ConsumerProfile modifyProfile(ConsumerProfile profile) {
        //Save the updated consumer profile to repository
        return consumerProfileRepository.save(profile);
    }
}