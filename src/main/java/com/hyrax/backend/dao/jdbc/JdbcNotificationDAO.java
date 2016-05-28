package com.hyrax.backend.dao.jdbc;

import com.hyrax.backend.dao.NotificationDAO;
import com.hyrax.backend.entity.Notification;
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
public class JdbcNotificationDAO implements NotificationDAO {

    private final NamedParameterJdbcTemplate namedTemplate;

    @Autowired
    public JdbcNotificationDAO(NamedParameterJdbcTemplate namedTemplate) {
        this.namedTemplate = namedTemplate;
    }

    public int save(Notification notification) {
        String sql = "INSERT INTO notification(id, vehicle_id, user_name, type, description, is_readed, read_time, create_time) " +
                "VALUES (:id, :vehicle_id, :user_name, :type, :description, :is_readed, :read_time, :create_time)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", notification.getId())
                .addValue("vehicle_id", notification.getVehicleId())
                .addValue("user_name", notification.getUserName())
                .addValue("type", notification.getType().toString())
                .addValue("description", notification.getDescription())
                .addValue("is_readed", notification.isReaded())
                .addValue("read_time", notification.getReadTime())
                .addValue("create_time", notification.getCreateTime());

        return namedTemplate.update(sql, parameterSource);
    }

    public Notification get(UUID id) {
        String sql = "SELECT * FROM notification WHERE id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);

        List<Notification> notificationList = namedTemplate.query(sql, parameterSource, NOTIFICATION_ROW_MAPPER);
        if (notificationList.isEmpty()) {
            return null;
        }
        return notificationList.get(0);
    }

    public List<Notification> getByVehicleId(UUID vehicleId) {
        String sql = "SELECT * FROM notification WHERE vehicle_id = :vehicle_id ORDER BY create_time DESC";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("vehicle_id", vehicleId);

        List<Notification> notificationList = namedTemplate.query(sql, parameterSource, NOTIFICATION_ROW_MAPPER);
        return notificationList;
    }

    public List<Notification> getByUserName(String userName) {
        String sql = "SELECT * FROM notification WHERE user_name = :user_name ORDER BY create_time DESC";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("user_name", userName);

        List<Notification> notificationList = namedTemplate.query(sql, parameterSource, NOTIFICATION_ROW_MAPPER);
        return notificationList;
    }

    public int delete(UUID id) {
        String sql = "DELETE FROM notification WHERE id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);

        int rows = namedTemplate.update(sql, parameterSource);
        return rows;
    }

    private static final RowMapper<Notification> NOTIFICATION_ROW_MAPPER = new RowMapper<Notification>() {
        public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Notification.newInstance()
                    .withId(UUID.fromString(rs.getString("id")))
                    .withVehicleId(UUID.fromString(rs.getString("vehicle_id")))
                    .withUserName(rs.getString("user_name"))
                    .withType(Notification.Type.valueOf(rs.getString("type")))
                    .withDescription(rs.getString("description"))
                    .withIsReaded(rs.getBoolean("is_readed"))
                    .withReadTime(rs.getTimestamp("read_time"))
                    .withCreateTime(rs.getTimestamp("create_time"));
        }
    };

}
