package com.hyrax.backend.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserTokenRequestFilter implements ContainerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(UserTokenRequestFilter.class);

    private static final String USER_TOKEN_HEADER = "X-Hyrax-UserToken";
    private static final String REGISTER_PATH = "user/register";
    private static final String LOGIN_PATH = "user/login";
    private static final String HYRAX_PATH = "status/hyrax";
    private static final List<String> excludePath = new ArrayList<String>();

    public UserTokenRequestFilter() {
        excludePath.add(REGISTER_PATH);
        excludePath.add(LOGIN_PATH);
        excludePath.add(HYRAX_PATH);
    }

    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (!isMatch(requestContext)) {
            log.info("skip user token filter, url:{} ", requestContext.getUriInfo().getAbsolutePath());
            return;
        }
        log.info("filter request by {}, request url:{} ", USER_TOKEN_HEADER, requestContext.getUriInfo().getAbsolutePath());
    }

    private boolean isMatch(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();
        if (excludePath.contains(path)) {
            return false;
        }
        return true;
    }

}
