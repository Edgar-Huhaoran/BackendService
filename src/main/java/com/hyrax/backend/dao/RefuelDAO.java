package com.hyrax.backend.dao;

import com.hyrax.backend.entity.Refuel;

import java.util.List;
import java.util.UUID;

public interface RefuelDAO {

    int save(Refuel refuel);

    int update(Refuel refuel);

    Refuel get(UUID id);

    List<Refuel> getByUserName(String userName);

}
