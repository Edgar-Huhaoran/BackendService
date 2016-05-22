package com.hyrax.backend.filter;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Component
@Priority(1000)
public class ApiAccessFilter implements ContainerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(ApiAccessFilter.class);

    private final boolean enableTestApi;
    private final String testApiPrefix;

    @Autowired
    public ApiAccessFilter(@Value("${testApi.enable}") boolean enableTestApi,
                           @Value("${testApi.prefix}") String testApiPrefix) {
        this.enableTestApi = enableTestApi;
        this.testApiPrefix = testApiPrefix;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (enableTestApi) {
            return;
        }

        String path = requestContext.getUriInfo().getPath();
        String[] parts = path.split("/");
        if (testApiPrefix.equals(parts[0])) {
            log.warn("testApi have been disable, request url:{}", path);
            requestContext.abortWith(Response.status(HttpStatus.NOT_FOUND_404).build());
        }
    }
}
