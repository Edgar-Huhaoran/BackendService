package com.hyrax.backend.dao;

import com.hyrax.backend.entity.Vehicle;

import java.util.List;
import java.util.UUID;

public interface VehicleDAO {

    int save(Vehicle vehicle);

    Vehicle get(UUID id);

    List<Vehicle> getByUserName(String userName);

}
