package io._29cu.usmserver.core.service.impl;

import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.repositories.UserRepository;
import io._29cu.usmserver.core.service.SecurityContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityContextServiceImpl implements SecurityContextService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getLoggedInUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Optional<User> currentUser = userRepository.findByUsername(authentication.getName());
        // TODO It may be better to return optional.
        return currentUser.orElse(null);
    }
}
