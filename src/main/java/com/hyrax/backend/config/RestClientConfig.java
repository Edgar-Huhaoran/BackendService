package com.hyrax.backend.config;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.ClientProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestClientConfig {

    @Bean
    public JPushClient jPushClient(@Value("${jpush.address}") String address) {
        return new JPushClient(address);
    }


    private static abstract class RestClient {
        private final Client client;
        private final String hostAddress;

        protected RestClient(String hostAddress) {
            this.hostAddress = hostAddress;
            client = ClientBuilder.newClient();
            client.property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true);
        }

        public WebTarget getResource() {
            return client.target(hostAddress);
        }
    }

    public static class JPushClient extends RestClient {
        public JPushClient(String hostAddress) {
            super(hostAddress);
        }
    }
}
