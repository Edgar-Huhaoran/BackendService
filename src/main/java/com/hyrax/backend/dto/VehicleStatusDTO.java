package com.hyrax.backend.dto;

import java.util.UUID;

import com.hyrax.backend.entity.VehicleStatus;
import com.hyrax.backend.entity.state.EngineState;
import com.hyrax.backend.entity.state.HeadlightState;
import com.hyrax.backend.entity.state.TransmissionState;

public class VehicleStatusDTO {

    private UUID id;
    private String userName;
    private float mileage;
    private float gasoline;
    private EngineState engineState;
    private TransmissionState transmissionState;
    private HeadlightState headlightState;
    private float lastMileage;
    private float engineOil;
    private float cleanFluid;
    private String message;

    public static VehicleStatusDTO newInstance() {
        return new VehicleStatusDTO();
    }

    public static VehicleStatusDTO fromVehicleStatus(VehicleStatus vehicleStatus) {
        VehicleStatusDTO vehicleStatusDTO = VehicleStatusDTO.newInstance()
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
        return generateMessage(vehicleStatusDTO);
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

    public float getMileage() {
        return mileage;
    }

    public void setMileage(float mileage) {
        this.mileage = mileage;
    }

    public float getGasoline() {
        return gasoline;
    }

    public void setGasoline(float gasoline) {
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

    public float getLastMileage() {
        return lastMileage;
    }

    public void setLastMileage(float lastMileage) {
        this.lastMileage = lastMileage;
    }

    public float getEngineOil() {
        return engineOil;
    }

    public void setEngineOil(float engineOil) {
        this.engineOil = engineOil;
    }

    public float getCleanFluid() {
        return cleanFluid;
    }

    public void setCleanFluid(float cleanFluid) {
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

    public VehicleStatusDTO withMileage(float mileage) {
        this.setMileage(mileage);
        return this;
    }

    public VehicleStatusDTO withGasoline(float gasoline) {
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

    public VehicleStatusDTO withLastMileage(float lastMileage) {
        this.setLastMileage(lastMileage);
        return this;
    }

    public VehicleStatusDTO withEngineOil(float engineOil) {
        this.setEngineOil(engineOil);
        return this;
    }

    public VehicleStatusDTO withCleanFluid(float cleanFluid) {
        this.setCleanFluid(cleanFluid);
        return this;
    }

    public VehicleStatusDTO withMessage(String message) {
        this.setMessage(message);
        return this;
    }

    public static VehicleStatusDTO generateMessage(VehicleStatusDTO vehicleStatusDTO) {
        String message;
        if (EngineState.ABNORMAL.equals(vehicleStatusDTO.getEngineState())) {
            message = "发动机异常,快去修理吧 =￣ω￣= ";
        } else if (TransmissionState.ABNORMAL.equals(vehicleStatusDTO.getTransmissionState())) {
            message = "变速器好像有点问题哎~";
        } else if (HeadlightState.ABNORMAL.equals(vehicleStatusDTO.getHeadlightState())) {
            message = "呀,车灯坏了...";
        } else if (vehicleStatusDTO.getGasoline() < 20.0f) {
            message = "油量低于20% /(ㄒ_ㄒ)/~~";
        } else if (vehicleStatusDTO.getMileage() - vehicleStatusDTO.getLastMileage() > 15000) {
            message = "是时候该来一次保养了";
        } else if (vehicleStatusDTO.getEngineOil() < 20.0f) {
            message = "机油低于20%";
        } else if (vehicleStatusDTO.getCleanFluid() < 20.0f) {
            message = "清洁液低于20%";
        } else {
            message = "状态不错哦~ O(∩_∩)O~~";
        }

        vehicleStatusDTO.setMessage(message);
        return vehicleStatusDTO;
    }
}
