package com.wexort.hatee.api.core.comment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CommentService {
    /**
     * Sample usage: curl $HOST:$PORT/comment?statusId=1
     *
     * @param statusId
     * @return
     */
    @GetMapping(
            value    = "/comment",
            produces = "application/json")
    List<Comment> getComments(@RequestParam(value = "statusId", required = true) int statusId);
}
