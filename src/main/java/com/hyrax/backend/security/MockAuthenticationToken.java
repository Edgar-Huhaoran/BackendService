package com.hyrax.backend.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class MockAuthenticationToken extends AbstractAuthenticationToken {

    public MockAuthenticationToken() {
        super(null);
    }

    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return null;
    }
}
