package com.hyrax.backend.service;

import java.sql.Timestamp;

import com.hyrax.backend.dao.UserDAO;
import com.hyrax.backend.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushService {

    private static Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final UserDAO userDAO;

    @Autowired
    public PushService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * 获取用户的推送ID
     * @param userName 用户名
     * @return
     */
    public String getPushId(String userName) {
        User user = userDAO.get(userName);
        if (user != null) {
            return user.getPushId();
        }
        return null;
    }

    /**
     * 设置用户的推送ID
     * @param userName 用户名
     * @param pushId 推送ID
     */
    public boolean setPushId(String userName, String pushId) {
        User user = userDAO.get(userName);
        if (user == null) {
            return false;
        }
        user.setPushId(pushId);
        user.setModifyTime(new Timestamp(System.currentTimeMillis()));
        userDAO.update(user);
        return true;
    }

    /**
     * 清除用户的推送ID
     * @param userName 用户名
     * @return
     */
    public boolean clearPushId(String userName) {
        User user = userDAO.get(userName);
        if (user == null) {
            return false;
        }
        user.setPushId(null);
        user.setModifyTime(new Timestamp(System.currentTimeMillis()));
        userDAO.update(user);
        return true;
    }
}
