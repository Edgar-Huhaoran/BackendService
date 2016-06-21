package com.hyrax.backend.service;

import com.hyrax.backend.credential.UserContext;
import com.hyrax.backend.credential.UserContextHolder;
import com.hyrax.backend.dao.UserDAO;
import com.hyrax.backend.dto.UserDTO;
import com.hyrax.backend.dto.UserDetailDTO;
import com.hyrax.backend.entity.User;
import com.hyrax.backend.exception.ErrorType;
import com.hyrax.backend.exception.HyraxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserDAO userDAO;
    private final UserTokenService userTokenService;
    private final PushService pushService;

    @Autowired
    public UserService(UserDAO userDAO,
                       UserTokenService userTokenService,
                       PushService pushService) {
        this.userDAO = userDAO;
        this.userTokenService = userTokenService;
        this.pushService = pushService;
    }

    /**
     * 用户注册
     * @param userDTO 用户信息
     */
    public void register(UserDTO userDTO) {
        log.info("register user with userName:{}, password:{}", userDTO.getUserName(), userDTO.getPassword());
        registerValidate(userDTO);

        User user = User.newInstance()
                .withId(UUID.randomUUID())
                .withUserName(userDTO.getUserName())
                .withPassword(userDTO.getPassword());
        userDAO.save(user);
    }

    /**
     * 用户登录
     * @param userDTO 登录的用户信息
     * @return 用户的Token
     */
    public String login(UserDTO userDTO) {
        log.info("user login with userName:{}, password:{}", userDTO.getUserName(), userDTO.getPassword());
        loginValidate(userDTO);

        User user = userDAO.get(userDTO.getUserName());
        if (user == null) {
            throw new HyraxException(ErrorType.USER_NAME_NOT_EXISTS);
        }
        if (!userDTO.getPassword().equals(user.getPassword())) {
            throw new HyraxException(ErrorType.PASSWORD_INCORRECT);
        }

        UserContextHolder.setContext(new UserContext(userDTO.getUserName()));

        return userTokenService.createUserToken(userDTO.getUserName());
    }

    /**
     * 登出当前用户
     */
    public void logout() {
        String userName = UserContextHolder.getUserName();
        pushService.clearPushId(userName);
        log.info("clear pushAndUpdate id for user {}", userName);
        log.info("user {} logout", userName);
    }

    /**
     * 获取当前用户的详细信息
     * @return
     */
    public UserDetailDTO getUserDetail() {
        String userName = UserContextHolder.getUserName();
        log.info("get user detail by user name {}", userName);

        User user = userDAO.get(userName);
        UserDetailDTO userDetailDTO = UserDetailDTO.fromUser(user);
        return userDetailDTO;
    }

    /**
     * 修改用户的数据
     * @param userDTO
     */
    public void modifyUserDetail(UserDTO userDTO) {
        modifyValidate(userDTO);
        String userName = UserContextHolder.getUserName();
        log.info("modify data for user {}", userName);

        User user = userDAO.get(userName);
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(userDTO.getPassword());
            log.info("set password {} for user {}", userDTO.getPassword(), userName);
        }
        if (userDTO.getFullName() != null && !userDTO.getFullName().isEmpty()) {
            user.setFullName(userDTO.getFullName());
            log.info("set full name {} for user {}", userDTO.getFullName(), userName);
        }
        userDAO.update(user);
    }

    /**
     * 修改用户数据时的数据验证
     * @param userDTO
     */
    private void modifyValidate(UserDTO userDTO) {
        if ((userDTO.getFullName() == null || userDTO.getFullName().isEmpty()) &&
                (userDTO.getPassword() == null || userDTO.getPassword().isEmpty())) {
            throw new HyraxException(ErrorType.MODIFY_DATA_EMPTY);
        }
    }

    /**
     * 用户注册时的参数验证
     * @param userDTO
     */
    private void registerValidate(UserDTO userDTO) {
        String userName = userDTO.getUserName();
        if (userName == null || userName.isEmpty()) {
            throw new HyraxException(ErrorType.USER_NAME_NULL);
        }
        if (userDAO.get(userName) != null) {
            throw new HyraxException(ErrorType.USER_NAME_EXISTS);
        }

        String password = userDTO.getPassword();
        if (password == null || password.isEmpty()) {
            throw new HyraxException(ErrorType.PASSWORD_NULL);
        }
    }

    /**
     * 用户登录时的参数验证
     * @param userDTO
     */
    private void loginValidate(UserDTO userDTO) {
        String userName = userDTO.getUserName();
        if (userName == null || userName.isEmpty()) {
            throw new HyraxException(ErrorType.USER_NAME_NULL);
        }

        String password = userDTO.getPassword();
        if (password == null || password.isEmpty()) {
            throw new HyraxException(ErrorType.PASSWORD_NULL);
        }
    }

}
