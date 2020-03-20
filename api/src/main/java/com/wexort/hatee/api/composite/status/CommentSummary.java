package com.wexort.hatee.api.composite.status;

public class CommentSummary {
    final int commentId;
    final int userId;
    final String content;

    public CommentSummary(int commentId, int userId, String content) {
        this.commentId = commentId;
        this.userId = userId;
        this.content = content;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }
}
