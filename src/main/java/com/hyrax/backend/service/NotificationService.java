package com.hyrax.backend.service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.hyrax.backend.dao.NotificationDAO;
import com.hyrax.backend.entity.Notification;
import com.hyrax.backend.entity.Notification.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class NotificationService {

    private static Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationDAO notificationDAO;

    @Autowired
    public NotificationService(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    /**
     * 检查未读取的通知
     */
    public void check() {
        List<Notification> notifications = notificationDAO.getAll();
        Set<String> userNameSet = new HashSet<>();

        for (Notification notification : notifications) {
            userNameSet.add(notification.getUserName());
        }

        for (String userName : userNameSet) {
            List<Notification> notificationList = notificationDAO.getByUserName(userName);
            for (Notification notification : notificationList) {
                if (!notification.isReaded()) {
                    push(userName);
                    break;
                }
            }
        }
    }

    /**
     * 发送推送通知
     * @param userName 被推送的用户名
     */
    public void push(String userName) {
        log.info("push notification to : {}", userName);
        // TODO finish push logic

    }

    /**
     * 创建一个通知
     * @param vehicleId 车辆的ID
     * @param userName 车辆所属的用户名
     * @param type 通知类型
     */
    public void create(UUID vehicleId, String userName, Type type) {
        create(vehicleId, userName, type, null);
    }

    public void create(UUID vehicleId, String userName, Type type, String description) {
        Notification notification = Notification.newInstance()
                .withId(UUID.randomUUID())
                .withVehicleId(vehicleId)
                .withUserName(userName)
                .withType(type)
                .withDescription(description)
                .withIsReaded(false)
                .withCreateTime(new Timestamp(System.currentTimeMillis()));
        notificationDAO.save(notification);
    }

    /**
     * 检查通知是否已经存在
     * @param vehicleId 车辆的ID
     * @param type 通知的类型
     * @return 是否存在
     */
    public boolean isExist(UUID vehicleId, Type type) {
        return isExist(vehicleId, type, null);
    }

    public boolean isExist(UUID vehicleId, Type type, String description) {
        Assert.notNull(vehicleId);
        Assert.notNull(type);

        List<Notification> notificationList = notificationDAO.getByVehicleId(vehicleId);
        for (Notification notification : notificationList) {
            if (type.equals(notification.getType()) &&
                    (description == null || description.equals(notification.getDescription()))) {
                return true;
            }
        }

        return false;
    }

    /**
     * 删除一个通知
     * @param vehicleId 车辆的ID
     * @param type 通知的类型
     */
    public void delete(UUID vehicleId, Type type) {
        delete(vehicleId, type, null);
    }

    public void delete(UUID vehicleId, Type type, String description) {
        Assert.notNull(vehicleId);
        Assert.notNull(type);

        List<Notification> notificationList = notificationDAO.getByVehicleId(vehicleId);
        for (Notification notification : notificationList) {
            if (type.equals(notification.getType()) &&
                    (description == null || description.equals(notification.getDescription()))) {
                notificationDAO.delete(notification.getId());
            }
        }
    }

}
