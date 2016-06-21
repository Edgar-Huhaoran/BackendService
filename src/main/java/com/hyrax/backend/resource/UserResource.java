package com.hyrax.backend.resource;

import com.hyrax.backend.dto.UserDTO;
import com.hyrax.backend.dto.UserDetailDTO;
import com.hyrax.backend.entity.Vehicle;
import com.hyrax.backend.service.UserService;
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
import java.util.List;
import java.util.Map;

@Controller
@Path("user")
public class UserResource {

    private final UserService userService;
    private final VehicleService vehicleService;

    @Autowired
    public UserResource(UserService userService, VehicleService vehicleService) {
        this.userService = userService;
        this.vehicleService = vehicleService;
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
        Map resultMap = new HashMap<>();
        resultMap.put("userToken", token);
        return Response.ok(resultMap).build();
    }

    @POST
    @Path("/loginWithVehicle")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginWithVehicle(UserDTO userDTO) {
        String token = userService.login(userDTO);  // 如果这个可以正常执行说明登录成功了,也就是说这个用户是合法的用户了
        List<Vehicle> vehicleList = vehicleService.getVehicles();

        Map resultMap = new HashMap<>();
        resultMap.put("userToken", token);
        resultMap.put("vehicleList", vehicleList);
        return Response.ok(resultMap).build();
    }

    @POST
    @Path("/logout")
    public Response login() {
        userService.logout();
        return Response.ok().build();
    }

    @GET
    @Path("/detail")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserDetail() {
        UserDetailDTO userDetailDTO = userService.getUserDetail();
        return Response.ok(userDetailDTO).build();
    }

    @POST
    @Path("/detail")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifyUserDetail(UserDTO userDTO) {
        userService.modifyUserDetail(userDTO);
        return Response.ok().build();
    }

    @POST
    @Path("/icon")
    @Consumes({"image/png", "image/jpg"})
    public Response uploadUserIcon(byte[] iconBytes) {
        userService.setIcon(iconBytes);
        return Response.ok().build();
    }

    @GET
    @Path("/icon/{iconName}/noToken")
    @Produces("image/png")
    public Response getUserIcon(@PathParam("iconName") String iconName) {
        byte[] imageBytes = userService.getIcon(iconName);
        return Response.ok(imageBytes).build();
    }

}
