package io._29cu.usmserver.core.service;

import io._29cu.usmserver.core.model.entities.User;

public interface SecurityContextService {
    User getLoggedInUser();
}
