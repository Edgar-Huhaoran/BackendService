package com.hyrax.backend.service;

import com.hyrax.backend.credential.UserContextHolder;
import com.hyrax.backend.dao.VehicleStatusDAO;
import com.hyrax.backend.entity.NotificationType;
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
    private final NotificationService notificationService;

    @Autowired
    public VehicleStatusService(VehicleStatusDAO vehicleStatusDAO, NotificationService notificationService) {
        this.vehicleStatusDAO = vehicleStatusDAO;
        this.notificationService = notificationService;
    }

    public void create(VehicleStatus vehicleStatus) {
        vehicleStatusDAO.save(vehicleStatus);
    }

    public int delete(UUID id) {
        if (id == null) {
            throw new HyraxException(ErrorType.ID_NULL);
        }

        String userName = UserContextHolder.getUserName();
        List<VehicleStatus> statusList = vehicleStatusDAO.getByUserName(userName);
        for (VehicleStatus status : statusList) {
            if (id.equals(status.getId())) {
                return vehicleStatusDAO.delete(id);
            }
        }

        return 0;
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

    public void check() {
        List<VehicleStatus> statusList = vehicleStatusDAO.getAll();
        for (VehicleStatus status : statusList) {
            checkGasoline(status);
            checkMileage(status);
            checkEquipment(status);
        }
    }

    private void checkGasoline(VehicleStatus status) {
        UUID vehicleId = status.getId();
        String userName = status.getUserName();
        float gasoline = status.getGasoline();

        boolean isNotifyExist = notificationService.isExist(vehicleId, NotificationType.FUEL_UNDER);
        if (gasoline < 20.0F && !isNotifyExist) {
            log.info("notify user {} with notification {} ", userName, NotificationType.FUEL_UNDER);
            notificationService.push(userName);
            String[] messages = NotificationType.FUEL_UNDER.getMessages();
            String message = messages[0];
            notificationService.create(vehicleId, userName, NotificationType.FUEL_UNDER, message);
        } else if (gasoline >= 20.0F && isNotifyExist){
            log.info("delete notification {} for user {}", NotificationType.FUEL_UNDER, userName);
            notificationService.delete(vehicleId, NotificationType.FUEL_UNDER);
        }
    }

    private void checkMileage(VehicleStatus status) {

    }

    private void checkEquipment(VehicleStatus status) {

    }



}
