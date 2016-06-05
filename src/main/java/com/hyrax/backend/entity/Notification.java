package com.hyrax.backend.entity;

import java.sql.Timestamp;
import java.util.UUID;

public class Notification {

    private UUID id;
    private UUID vehicleId;
    private String userName;
    private NotificationType type;
    private String description;
    private boolean isReaded;
    private Timestamp readTime;
    private Timestamp createTime;
    private String message;

    public static Notification newInstance() {
        return new Notification();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public boolean isReaded() {
        return isReaded;
    }

    public void setReaded(boolean readed) {
        isReaded = readed;
    }

    public Timestamp getReadTime() {
        return readTime;
    }

    public void setReadTime(Timestamp readTime) {
        this.readTime = readTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Notification withId(UUID id) {
        this.setId(id);
        return this;
    }

    public Notification withVehicleId(UUID id) {
        this.setVehicleId(id);
        return this;
    }

    public Notification withUserName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public Notification withType(NotificationType type) {
        this.setType(type);
        return this;
    }

    public Notification withDescription(String description) {
        this.setDescription(description);
        return this;
    }

    public Notification withIsReaded(boolean isReaded) {
        this.setReaded(isReaded);
        return this;
    }

    public Notification withReadTime(Timestamp readTime) {
        this.setReadTime(readTime);
        return this;
    }

    public Notification withCreateTime(Timestamp createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public Notification withMessage(String message) {
        this.setMessage(message);
        return this;
    }
}
