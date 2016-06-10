package com.hyrax.backend;

import com.hyrax.backend.service.MarkService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestServiceConfig {

    @Bean
    public MarkService markService() {
        return new MarkService();
    }

}
