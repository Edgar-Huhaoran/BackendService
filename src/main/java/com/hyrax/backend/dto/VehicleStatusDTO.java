package com.hyrax.backend.dto;

import java.util.UUID;

import com.hyrax.backend.entity.VehicleStatus;
import com.hyrax.backend.entity.state.EngineState;
import com.hyrax.backend.entity.state.HeadlightState;
import com.hyrax.backend.entity.state.TransmissionState;

public class VehicleStatusDTO {

    private UUID id;
    private String userName;
    private int mileage;
    private int gasoline;
    private EngineState engineState;
    private TransmissionState transmissionState;
    private HeadlightState headlightState;
    private int lastMileage;
    private int engineOil;
    private int cleanFluid;
    private String message;

    public static VehicleStatusDTO newInstance() {
        return new VehicleStatusDTO();
    }

    public static VehicleStatusDTO fromVehicleStatus(VehicleStatus vehicleStatus) {
        return VehicleStatusDTO.newInstance()
                .withId(vehicleStatus.getId())
                .withUserName(vehicleStatus.getUserName())
                .withMileage(vehicleStatus.getMileage())
                .withGasoline(vehicleStatus.getGasoline())
                .withEngineState(vehicleStatus.getEngineState())
                .withTransmissionState(vehicleStatus.getTransmissionState())
                .withHeadlightState(vehicleStatus.getHeadlightState())
                .withLastMileage(vehicleStatus.getLastMileage())
                .withEngineOil(vehicleStatus.getEngineOil())
                .withCleanFluid(vehicleStatus.getCleanFluid());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getGasoline() {
        return gasoline;
    }

    public void setGasoline(int gasoline) {
        this.gasoline = gasoline;
    }

    public EngineState getEngineState() {
        return engineState;
    }

    public void setEngineState(EngineState engineState) {
        this.engineState = engineState;
    }

    public TransmissionState getTransmissionState() {
        return transmissionState;
    }

    public void setTransmissionState(TransmissionState transmissionState) {
        this.transmissionState = transmissionState;
    }

    public HeadlightState getHeadlightState() {
        return headlightState;
    }

    public void setHeadlightState(HeadlightState headlightState) {
        this.headlightState = headlightState;
    }

    public int getLastMileage() {
        return lastMileage;
    }

    public void setLastMileage(int lastMileage) {
        this.lastMileage = lastMileage;
    }

    public int getEngineOil() {
        return engineOil;
    }

    public void setEngineOil(int engineOil) {
        this.engineOil = engineOil;
    }

    public int getCleanFluid() {
        return cleanFluid;
    }

    public void setCleanFluid(int cleanFluid) {
        this.cleanFluid = cleanFluid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public VehicleStatusDTO withId(UUID id) {
        this.setId(id);
        return this;
    }

    public VehicleStatusDTO withUserName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public VehicleStatusDTO withMileage(int mileage) {
        this.setMileage(mileage);
        return this;
    }

    public VehicleStatusDTO withGasoline(int gasoline) {
        this.setGasoline(gasoline);
        return this;
    }

    public VehicleStatusDTO withEngineState(EngineState engineState) {
        this.setEngineState(engineState);
        return this;
    }

    public VehicleStatusDTO withTransmissionState(TransmissionState transmissionState) {
        this.setTransmissionState(transmissionState);
        return this;
    }

    public VehicleStatusDTO withHeadlightState(HeadlightState headlightState) {
        this.setHeadlightState(headlightState);
        return this;
    }

    public VehicleStatusDTO withLastMileage(int lastMileage) {
        this.setLastMileage(lastMileage);
        return this;
    }

    public VehicleStatusDTO withEngineOil(int engineOil) {
        this.setEngineOil(engineOil);
        return this;
    }

    public VehicleStatusDTO withCleanFluid(int cleanFluid) {
        this.setCleanFluid(cleanFluid);
        return this;
    }

    public VehicleStatusDTO withMessage(String message) {
        this.setMessage(message);
        return this;
    }

    public static VehicleStatusDTO generateMessage(VehicleStatusDTO vehicleStatusDTO, int maintainCycle) {
        String message;
        if (EngineState.ABNORMAL.equals(vehicleStatusDTO.getEngineState())) {
            message = "发动机异常,快去修理吧 =￣ω￣= ";
        } else if (TransmissionState.ABNORMAL.equals(vehicleStatusDTO.getTransmissionState())) {
            message = "变速器好像有点问题哎~";
        } else if (HeadlightState.ABNORMAL.equals(vehicleStatusDTO.getHeadlightState())) {
            message = "呀,车灯坏了...";
        } else if (vehicleStatusDTO.getGasoline() < 20) {
            message = "油量低于20% /(ㄒ_ㄒ)/~~";
        } else if (vehicleStatusDTO.getMileage() - vehicleStatusDTO.getLastMileage() > maintainCycle) {
            message = "是时候来一次保养了";
        } else if (vehicleStatusDTO.getEngineOil() < 20) {
            message = "机油低于20%";
        } else if (vehicleStatusDTO.getCleanFluid() < 20) {
            message = "清洁液低于20%";
        } else {
            message = "状态不错哦~ O(∩_∩)O~~";
        }

        vehicleStatusDTO.setMessage(message);
        return vehicleStatusDTO;
    }
}
