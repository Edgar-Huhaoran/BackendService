package com.hyrax.backend.entity;

import com.hyrax.backend.entity.state.EngineState;
import com.hyrax.backend.entity.state.HeadlightState;
import com.hyrax.backend.entity.state.TransmissionState;

import java.util.UUID;

public class VehicleStatus {

    private UUID id;
    private String userName;
    private float mileage;
    private float gasoline;
    private EngineState engineState;
    private TransmissionState transmissionState;
    private HeadlightState headlightState;

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
}
