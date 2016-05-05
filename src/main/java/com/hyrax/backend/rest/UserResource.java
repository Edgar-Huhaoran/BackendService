package com.hyrax.backend.rest;

import com.hyrax.backend.dto.UserDTO;
import com.hyrax.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Controller
@Path("user")
public class UserResource {

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Path("/hyrax")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHyraxStatus() {
        String hyraxStatus = "Hyrax with be the best! " + new Timestamp(System.currentTimeMillis()).toString();

        Map statusMap = new HashMap<>();
        statusMap.put("status", hyraxStatus);
        return Response.ok(statusMap).build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(UserDTO userDTO) {
        userService.register(userDTO);
        return Response.ok().build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserDTO userDTO) {
        String token = userService.login(userDTO);

        Map tokenMap = new HashMap<>();
        tokenMap.put("userToken", token);
        return Response.ok(tokenMap).build();
    }

}
