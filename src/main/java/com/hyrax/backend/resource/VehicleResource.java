package com.hyrax.backend.resource;

import com.hyrax.backend.dto.VehicleDTO;
import com.hyrax.backend.dto.VehicleStatusDTO;
import com.hyrax.backend.entity.Vehicle;
import com.hyrax.backend.entity.VehicleStatus;
import com.hyrax.backend.service.MarkService;
import com.hyrax.backend.service.VehicleService;
import com.hyrax.backend.service.VehicleStatusService;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@Path("vehicle")
public class VehicleResource {

    private final VehicleService vehicleService;
    private final VehicleStatusService vehicleStatusService;
    private final MarkService markService;

    @Autowired
    public VehicleResource(VehicleService vehicleService,
                           VehicleStatusService vehicleStatusService,
                           MarkService markService) {
        this.vehicleService = vehicleService;
        this.vehicleStatusService = vehicleStatusService;
        this.markService = markService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVehicles() {
        List<Vehicle> vehicleList = vehicleService.getVehicles();
        Map resultMap = new HashMap<>();
        resultMap.put("vehicleList", vehicleList);
        return Response.ok(resultMap).build();
    }

    @GET
    @Path("/{vehicleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVehicle(@PathParam("vehicleId") UUID id) {
        Vehicle vehicle = vehicleService.getVehicle(id);
        return Response.ok(vehicle).build();
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
    @Path("/delete/{vehicleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteVehicle(@PathParam("vehicleId") UUID id) {
        int rows = vehicleService.deleteVehicle(id);
        Map resultMap = new HashMap<>();
        resultMap.put("rows", String.valueOf(rows));
        return Response.ok(resultMap).build();
    }

    @GET
    @Path("/status/{vehicleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVehicleStatus(@PathParam("vehicleId") UUID id) {
        VehicleStatusDTO vehicleStatus = vehicleStatusService.getVehicleStatus(id);
        return Response.ok(vehicleStatus).build();
    }

    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVehicleStatusList() {
        List<VehicleStatusDTO> vehicleStatusDTOList = vehicleStatusService.getVehiclesStatus();
        Map resultMap = new HashMap<>();
        resultMap.put("vehicleStatusDTOList", vehicleStatusDTOList);
        return Response.ok(resultMap).build();
    }

    @GET
    @Path("/mark/{icon}/noToken")
    @Produces({"image/png", "image/jpg"})
    public Response getFullImage(@PathParam("icon") String icon) {
        byte[] imageBytes = markService.getMark(icon);
        return Response.ok(imageBytes).build();
    }



    // TODO: 仅用于测试的 API
    @POST
    @Path("/status/testApi")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setVehicleStatus(VehicleStatus vehicleStatus) {
        vehicleStatusService.update(vehicleStatus);
        Map resultMap = new HashMap<>();
        resultMap.put("vehicleId", vehicleStatus.getId().toString());
        return Response.ok(resultMap).build();
    }
}
