package com.cloudxhoster.api.controller;

import com.cloudxhoster.api.model.Hosting;
import com.cloudxhoster.api.domain.HostingEntity;
import com.cloudxhoster.api.mapper.TransFormer;
import com.cloudxhoster.api.service.dashboard.DashBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/api/v1/dashboard")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DashBoardResource {

    Logger LOG = LoggerFactory.getLogger(DashBoardResource.class);

    private final DashBoardService dashBoardService;
    private final TransFormer<HostingEntity, Hosting> hostingDtoMapper;

    @Inject
    public DashBoardResource(DashBoardService dashBoardService,
                             @Named("hostingMapper") TransFormer<HostingEntity, Hosting> hostingDtoMapper) {
        this.dashBoardService = dashBoardService;
        this.hostingDtoMapper = hostingDtoMapper;
    }

    @GET
    @Path("/{userId}")
    public Response getDashboard(@PathParam("userId") Long userId) {
        LOG.info("[CloudxHosterAPI] Fetching hostings for a user {}", userId);
        List<Hosting> hostings = this.hostingDtoMapper
                .toList(dashBoardService.getHostings(userId));
        LOG.info("[CloudxHosterAPI] Fetched hostings {} for user {}", hostings, userId);
        return Response.ok(hostings).build();
    }
}
