package com.hyrax.backend.dao.jdbc;

import com.hyrax.backend.dao.UserDAO;
import com.hyrax.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class JdbcUserDAO implements UserDAO {

    private final NamedParameterJdbcTemplate namedTemplate;

    @Autowired
    public JdbcUserDAO(NamedParameterJdbcTemplate namedTemplate) {
        this.namedTemplate = namedTemplate;
    }

    public int save(User user) {
        String sql = "INSERT INTO user_account(id, user_name, password, icon, create_time, modify_time) " +
                "VALUES (:id, :user_name, :password, :icon, :create_time, :modify_time)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("user_name", user.getUserName())
                .addValue("password", user.getPassword())
                .addValue("icon", user.getIcon())
                .addValue("create_time", user.getCreateTime())
                .addValue("modify_time", user.getModifyTime());

        return namedTemplate.update(sql, parameterSource);
    }

    public User get(UUID id) {
        String sql = "SELECT * FROM user_account WHERE id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);

        List<User> userList = namedTemplate.query(sql, parameterSource, USER_ROW_MAPPER);
        if (userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }

    public User get(String userName) {
        String sql = "SELECT * FROM user_account WHERE user_name = :user_name";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("user_name", userName);

        List<User> userList = namedTemplate.query(sql, parameterSource, USER_ROW_MAPPER);
        if (userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }

    private static final RowMapper<User> USER_ROW_MAPPER = new RowMapper<User>() {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.newInstance()
                    .withId(UUID.fromString(rs.getString("id")))
                    .withUserName(rs.getString("user_name"))
                    .withPassword(rs.getString("password"))
                    .withIcon(rs.getString("icon"))
                    .withCreateTime(rs.getTimestamp("create_time"))
                    .withModifyTime(rs.getTimestamp("modify_time"));
        }
    };

}
