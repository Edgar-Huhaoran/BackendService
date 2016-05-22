package com.hyrax.backend.config;

import com.hyrax.backend.exception.HyraxMapper;
import com.hyrax.backend.filter.UserContextFilter;
import com.hyrax.backend.resource.RefuelResource;
import com.hyrax.backend.resource.StatusResource;
import com.hyrax.backend.resource.UserResource;
import com.hyrax.backend.resource.VehicleResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {

        // Resource Register
        register(StatusResource.class);
        register(UserResource.class);
        register(VehicleResource.class);
        register(RefuelResource.class);

        // Filter Register
        register(UserContextFilter.class);

        // Mapper Register
        register(HyraxMapper.class);
    }
}
