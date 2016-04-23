package com.hyrax.backend.rest;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Resource
@Path("status")
public class StatusResource {

    @GET
    @Path("hyrax")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHyraxStatus() {
        String hyraxStatus = "Hyrax with be the best!";
        return Response.ok(hyraxStatus).build();
    }
}
