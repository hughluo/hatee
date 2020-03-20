package com.wexort.hatee.api.core.status;

public class Status {
    private final int statusId;
    private final int userId;
    private final int epoch;
    private final String content;

    public Status() {
        statusId = 0;
        userId = 0;
        epoch = 0;
        content = "";
    }

    public Status(int statusId, int userId, int epoch, String content) {
        this.statusId = statusId;
        this.userId = userId;
        this.epoch = epoch;
        this.content = content;
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
}
