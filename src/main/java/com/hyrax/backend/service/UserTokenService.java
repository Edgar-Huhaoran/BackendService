package com.hyrax.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyrax.backend.exception.ErrorType;
import com.hyrax.backend.exception.HyraxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Base64;

@Service
public class UserTokenService {

    private final String HMAC_ALGO = "HmacSHA256";
    private final String SEPARATOR = ".";
    private final byte[] secretBytes;
    private final int seconds;

    private final Mac hmac;

    @Autowired
    public UserTokenService(@Value("${token.key}") String keyString,
                            @Value("${token.seconds}") int seconds) {
        try {
            this.seconds = seconds;
            this.secretBytes = keyString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("argument is illegal" + e);
        }

        try {
            hmac = Mac.getInstance(HMAC_ALGO);
            hmac.init(new SecretKeySpec(secretBytes, HMAC_ALGO));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException("failed to initialize HMAC: " + e.getMessage(), e);
        }
    }

    public String createUserToken(String userName) {
        Timestamp validTime = new Timestamp(System.currentTimeMillis() + seconds * 1000);
        TokenUser tokenUser = TokenUser.newInstance().withUserName(userName).withValidTime(validTime);

        byte[] tokenBody = toJson(tokenUser);
        byte[] tokenHmac = createHmac(tokenBody);

        return new StringBuilder()
                .append(toBase64(tokenBody))
                .append(SEPARATOR)
                .append(toBase64(tokenHmac))
                .toString();
    }

    public String parseUserToken(String token) {
        String[] parts = token.split(SEPARATOR);
        if (parts.length == 2 && parts[0].length() > 0 && parts[1].length() > 0) {
            byte[] tokenBody = fromBase64(parts[0]);
            byte[] tokenHmac = fromBase64(parts[1]);

            boolean isValidHmac = Arrays.equals(createHmac(tokenBody), tokenHmac);
            if (isValidHmac) {
                TokenUser tokenUser = fromJson(tokenBody);
                boolean isValidTime = new Timestamp(System.currentTimeMillis()).after(tokenUser.getValidTime());
                if (isValidTime) {
                    return tokenUser.getUserName();
                }
                throw new HyraxException(ErrorType.TOKEN_EXPIRED);
            }
        }
        throw new HyraxException(ErrorType.TOKEN_INVALID);
    }

    private byte[] toJson(TokenUser tokenUser) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsBytes(tokenUser);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("generate Json byte failed", e);
        }
    }

    private TokenUser fromJson(byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(bytes, TokenUser.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("generate TokenUser failed", e);
        }
    }

    private synchronized byte[] createHmac(byte[] content) {
        return hmac.doFinal(content);
    }

    private String toBase64(byte[] content) {
        return Base64.getEncoder().encodeToString(content);
    }

    private byte[] fromBase64(String encoded) {
        return Base64.getDecoder().decode(encoded);
    }

    // inner class for generate user token
    private static class TokenUser {
        private String userName;
        private Timestamp validTime;

        public static TokenUser newInstance() {
            return new TokenUser();
        }

        public Timestamp getValidTime() {
            return validTime;
        }

        public void setValidTime(Timestamp validTime) {
            this.validTime = validTime;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public TokenUser withUserName(String userName) {
            this.setUserName(userName);
            return this;
        }

        public TokenUser withValidTime(Timestamp validTime) {
            this.setValidTime(validTime);
            return this;
        }
    }

}
