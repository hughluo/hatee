package com.wexort.hatee.core.comment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.wexort.hatee.util.exceptions.InvalidInputException;
import com.wexort.hatee.util.http.ServiceUtil;

import com.wexort.hatee.api.core.comment.Comment;
import com.wexort.hatee.api.core.comment.CommentService;


import java.util.ArrayList;
import java.util.List;

@RestController
public class CommentServiceImpl implements CommentService {

    private static final Logger LOG = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final ServiceUtil serviceUtil;

    @Autowired
    public CommentServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }

    @Override
    public List<Comment> getComments(int statusId) {

        if (statusId < 1) throw new InvalidInputException("Invalid statusId: " + statusId);

        if (statusId == 213) {
            LOG.debug("No comments found for statusId: {}", statusId);
            return  new ArrayList<>();
        }

        List<Comment> list = new ArrayList<>();
        list.add(new Comment(111, statusId, 1, "Content 1"));
        list.add(new Comment(222, statusId, 1, "Content 2"));
        list.add(new Comment(333, statusId, 1, "Content 3"));

        LOG.debug("/comments response size: {}", list.size());

        return list;
    }
}

