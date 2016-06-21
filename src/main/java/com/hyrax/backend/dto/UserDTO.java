package com.hyrax.backend.dto;

public class UserDTO implements Cloneable {

    private String userName;
    private String password;
    private String fullName;

    public static UserDTO newInstance() {
        return new UserDTO();
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
