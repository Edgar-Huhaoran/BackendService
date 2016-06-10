package com.hyrax.backend.service;

import com.hyrax.backend.credential.UserContextHolder;
import com.hyrax.backend.dao.VehicleDAO;
import com.hyrax.backend.dao.VehicleStatusDAO;
import com.hyrax.backend.dto.VehicleStatusDTO;
import com.hyrax.backend.entity.Notification;
import com.hyrax.backend.entity.NotificationType;
import com.hyrax.backend.entity.Vehicle;
import com.hyrax.backend.entity.VehicleStatus;
import com.hyrax.backend.entity.state.EngineState;
import com.hyrax.backend.entity.state.HeadlightState;
import com.hyrax.backend.entity.state.TransmissionState;
import com.hyrax.backend.exception.ErrorType;
import com.hyrax.backend.exception.HyraxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VehicleStatusService {

    private static final Logger log = LoggerFactory.getLogger(VehicleStatusService.class);

    private final VehicleDAO vehicleDAO;
    private final VehicleStatusDAO vehicleStatusDAO;
    private final NotificationService notificationService;

    @Autowired
    public VehicleStatusService(VehicleDAO vehicleDAO,
                                VehicleStatusDAO vehicleStatusDAO,
                                NotificationService notificationService) {
        this.vehicleDAO = vehicleDAO;
        this.vehicleStatusDAO = vehicleStatusDAO;
        this.notificationService = notificationService;
    }

    /**
     * 创建一个汽车状态的记录
     * @param vehicleStatus
     */
    public void create(VehicleStatus vehicleStatus) {
        vehicleStatusDAO.save(vehicleStatus);
    }

    /**
     * 更新汽车状态
     * @param vehicleStatus
     */
    public void update(VehicleStatus vehicleStatus) {
        vehicleStatusDAO.update(vehicleStatus);
    }

    /**
     * 通过ID删除一个汽车状态的记录
     * @param id 被删除状态的汽车ID
     * @return
     */
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

    /**
     * 根据汽车的ID获取汽车状态数据
     * @param id 被获取状态的汽车ID
     * @return
     */
    public VehicleStatusDTO getVehicleStatus(UUID id) {
        if (id == null) {
            throw new HyraxException(ErrorType.ID_NULL);
        }

        VehicleStatus vehicleStatus = vehicleStatusDAO.get(id);
        String userName = UserContextHolder.getUserName();
        if (!userName.equals(vehicleStatus.getUserName())) {
            throw new HyraxException(ErrorType.NO_PERMISSION);
        }

        VehicleStatusDTO vehicleStatusDTO = VehicleStatusDTO.fromVehicleStatus(vehicleStatus);
        int maintainCycle = vehicleDAO.get(id).getMaintainCycle();
        return VehicleStatusDTO.generateMessage(vehicleStatusDTO, maintainCycle);
    }

    /**
     * 获取当前用户的所有汽车状态数据
     * @return
     */
    public List<VehicleStatusDTO> getVehiclesStatus() {
        String userName = UserContextHolder.getUserName();
        List<VehicleStatus> vehicleStatusList = vehicleStatusDAO.getByUserName(userName);
        List<VehicleStatusDTO> vehicleStatusDTOList = new ArrayList<>();

        for (VehicleStatus vehicleStatus : vehicleStatusList) {
            VehicleStatusDTO vehicleStatusDTO = VehicleStatusDTO.fromVehicleStatus(vehicleStatus);
            int maintainCycle = vehicleDAO.get(vehicleStatus.getId()).getMaintainCycle();
            vehicleStatusDTOList.add(VehicleStatusDTO.generateMessage(vehicleStatusDTO, maintainCycle));
        }
        return vehicleStatusDTOList;
    }


    // =================================== 检测汽车状态的方法 ================================== //
    /**
     * 检查汽车状态
     */
    public void check() {
        List<VehicleStatus> statusList = vehicleStatusDAO.getAll();
        for (VehicleStatus status : statusList) {
            checkGasoline(status);
            checkMileage(status);
            checkEquipment(status);
        }
    }

    /**
     * 检查汽车油量状态
     * @param status
     */
    private void checkGasoline(VehicleStatus status) {
        UUID vehicleId = status.getId();
        String userName = status.getUserName();
        int gasoline = status.getGasoline();

        boolean isNotifyExist = notificationService.isExist(vehicleId, NotificationType.FUEL_UNDER);
        if (gasoline < 20 && !isNotifyExist) {
            log.info("notify user {} with notification {} ", userName, NotificationType.FUEL_UNDER);
            notificationService.push(userName);
            String[] messages = NotificationType.FUEL_UNDER.getMessages();
            String message = messages[0];
            notificationService.create(vehicleId, userName, NotificationType.FUEL_UNDER, message);
        } else if (gasoline >= 20 && isNotifyExist) {
            log.info("delete notification {} for user {}", NotificationType.FUEL_UNDER, userName);
            notificationService.delete(vehicleId, NotificationType.FUEL_UNDER);
        }
    }

    /**
     * 检查汽车行驶里程
     * @param status
     */
    private void checkMileage(VehicleStatus status) {
        UUID vehicleId = status.getId();
        String userName = status.getUserName();
        int mileage = status.getMileage();

        int currentLevel = (int)mileage / 15000;
        clearMileageNotification(vehicleId, userName, currentLevel);
        if (currentLevel == 0) {
            return;
        }

        boolean isNotifyExist = notificationService.isExist(vehicleId, NotificationType.MILEAGE_ACHIEVE, currentLevel);
        if (!isNotifyExist) {
            log.info("notify user {} with notification {} level {} ", userName, NotificationType.MILEAGE_ACHIEVE, currentLevel);
            notificationService.push(userName);
            String[] messages = NotificationType.MILEAGE_ACHIEVE.getMessages();
            String message = messages[0] + currentLevel * 15000 + messages[1];
            notificationService.create(vehicleId, userName, NotificationType.MILEAGE_ACHIEVE, currentLevel, message);
        }

    }

    /**
     * 删除超过当前里程的通知
     * @param vehicleId
     * @param userName
     * @param currentLevel
     */
    private void clearMileageNotification(UUID vehicleId, String userName, int currentLevel) {
        List<Notification> notificationList = notificationService.get(vehicleId, NotificationType.MILEAGE_ACHIEVE);
        for (Notification notification : notificationList) {
            int notificationLevel = Integer.valueOf(notification.getDescription());
            if (notificationLevel > currentLevel) {
                log.info("delete notification {} level {} for user {}", NotificationType.MILEAGE_ACHIEVE,
                         notification.getDescription(), userName);
                notificationService.delete(vehicleId, NotificationType.MILEAGE_ACHIEVE, notificationLevel);
            }
        }
    }

    /**
     * 检查汽车设备状态
     * @param status
     */
    private void checkEquipment(VehicleStatus status) {
        UUID vehicleId = status.getId();
        String userName = status.getUserName();

        EngineState engineState = status.getEngineState();
        checkEngineState(vehicleId, userName, engineState);

        TransmissionState transmissionState = status.getTransmissionState();
        checkTransmissionState(vehicleId, userName, transmissionState);

        HeadlightState headlightState = status.getHeadlightState();
        checkHeadlightState(vehicleId, userName, headlightState);
    }

    /**
     * 检测引擎测状态
     * @param vehicleId
     * @param userName
     * @param engineState
     */
    private void checkEngineState(UUID vehicleId, String userName, EngineState engineState) {
        boolean isNotifyExist = notificationService.isExist(vehicleId, NotificationType.ENGINE_ABNORMAL);
        if (EngineState.ABNORMAL.equals(engineState) && !isNotifyExist) {
            log.info("notify user {} with notification {} ", userName, NotificationType.ENGINE_ABNORMAL);
            notificationService.push(userName);
            String[] messages = NotificationType.ENGINE_ABNORMAL.getMessages();
            String message = messages[0];
            notificationService.create(vehicleId, userName, NotificationType.ENGINE_ABNORMAL, message);
        } else if (!EngineState.ABNORMAL.equals(engineState) && isNotifyExist) {
            log.info("delete notification {} for user {}", NotificationType.ENGINE_ABNORMAL, userName);
            notificationService.delete(vehicleId, NotificationType.ENGINE_ABNORMAL);
        }
    }

    /**
     * 检测转换器的状态
     * @param vehicleId
     * @param userName
     * @param transmissionState
     */
    private void checkTransmissionState(UUID vehicleId, String userName, TransmissionState transmissionState) {
        boolean isNotifyExist = notificationService.isExist(vehicleId, NotificationType.TRANSMISSION_ABNORMAL);
        if (TransmissionState.ABNORMAL.equals(transmissionState) && !isNotifyExist) {
            log.info("notify user {} with notification {} ", userName, NotificationType.TRANSMISSION_ABNORMAL);
            notificationService.push(userName);
            String[] messages = NotificationType.TRANSMISSION_ABNORMAL.getMessages();
            String message = messages[0];
            notificationService.create(vehicleId, userName, NotificationType.TRANSMISSION_ABNORMAL, message);
        } else if (!TransmissionState.ABNORMAL.equals(transmissionState) && isNotifyExist) {
            log.info("delete notification {} for user {}", NotificationType.TRANSMISSION_ABNORMAL, userName);
            notificationService.delete(vehicleId, NotificationType.TRANSMISSION_ABNORMAL);
        }
    }

    /**
     * 检测车灯的状态
     * @param vehicleId
     * @param userName
     * @param headlightState
     */
    private void checkHeadlightState(UUID vehicleId, String userName, HeadlightState headlightState) {
        boolean isNotifyExist = notificationService.isExist(vehicleId, NotificationType.HEADLIGHT_ABNORMAL);
        if (HeadlightState.ABNORMAL.equals(headlightState) && !isNotifyExist) {
            log.info("notify user {} with notification {} ", userName, NotificationType.HEADLIGHT_ABNORMAL);
            notificationService.push(userName);
            String[] messages = NotificationType.HEADLIGHT_ABNORMAL.getMessages();
            String message = messages[0];
            notificationService.create(vehicleId, userName, NotificationType.HEADLIGHT_ABNORMAL, message);
        } else if (!HeadlightState.ABNORMAL.equals(headlightState) && isNotifyExist) {
            log.info("delete notification {} for user {}", NotificationType.HEADLIGHT_ABNORMAL, userName);
            notificationService.delete(vehicleId, NotificationType.HEADLIGHT_ABNORMAL);
        }
    }

}
