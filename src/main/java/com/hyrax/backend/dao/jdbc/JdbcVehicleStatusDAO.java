package com.hyrax.backend.dao.jdbc;

import com.hyrax.backend.dao.VehicleStatusDAO;
import com.hyrax.backend.entity.VehicleStatus;
import com.hyrax.backend.entity.state.EngineState;
import com.hyrax.backend.entity.state.HeadlightState;
import com.hyrax.backend.entity.state.TransmissionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(JdbcVehicleStatusDAO.class);

    private final NamedParameterJdbcTemplate namedTemplate;

    @Autowired
    public JdbcVehicleStatusDAO(NamedParameterJdbcTemplate namedTemplate) {
        this.namedTemplate = namedTemplate;
    }

    public int save(VehicleStatus vehicleStatus) {
        String sql = "INSERT INTO vehicle_status(id, user_name, mileage, gasoline, engine_state, transmission_state, " +
                "headlight_state, create_time, modify_time, last_mileage, engine_oil, clean_fluid) " +
                "VALUES (:id, :user_name, :mileage, :gasoline, :engine_state, :transmission_state, " +
                ":headlight_state, :create_time, :modify_time, :last_mileage, :engine_oil, :clean_fluid)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", vehicleStatus.getId())
                .addValue("user_name", vehicleStatus.getUserName())
                .addValue("mileage", vehicleStatus.getMileage())
                .addValue("gasoline", vehicleStatus.getGasoline())
                .addValue("engine_state", vehicleStatus.getEngineState().toString())
                .addValue("transmission_state", vehicleStatus.getTransmissionState().toString())
                .addValue("headlight_state", vehicleStatus.getHeadlightState().toString())
                .addValue("create_time", vehicleStatus.getCreateTime())
                .addValue("modify_time", vehicleStatus.getModifyTime())
                .addValue("last_mileage", vehicleStatus.getLastMileage())
                .addValue("engine_oil", vehicleStatus.getEngineOil())
                .addValue("clean_fluid", vehicleStatus.getCleanFluid());

        return namedTemplate.update(sql, parameterSource);
    }

    public int update(VehicleStatus vehicleStatus) {
        String sql = "UPDATE vehicle_status SET user_name = :user_name , mileage = :mileage, gasoline = :gasoline, " +
                "engine_state = :engine_state, transmission_state = :transmission_state, headlight_state = :headlight_state, " +
                "create_time = :create_time, modify_time = :modify_time, last_mileage = :last_mileage, engine_oil = :engine_oil, " +
                "clean_fluid = :clean_fluid WHERE id = :id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", vehicleStatus.getId())
                .addValue("user_name", vehicleStatus.getUserName())
                .addValue("mileage", vehicleStatus.getMileage())
                .addValue("gasoline", vehicleStatus.getGasoline())
                .addValue("engine_state", vehicleStatus.getEngineState().toString())
                .addValue("transmission_state", vehicleStatus.getTransmissionState().toString())
                .addValue("headlight_state", vehicleStatus.getHeadlightState().toString())
                .addValue("create_time", vehicleStatus.getCreateTime())
                .addValue("modify_time", vehicleStatus.getModifyTime())
                .addValue("last_mileage", vehicleStatus.getLastMileage())
                .addValue("engine_oil", vehicleStatus.getEngineOil())
                .addValue("clean_fluid", vehicleStatus.getCleanFluid());

        return namedTemplate.update(sql, parameterSource);
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

    public List<VehicleStatus> getAll() {
        String sql = "SELECT * FROM vehicle_status";

        List<VehicleStatus> vehicleStatusList = namedTemplate.query(sql, VEHICLE_STATUS_ROW_MAPPER);
        return vehicleStatusList;
    }

    public int delete(UUID id) {
        String sql = "DELETE FROM vehicle_status WHERE id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);

        int rows = namedTemplate.update(sql, parameterSource);
        return rows;
    }

    private static final RowMapper<VehicleStatus> VEHICLE_STATUS_ROW_MAPPER = new RowMapper<VehicleStatus>() {
        public VehicleStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
            log.info("vehicleStatus id {} , headlightState {}", rs.getString("id"), rs.getString("headlight_state"));
            log.info("type {}, {}", HeadlightState.GREAT, HeadlightState.ABNORMAL);
            return VehicleStatus.newInstance()
                    .withId(UUID.fromString(rs.getString("id")))
                    .withUserName(rs.getString("user_name"))
                    .withMileage(rs.getFloat("mileage"))
                    .withGasoline(rs.getFloat("gasoline"))
                    .withEngineState(EngineState.valueOf(rs.getString("engine_state")))
                    .withTransmissionState(TransmissionState.valueOf(rs.getString("transmission_state")))
                    .withHeadlightState(HeadlightState.valueOf(rs.getString("headlight_state")))
                    .withCreateTime(rs.getTimestamp("create_time"))
                    .withModifyTime(rs.getTimestamp("modify_time"))
                    .withLastMileage(rs.getFloat("last_mileage"))
                    .withEngineOil(rs.getFloat("engine_oil"))
                    .withCleanFluid(rs.getFloat("clean_fluid"));
        }
    };

}
