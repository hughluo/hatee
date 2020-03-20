package com.wexort.hatee.composite.status.services;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


import com.wexort.hatee.api.core.status.Status;
import com.wexort.hatee.api.core.comment.Comment;
import com.wexort.hatee.api.composite.status.StatusCompositeService;
import com.wexort.hatee.api.composite.status.StatusAggregate;
import com.wexort.hatee.api.composite.status.CommentSummary;



import com.wexort.hatee.util.exceptions.NotFoundException;
import com.wexort.hatee.util.http.ServiceUtil;

@RestController
public class StatusCompositeServiceImpl implements StatusCompositeService {

    private final ServiceUtil serviceUtil;
    private StatusCompositeIntegration integration;

    @Autowired
    public StatusCompositeServiceImpl(ServiceUtil serviceUtil, StatusCompositeIntegration integration) {
        this.serviceUtil = serviceUtil;
        this.integration = integration;
    }

    public StatusAggregate getStatus(int statusId) {
        System.out.println("!!!called " + statusId);

        Status status = integration.getStatus(statusId);
        if (status == null) throw new NotFoundException("No status found for statusId: " + statusId);

        List<Comment> comments = integration.getComments(statusId);

        return createStatusAggregate(status, comments);
    }

    private StatusAggregate createStatusAggregate(Status status, List<Comment> comments) {

        // 1. Setup status info
        int statusId = status.getStatusId();
        int userId = status.getUserId();
        int epoch = status.getEpoch();
        String statusContent = status.getContent();

        // 2. Copy summary comment info, if available
        List<CommentSummary> commentSummaries = (comments == null)  ? null :
                comments.stream()
                        .map(r -> new CommentSummary(r.getCommentId(), r.getUserId(), r.getContent()))
                        .collect(Collectors.toList());


        return new StatusAggregate(statusId, userId, epoch, statusContent, commentSummaries);
    }
}
