package com.hyrax.backend.rest;

import com.hyrax.backend.exception.HyraxMapper;
import com.hyrax.backend.filter.UserTokenRequestFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {

        // Resource Register
        register(StatusResource.class);
        register(UserResource.class);

        // Filter Register
        register(UserTokenRequestFilter.class);

        // Mapper Register
        register(HyraxMapper.class);
    }
}
