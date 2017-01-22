package io._29cu.usmserver.controllers.rest.resources;

import java.util.Optional;

public final class PageParams {


    private Long sinceId;
    private Long maxId;

    public Optional<Long> getSinceId() {
        return Optional.ofNullable(sinceId);
    }

    public Optional<Long> getMaxId() {
        return Optional.ofNullable(maxId);
    }


}
