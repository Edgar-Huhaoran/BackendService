package com.hyrax.backend.service;

import java.sql.Timestamp;
import java.util.List;
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

    public void push(String userName) {
        log.info("push notification to : {}", userName);
        // TODO finish push logic

    }

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
