package com.hyrax.backend.entity;

public enum NotificationType {

    FUEL_UNDER("油量低于20%"),
    MILEAGE_ACHIEVE("里程达到", "公里,需要维护"),
    ENGINE_ABNORMAL("发动机异常"),
    TRANSMISSION_ABNORMAL("变速器异常"),
    HEADLIGHT_ABNORMAL("车灯损坏");

    private final String[] messages;

    NotificationType(String... messages) {
        this.messages = messages;
    }

    public String[] getMessages() {
        return messages;
    }

}
