package com.wexort.hatee.core.status;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.wexort.hatee.util.exceptions.InvalidInputException;
import com.wexort.hatee.util.exceptions.NotFoundException;
import com.wexort.hatee.util.http.ServiceUtil;


@RestController
public class StatusServiceImpl implements StatusService {

    private static final Logger LOG = LoggerFactory.getLogger(StatusServiceImpl.class);

    private final ServiceUtil serviceUtil;

    @Autowired
    public StatusServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }

    @Override
    public Status getStatus(int statusId) {
        LOG.debug("/status return the found status for statusId={}", statusId);

        if (statusId < 1) throw new InvalidInputException("Invalid statusId: " + statusId);

        if (statusId == 13) throw new NotFoundException("No status found for statusId: " + statusId);

        return new Status(statusId,  123, 123, "hello world!");
    }
}
