package com.hyrax.backend.rest;

import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Controller
@Path("status")
public class StatusResource {

    @GET
    @Path("/hyrax")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHyraxStatus() {
        String hyraxStatus = "Hyrax with be the best! " + new Timestamp(System.currentTimeMillis()).toString();

        Map statusMap = new HashMap<>();
        statusMap.put("status", hyraxStatus);
        return Response.ok(statusMap).build();
    }

}
