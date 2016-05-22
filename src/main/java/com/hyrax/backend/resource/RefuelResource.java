package com.hyrax.backend.resource;

import com.hyrax.backend.dto.RefuelDTO;
import com.hyrax.backend.entity.Refuel;
import com.hyrax.backend.service.RefuelService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Resource
@Path("refuel")
public class RefuelResource {

    private final RefuelService refuelService;

    @Autowired
    public RefuelResource(RefuelService refuelService) {
        this.refuelService = refuelService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response refuel(RefuelDTO refuelDTO) {
        UUID refuleId = refuelService.appointRefuel(refuelDTO);
        Map refuelMap = new HashMap<>();
        refuelMap.put("refuelId", refuleId.toString());
        return Response.ok(refuelMap).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRefuels() {
        List<Refuel> refuelList = refuelService.getRefuels();
        return Response.ok(refuelList).build();
    }
}
