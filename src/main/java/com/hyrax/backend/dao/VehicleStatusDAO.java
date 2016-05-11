package com.hyrax.backend.dao;

import com.hyrax.backend.entity.VehicleStatus;

import java.util.List;
import java.util.UUID;

public interface VehicleStatusDAO {

    VehicleStatus get(UUID id);

    List<VehicleStatus> getByUserName(String userName);
}
