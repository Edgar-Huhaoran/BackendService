package com.hyrax.backend.dao;

import com.hyrax.backend.entity.User;

import java.util.UUID;

public interface UserDAO {

    int save(User user);

    User get(UUID id);

    User get(String userName);

}
