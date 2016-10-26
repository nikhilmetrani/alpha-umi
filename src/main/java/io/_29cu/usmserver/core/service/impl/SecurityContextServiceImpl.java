package io._29cu.usmserver.core.service.impl;

import io._29cu.usmserver.core.model.entities.AuUser;
import io._29cu.usmserver.core.repositories.AuUserRepository;
import io._29cu.usmserver.core.service.SecurityContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityContextServiceImpl implements SecurityContextService {
    @Autowired
    private AuUserRepository auUserRepository;

    @Override
    public AuUser getLoggedInUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Optional<AuUser> currentUser = auUserRepository.findByUsername(authentication.getName());
        // TODO It may be better to return optional.
        return currentUser.orElse(null);
    }
}
