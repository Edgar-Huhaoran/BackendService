package com.edgarhu.hyrax.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({BackendConfig.class})
public class BootStrap extends SpringBootServletInitializer {

    private static final Logger log = LoggerFactory.getLogger(BootStrap.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BootStrap.class);
    }

    public static void main(String[] args) {
        log.info("Starting application!");
        new BootStrap()
                .configure(new SpringApplicationBuilder(BootStrap.class))
                .run(args);
    }

}
