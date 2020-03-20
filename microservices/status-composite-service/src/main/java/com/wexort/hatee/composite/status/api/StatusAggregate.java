package com.wexort.hatee.composite.status.api;

import java.util.List;

public class StatusAggregate {
    private final int statusId;
    private final int userId;
    private final int epoch;
    private final String content;
    private final List<CommentSummary> comments;

    public StatusAggregate(int statusId, int userId, int epoch, String content, List<CommentSummary> comments) {
        this.statusId = statusId;
        this.userId = userId;
        this.epoch = epoch;
        this.content = content;
        this.comments = comments;
    }

    public int getStatusId() {
        return statusId;
    }

    public int getUserId() {
        return userId;
    }

    public int getEpoch() {
        return epoch;
    }

    public String getContent() {
        return content;
    }

    public List<CommentSummary> getComments() {
        return comments;
    }
}
