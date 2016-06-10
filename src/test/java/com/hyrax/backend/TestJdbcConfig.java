package com.hyrax.backend;

import javax.sql.DataSource;

import com.hyrax.backend.config.JdbcConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class TestJdbcConfig extends JdbcConfig {

    @Value("${database.url}")
    private String url;
    @Value("${database.user}")
    private String user;
    @Value("${database.pass}")
    private String pass;

    @Bean
    public static PropertyPlaceholderConfigurer propConfig() {
        PropertyPlaceholderConfigurer ppc =  new PropertyPlaceholderConfigurer();
        ppc.setLocation(new ClassPathResource("application-test.properties"));
        return ppc;
    }

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create().url(url).username(user).password(pass).build();
    }

}
