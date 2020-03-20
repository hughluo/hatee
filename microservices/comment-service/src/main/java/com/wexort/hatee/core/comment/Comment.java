package com.wexort.hatee.core.comment;

public class Comment {
    private final int commentId;
    private final int statusId;
    private final int userId;
    private final String content;

    public Comment() {
        commentId = 0;
        statusId = 0;
        userId = 0;
        content = "";
    }

    public Comment(int commentId, int statusId, int userId, String content) {
        this.commentId = commentId;
        this.statusId = statusId;
        this.userId = userId;
        this.content = content;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getStatusId() {
        return statusId;
    }

    public int getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }
}


