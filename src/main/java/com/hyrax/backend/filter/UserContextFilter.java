package com.hyrax.backend.filter;

import com.hyrax.backend.credential.UserContext;
import com.hyrax.backend.credential.UserContextHolder;
import com.hyrax.backend.exception.ErrorType;
import com.hyrax.backend.exception.HyraxException;
import com.hyrax.backend.service.UserTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;
import java.util.List;

@Component
@Priority(1001)
public class UserContextFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger log = LoggerFactory.getLogger(UserContextFilter.class);

    private static final String USER_TOKEN_HEADER = "X-Hyrax-UserToken";

    private final List<String> excludePath;
    private final String testApiPrefix;
    private final String testApiPostfix;
    private final UserTokenService userTokenService;

    @Autowired
    public UserContextFilter(@Value("#{'${context.exclude.paths}'.split(',')}") List<String> excludePath,
                             @Value("${testApi.prefix}") String testApiPrefix,
                             @Value("${testApi.postfix}") String testApiPostfix,
                             UserTokenService userTokenService) {
        this.excludePath = excludePath;
        this.testApiPrefix = testApiPrefix;
        this.testApiPostfix = testApiPostfix;
        this.userTokenService = userTokenService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (!needFilter(requestContext)) {
            log.info("skip request filter, url {} ", requestContext.getUriInfo().getAbsolutePath());
            return;
        }

        String userToken = requestContext.getHeaderString(USER_TOKEN_HEADER);
        if (userToken == null || userToken.isEmpty()) {
            throw new HyraxException(ErrorType.TOKEN_NOT_EXISTS);
        }

        String userName = userTokenService.parseUserToken(userToken);
        UserContext userContext = new UserContext(userName);
        UserContextHolder.setContext(userContext);
        log.info("filter request, url {}, userName: {} ", requestContext.getUriInfo().getAbsolutePath(),
                UserContextHolder.getUserName());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (!needFilter(requestContext)) {
            log.info("skip response filter, url {} ", requestContext.getUriInfo().getAbsolutePath());
            return;
        }
        log.info("filter response, url {}, userName: {} ",  requestContext.getUriInfo().getAbsolutePath(),
                UserContextHolder.getUserName());

        UserContextHolder.clearContext();
    }

    private boolean needFilter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();
        if (excludePath.contains(path)) {
            return false;
        }

        String parts[] = path.split("/");
        if (testApiPrefix.equals(parts[0]) || testApiPostfix.equals(parts[parts.length - 1])) {
            return false;
        }

        return true;
    }

}
