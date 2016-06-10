package com.hyrax.backend.entity;

import com.hyrax.backend.entity.state.EngineState;
import com.hyrax.backend.entity.state.HeadlightState;
import com.hyrax.backend.entity.state.TransmissionState;

import java.sql.Timestamp;
import java.util.UUID;

public class VehicleStatus {

    private UUID id;
    private String userName;
    private int mileage;
    private int gasoline;
    private EngineState engineState;
    private TransmissionState transmissionState;
    private HeadlightState headlightState;
    private Timestamp createTime;
    private Timestamp modifyTime;
    private int lastMileage;
    private int engineOil;
    private int cleanFluid;

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

    public VehicleStatus withId(UUID id) {
        this.setId(id);
        return this;
    }

    public VehicleStatus withUserName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public VehicleStatus withMileage(int mileage) {
        this.setMileage(mileage);
        return this;
    }

    public VehicleStatus withGasoline(int gasoline) {
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

    public VehicleStatus withLastMileage(int lastMileage) {
        this.setLastMileage(lastMileage);
        return this;
    }

    public VehicleStatus withEngineOil(int engineOil) {
        this.setEngineOil(engineOil);
        return this;
    }

    public VehicleStatus withCleanFluid(int cleanFluid) {
        this.setCleanFluid(cleanFluid);
        return this;
    }

}
