package com.wexort.hatee.core.status;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface StatusService {
    @GetMapping(
            value = "status/{statusId}",
            produces = "application/json")
    Status getStatus(@PathVariable int statusId);
}
