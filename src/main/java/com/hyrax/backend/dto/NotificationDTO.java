package com.hyrax.backend.dto;

import java.util.UUID;

import com.hyrax.backend.entity.Notification;
import com.hyrax.backend.entity.NotificationType;

public class NotificationDTO {

    private UUID vehicleId;
    private String userName;
    private NotificationType type;
    private String description;
    private String message;

    public static NotificationDTO fromNotification(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setVehicleId(notification.getId());
        notificationDTO.setUserName(notification.getUserName());
        notificationDTO.setType(notification.getType());
        notificationDTO.setDescription(notification.getDescription());
        notificationDTO.setMessage(notification.getMessage());
        return notificationDTO;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(UUID vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
