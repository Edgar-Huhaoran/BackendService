package com.hyrax.backend.dao;

import com.hyrax.backend.entity.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationDAO {

    int save(Notification notification);

    Notification get(UUID id);

    List<Notification> getByVehicleId(UUID vehicleId);

    List<Notification> getByUserName(String userName);

    List<Notification> getAll();

    int update(Notification notification);

    int delete(UUID id);

}
