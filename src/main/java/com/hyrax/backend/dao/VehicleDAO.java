package com.hyrax.backend.dao;

import com.hyrax.backend.entity.Vehicle;

import java.util.List;
import java.util.UUID;

public interface VehicleDAO {

    int save(Vehicle vehicle);

    Vehicle get(UUID id);

    Vehicle getByVehicleNumber(String number);

    Vehicle getByVehicleNumber(String number, String userName);

    List<Vehicle> getByUserName(String userName);

    int delete(UUID id);

}
