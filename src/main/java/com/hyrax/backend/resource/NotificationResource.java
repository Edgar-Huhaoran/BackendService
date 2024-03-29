package com.hyrax.backend.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hyrax.backend.dto.NotificationDTO;
import com.hyrax.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;

@Resource
@Path("notification")
public class NotificationResource {

    private final NotificationService notificationService;

    @Autowired
    public NotificationResource(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @POST
    @Path("/pushId")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerPushId(Map<String, String> map) {
        String pushId = map.get("pushId");
        notificationService.registerPushId(pushId);
        return Response.ok().build();
    }

}
