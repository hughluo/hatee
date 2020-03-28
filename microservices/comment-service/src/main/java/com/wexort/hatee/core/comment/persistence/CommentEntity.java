package com.wexort.hatee.core.comment.persistence;

import javax.persistence.*;

@Entity
@Table(name = "comments", indexes = { @Index(name = "comments_unique_idx", unique = true, columnList = "statusId,commentId") })
public class CommentEntity {
    @Id @GeneratedValue
    private int id;

    @Version
    private int version;

    private int statusId;
    private int commentId;
    private int userId;
    private String content;

    public CommentEntity() {}


    public CommentEntity(int commentId, int statusId, int userId, String content) {
        this.commentId = commentId;
        this.statusId = statusId;
        this.userId = userId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
