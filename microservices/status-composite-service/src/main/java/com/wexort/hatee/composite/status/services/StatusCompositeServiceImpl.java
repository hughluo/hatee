package com.wexort.hatee.composite.status.services;

import java.util.List;
import java.util.stream.Collectors;


import com.wexort.hatee.composite.status.api.StatusCompositeService;
import com.wexort.hatee.composite.status.api.*;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.wexort.hatee.core.status.Status;
import com.wexort.hatee.core.comment.Comment;
import com.wexort.hatee.util.exceptions.NotFoundException;
import com.wexort.hatee.util.http.ServiceUtil;

public class StatusCompositeServiceImpl implements StatusCompositeService {

    private final ServiceUtil serviceUtil;
    private StatusCompositeIntegration integration;

    @Autowired
    public StatusCompositeServiceImpl(ServiceUtil serviceUtil, StatusCompositeIntegration integration) {
        this.serviceUtil = serviceUtil;
        this.integration = integration;
    }

    @Override
    public StatusAggregate getStatus(int statusId) {

        Status status = integration.getStatus(statusId);
        if (status == null) throw new NotFoundException("No status found for statusId: " + statusId);

        List<Recommendation> recommendations = integration.getRecommendations(statusId);

        List<Comment> comments = integration.getComments(statusId);

        return createStatusAggregate(status, recommendations, comments, serviceUtil.getServiceAddress());
    }

    private StatusAggregate createStatusAggregate(Status status, List<Recommendation> recommendations, List<Comment> comments, String serviceAddress) {

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
