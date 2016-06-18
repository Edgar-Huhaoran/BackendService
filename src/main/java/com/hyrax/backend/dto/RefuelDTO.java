package com.hyrax.backend.dto;

import java.sql.Timestamp;
import java.util.UUID;

import com.hyrax.backend.entity.AmountType;
import com.hyrax.backend.entity.Refuel;
import com.hyrax.backend.entity.state.RefuelState;

public class RefuelDTO {

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

    public static RefuelDTO fromRefuel(Refuel refuel) {
        RefuelDTO refuelDTO = new RefuelDTO();
        refuelDTO.setId(refuel.getId());
        refuelDTO.setUserName(refuel.getUserName());
        refuelDTO.setVehicleNumber(refuel.getVehicleNumber());
        refuelDTO.setFuelType(refuel.getFuelType());
        refuelDTO.setPrice(refuel.getPrice());
        refuelDTO.setAmount(refuel.getAmount());
        refuelDTO.setAmountType(refuel.getAmountType());
        refuelDTO.setStationId(refuel.getStationId());
        refuelDTO.setStationName(refuel.getStationName());
        refuelDTO.setState(refuel.getState());
        refuelDTO.setAppointTime(refuel.getAppointTime());
        return refuelDTO;
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

}
