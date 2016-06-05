package com.hyrax.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyrax.backend.credential.UserToken;
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
import java.util.regex.Pattern;

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

    /**
     * 创建一个用户登录的Token
     * @param userName 用户名
     * @return 被创建的Token
     */
    public String createUserToken(String userName) {
        Timestamp validTime = new Timestamp(System.currentTimeMillis() + seconds * 1000);
        UserToken tokenUser = UserToken.newInstance().withUserName(userName).withValidTime(validTime);

        byte[] tokenBody = toJson(tokenUser);
        byte[] tokenHmac = createHmac(tokenBody);

        return new StringBuilder()
                .append(toBase64(tokenBody))
                .append(SEPARATOR)
                .append(toBase64(tokenHmac))
                .toString();
    }

    /**
     * Token解析
     * @param token Token
     * @return 被解析出来的用户名
     */
    public String parseUserToken(String token) {
        String[] parts = token.split(Pattern.quote(SEPARATOR));
        if (parts.length == 2 && parts[0].length() > 0 && parts[1].length() > 0) {
            byte[] tokenBody = fromBase64(parts[0]);
            byte[] tokenHmac = fromBase64(parts[1]);

            boolean isValidHmac = Arrays.equals(createHmac(tokenBody), tokenHmac);
            if (isValidHmac) {
                UserToken userToken = fromJson(tokenBody);
                boolean isValidTime = new Timestamp(System.currentTimeMillis()).before(userToken.getValidTime());
                if (isValidTime) {
                    String userName = userToken.getUserName();
                    if (userName == null) {
                        throw new HyraxException(ErrorType.TOKEN_INVALID);
                    }
                    return userName;
                }
                throw new HyraxException(ErrorType.TOKEN_EXPIRED);
            }
        }
        throw new HyraxException(ErrorType.TOKEN_INVALID);
    }

    /**
     * 把用户的Token转化成字节数组
     * @param tokenUser
     * @return
     */
    private byte[] toJson(UserToken tokenUser) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsBytes(tokenUser);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("generate Json byte failed", e);
        }
    }

    /**
     * 从字节数组中转化出用户的Token
     * @param bytes
     * @return
     */
    private UserToken fromJson(byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(bytes, UserToken.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("generate UserToken failed", e);
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

}
