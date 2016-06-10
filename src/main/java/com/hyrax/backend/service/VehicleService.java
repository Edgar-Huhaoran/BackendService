package com.hyrax.backend.service;

import com.hyrax.backend.credential.UserContextHolder;
import com.hyrax.backend.dao.VehicleDAO;
import com.hyrax.backend.dto.VehicleDTO;
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

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class VehicleService {

    private static final Logger log = LoggerFactory.getLogger(VehicleService.class);

    private final VehicleDAO vehicleDAO;
    private final VehicleStatusService vehicleStatusService;
    private final NotificationService notificationService;
    private final MarkService markService;

    @Autowired
    public VehicleService(VehicleDAO vehicleDAO,
                          VehicleStatusService vehicleStatusService,
                          NotificationService notificationService,
                          MarkService markService) {
        this.vehicleDAO = vehicleDAO;
        this.vehicleStatusService = vehicleStatusService;
        this.notificationService = notificationService;
        this.markService = markService;
    }

    /**
     * 在当前的用户下添加一辆汽车
     * @param vehicleDTO 汽车数据
     * @return 新增汽车记录的ID
     */
    public UUID createVehicle(VehicleDTO vehicleDTO) {
        assertValid(vehicleDTO);

        UUID id = UUID.randomUUID();
        String userName = UserContextHolder.getUserName();

        String brand = vehicleDTO.getBrand();
        String markUrl = markService.getUrl(brand);
        Vehicle vehicle = Vehicle.newInstance()
                .withId(id)
                .withUserName(userName)
                .withBrand(brand)
                .withMark(markUrl)
                .withModel(vehicleDTO.getModel())
                .withNumber(vehicleDTO.getNumber())
                .withEngine(vehicleDTO.getEngine())
                .withDoorNum(vehicleDTO.getDoorNum())
                .withSeatNum(vehicleDTO.getSeatNum())
                .withCreateTime(new Timestamp(System.currentTimeMillis()))
                .withModifyTime(new Timestamp(System.currentTimeMillis()))
                .withGasCapacity(vehicleDTO.getGasCapacity())
                .withMaintainCycle(vehicleDTO.getMaintainCycle())
                .withCarFrame(vehicleDTO.getCarFrame());
        vehicleDAO.save(vehicle);

        createVehicleStatus(id, userName);
        return id;
    }

    /**
     * 获取当前用户的所有汽车数据
     * @return
     */
    public List<Vehicle> getVehicles() {
        String userName = UserContextHolder.getUserName();
        return vehicleDAO.getByUserName(userName);
    }

    /**
     * 根据汽车的ID获取汽车的数据
     * @param id 汽车的ID
     * @return 被查询的汽车数据
     */
    public Vehicle getVehicle(UUID id) {
        String userName = UserContextHolder.getUserName();
        List<Vehicle> vehicleList = vehicleDAO.getByUserName(userName);
        if (vehicleList != null) {
            for (Vehicle vehicle : vehicleList) {
                if (id.equals(vehicle.getId())) {
                    return vehicle;
                }
            }
        }

        log.warn("no permission or can not find vehicle");
        throw new HyraxException(ErrorType.RESOURCE_NOT_FOUND);
    }

    /**
     * 删除指定ID的汽车数据
     * @param id
     * @return
     */
    public int deleteVehicle(UUID id) {
        if (id == null) {
            throw new HyraxException(ErrorType.ID_NULL);
        }

        String userName = UserContextHolder.getUserName();
        List<Vehicle> vehicleList = vehicleDAO.getByUserName(userName);  //必须是属于当前用户的汽车才允许删除
        for (Vehicle vehicle : vehicleList) {
            if (id.equals(vehicle.getId())) {  // 先删除通知,再删除状态,最后删除汽车数据
                for (NotificationType type : NotificationType.values()) {
                    notificationService.delete(id, type);
                }
                vehicleStatusService.delete(id);
                return vehicleDAO.delete(id);
            }
        }

        return 0;
    }

    private void assertValid(VehicleDTO vehicleDTO) {
        if(vehicleDTO.getBrand() == null || vehicleDTO.getBrand().isEmpty() ||
                vehicleDTO.getModel() == null || vehicleDTO.getModel().isEmpty() ||
                vehicleDTO.getNumber() == null || vehicleDTO.getNumber().isEmpty() ||
                vehicleDTO.getEngine() == null || vehicleDTO.getEngine().isEmpty() ||
                vehicleDTO.getDoorNum() == 0 || vehicleDTO.getSeatNum() == 0) {
            throw new HyraxException(ErrorType.VEHICLE_INFO_INVALID);
        }
    }

    private void createVehicleStatus(UUID vehicleId, String userName) {
        VehicleStatus vehicleStatus = VehicleStatus.newInstance()
                .withId(vehicleId)
                .withUserName(userName)
                .withMileage(1)
                .withGasoline(100)
                .withEngineState(EngineState.GREAT)
                .withTransmissionState(TransmissionState.GREAT)
                .withHeadlightState(HeadlightState.GREAT)
                .withCreateTime(new Timestamp(System.currentTimeMillis()))
                .withModifyTime(new Timestamp(System.currentTimeMillis()))
                .withLastMileage(1)
                .withEngineOil(100)
                .withCleanFluid(100);
        vehicleStatusService.create(vehicleStatus);
    }

}
