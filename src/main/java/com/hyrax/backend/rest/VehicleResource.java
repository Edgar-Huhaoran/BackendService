package com.hyrax.backend.rest;

import com.hyrax.backend.dto.VehicleDTO;
import com.hyrax.backend.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@Path("vehicle")
public class VehicleResource {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleResource(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVehicles() {
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createVehicle(VehicleDTO vehicleDTO) {
        UUID vehicleId = vehicleService.createVehicle(vehicleDTO);
        Map resultMap = new HashMap<>();
        resultMap.put("vehicleId", vehicleId.toString());
        return Response.ok(resultMap).build();
    }

    @GET
    @Path("/{vehicleId}/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVehicleById(@PathParam("vehicleId") UUID id) {
        return Response.ok().build();
    }

}
