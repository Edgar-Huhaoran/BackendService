package com.hyrax.backend.credential;

import java.sql.Timestamp;

public class UserToken {

    private String userName;
    private Timestamp validTime;

    public static UserToken newInstance() {
        return new UserToken();
    }

    public Timestamp getValidTime() {
        return validTime;
    }

    public void setValidTime(Timestamp validTime) {
        this.validTime = validTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserToken withUserName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public UserToken withValidTime(Timestamp validTime) {
        this.setValidTime(validTime);
        return this;
    }

}
