package com.cloudxhoster.api.controller;

import com.cloudxhoster.api.model.request.HostingForm;
import com.cloudxhoster.api.model.request.HostingRequest;
import com.cloudxhoster.api.exception.GenericException;
import com.cloudxhoster.api.service.hosting.HostingService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/v1/website")
public class HostingResource {

    private static final Logger LOG = LoggerFactory.getLogger(HostingResource.class);

    private final HostingService hostingService;

    @Inject
    public HostingResource(HostingService hostingService) {
        this.hostingService = hostingService;
    }

    @POST
    @Path("/host")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response host(HostingRequest hostingRequest) throws GenericException {
        LOG.info("[CloudxHosterAPI] Trying to host with informations: {}", hostingRequest);
        this.hostingService.host(hostingRequest);
        LOG.info("[CloudxHosterAPI] Hosted website with informations: {}", hostingRequest);
        return Response.ok(true).build();
    }

    @POST
    @Path("/upload")
    public Response host(@MultipartForm HostingForm hostingForm) throws GenericException {
        LOG.info("[CloudxHosterAPI] Trying to host with informations: {}", hostingForm);
        this.hostingService.host(hostingForm);
        LOG.info("[CloudxHosterAPI] Hosted website with informations: {}", hostingForm);
        return Response.ok(true).build();
    }
}
