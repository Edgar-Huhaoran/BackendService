package com.hyrax.backend.rest;

import com.hyrax.backend.exception.HyraxMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {

        // Resource Register
        register(StatusResource.class);
        register(UserResource.class);

        // Mapper Register
        register(HyraxMapper.class);
    }
}
