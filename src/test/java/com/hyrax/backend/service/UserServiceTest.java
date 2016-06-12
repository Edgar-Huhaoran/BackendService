package com.hyrax.backend.service;

import com.hyrax.backend.TestBase;
import com.hyrax.backend.credential.UserContext;
import com.hyrax.backend.credential.UserContextHolder;
import com.hyrax.backend.dao.UserDAO;
import com.hyrax.backend.dto.UserDTO;
import com.hyrax.backend.entity.User;
import com.hyrax.backend.exception.HyraxException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest extends TestBase {

    @Mock
    private UserDAO userDAO;
    @Mock
    private UserTokenService userTokenService;
    @Mock
    private PushService pushService;

    private String userName;
    private String password;

    private User user;
    private UserDTO userDTO;
    private UserService userService;

    @Before
    public void setup() {
        initMocks(this);

        userName = "userName";
        password = "password";

        user = new User();
        userDTO = new UserDTO();
        userService = new UserService(userDAO, userTokenService, pushService);

        user.setUserName(userName);
        user.setPassword(password);
        userDTO.setUserName(userName);
        userDTO.setPassword(password);
    }

    @Test
    public void throw_Hyrax_Exception_if_register_user_name_already_exists() {
        when(userDAO.get(userName)).thenReturn(user);

        HyraxException hyraxException = null;
        try {
            userService.register(userDTO);
        } catch (HyraxException e) {
            hyraxException = e;
        }

        assertNotNull("throw exception if register user name already exists", hyraxException);
    }

    @Test
    public void test_register_user() {
        userService.register(userDTO);
        verify(userDAO).save(any(User.class));
    }

    @Test
    public void throw_Hyrax_Exception_if_login_password_is_empty() {
        user.setPassword(null);

        HyraxException hyraxException = null;
        try {
            userService.login(userDTO);
        } catch (HyraxException e) {
            hyraxException = e;
        }

        assertNotNull("throw exception if login user password is empty", hyraxException);
    }

    @Test
    public void login_failed_if_password_is_incorrect() {
        user.setPassword("123456");
        when(userDAO.get(userName)).thenReturn(user);

        HyraxException hyraxException = null;
        try {
            userService.login(userDTO);
        } catch (HyraxException e) {
            hyraxException = e;
        }

        assertNotNull("throw exception if user password is incorrect", hyraxException);
    }

    @Test
    public void test_user_login() {
        when(userDAO.get(userName)).thenReturn(user);

        UserContextHolder.clearContext();
        userService.login(userDTO);

        assertEquals(userName, UserContextHolder.getUserName());
        verify(userTokenService).createUserToken(userName);
    }

    @Test
    public void test_user_logout() {
        UserContextHolder.setContext(new UserContext(userName));
        userService.logout();
        verify(pushService).clearPushId(userName);
    }

}
