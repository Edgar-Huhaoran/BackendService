package com.hyrax.backend.credential;

import org.springframework.util.Assert;

public class UserContextHolder {

    private static final ThreadLocal<UserContext> userContextLocal = new ThreadLocal<UserContext>() {
        @Override
        protected UserContext initialValue() {
            return new UserContext(null);
        }
    };

    public static void clearContext() {
        userContextLocal.remove();
    }

    public static UserContext getContext() {
        return userContextLocal.get();
    }

    public static void setContext(UserContext userContext) {
        Assert.notNull(userContext, "Only non-null SecurityContext instances are permitted");
        userContextLocal.set(userContext);
    }

    public static String getUserName() {
        return getContext().getUserName();
    }

}
