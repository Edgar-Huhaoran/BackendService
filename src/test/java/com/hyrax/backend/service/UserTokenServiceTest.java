package com.hyrax.backend.service;

import com.hyrax.backend.TestBase;
import com.hyrax.backend.exception.HyraxException;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class UserTokenServiceTest extends TestBase {

    private String keyStr;
    private int seconds;
    private String userName;

    private UserTokenService userTokenService;

    @Before
    public void setup() {
        keyStr = "M2Y2YjkxZDEtYmNlOwIiIUfe9u2LJqReTljNG3kPy1jZTJIjeuhqo921h8ZGIwYWRlZTE0NGNh";
        seconds = 3600;
        userName = "userName";

        userTokenService = new UserTokenService(keyStr, seconds);
    }

    @Test
    public void test_create_user_token() {
        String token = userTokenService.createUserToken(userName);
        assertNotNull(token);
    }

    @Test
    public void throw_exception_while_parsed_token_is_invalid() {
        HyraxException hyraxException = null;
        try {
            userTokenService.parseUserToken("123.456test");
        } catch (HyraxException e) {
            hyraxException = e;
        }
        assertNotNull(hyraxException);
    }

    @Test
    public void throw_exception_while_parsed_token_expire() {
        userTokenService = new UserTokenService(keyStr, 0);
        String token = userTokenService.createUserToken(userName);

        HyraxException hyraxException = null;
        try {
            userTokenService.parseUserToken(token);
        } catch (HyraxException e) {
            hyraxException = e;
        }

        assertNotNull(hyraxException);
    }

    @Test
    public void throw_exception_while_token_user_name_is_empty() {
        String token = userTokenService.createUserToken(null);

        HyraxException hyraxException = null;
        try {
            userTokenService.parseUserToken(token);
        } catch (HyraxException e) {
            hyraxException = e;
        }

        assertNotNull(hyraxException);
    }

    @Test
    public void return_correct_user_name_while_parse_token_successfully() {
        String token = userTokenService.createUserToken(userName);
        String result = userTokenService.parseUserToken(token);
        assertEquals(userName, result);
    }

}
