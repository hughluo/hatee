package com.wexort.hatee.api.composite.status;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface StatusCompositeService {
    @GetMapping(
            value = "/status-composite/{statusId}",
            produces = "application/json")
    StatusAggregate getStatus(@PathVariable int statusId);
}
