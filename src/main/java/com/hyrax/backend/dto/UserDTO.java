package com.hyrax.backend.dto;

import java.util.UUID;

public class UserDTO implements Cloneable {

    private UUID id;
    private String userName;
    private String password;
    private String token;

    public static UserDTO newInstance() {
        return new UserDTO();
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDTO withId(UUID id) {
        this.id = id;
        return this;
    }

    public UserDTO withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserDTO withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserDTO withToken(String token) {
        this.token = token;
        return this;
    }

    public UserDTO build() {
        return this;
    }

    @Override
    public UserDTO clone() {
        try {
            return (UserDTO) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}
