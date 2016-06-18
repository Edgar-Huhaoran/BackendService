package com.hyrax.backend.dao.jdbc;

import com.hyrax.backend.dao.RefuelDAO;
import com.hyrax.backend.entity.AmountType;
import com.hyrax.backend.entity.Refuel;
import com.hyrax.backend.entity.state.RefuelState;
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
public class JdbcRefuelDAO implements RefuelDAO {

    private final NamedParameterJdbcTemplate namedTemplate;

    @Autowired
    public JdbcRefuelDAO(NamedParameterJdbcTemplate namedTemplate) {
        this.namedTemplate = namedTemplate;
    }

    public int save(Refuel refuel) {
        String sql = "INSERT INTO refuel(id, user_name, vehicle_number, fuel_type, price, amount, amount_type, " +
                "station_id, station_name, state, appoint_time, create_time, modify_time) " +
                "VALUES (:id, :user_name, :vehicle_number, :fuel_type, :price, :amount, :amount_type, " +
                ":station_id, :station_name, :state, :appoint_time, :create_time, :modify_time)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", refuel.getId())
                .addValue("user_name", refuel.getUserName())
                .addValue("vehicle_number", refuel.getVehicleNumber())
                .addValue("fuel_type", refuel.getFuelType())
                .addValue("price", refuel.getPrice())
                .addValue("amount", refuel.getAmount())
                .addValue("amount_type", refuel.getAmountType().toString())
                .addValue("station_id", refuel.getStationId())
                .addValue("station_name", refuel.getStationName())
                .addValue("state", refuel.getState().toString())
                .addValue("appoint_time", refuel.getAppointTime())
                .addValue("create_time", refuel.getCreateTime())
                .addValue("modify_time", refuel.getModifyTime());

        return namedTemplate.update(sql, parameterSource);
    }

    public int update(Refuel refuel) {
        String sql = "UPDATE refuel SET user_name = :user_name, vehicle_number = :vehicle_number, fuel_type = :fuel_type, " +
                "price = :price, amount = :amount, amount_type = :amount_type, station_id = :station_id, station_name = :station_name, " +
                "state = :state, appoint_time = :appoint_time, modify_time = :modify_time WHERE id = :id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", refuel.getId())
                .addValue("user_name", refuel.getUserName())
                .addValue("vehicle_number", refuel.getVehicleNumber())
                .addValue("fuel_type", refuel.getFuelType())
                .addValue("price", refuel.getPrice())
                .addValue("amount", refuel.getAmount())
                .addValue("amount_type", refuel.getAmountType().toString())
                .addValue("station_id", refuel.getStationId())
                .addValue("station_name", refuel.getStationName())
                .addValue("state", refuel.getState().toString())
                .addValue("appoint_time", refuel.getAppointTime())
                .addValue("modify_time", refuel.getModifyTime());

        return namedTemplate.update(sql, parameterSource);
    }

    public Refuel get(UUID id) {
        String sql = "SELECT * FROM refuel WHERE id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);

        List<Refuel> refuelList = namedTemplate.query(sql, parameterSource, REFUEL_ROW_MAPPER);
        if (refuelList.isEmpty()) {
            return null;
        }
        return refuelList.get(0);
    }

    public List<Refuel> getByUserName(String userName) {
        String sql = "SELECT * FROM refuel WHERE user_name = :user_name ORDER BY create_time DESC";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("user_name", userName);

        List<Refuel> refuelList = namedTemplate.query(sql, parameterSource, REFUEL_ROW_MAPPER);
        return refuelList;
    }

    private static final RowMapper<Refuel> REFUEL_ROW_MAPPER = new RowMapper<Refuel>() {
        public Refuel mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Refuel.newInstance()
                    .withId(UUID.fromString(rs.getString("id")))
                    .withUserName(rs.getString("user_name"))
                    .withVehicleNumber(rs.getString("vehicle_number"))
                    .withFuelType(rs.getString("fuel_type"))
                    .withPrice(rs.getDouble("price"))
                    .withAmount(rs.getDouble("amount"))
                    .withAmountType(AmountType.valueOf(rs.getString("amount_type")))
                    .withStationId(rs.getString("station_id"))
                    .withStationName(rs.getString("station_name"))
                    .withState(RefuelState.valueOf(rs.getString("state")))
                    .withAppointTime(rs.getTimestamp("appoint_time"))
                    .withCreateTime(rs.getTimestamp("create_time"))
                    .withModifyTime(rs.getTimestamp("modify_time"));
        }
    };

}
