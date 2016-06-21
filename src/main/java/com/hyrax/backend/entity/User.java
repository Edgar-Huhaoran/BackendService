package com.hyrax.backend.entity;

import java.sql.Timestamp;
import java.util.UUID;

public class User {

    private UUID id;
    private String userName;
    private String password;
    private String icon;
    private Timestamp createTime;
    private Timestamp modifyTime;
    private String pushId;
    private String fullName;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public static User newInstance() {
        return new User();
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public User withId(UUID id) {
        this.id = id;
        return this;
    }

    public User withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public User withPassword(String password) {
        this.password = password;
        return this;
    }

    public User withIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public User withCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    public User withModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public User withPushId(String pushId) {
        this.pushId = pushId;
        return this;
    }

    public User withFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

}
