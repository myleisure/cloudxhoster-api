package com.cloudxhoster.api.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<GenericException> {

    @Override
    public Response toResponse(GenericException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), e.getBusinessErrorCodeEnum().getCode());
        return Response.ok().entity(response).build();
    }
}