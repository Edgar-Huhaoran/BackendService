package com.hyrax.backend.dao.jdbc;

import com.hyrax.backend.dao.RefuelDAO;
import com.hyrax.backend.entity.Refuel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRefuelDAO implements RefuelDAO {

    private final NamedParameterJdbcTemplate namedTemplate;

    @Autowired
    public JdbcRefuelDAO(NamedParameterJdbcTemplate namedTemplate) {
        this.namedTemplate = namedTemplate;
    }

    public int save(Refuel refuel) {
        String sql = "INSERT INTO refuel(id, user_name, owner_name, from_time, to_time, station_id, station_name, " +
                "fuel_type, litre, cost, state, create_time, modify_time) " +
                "VALUES (:id, :user_name, :owner_name, :from_time, :to_time, :station_id, :station_name, " +
                ":fuel_type, :litre, :cost, :state, :create_time, :modify_time)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", refuel.getId())
                .addValue("user_name", refuel.getUserName())
                .addValue("owner_name", refuel.getOwnerName())
                .addValue("from_time", refuel.getFromTime())
                .addValue("to_time", refuel.getToTime())
                .addValue("station_id", refuel.getStationId())
                .addValue("station_name", refuel.getStationName())
                .addValue("refuel_type", refuel.getFuelType())
                .addValue("litre", refuel.getLitre())
                .addValue("cost", refuel.getCost())
                .addValue("state", refuel.getState())
                .addValue("create_time", refuel.getCreateTime())
                .addValue("modify_time", refuel.getModifyTime());

        return namedTemplate.update(sql, parameterSource);
    }

}
