package com.hyrax.backend.dto;

import com.hyrax.backend.entity.User;

public class UserDetailDTO {

    private String userName;
    private String fullName;
    private String icon;

    public static UserDetailDTO fromUser(User user) {
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setUserName(user.getUserName());
        userDetailDTO.setFullName(user.getFullName());
        userDetailDTO.setIcon(user.getIcon());
        return userDetailDTO;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
