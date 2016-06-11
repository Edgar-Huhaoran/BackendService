package com.hyrax.backend.service;

import java.util.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class PushGenerator {

    private final String appKey = "8d38df46a12861afbed5e0ec";
    private final String masterSecret = "9b44ca7daa5a0ee602193c3a";

    @Test
    public void generateAuthorization() {
        String authStr = appKey + ":" + masterSecret;
        String base64AuthStr = Base64.getEncoder().encodeToString(authStr.getBytes());
        System.out.println("Authorization:" + base64AuthStr);
    }

}
