package com.wexort.hatee.core.status.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="statuses")
public class StatusEntity {
    @Id
    private String id;

    @Version
    private Integer version;

    @Indexed(unique = true)
    private int statusId;

    private int userId;
    private int epoch;
    private String content;

    public StatusEntity() {}

    public StatusEntity(int statusId, int userId, int epoch, String content) {
        this.statusId = statusId;
        this.userId = userId;
        this.epoch = epoch;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEpoch() {
        return epoch;
    }

    public void setEpoch(int epoch) {
        this.epoch = epoch;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
