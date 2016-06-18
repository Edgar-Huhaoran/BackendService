package com.hyrax.backend.entity;

import com.hyrax.backend.entity.state.RefuelState;

import java.sql.Timestamp;
import java.util.UUID;

public class Refuel {

    private UUID id;
    private String userName;
    private String vehicleNumber;
    private String fuelType;
    private double price;
    private double amount;
    private AmountType amountType;
    private String stationId;
    private String stationName;
    private RefuelState state;
    private Timestamp appointTime;
    private Timestamp createTime;
    private Timestamp modifyTime;

    public static Refuel newInstance() {
        return new Refuel();
    }



    // set and get method
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

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public AmountType getAmountType() {
        return amountType;
    }

    public void setAmountType(AmountType amountType) {
        this.amountType = amountType;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public RefuelState getState() {
        return state;
    }

    public void setState(RefuelState state) {
        this.state = state;
    }

    public Timestamp getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(Timestamp appointTime) {
        this.appointTime = appointTime;
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



    // with method
    public Refuel withId(UUID id) {
        this.setId(id);
        return this;
    }

    public Refuel withUserName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public Refuel withVehicleNumber(String vehicleNumber) {
        this.setVehicleNumber(vehicleNumber);
        return this;
    }

    public Refuel withFuelType(String refuelType) {
        this.setFuelType(refuelType);
        return this;
    }

    public Refuel withPrice(double price) {
        this.setPrice(price);
        return this;
    }

    public Refuel withAmount(double amount) {
        this.setAmount(amount);
        return this;
    }

    public Refuel withAmountType(AmountType amountType) {
        this.setAmountType(amountType);
        return this;
    }

    public Refuel withStationId(String stationId) {
        this.setStationId(stationId);
        return this;
    }

    public Refuel withStationName(String stationName) {
        this.setStationName(stationName);
        return this;
    }

    public Refuel withState(RefuelState state) {
        this.setState(state);
        return this;
    }

    public Refuel withAppointTime(Timestamp appointTime) {
        this.setAppointTime(appointTime);
        return this;
    }

    public Refuel withCreateTime(Timestamp createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public Refuel withModifyTime(Timestamp modifyTime) {
        this.setModifyTime(modifyTime);
        return this;
    }

}
