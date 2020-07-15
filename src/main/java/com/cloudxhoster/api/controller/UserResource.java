package com.cloudxhoster.api.controller;

import com.cloudxhoster.api.domain.UserEntity;
import com.cloudxhoster.api.exception.GenericException;
import com.cloudxhoster.api.mapper.TransFormer;
import com.cloudxhoster.api.model.User;
import com.cloudxhoster.api.service.user.UserService;
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
@Path("/api/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final Logger LOG = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;
    private final TransFormer<UserEntity, User> userMapper;

    @Inject
    public UserResource(UserService userService,
                        @Named("userMapper") TransFormer<UserEntity, User> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GET
    @Path("/")
    public Response getAll() {
        LOG.info("[CloudxHosterAPI] Trying to fetch all users");
        List<User> users = this.userMapper.toList(this.userService.findAll());
        LOG.info("[CloudxHosterAPI] Fetched users : {}", users);
        return Response.ok(users).build();
    }

    /**
     * This is not finished yet
     *
     * @param firstName
     * @param lastName
     * @param email
     * @param countryId
     * @return Response
     * @throws GenericException
     */
    @POST
    @Path("/register")
    public Response signIn(@QueryParam("firstname") String firstName,
                           @QueryParam("lastname") String lastName,
                           @QueryParam("email") String email,
                           @QueryParam("country_id") Long countryId)
            throws GenericException {
        LOG.info("[CloudxHosterAPI] Registering user with email : {}", email);
        this.userService.save(email, firstName, lastName, countryId);
        LOG.info("[CloudxHosterAPI] User with email {} has been registered", email);
        return Response.ok(true).build();
    }
}
