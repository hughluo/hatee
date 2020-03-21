package com.wexort.hatee.api.composite.status;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(description = "REST API for composite status information.")
public interface StatusCompositeService {

    /**
     * Sample usage: curl $HOST:$PORT/status-composite/1
     *
     * @param statusId
     * @return the composite status info, if found, else null
     */
    @ApiOperation(
            value = "${api.status-composite.get-composite-status.description}",
            notes = "${api.status-composite.get-composite-status.notes}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request, invalid format of the request. See response message for more information."),
            @ApiResponse(code = 404, message = "Not found, the specified id does not exist."),
            @ApiResponse(code = 422, message = "Unprocessable entity, input parameters caused the processing to fails. See response message for more information.")
    })
    @GetMapping(
            value = "/status-composite/{statusId}",
            produces = "application/json")
    StatusAggregate getStatus(@PathVariable int statusId);
}
