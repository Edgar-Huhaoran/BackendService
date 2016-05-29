package com.hyrax.backend.service;

import com.hyrax.backend.credential.UserContext;
import com.hyrax.backend.credential.UserContextHolder;
import com.hyrax.backend.dao.UserDAO;
import com.hyrax.backend.dto.UserDTO;
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

    @Autowired
    public UserService(UserDAO userDAO, UserTokenService userTokenService) {
        this.userDAO = userDAO;
        this.userTokenService = userTokenService;
    }

    public void register(UserDTO userDTO) {
        log.info("register user with userName:{}, password:{}", userDTO.getUserName(), userDTO.getPassword());
        registerValidate(userDTO);

        User user = User.newInstance()
                .withId(UUID.randomUUID())
                .withUserName(userDTO.getUserName())
                .withPassword(userDTO.getPassword())
                .withCreateTime(new Timestamp(System.currentTimeMillis()))
                .withModifyTime(new Timestamp(System.currentTimeMillis()));
        userDAO.save(user);
    }

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
