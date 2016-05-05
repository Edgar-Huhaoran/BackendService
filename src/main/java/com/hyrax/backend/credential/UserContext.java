package com.hyrax.backend.credential;

public class UserContext {

    private final String userName;

    public UserContext() {
        this.userName = null;
    }

    public UserContext(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
