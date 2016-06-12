package com.hyrax.backend.entity;

public enum NotificationType {

    FUEL_UNDER("<font color=\"#ff0000\">油量低于20%</font>"),
    MILEAGE_ACHIEVE("<font color=\"#ffff00\">里程达到", "公里,需要维护</font>"),
    ENGINE_ABNORMAL("<font color=\"#ff0000\">发动机异常</font>"),
    TRANSMISSION_ABNORMAL("<font color=\"#ff0000\">变速器异常</font>"),
    HEADLIGHT_ABNORMAL("<font color=\"#ff0000\">车灯损坏</font>");

    private final String[] messages;

    NotificationType(String... messages) {
        this.messages = messages;
    }

    public String[] getMessages() {
        return messages;
    }

}
