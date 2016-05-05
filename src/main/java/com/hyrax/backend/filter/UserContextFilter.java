package com.hyrax.backend.filter;

import com.hyrax.backend.credential.UserContext;
import com.hyrax.backend.credential.UserContextHolder;
import com.hyrax.backend.exception.ErrorType;
import com.hyrax.backend.exception.HyraxException;
import com.hyrax.backend.service.UserTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserContextFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger log = LoggerFactory.getLogger(UserContextFilter.class);

    private static final String USER_TOKEN_HEADER = "X-Hyrax-UserToken";
    private static final String REGISTER_PATH = "user/register";
    private static final String LOGIN_PATH = "user/login";
    private static final String HYRAX_PATH = "status/hyrax";
    private static final List<String> excludePath = new ArrayList<String>();

    private final UserTokenService userTokenService;

    @Autowired
    public UserContextFilter(UserTokenService userTokenService) {
        excludePath.add(REGISTER_PATH);
        excludePath.add(LOGIN_PATH);
        excludePath.add(HYRAX_PATH);

        this.userTokenService = userTokenService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (!isMatch(requestContext)) {
            log.info("skip request filter, url {} ", requestContext.getUriInfo().getAbsolutePath());
            return;
        }
        log.info("filter request, url {} ", requestContext.getUriInfo().getAbsolutePath());

        String userToken = requestContext.getHeaderString(USER_TOKEN_HEADER);
        if (userToken == null || userToken.isEmpty()) {
            throw new HyraxException(ErrorType.TOKEN_NOT_EXISTS);
        }

        String userName = userTokenService.parseUserToken(userToken);
        UserContext userContext = new UserContext(userName);
        UserContextHolder.setContext(userContext);
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (!isMatch(requestContext)) {
            log.info("skip response filter, url {} ", requestContext.getUriInfo().getAbsolutePath());
            return;
        }
        log.info("filter response, url {} ",  requestContext.getUriInfo().getAbsolutePath());

        UserContextHolder.clearContext();
    }

    private boolean isMatch(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();
        if (excludePath.contains(path)) {
            return false;
        }
        return true;
    }

}
