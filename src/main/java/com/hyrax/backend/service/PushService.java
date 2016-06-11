package com.hyrax.backend.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyrax.backend.config.RestClientConfig.JPushClient;
import com.hyrax.backend.dao.UserDAO;
import com.hyrax.backend.dao.VehicleDAO;
import com.hyrax.backend.dto.JPushDTO;
import com.hyrax.backend.dto.NotificationDTO;
import com.hyrax.backend.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PushService {

    private static Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final UserDAO userDAO;
    private final VehicleDAO vehicleDAO;
    private final JPushClient jPushClient;

    private final String path;
    private final String base64AuthStr;

    @Autowired
    public PushService(UserDAO userDAO,
                       VehicleDAO vehicleDAO,
                       JPushClient jPushClient,
                       @Value("${jpush.path}") String path,
                       @Value("${jpush.appKey}") String appKey,
                       @Value("${jpush.masterSecret}") String masterSecret) {
        this.userDAO = userDAO;
        this.vehicleDAO = vehicleDAO;
        this.jPushClient = jPushClient;

        this.path = path;
        String authStr = appKey + ":" + masterSecret;
        base64AuthStr = "Basic " + Base64.getEncoder().encodeToString(authStr.getBytes());
    }

    public boolean push(NotificationDTO notificationDTO) {
        String pushId = getPushId(notificationDTO.getUserName());
        if (pushId == null || "".equals(pushId)) {
            return false;
        }

        byte[] body = generateBody(pushId, notificationDTO);
        Response response = jPushClient.getResource()
                .path(path)
                .request()
                .header("Authorization", base64AuthStr)
                .post(Entity.entity(body, MediaType.APPLICATION_JSON_TYPE));
        if (response.getStatus() != 200) {
            log.error("something wrong with connect jpush !!!  but I don't know how to fix it: {}", response);
        }

        return true;
    }

    /**
     * 生成HTTP请求的body
     * @param pushId
     * @param notificationDTO
     * @return
     */
    private byte[] generateBody(String pushId, NotificationDTO notificationDTO) {
        JPushDTO jPushDTO = generateJPushDTO(pushId, notificationDTO);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsBytes(jPushDTO);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("generate Json byte failed", e);
        }
    }

    /**
     * 生成封装body的JPustDTO对象
     * @param pushId
     * @param notificationDTO
     * @return
     */
    private JPushDTO generateJPushDTO(String pushId, NotificationDTO notificationDTO) {
        String vehicleNumber = vehicleDAO.get(notificationDTO.getVehicleId()).getNumber();
        String message = notificationDTO.getMessage();

        JPushDTO.Notification.Android android = new JPushDTO.Notification.Android();
        android.setAlert(message);
        android.setTitle(vehicleNumber);
        android.setBuilderId(1);
        android.setExtras(notificationDTO);

        JPushDTO.Notification notification = new JPushDTO.Notification();
        notification.setAndroid(android);

        List<String> pushIdList = new ArrayList<>();
        pushIdList.add(pushId);
        JPushDTO.Audience audience = new JPushDTO.Audience();
        audience.setRegistrationId(pushIdList);

        JPushDTO jPushDTO = new JPushDTO();
        jPushDTO.setPlatform("all");
        jPushDTO.setAudience(audience);
        jPushDTO.setNotification(notification);
        return jPushDTO;
    }



    // ====================================== 数据库操作 ====================================== //

    /**
     * 获取用户的推送ID
     * @param userName 用户名
     * @return
     */
    public String getPushId(String userName) {
        User user = userDAO.get(userName);
        if (user != null) {
            return user.getPushId();
        }
        return null;
    }

    /**
     * 设置用户的推送ID
     * @param userName 用户名
     * @param pushId 推送ID
     */
    public boolean setPushId(String userName, String pushId) {
        User user = userDAO.get(userName);
        if (user == null) {
            return false;
        }
        user.setPushId(pushId);
        user.setModifyTime(new Timestamp(System.currentTimeMillis()));
        userDAO.update(user);
        return true;
    }

    /**
     * 清除用户的推送ID
     * @param userName 用户名
     * @return
     */
    public boolean clearPushId(String userName) {
        User user = userDAO.get(userName);
        if (user == null) {
            return false;
        }
        user.setPushId(null);
        user.setModifyTime(new Timestamp(System.currentTimeMillis()));
        userDAO.update(user);
        return true;
    }
}
