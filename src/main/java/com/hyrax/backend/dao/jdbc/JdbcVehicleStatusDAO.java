package com.hyrax.backend.dao.jdbc;

import com.hyrax.backend.dao.VehicleStatusDAO;
import com.hyrax.backend.entity.VehicleStatus;
import com.hyrax.backend.entity.state.EngineState;
import com.hyrax.backend.entity.state.HeadlightState;
import com.hyrax.backend.entity.state.TransmissionState;
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
public class JdbcVehicleStatusDAO implements VehicleStatusDAO {

    private final NamedParameterJdbcTemplate namedTemplate;

    @Autowired
    public JdbcVehicleStatusDAO(NamedParameterJdbcTemplate namedTemplate) {
        this.namedTemplate = namedTemplate;
    }

    public VehicleStatus get(UUID id) {
        String sql = "SELECT * FROM vehicle_status WHERE id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);

        List<VehicleStatus> vehicleStatusList = namedTemplate.query(sql, parameterSource, VEHICLE_STATUS_ROW_MAPPER);
        if (vehicleStatusList.isEmpty()) {
            return null;
        }
        return vehicleStatusList.get(0);
    }

    public List<VehicleStatus> getByUserName(String userName) {
        String sql = "SELECT * FROM vehicle_status WHERE user_name = :user_name";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("user_name", userName);

        List<VehicleStatus> vehicleStatusList = namedTemplate.query(sql, parameterSource, VEHICLE_STATUS_ROW_MAPPER);
        return vehicleStatusList;
    }


    private static final RowMapper<VehicleStatus> VEHICLE_STATUS_ROW_MAPPER = new RowMapper<VehicleStatus>() {
        public VehicleStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
            return VehicleStatus.newInstance()
                    .withId(UUID.fromString(rs.getString("id")))
                    .withUserName(rs.getString("user_name"))
                    .withMileage(Float.valueOf(rs.getString("mileage")))
                    .withGasoline(Float.valueOf(rs.getString("gasoline")))
                    .withEngineState(EngineState.valueOf(rs.getString("engine_state")))
                    .withTransmissionState(TransmissionState.valueOf(rs.getString("transmission_state")))
                    .withHeadlightState(HeadlightState.valueOf(rs.getString("headlight_state")))
                    .withCreateTime(rs.getTimestamp("create_time"))
                    .withModifyTime(rs.getTimestamp("modify_time"));
        }
    };

}
