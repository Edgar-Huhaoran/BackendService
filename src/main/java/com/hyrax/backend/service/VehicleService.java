package com.hyrax.backend.service;

import com.hyrax.backend.credential.UserContextHolder;
import com.hyrax.backend.dao.VehicleDAO;
import com.hyrax.backend.dao.VehicleStatusDAO;
import com.hyrax.backend.dto.VehicleDTO;
import com.hyrax.backend.entity.Vehicle;
import com.hyrax.backend.entity.VehicleStatus;
import com.hyrax.backend.exception.ErrorType;
import com.hyrax.backend.exception.HyraxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class VehicleService {

    private static final Logger log = LoggerFactory.getLogger(VehicleService.class);

    private final VehicleDAO vehicleDAO;
    private final VehicleStatusDAO vehicleStatusDAO;

    @Autowired
    public VehicleService(VehicleDAO vehicleDAO, VehicleStatusDAO vehicleStatusDAO) {
        this.vehicleDAO = vehicleDAO;
        this.vehicleStatusDAO = vehicleStatusDAO;
    }

    public UUID createVehicle(VehicleDTO vehicleDTO) {
        assertValid(vehicleDTO);

        UUID id = UUID.randomUUID();
        String userName = UserContextHolder.getUserName();
        Vehicle vehicle = Vehicle.newInstance()
                .withId(id)
                .withUserName(userName)
                .withBrand(vehicleDTO.getBrand())
                .withMark(vehicleDTO.getMark())
                .withModel(vehicleDTO.getModel())
                .withNumber(vehicleDTO.getNumber())
                .withEngine(vehicleDTO.getEngine())
                .withDoorNum(vehicleDTO.getDoorNum())
                .withSeatNum(vehicleDTO.getSeatNum())
                .withCreateTime(new Timestamp(System.currentTimeMillis()))
                .withModifyTime(new Timestamp(System.currentTimeMillis()));
        vehicleDAO.save(vehicle);
        return id;
    }

    public List<Vehicle> getVehicles() {
        String userName = UserContextHolder.getUserName();
        return vehicleDAO.getByUserName(userName);
    }

    public VehicleStatus getVehicleStatus(UUID id) {
        if (id == null) {
            throw new HyraxException(ErrorType.ID_NULL);
        }

        String userName = UserContextHolder.getUserName();
        List<VehicleStatus> vehicleStatusList = vehicleStatusDAO.getByUserName(userName);
        for (VehicleStatus vehicleStatus : vehicleStatusList) {
            if (id.equals(vehicleStatus.getId())) {
                return vehicleStatus;
            }
        }

        return null;
    }

    public int deleteVehicle(UUID id) {
        if (id == null) {
            throw new HyraxException(ErrorType.ID_NULL);
        }

        String userName = UserContextHolder.getUserName();
        List<Vehicle> vehicleList = vehicleDAO.getByUserName(userName);
        for (Vehicle vehicle : vehicleList) {
            if (id.equals(vehicle.getId())) {
                return vehicleDAO.delete(id);
            }
        }

        return 0;
    }

    private void assertValid(VehicleDTO vehicleDTO) {
        if(vehicleDTO.getBrand() == null || vehicleDTO.getBrand().isEmpty() ||
                vehicleDTO.getMark() == null || vehicleDTO.getMark().isEmpty() ||
                vehicleDTO.getModel() == null || vehicleDTO.getModel().isEmpty() ||
                vehicleDTO.getNumber() == null || vehicleDTO.getNumber().isEmpty() ||
                vehicleDTO.getEngine() == null || vehicleDTO.getEngine().isEmpty() ||
                vehicleDTO.getDoorNum() == 0 || vehicleDTO.getSeatNum() == 0) {
            throw new HyraxException(ErrorType.VEHICLE_INFO_INVALID);
        }
    }
}
