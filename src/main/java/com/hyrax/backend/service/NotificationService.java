package com.hyrax.backend.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.hyrax.backend.credential.UserContextHolder;
import com.hyrax.backend.dao.NotificationDAO;
import com.hyrax.backend.dto.NotificationDTO;
import com.hyrax.backend.entity.Notification;
import com.hyrax.backend.entity.NotificationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class NotificationService {

    private static Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationDAO notificationDAO;
    private final PushService pushService;

    @Autowired
    public NotificationService(NotificationDAO notificationDAO,
                               PushService pushService) {
        this.notificationDAO = notificationDAO;
        this.pushService = pushService;
    }

    /**
     * 为当前用户注册推送用的ID
     * @param pushId
     */
    public void registerPushId(String pushId) {
        if (pushId == null || "".equals(pushId)) {
            log.info("skip pushAndUpdate id register, cause of pushAndUpdate id is empty");
            return;
        }

        String userName = UserContextHolder.getUserName();
        pushService.setPushId(userName, pushId);
        log.info("register push id {} for user {}", pushId, userName);

        List<Notification> notificationList = notificationDAO.getByUserName(userName);
        for (Notification notification : notificationList) {
            if (!notification.isReaded()) {
                pushAndUpdate(notification);
            }
        }
    }

    /**
     * 推送消息
     * @param notification
     */
    public void pushAndUpdate(Notification notification) {
        NotificationDTO notificationDTO = NotificationDTO.fromNotification(notification);

        boolean isSucceed = pushService.push(notificationDTO);
        if (!isSucceed) {
            log.info("push notification {} to user {} failed", notificationDTO.getMessage(),
                     notificationDTO.getUserName());
            return;
        }

        log.info("push notification {} to user {} success", notificationDTO.getMessage(),
                 notificationDTO.getUserName());
        notification.setReaded(true);
        notification.setReadTime(new Timestamp(System.currentTimeMillis()));
        notificationDAO.update(notification);
    }


    // ====================================== 数据库操作 ====================================== //

    /**
     * 创建一个通知
     * @param vehicleId 车辆的ID
     * @param userName 车辆所属的用户名
     * @param type 通知类型
     */
    public Notification create(UUID vehicleId, String userName, NotificationType type, String message) {
        return create(vehicleId, userName, type, null, message);
    }

    public Notification create(UUID vehicleId, String userName, NotificationType type, int description, String message) {
        return create(vehicleId, userName, type, String.valueOf(description), message);
    }

    public Notification create(UUID vehicleId, String userName, NotificationType type, String description, String message) {
        Notification notification = Notification.newInstance()
                .withId(UUID.randomUUID())
                .withVehicleId(vehicleId)
                .withUserName(userName)
                .withType(type)
                .withDescription(description)
                .withIsReaded(false)
                .withCreateTime(new Timestamp(System.currentTimeMillis()))
                .withMessage(message);
        notificationDAO.save(notification);
        return notification;
    }

    /**
     * 检查通知是否已经存在
     * @param vehicleId 车辆的ID
     * @param type 通知的类型
     * @return 是否存在
     */
    public boolean isExist(UUID vehicleId, NotificationType type) {
        return isExist(vehicleId, type, null);
    }

    public boolean isExist(UUID vehicleId, NotificationType type, int description) {
        return isExist(vehicleId, type, String.valueOf(description));
    }

    public boolean isExist(UUID vehicleId, NotificationType type, String description) {
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
     * 获取所有满足条件的通知
     * @param vehicleId 车辆的ID
     * @param type 通知类型
     * @return
     */
    public List<Notification> get(UUID vehicleId, NotificationType type) {
        Assert.notNull(vehicleId);
        Assert.notNull(type);

        List<Notification> resultList = new ArrayList<>();
        List<Notification> notificationList = notificationDAO.getByVehicleId(vehicleId);
        for (Notification notification : notificationList) {
            if (type.equals(notification.getType())) {
                resultList.add(notification);
            }
        }

        return resultList;
    }


    /**
     * 删除一个通知
     * @param vehicleId 车辆的ID
     * @param type 通知的类型
     */
    public void delete(UUID vehicleId, NotificationType type) {
        delete(vehicleId, type, null);
    }

    public void delete(UUID vehicleId, NotificationType type, int description) {
        delete(vehicleId, type, String.valueOf(description));
    }

    public void delete(UUID vehicleId, NotificationType type, String description) {
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
