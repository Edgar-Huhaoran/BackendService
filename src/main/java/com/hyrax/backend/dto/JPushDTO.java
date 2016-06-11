package com.hyrax.backend.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JPushDTO {

    private String platform;
    private Audience audience;
    private Notification notification;

    public static class Audience {
        @JsonProperty("registration_id")
        private List<String> registrationId;

        public List<String> getRegistrationId() {
            return registrationId;
        }

        public void setRegistrationId(List<String> registrationId) {
            this.registrationId = registrationId;
        }
    }

    public static class Notification {
        private Android android;

        public static class Android {
            private String alert;
            private String title;
            @JsonProperty("builder_id")
            private int builderId;
            private Object extras;

            public String getAlert() {
                return alert;
            }

            public void setAlert(String alert) {
                this.alert = alert;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getBuilderId() {
                return builderId;
            }

            public void setBuilderId(int builderId) {
                this.builderId = builderId;
            }

            public Object getExtras() {
                return extras;
            }

            public void setExtras(Object extras) {
                this.extras = extras;
            }
        }

        public Android getAndroid() {
            return android;
        }

        public void setAndroid(Android android) {
            this.android = android;
        }
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
