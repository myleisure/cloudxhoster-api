package com.cloudxhoster.api.controller;


import com.cloudxhoster.api.domain.CountryEntity;
import com.cloudxhoster.api.service.country.CountryService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/api/v1/countries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CountryResource {

    private final CountryService countryService;

    @Inject
    public CountryResource(CountryService countryService) {
        this.countryService = countryService;
    }

    @GET
    public Response getCountries() {
        List<CountryEntity> prod = countryService.getCountries();
        return Response.ok(prod).build();
    }
}