package io._29cu.usmserver.core.service;

import io._29cu.usmserver.core.model.entities.AuUser;

public interface SecurityContextService {
    AuUser getLoggedInUser();
}
