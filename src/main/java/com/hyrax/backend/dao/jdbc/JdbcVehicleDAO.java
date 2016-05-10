package com.hyrax.backend.dao.jdbc;

import com.hyrax.backend.dao.VehicleDAO;
import com.hyrax.backend.entity.Vehicle;
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
public class JdbcVehicleDAO implements VehicleDAO {

    private final NamedParameterJdbcTemplate namedTemplate;

    @Autowired
    public JdbcVehicleDAO(NamedParameterJdbcTemplate namedTemplate) {
        this.namedTemplate = namedTemplate;
    }

    public int save(Vehicle vehicle) {
        String sql = "INSERT INTO vehicle(id, user_name, brand, mark, model, number, engine, door_num, seat_num, create_time, modify_time) " +
                "VALUES (:id, :user_name, :brand, :mark, :model, :number, :engine, :door_num, :seat_num, :create_time, :modify_time)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", vehicle.getId())
                .addValue("user_name", vehicle.getUserName())
                .addValue("brand", vehicle.getBrand())
                .addValue("mark", vehicle.getMark())
                .addValue("model", vehicle.getModel())
                .addValue("number", vehicle.getNumber())
                .addValue("engine", vehicle.getEngine())
                .addValue("door_num", vehicle.getDoorNum())
                .addValue("seat_num", vehicle.getSeatNum())
                .addValue("create_time", vehicle.getCreateTime())
                .addValue("modify_time", vehicle.getModifyTime());

        return namedTemplate.update(sql, parameterSource);
    }

    public Vehicle get(UUID id) {
        String sql = "SELECT * FROM vehicle WHERE id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);

        List<Vehicle> vehicleList = namedTemplate.query(sql, parameterSource, VEHICLE_ROW_MAPPER);
        if (vehicleList.isEmpty()) {
            return null;
        }
        return vehicleList.get(0);
    }

    public List<Vehicle> getByUserName(String userName) {
        String sql = "SELECT * FROM vehicle WHERE user_name = :user_name";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("user_name", userName);

        List<Vehicle> vehicleList = namedTemplate.query(sql, parameterSource, VEHICLE_ROW_MAPPER);
        return vehicleList;
    }


    private static final RowMapper<Vehicle> VEHICLE_ROW_MAPPER = new RowMapper<Vehicle>() {
        public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Vehicle.newInstance()
                    .withId(UUID.fromString(rs.getString("id")))
                    .withUserName(rs.getString("user_name"))
                    .withBrand(rs.getString("brand"))
                    .withMark(rs.getString("mark"))
                    .withModel(rs.getString("model"))
                    .withNumber(rs.getString("number"))
                    .withEngine(rs.getString("engine"))
                    .withDoorNum(rs.getInt("door_num"))
                    .withSeatNum(rs.getInt("seat_num"))
                    .withCreateTime(rs.getTimestamp("create_time"))
                    .withModifyTime(rs.getTimestamp("modify_time"));
        }
    };

}
