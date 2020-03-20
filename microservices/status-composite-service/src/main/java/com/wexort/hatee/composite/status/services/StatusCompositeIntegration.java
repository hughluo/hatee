package com.wexort.hatee.composite.status.services;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import static org.springframework.http.HttpMethod.GET;


import com.wexort.hatee.api.core.status.Status;
import com.wexort.hatee.api.core.status.StatusService;
import com.wexort.hatee.api.core.comment.Comment;
import com.wexort.hatee.api.core.comment.CommentService;
import com.wexort.hatee.api.composite.status.CommentSummary;

import com.wexort.hatee.util.exceptions.InvalidInputException;
import com.wexort.hatee.util.exceptions.NotFoundException;
import com.wexort.hatee.util.http.HttpErrorInfo;

import java.io.IOException;

@Component
public class StatusCompositeIntegration implements StatusService, CommentService {

    private static final Logger LOG = LoggerFactory.getLogger(StatusCompositeIntegration.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    private final String statusServiceUrl;
    private final String commentServiceUrl;

    @Autowired
    public StatusCompositeIntegration(
            RestTemplate restTemplate,
            ObjectMapper mapper,

            @Value("${app.status-service.host}") String statusServiceHost,
            @Value("${app.status-service.port}") int    statusServicePort,

            @Value("${app.comment-service.host}") String commentServiceHost,
            @Value("${app.comment-service.port}") int    commentServicePort
    ) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        statusServiceUrl = "http://" + statusServiceHost + ":" + statusServicePort + "/status/";
        commentServiceUrl = "http://" + commentServiceHost + ":" + commentServicePort + "/comment?statusId=";
    }

    public Status getStatus(int statusId) {

        try {
            String url = statusServiceUrl + statusId;
            LOG.debug("Will call getStatus API on URL: {}", url);

            Status status = restTemplate.getForObject(url, Status.class);
            LOG.debug("Found a status with id: {}", status.getStatusId());

            return status;

        } catch (HttpClientErrorException ex) {

            switch (ex.getStatusCode()) {

                case NOT_FOUND:
                    throw new NotFoundException(getErrorMessage(ex));

                case UNPROCESSABLE_ENTITY :
                    throw new InvalidInputException(getErrorMessage(ex));

                default:
                    LOG.warn("Got a unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
                    LOG.warn("Error body: {}", ex.getResponseBodyAsString());
                    throw ex;
            }
        }
    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        } catch (IOException ioex) {
            return ex.getMessage();
        }
    }

    public List<Comment> getComments(int productId) {

        try {
            String url = commentServiceUrl + productId;

            LOG.debug("Will call getComments API on URL: {}", url);
            List<Comment> comments = restTemplate.exchange(url, GET, null, new ParameterizedTypeReference<List<Comment>>() {}).getBody();

            LOG.debug("Found {} comments for a product with id: {}", comments.size(), productId);
            return comments;

        } catch (Exception ex) {
            LOG.warn("Got an exception while requesting comments, return zero comments: {}", ex.getMessage());
            return new ArrayList<>();
        }
    }


}
