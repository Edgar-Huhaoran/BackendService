package com.hyrax.backend.entity;

import com.hyrax.backend.entity.state.EngineState;
import com.hyrax.backend.entity.state.HeadlightState;
import com.hyrax.backend.entity.state.TransmissionState;

import java.sql.Timestamp;
import java.util.UUID;

public class VehicleStatus {

    private UUID id;
    private String userName;
    private float mileage;
    private float gasoline;
    private EngineState engineState;
    private TransmissionState transmissionState;
    private HeadlightState headlightState;
    private Timestamp createTime;
    private Timestamp modifyTime;

    public static VehicleStatus newInstance() {
        return new VehicleStatus();
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public VehicleStatus withId(UUID id) {
        this.setId(id);
        return this;
    }

    public VehicleStatus withUserName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public VehicleStatus withMileage(float mileage) {
        this.setMileage(mileage);
        return this;
    }

    public VehicleStatus withGasoline(float gasoline) {
        this.setGasoline(gasoline);
        return this;
    }

    public VehicleStatus withEngineState(EngineState engineState) {
        this.setEngineState(engineState);
        return this;
    }

    public VehicleStatus withTransmissionState(TransmissionState transmissionState) {
        this.setTransmissionState(transmissionState);
        return this;
    }

    public VehicleStatus withHeadlightState(HeadlightState headlightState) {
        this.setHeadlightState(headlightState);
        return this;
    }

    public VehicleStatus withCreateTime(Timestamp createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public VehicleStatus withModifyTime(Timestamp modifyTime) {
        this.setModifyTime(modifyTime);
        return this;
    }

}
