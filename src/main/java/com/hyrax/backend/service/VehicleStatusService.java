package com.hyrax.backend.service;

import com.hyrax.backend.credential.UserContextHolder;
import com.hyrax.backend.dao.VehicleStatusDAO;
import com.hyrax.backend.entity.VehicleStatus;
import com.hyrax.backend.exception.ErrorType;
import com.hyrax.backend.exception.HyraxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VehicleStatusService {

    private static final Logger log = LoggerFactory.getLogger(VehicleStatusService.class);

    private final VehicleStatusDAO vehicleStatusDAO;

    @Autowired
    public VehicleStatusService(VehicleStatusDAO vehicleStatusDAO) {
        this.vehicleStatusDAO = vehicleStatusDAO;
    }

    public void create(VehicleStatus vehicleStatus) {
        vehicleStatusDAO.save(vehicleStatus);
    }

    public VehicleStatus getVehicleStatus(UUID id) {
        if (id == null) {
            throw new HyraxException(ErrorType.ID_NULL);
        }

        VehicleStatus vehicleStatus = vehicleStatusDAO.get(id);
        String userName = UserContextHolder.getUserName();
        if (!userName.equals(vehicleStatus.getUserName())) {
            throw new HyraxException(ErrorType.NO_PERMISSION);
        }
        return vehicleStatus;
    }

    public List<VehicleStatus> getVehiclesStatus() {
        String userName = UserContextHolder.getUserName();
        List<VehicleStatus> vehicleStatusList = vehicleStatusDAO.getByUserName(userName);
        return vehicleStatusList;
    }

    public void checkVehicleStatus() {


    }

}
